let vm = new Vue({
    el: '.main-content',
    data: function () {
        return {
            setting:{//树设置对象
                data:{//树的data设置
                    key:{
                        title:'title'
                    },
                    simpleData: {
                        enable:true, //设置节点数组使用一维数组格式
                        pIdKey:'parentId'  //设置父id属性名
                    }
                },
                callback:{/*事件回调函数设置*/
                    onClick: this.onClick
                },
                view:{/*显示处理回调设置*/
                    fontCss: this.fontCss
                }
            },
            nodes:[],
            name:'全部',  //显示office的名字
            up:false,    //显示向上向下的标记
            params:{//提交后台用的参数对象
                officeId:'',
                name:'',
                type:''
            },
            pageInfo: {
                current: 1,
                size: 5
            }//接受后台页对象
        }
    },

    methods: {
        select: function (current, size) {
            //分页查询返回信息放入pageInfo
            axios({
                url:`/manager/examine/select/${current}/${size}`,
                params:this.params
            }).then(response=>{
                this.pageInfo=response.data.data;
            })
        },
        selectAll:function(){
            this.params={//提交后台用的参数对象
                officeId:'',
                name:'',
                type:''
            };
            this.name = '全部';
            this.select(1,this.pageInfo.size);
        },
        initTree:function () {//初始化菜单树
            //树初始化  init(dom节点对象,设置对象,节点数组对象)
            axios({
                url:'/manager/office/selectAll'
            }).then(response=>{
                this.nodes=response.data.data;
                //动态生成一个根节点
                this.nodes[this.nodes.length]={id:0,name:'全部机构',open:true};

                //必须获取节点集合后初始化
                let zTree = $.fn.zTree.init($('#pullDownTreeone'),this.setting,this.nodes);
            }).catch(error=>{
                layer.msg(error.message)
            })


        },
        onClick: function(event,treeId,treeNode) {
            // console.log(event)
            // console.log(treeId)
            // console.log(treeNode);
            // console.log($.fn.zTree.getZTreeObj(treeId));//获取树对象
            this.name=treeNode.name;//给当前vue的name属性赋值  实际上this并不是vue  也获取不到
            // console.log(this);
            if(treeNode.id!=0){
                this.params.officeId=treeNode.id;//赋值officeId条件
            }

        },
        search:function () {
            /*
            * 1.获取树对象
            * 2.调用getNodesByParamFuzzy(属性名,属性值,父节点id)模糊匹配节点
            * */
            let zTreeObj = $.fn.zTree.getZTreeObj("pullDownTreeone");
            let nodesbyParamFuzzy = zTreeObj.getNodesByParamFuzzy("name",this.name,null);
            // console.log(nodesbyParamFuzzy);
            /*
            * 3.高亮显示处理
            * */
            //还原所有高亮属性为false
            // console.log(zTreeObj.getNodes());
            //获取所有节点，并转换成简单数据格式
            let treeNodes = zTreeObj.transformToArray(zTreeObj.getNodes());
            for (let i = 0; i < treeNodes.length; i++) {
                treeNodes[i].hightLight=false;
                zTreeObj.updateNode(treeNodes[i]);
            }
            //将查找到的节点高亮属性设置为true
            for (let i in nodesbyParamFuzzy) {
                nodesbyParamFuzzy[i].hightLight=true;//设置高亮属性
                zTreeObj.updateNode(nodesbyParamFuzzy[i])//更新树对象上的节点信息，会自动触发显示回调
            }
        },
        fontCss:function (treeId,treeNode) {
            /*根据高亮标记设置高亮红色*/
            return treeNode.hightLight?{color:'red'}:{color:'black'};
        },
        changeUp:function () {//修改up标记状态
            this.up=!this.up;
        }
    },
    created: function () {
        this.select(1, this.pageInfo.size);
    },
    mounted:function () {
        this.initTree();
    }
});