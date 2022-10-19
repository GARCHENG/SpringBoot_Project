new Vue({
    el: '#main-container',
    data: function(){
        return {
            setting:{//ztree的配置对象
                data:{//ztree的数据配置对象
                    simpleData: {//设置简单节点数组配置
                        enable:true,   //默认false 设置true表示节点数组可以是一位结构
                        pIdKey: 'parentId'  //设置自定义的父id属性名
                    }
                },
                check:{//设置checkbox配置  enable：true表示开启选项框
                    enable: true,
                    /*
                    * Y:控制选中的父和子checkbox关联
                    * N：控制取消选中的父和子checkbox关联
                    * p:关联父节点
                    * s:关联子节点
                    * */
                    chkboxType: {'Y':'ps','N':'ps'}
                }
            },
            role: {
                resources: [],   //已授权资源
                offices: [],     //已授权公司
                odlResources: [],   //旧授权资源
                oldOffices: []     //旧授权公司
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
        doUpdate:function (){
            //处理resources，必须有值
            let flag = this.checkResources();
            if(!flag){
                layer.msg("必须有授权资源");
                return;
            }
            //处理office
            this.checkOffices();
            axios({
                'url':'/manager/role/saveOrUpdate',
                'method':'post',
                data:this.role
            }).then(response=>{
                parent.layer.msg(response.data.msg);
                let index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);//关闭当前子窗口
            }).catch(e=>{
                layer.msg(e.message);
            })
        },
        checkResources:function (){
            //获取被选中的resources集合
            let zTreeObj = $.fn.zTree.getZTreeObj("select-treetreeSelectResEdit");
            // console.log(zTreeObj.getNodes())
            let _resources = zTreeObj.getCheckedNodes();
            if(_resources.length===0){
                return false;//必须要有值
            }
            //不能将父节点提交到后台，需要移除
            if(_resources[0].id===0){
                _resources.shift();//移除数组中的一个元素  顶级父节点
            }
            //role的新旧资源判断，如果不一致则更新
            if(_resources.length!==this.role.oldResources){//需要更新
                this.role.resources=_resources;
            }else{//长度一致  进一步比较两个数组元素是否一致  两个数组如果一致，元素位置一致
                for (let i in this.role.oldResources) {
                    if(this.role.oldResources[i].id!==_resources[i.id]){
                        this.role.resources=_resources;
                        return true;
                    }
                }
                this.role.resources = null;//长度相同，元素页一致
            }
            return true;
        },
        checkOffices:function (){

            let treeObj = $.fn.zTree.getZTreeObj("select-treetreeSelectOfficeEdit");
            if(treeObj!=undefined){//有初始化公司树
                let _offices = treeObj.getCheckedNodes();
                if(_offices.length===0){
                   //公司树存在，dataScope为9  不更新  office为null
                    this.role.offices = null;
                    return;
                }
                //不能将父节点提交到后台，需要移除
                if(_offices[0].id===0){
                    _offices.shift();//移除数组中的一个元素  顶级父节点
                }
                //role的新旧授权机构判断，如果不一致则更新
                if(this.role.oldOffices==null||this.role.oldOffices.length==0){//原来dataScope不是9
                    this.role.offices=_offices;
                }else if(_offices.length!==this.role.oldOffices){//需要更新
                    this.role.offices=_offices;
                }else{//长度一致  进一步比较两个数组元素是否一致  两个数组如果一致，元素位置一致
                    for (let i in this.role.oldOffices) {
                        if(this.role.oldOffices[i].id!==_offices[i.id]){
                            this.role.offices=_offices;
                            break;
                        }
                    }
                    this.role.offices = null;//长度相同，元素也一致
                }
            }else{
                this.role.offices=null;//无初始化公司树    office为null
            }
        },
        /**
         * 根据角色的id查询已授权节点，设置选中，并创建树
         */
        selectResourcesByRid(nodes) {
            for (let i in nodes) {
                let _resources = this.role.resources;
                for (let j in _resources) {
                    if(_resources[j].id===nodes[i].id){//在权限树找到对应节点，设置选中
                        nodes[i].checked=true;  //true则会被选中
                        break;
                    }
                }
            }
            //设置了节点选中后再初始化树
            let treeObj = $.fn.zTree.init($('#select-treetreeSelectResEdit'),this.setting,nodes);
        },
        selectOfficeByRid(nodes) {//初始化公司树
            for (let i in nodes) {
                let _offices = this.role.offices;
                for (let j in _offices) {
                    if(_offices[j].id===nodes[i].id){//在权限树找到对应节点，设置选中
                        nodes[i].checked=true;  //true则会被选中
                        break;
                    }
                }
            }
            //设置了节点选中后再初始化树
            let treeObj = $.fn.zTree.init($('#select-treetreeSelectOfficeEdit'),this.setting,nodes);
            $("#treeSelectOfficeEdit").css("display","inline-block");//显示公司树
        },
        //处理chose的change事件
        changeDataScope(e,param) {//e是事件对象   param: {selected:值}
            //给role的dataScope赋值
            this.role.dataScope=param.selected;
            //判断是其他改为明细
            if (this.role.dataScope==='9'){
                this.initOfficeTree();
            }else{//或者是改为非明细
                //取出公司树对象，如果存在，则销毁公司树，隐藏div
                let treeObj = $.fn.zTree.getZTreeObj("select-treetreeSelectOfficeEdit");
                if(treeObj!=undefined){
                    $.fn.zTree.destroy("select-treetreeSelectOfficeEdit");
                    $("#treeSelectOfficeEdit").css("display","none");
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
        initTree:function (){
            axios({
                url:'/manager/menu/list'
            }).then(response=>{
                let nodes = response.data.data;
                //添加新的父节点
                nodes[nodes.length]={id:0,name:'权限列表',open:true}
                //动态设置节点，将当前role已分配权限的节点设置选中
                this.selectResourcesByRid(nodes);
            })
        },
        initOfficeTree() {//初始化公司树
            axios({
                url:'/manager/office/selectAll'
            }).then(response=>{
                let nodes = response.data.data;
                //添加新的父节点
                nodes[nodes.length]={id:0,name:'机构列表',open:true}
                //动态设置节点，将当前role已分配权限的节点设置选中
                this.selectOfficeByRid(nodes);
            })
        }
    },
    created: function () {
        this.role = parent.layer.obj;
        //设置新旧授权信息
        this.role.oldResources=this.role.resources;
        this.role.oldOffices=this.role.offices
    },
    mounted:function (){
        // console.log(this.role);
        this.initTree();//初始化角色权限树
        //根据select动态生成chosen插件  将原select组件隐藏，动态生成div实现
        $("#chosenSelectEdit").chosen({width: "40%",search_contains: true});
        //如果dataScope是按明细查询(9)初始化公司树用于跨机构授权管理
        if(this.role.dataScope==='9'){
            this.initOfficeTree();
        }
        //绑定chose的动态事件
        $("#chosenSelectEdit").on('change',this.changeDataScope);
    }
})

