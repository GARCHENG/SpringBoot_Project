let em = new Vue({
    el:'#main-container',
    data:{
        pageInfo:{
            current:1,
            size:5
        },
        app:{}, //规范：对象提供默认值，避免页面出现属性找不到的错误
        active:false  //是否激活状态   false表示列表div  true表示新增表单div
    },
    methods:{
        select:function(pageNum,pageSize){
            axios({
                url: '/manager/app/select',
                params:{
                    current:pageNum,
                    size:pageSize
                }
            }).then(response=>{
                console.log(response.data);
                this.pageInfo = response.data.data; //RespResult的data
            }).catch(function (error) {
                console.log(error);
            });
        },
        save:function () {
            axios({
                url:'/manager/app/saveOrUpdate',
                data:this.app,
                method:'post'
            }).then(response=>{
                if (response.data.code==200){
                    //成功后 切换列表页，重新更新列表 清空新增表单数据
                    this.clear();
                    this.select(1,this.pageInfo.size);
                    this.active=false;
                }
                layer.msg(response.data.msg);
            }).catch(error=>{
                layer.msg(error.message);
            });
        },
        clear:function(){
            this.app={}  //清空数据
        },
        changeActive:function(){
            this.active=!this.active;  //切换
        },
        toUpdate:function(appid){
            axios({
                url:'/manager/app/selectOne',
                params: {id:appid}
            }).then(response => {
                if (response.data.code==500) {
                    layer.msg(response.data.msg)
                    return;
                }
                // console.log(response.data);
                // console.log(this);
                //返回数据，绑定到当前父窗口layer的appVersion上，传递给子窗口
                //对一些实时性要求不高的数据更新，可以不查询数据，直接方法传参对象，传递到layer子窗口
                layer.appVersion = response.data.data;
                console.log(layer);
                // console.log(this)
                let index = layer.open({
                    type:2, //类型：0信息框，1页面层  2 iframe层  3加载层  4 tips层
                    title:'更新app',
                    content:'/manager/app/app-update.html',
                    area:['60%','80%'], //弹出层宽高
                    end: () => {//将上下文中的this传递到end的回调函数中，弹出层销毁后的回调
                        //刷新页面数据
                        this.select(this.pageInfo.current,this.pageInfo.size);
                    }
                });
            })
        },
        doDelete:function (appid) {
            layer.msg('是否删除？',{
                time:0, // 无自动消失计时
                btn:['是','否'],
                yes:index=>{  //index是当前消息框索引
                    axios({
                        url:'/manager/app/delete',
                        params:{
                            id:appid
                        }
                    }).then(response=>{
                        layer.close(index); //关闭当前的msg窗口
                        layer.msg(response.data.msg);
                        //删除成功后刷新数据列表
                        if (response.data.code==200){
                            this.select(this.pageInfo.current,this.pageInfo.size);
                        }

                    });
                }
            });
        }
    },
    created:function () {
        this.select(this.pageInfo.current,this.pageInfo.size);
    }
});