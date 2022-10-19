new Vue({
    el: '#main-container',
    data: function(){
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
            treeNode:'',
            role:{
                id:'',//角色id
                name:'', //显示role名字
            },
            yxUsers:[],//已选人员列表
            dxUsers:[],
            showRemoveBtn: 'hide' ,//是否显示删除已选人员按钮    添加hide样式表示隐藏
            yxIds:[],//  从已选人员列表中删除的用户id数组
            showAddBtn: 'hide' ,//是否显示添加待选人员按钮    添加hide样式表示隐藏
            dxIds:[],
            oid:''
        }
    },
    methods: {
        dxUser:function (oid){//查询公司待选人员
            this.oid = oid;
            axios({
                url:`/manager/user/selectNoRole/${this.role.id}/${oid}`
            }).then(r=>{
                this.dxUsers=r.data.data;
                //设置所有的已选用户默认不选中
                for (let i in this.dxUsers) {
                    this.dxUsers[i].show = false;//动态赋值show属性，用于控制checkbox选中状态
                }
            })
        },
        yxUser:function (){//查询当前角色已选人员
            axios({
                url:`/manager/user/selectByRid/${this.role.id}`
            }).then(r=>{
                this.yxUsers=r.data.data;
                //设置所有的已选用户默认不选中
                for (let i in this.yxUsers) {
                    this.yxUsers[i].show = false;//动态赋值show属性，用于控制checkbox选中状态
                }
            })
        },
        /**
         * 根据传入的选中人员id：
         * 1.找出被点击的人员，设置选中状态(true/false)
         * 2.判断是否选中，如果选中需要显示移除人员按钮，并且将用户id添加到yxIds中
         * 3.如果是取消选中，将用户id从yxIds中移除
         * 4.判断是否需要隐藏移除人员按钮
         * @param id
         */
        checkYxUsers:function (id){
            for (let i in this.yxUsers) {
                // 1.找出被点击的人员，设置选中状态(true/false)
                if(this.yxUsers[i].id==id){
                    this.yxUsers[i].show=!this.yxUsers[i].show;
                    //2.判断是否选中，如果选中需要显示移除人员按钮，并且将用户id添加到yxIds中
                    if(this.yxUsers[i].show){
                        this.showRemoveBtn = 'show';
                        this.yxIds.push(id);//在数组尾部插入新元素
                    }else {
                        //3.如果是取消选中，将用户id从yxIds中移除
                        for (let j in this.yxIds) {
                            if(this.yxIds[j]==id){
                                //从数组中删除元素  splice(index)  根据索引删除元素
                                this.yxIds.splice(j);
                            }
                        }
                    }
                }
            }
            //4.判断是否需要隐藏移除人员按钮
            let checkbox = $('#yxuser').find('input:checked');
            if(checkbox.length==0){
                this.showRemoveBtn='hide';
            }
        },
        /**
         * 删除已选人员
         * 1.传递rid和yxIds到后台删除
         * 2.刷新已选人员列表和隐藏移除按钮
         * 3.刷新当前选中公司的未选人员列表
         */
        removeYxUsers:function (){
            /*
            提交参数格式：
            {rid:this.role.rid,ids:this.yxIds}
            但前端自动组装出来的参数是
            http://localhost:8080/manager/role/deleteBatch?ids[]=68&ids[]=40&rid=2
            后台无法自动封装，会当初参数名为'ids[]'
            解决方案：
            ids:this.yxIds+''
            前端处理：
            1.将数组序列化ids="68,40"
            2.放入地址栏提交传递，等同于ids=68&ids=40
             */
            axios({
                url:'/manager/role/deleteBatch',
                params:{rid:this.role.id,ids:this.yxIds+''}
            }).then(r=>{
                this.yxUser();
                this.showRemoveBtn='hide';
                this.yxIds=[];
                if(this.treeNode!=undefined&&this.treeNode!=""){
                    this.dxUser(this.treeNode.id);
                }
            })
        },
        checkDxUsers:function (id){//添加待选人员
            for (let i in this.dxUsers) {
                // 1.找出被点击的人员，设置选中状态(true/false)
                if(this.dxUsers[i].id==id){
                    this.dxUsers[i].show=!this.dxUsers[i].show;
                    //2.判断是否选中，如果选中需要显示移除人员按钮，并且将用户id添加到yxIds中
                    if(this.dxUsers[i].show){
                        this.showAddBtn = 'show';
                        this.dxIds.push(id);//在数组尾部插入新元素
                    }else {
                        //3.如果是取消选中，将用户id从yxIds中移除
                        for (let j in this.dxIds) {
                            if(this.dxIds[j]==id){
                                //从数组中删除元素  splice(index)  根据索引删除元素
                                this.dxIds.splice(j);
                            }
                        }
                    }
                }
            }
            //4.判断是否需要隐藏移除人员按钮
            let checkbox = $('#dxuser').find('input:checked');
            if(checkbox.length==0){
                this.showAddBtn='hide';
            }
        },
        addDxUsers:function (){
            /*
            提交参数格式：
            {rid:this.role.rid,ids:this.dxIds}
            但前端自动组装出来的参数是
            http://localhost:8080/manager/role/deleteBatch?ids[]=68&ids[]=40&rid=2
            后台无法自动封装，会当初参数名为'ids[]'
            解决方案：
            ids:this.dxIds+''
            前端处理：
            1.将数组序列化ids="68,40"
            2.放入地址栏提交传递，等同于ids=68&ids=40
             */
            axios({
                url:'/manager/role/insertBatch',
                params:{rid:this.role.id,ids:this.dxIds+''}
            }).then(r=>{
                this.dxUser(this.oid);
                this.showAddBtn='hide';
                this.dxIds=[];
                this.yxUser();

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
                let tree = $.fn.zTree.init($('#treeOffice'),this.setting,this.nodes);
                // console.log(tree);
                // console.log(tree.getNodes());
            })

        },
        onClick:function (event,treeId,treeNode){
            // console.log(event);//事件对象
            // console.log(treeId);//树id  ul的id
            // console.log(treeNode);//树节点  触发点击事件的节点
            /*this.name=treeNode.name;
            if(treeNode.id!=0){//全部则不需要指定id
                this.params.id=treeNode.id;
            }*/

            // console.log(this.name);
            let zTreeObj = $.fn.zTree.getZTreeObj("treeOffice");
            //设置还原所有节点高亮属性为false
            //transformToArray(nodes):将多维结构的节点数组，转换成简单数组结构（1维结构）
            let treeNodes = zTreeObj.transformToArray(zTreeObj.getNodes());
            for (let i in treeNodes) {
                treeNodes[i].highLight=false;
                zTreeObj.updateNode(treeNodes[i]);
            }
            treeNode.highLight=true;
            zTreeObj.updateNode(treeNode);
            if(treeNode.id!=0){
                this.dxUser(treeNode.id);//查询待选人员
            }
            this.treeNode = treeNode;
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
    created: function () {
        this.role.name = parent.layer.name;
        this.role.id = parent.layer.id;
    },
    mounted:function (){
        this.initTree();
        this.yxUser();//初始化已选人员列表
    }
})

