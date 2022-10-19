new Vue({
    el: '#main-container',
    data: function () {
        // console.log(this);//this是vue对象
        return {
            setting: {//ztree的配置对象
                data: {//ztree的数据配置对象
                    simpleData: {//设置简单节点数组配置
                        enable: true,   //默认false 设置true表示节点数组可以是一位结构
                        pIdKey: 'parentId'  //设置自定义的父id属性名
                    }/*,
                    key:{
                        title:'title'
                    }*/
                },
                check: {//设置checkbox配置  enable：true表示开启选项框
                    enable: true,
                    /*
                    * Y:控制选中的父和子checkbox关联
                    * N：控制取消选中的父和子checkbox关联
                    * p:关联父节点
                    * s:关联子节点
                    * */
                    chkboxType: {'Y': 'ps', 'N': 'ps'}
                },
                callback: {//回调事件处理
                    onClick: this.onClick
                },
                view: {//显示设置
                    fontCss: this.fontCss /*{color:'red'}*/
                }
            },
            nodes: [],//公司树节点数组
            name: '全部', //显示office名字
            name2: '全部', //显示office名字
            params: {//提交后台的参数对象
                id: '',
                dataScope: '',
                name: '',
                remarks: ''
            },
            input: '',//搜索框
            pageInfo: {
                current: 1,
                size: 5
            },
            up: false,//显示向上或向下的标记
            newRole: {
                resources: [],   //已授权资源
                offices: [],     //已授权公司
                odlResources: [],   //旧授权资源
                oldOffices: [],     //旧授权公司
                dataScope: '',
                name: '',
                officeId:''
            }
        }
    },
    methods: {
        /*
        * 1.获取需要提交的resources和offices
        * 2.role的新旧资源和授权公司判断，如果不一致则更新，resources和offices有值
        * 3.如果一致则不更新，resources和offices无值
        * 4.如果取消跨机构授权，offices无值
        * 5.不能将父节点提交到后台，需要移除
        * */
        save: function () {
            //处理resources，必须有值
            let flag = this.checkResources();
            if (!flag) {
                layer.msg("必须有授权资源");
                return;
            }
            //处理office
            this.checkOffices();
            axios({
                'url': '/manager/role/saveOrUpdate',
                'method': 'post',
                data: this.newRole
            }).then(response => {
                layer.msg(response.data.msg);
                this.newRole = {
                    resources: [],   //已授权资源
                    offices: [],     //已授权公司
                    odlResources: [],   //旧授权资源
                    oldOffices: [],     //旧授权公司
                    dataScope: '',
                    name: '',
                    officeId:''
                };
                this.name2 ='全部';
                // let index = parent.layer.getFrameIndex(window.name);
                // parent.layer.close(index);//关闭当前子窗口
            }).catch(e => {
                layer.msg(e.message);
            })
        },
        checkResources: function () {
            //获取被选中的resources集合
            let zTreeObj = $.fn.zTree.getZTreeObj("select-treetreeSelectResEdit");
            // console.log(zTreeObj.getNodes())
            let _resources = zTreeObj.getCheckedNodes();
            if (_resources.length === 0) {
                return false;//必须要有值
            }
            //不能将父节点提交到后台，需要移除
            if (_resources[0].id === 0) {
                _resources.shift();//移除数组中的一个元素  顶级父节点
            }
            //role的新旧资源判断，如果不一致则更新
            if (_resources.length !== this.newRole.oldResources) {//需要更新
                this.newRole.resources = _resources;
            } else {//长度一致  进一步比较两个数组元素是否一致  两个数组如果一致，元素位置一致
                for (let i in this.newRole.oldResources) {
                    if (this.newRole.oldResources[i].id !== _resources[i.id]) {
                        this.newRole.resources = _resources;
                        return true;
                    }
                }
                this.newRole.resources = null;//长度相同，元素页一致
            }
            // this.newRole.resources = _resources;
            return true;
        },
        checkOffices: function () {

            let treeObj = $.fn.zTree.getZTreeObj("select-treetreeSelectOfficeEdit");
            if (treeObj != undefined) {//有初始化公司树
                let _offices = treeObj.getCheckedNodes();
                if (_offices.length === 0) {
                    //公司树存在，dataScope为9  不更新  office为null
                    this.newRole.offices = null;
                    return;
                }
                //不能将父节点提交到后台，需要移除
                if (_offices[0].id === 0) {
                    _offices.shift();//移除数组中的一个元素  顶级父节点
                }
                //role的新旧授权机构判断，如果不一致则更新
                if (this.newRole.oldOffices == null || this.newRole.oldOffices.length == 0) {//原来dataScope不是9
                    this.newRole.offices = _offices;
                } else if (_offices.length !== this.newRole.oldOffices) {//需要更新
                    this.newRole.offices = _offices;
                } else {//长度一致  进一步比较两个数组元素是否一致  两个数组如果一致，元素位置一致
                    for (let i in this.newRole.oldOffices) {
                        if (this.newRole.oldOffices[i].id !== _offices[i.id]) {
                            this.newRole.offices = _offices;
                            break;
                        }
                    }
                    this.newRole.offices = null;//长度相同，元素也一致
                }
                // this.newRole.offices = _offices;
            } else {
                this.newRole.offices = null;//无初始化公司树    office为null
            }
        },
        /**
         * 根据角色的id查询已授权节点，设置选中，并创建树
         */
        selectResourcesByRid(nodes) {
            for (let i in nodes) {
                let _resources = this.newRole.resources;
                for (let j in _resources) {
                    if (_resources[j].id === nodes[i].id) {//在权限树找到对应节点，设置选中
                        nodes[i].checked = true;  //true则会被选中
                        break;
                    }
                }
            }
            //设置了节点选中后再初始化树
            let treeObj = $.fn.zTree.init($('#select-treetreeSelectResEdit'), this.setting, nodes);
        },
        selectOfficeByRid(nodes) {//初始化公司树
            for (let i in nodes) {
                let _offices = this.newRole.offices;
                for (let j in _offices) {
                    if (_offices[j].id === nodes[i].id) {//在权限树找到对应节点，设置选中
                        nodes[i].checked = true;  //true则会被选中
                        break;
                    }
                }
            }
            //设置了节点选中后再初始化树
            let treeObj = $.fn.zTree.init($('#select-treetreeSelectOfficeEdit'), this.setting, nodes);
            $("#treeSelectOfficeEdit").css("display", "inline-block");//显示公司树
        },
        //处理chose的change事件
        changeDataScope(e, param) {//e是事件对象   param: {selected:值}
            //给role的dataScope赋值
            this.newRole.dataScope = param.selected;
            //判断是其他改为明细
            if (this.newRole.dataScope === '9') {
                this.initOfficeTree();
            } else {//或者是改为非明细
                //取出公司树对象，如果存在，则销毁公司树，隐藏div
                let treeObj = $.fn.zTree.getZTreeObj("select-treetreeSelectOfficeEdit");
                if (treeObj != undefined) {
                    $.fn.zTree.destroy("select-treetreeSelectOfficeEdit");
                    $("#treeSelectOfficeEdit").css("display", "none");
                }
            }

        },
        /**
         * ztree开发流程：
         * 1.声明配置setting对象
         * 2.声明配置nodes对象
         * 3.根据配置和节点对象，创建ztree对象
         * $.fn.zTree.init(需要挂载树的dom节点对象，配置对象，节点数组对象)
         * 树初始化方法
         */
        initMenuTree: function () {
            axios({
                url: '/manager/menu/list'
            }).then(response => {
                let nodes = response.data.data;
                //添加新的父节点
                nodes[nodes.length] = {id: 0, name: '权限列表', open: true}
                //动态设置节点，将当前role已分配权限的节点设置选中
                this.selectResourcesByRid(nodes);
            })
        },
        initOfficeTree() {//初始化公司树
            axios({
                url: '/manager/office/selectAll'
            }).then(response => {
                let nodes = response.data.data;
                //添加新的父节点
                nodes[nodes.length] = {id: 0, name: '机构列表', open: true}
                //动态设置节点，将当前role已分配权限的节点设置选中
                this.selectOfficeByRid(nodes);
            })
        },
        //列表接口查询,传递分页参数，返回分页结果page对象
        select: function (current, size) {
            axios({
                url: `/manager/role/select/${current}/${size}`,
                params: this.params
            }).then(response => {
                //箭头函数会将上下文中的this(vue对象)传递过来
                // console.log(response);
                //response.data->  ResultBean<Page>   ResultBean.data -> Page对象  列表数据->Page对象.records
                this.pageInfo = response.data.data;
            })
        },
        selectAll: function () {//查询所有，不带条件
            this.params = {//初始化参数  ，不提交查询条件参数
                id: '',
                dataScope: '',
                name: '',
                remarks: ''
            }
            this.select(1, this.pageInfo.size);
        },
        changeUp: function () {
            this.up = !this.up;
        },
        toDetail: function (oid) {
            /*axios({
                url:`/manager/role/selectOne/${oid}`,
            }).then(respone=>{
                if(respone.data.code!=200){//发生异常的前端处理
                    layer.msg(respone.data.msg);
                    return;
                }
                //从数据库查询出id对应的obj信息，再传递到子页面
                layer.obj = respone.data.data;
                let index = layer.open({
                    type:2,
                    title: false,
                    content:'/manager/role/role-detail.html',
                    area: ['80%','80%'],
                    end: () => {
                        this.select(this.pageInfo.current,this.pageInfo.size);
                    }
                })
            })*/
        },
        managerUser: function (name, id) {
            //id和name传递到子页面
            layer.id = id;
            layer.name = name;
            let index = layer.open({
                type: 2,
                title: false,
                content: '/manager/role/role-user.html',
                area: ['80%', '80%'],
                end: () => {
                    this.select(this.pageInfo.current, this.pageInfo.size);
                }
            })

        },
        toUpdate: function (id) {
            axios({
                url: '/manager/role/selectOne',
                params: {id: id}
            }).then(respone => {
                if (respone.data.code != 200) {//发生异常的前端处理
                    layer.msg(respone.data.msg);
                    return;
                }
                //从数据库查询出id对应的app信息，再传递到子页面
                layer.obj = respone.data.data;
                let index = layer.open({
                    type: 2,
                    title: false,
                    content: '/manager/role/role-update.html',
                    area: ['60%', '80%'],
                    end: () => {
                        this.select(this.pageInfo.current, this.pageInfo.size);
                    }
                })
            })
        },
        /**
         * ztree开发流程：
         * 1.声明配置setting对象
         * 2.声明配置nodes对象
         * 3.根据配置和节点对象，创建ztree对象
         * $.fn.zTree.init(需要挂载树的dom节点对象，配置对象，节点数组对象)
         * 树初始化方法
         */
        initTree: function () {
            axios({
                url: '/manager/office/selectAll'
            }).then(response => {
                this.nodes = response.data.data;
                //动态生成一个父节点
                this.nodes[this.nodes.length] = {id: 0, name: '全部机构', open: true};

                let tree = $.fn.zTree.init($('#pullDownTreeone'), this.setting, this.nodes);
                let tree2 = $.fn.zTree.init($('#pullDownTreeone2'), this.setting, this.nodes);
                // console.log(tree);
                // console.log(tree.getNodes());
            })

        },

        onClick: function (event, treeId, treeNode) {
            // console.log(event);//事件对象
            // console.log(treeId);//树id  ul的id
            // console.log(treeNode);//树节点  触发点击事件的节点
            if (treeId=='pullDownTreeone'){
                this.name = treeNode.name;
                if (treeNode.id != 0) {//全部则不需要指定id
                    this.params.id = treeNode.id;

                }
            }else if(treeId=='pullDownTreeone2'){
                this.name2 = treeNode.name;
                if (treeNode.id != 0) {//
                    this.newRole.officeId = treeNode.id;
                }


            }


            // console.log(this.name);
        },


        search: function () {
            //1.根据搜索框输入的内容，从ztree的所有节点中进行模糊匹配，找到相关节点
            //a.获取树节点
            let zTreeObj = $.fn.zTree.getZTreeObj("pullDownTreeone");
            //b.模糊查询节点
            let fuzzyNodes = zTreeObj.getNodesByParamFuzzy("name", this.input);
            // console.log(fuzzyNodes);

            //2.高亮显示
            //设置还原所有节点高亮属性为false
            //transformToArray(nodes):将多维结构的节点数组，转换成简单数组结构（1维结构）
            let treeNodes = zTreeObj.transformToArray(zTreeObj.getNodes());

            for (let i in treeNodes) {
                treeNodes[i].highLight = false;
                zTreeObj.updateNode(treeNodes[i]);
            }

            //设置指定节点高亮
            for (let i = 0; i < fuzzyNodes.length; i++) {
                //找出树中对应的节点，设置高亮属性highLight为true,更新树
                fuzzyNodes[i].highLight = true;
                zTreeObj.updateNode(fuzzyNodes[i]);
            }
        },


        /*
        * 根据传入的树id、树节点参数，进行判断，设置不同的显示样式
        * 每个节点显示的时候都会调用当前方法进行判断
        * */
        fontCss: function (treeId, treeNode) {
            //设置显示样式，如果带有高亮属性的则为红色字体
            return treeNode.highLight ? {color: 'red'} : {color: 'black'};
        }
    },

    created: function () {
        this.select(this.pageInfo.current, this.pageInfo.size);
    },//注意ul节点需要挂载到dom后，才能找到该节点  使用mounted生命周期方法
    mounted: function () {
        this.initTree();

        this.initMenuTree();//初始化角色权限树
        /**
         * chosen下拉列表插件，通过隐藏原select组件，动态生成新div+ul来组成新的页面组件
         * $("#dom节点id").chosen(config) 初始化chosen
         *
         * config:
         * width-设置占据父元素宽度
         * disable_search-是否显示搜索框
         * search_contains-是否模糊查询
         */
        $("#userDataScope").chosen({width: "80%", search_contains: true});
        $("#saveUserDataScope").chosen({width: "50%", search_contains: true});
        //对下拉列表进行事件处理，获取选中的值
        //e-事件源对象     param封装了下拉选项值的key-value结构数据  {selected:xxx}
        $("#userDataScope").on("change", (e, param) => {
            this.params.dataScope = param.selected;
        });
        // console.log(this.role);

        //根据select动态生成chosen插件  将原select组件隐藏，动态生成div实现
        // $("#saveUserDataScope").chosen({width: "40%", search_contains: true});
        //如果dataScope是按明细查询(9)初始化公司树用于跨机构授权管理
        // if (this.role.dataScope === '9') {
        //     this.initOfficeTree();
        // }
        //绑定chose的动态事件
        $("#saveUserDataScope").on('change', this.changeDataScope);

        // $("#saveUserDataScope").on('change',(e, param) => {
        //
        //     console.log(e);
        //     console.log(param);
        //
        //
        // } );


    }
})








