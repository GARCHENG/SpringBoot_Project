new Vue({
    el: '#main-container',
    data:function(){
        // console.log(this);//this是vue对象
        return {
            setting:{//ztree的配置对象
                data:{//ztree的数据配置对象
                    simpleData: {//设置简单节点数组配置
                        enable:true,   //默认false 设置true表示节点数组可以是一位结构
                        pIdKey: 'parentId'  //设置自定义的父id属性名
                    }/*,
                    key:{
                        title:'title'
                    }*/
                },
                callback:{//回调事件处理
                    onClick: this.onClick
                },
                view:{//显示设置
                    fontCss: this.fontCss /*{color:'red'}*/
                }
            },
            nodes:[],//公司树节点数组
            name:'全部', //显示office名字
            params:{//提交后台的参数对象
                officeId:'',
                status:'',
                startDate:'',
                endDate:''
            },
            input:'',//搜索框
            pageInfo:{
                current:1,
                size:5
            },
            up: false //显示向上或向下的标记
        }
    },
    methods:{
        //列表接口查询,传递分页参数，返回分页结果page对象
        select:function (current,size){
            axios({
                url:`/manager/work/select/${current}/${size}`,
                params:this.params
            }).then(response =>{
                //箭头函数会将上下文中的this(vue对象)传递过来
                // console.log(response);
                //response.data->  ResultBean<Page>   ResultBean.data -> Page对象  列表数据->Page对象.records
                this.pageInfo=response.data.data;
            }).catch(function (error){
                console.log(error);
            })
        },
        selectAll:function (){//查询所有，不带条件
            this.params = {//初始化参数  ，不提交查询条件参数
                officeId:'',
                status:'',
                startDate:'',
                endDate:''
            }
            this.select(1,this.pageInfo.size);
        },
        changeUp:function (){
            this.up = !this.up;
        },
        toDetail:function (oid){
            axios({
                url:`/manager/work/selectOne/${oid}`,
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
                    content:'/manager/work/work-detail.html',
                    area: ['80%','80%'],
                    end: () => {
                        this.select(this.pageInfo.current,this.pageInfo.size);
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
        initTree:function (){
            axios({
                url:'/manager/office/selectAll'
            }).then(response=>{
                this.nodes = response.data.data;
                //动态生成一个父节点
                this.nodes[this.nodes.length] = {id:0,name:'全部机构',open:true};

                let tree = $.fn.zTree.init($('#pullDownTreeone'),this.setting,this.nodes);
                // console.log(tree);
                // console.log(tree.getNodes());
            })

        },
        onClick:function (event,treeId,treeNode){
            // console.log(event);//事件对象
            // console.log(treeId);//树id  ul的id
            // console.log(treeNode);//树节点  触发点击事件的节点
            this.name=treeNode.name;
            if(treeNode.id!=0){//全部则不需要指定officeId
                this.params.officeId=treeNode.id;
            }

            // console.log(this.name);
        },
        search:function (){
            //1.根据搜索框输入的内容，从ztree的所有节点中进行模糊匹配，找到相关节点
            //a.获取树节点
            let zTreeObj = $.fn.zTree.getZTreeObj("pullDownTreeone");
            //b.模糊查询节点
            let fuzzyNodes = zTreeObj.getNodesByParamFuzzy("name",this.input);
            // console.log(fuzzyNodes);

            //2.高亮显示
            //设置还原所有节点高亮属性为false
            //transformToArray(nodes):将多维结构的节点数组，转换成简单数组结构（1维结构）
            let treeNodes = zTreeObj.transformToArray(zTreeObj.getNodes());

            for (let i in treeNodes) {
                treeNodes[i].highLight=false;
                zTreeObj.updateNode(treeNodes[i]);
            }

            //设置指定节点高亮
            for (let i = 0; i < fuzzyNodes.length; i++) {
                //找出树中对应的节点，设置高亮属性highLight为true,更新树
                fuzzyNodes[i].highLight=true;
                zTreeObj.updateNode(fuzzyNodes[i]);
            }
        },
        /*
        * 根据传入的树id、树节点参数，进行判断，设置不同的显示样式
        * 每个节点显示的时候都会调用当前方法进行判断
        * */
        fontCss: function (treeId,treeNode){
            //设置显示样式，如果带有高亮属性的则为红色字体
            return treeNode.highLight?{color:'red'}:{color:'black'};
        }
    },
    created:function (){
        this.select(this.pageInfo.current,this.pageInfo.size);
    },//注意ul节点需要挂载到dom后，才能找到该节点  使用mounted生命周期方法
    mounted:function (){
        this.initTree();
    }
})

