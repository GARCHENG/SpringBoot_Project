var v = new Vue({
    el:'#main-container',
    data:{
        pageInfo:{
            current:1,
            size:5
        },
        condition:{
            startDate:'',
            endDate:'',
            type:'',
            check:''
        }
    },
    methods:{
        //列表接口查询,传递分页参数，返回分页结果page对象
        select:function (current,size){
            axios({
                url:`/manager/qualification/select/${current}/${size}`,
                params:this.condition
            }).then(response =>{
                //箭头函数会将上下文中的this(vue对象)传递过来
                // console.log(response);
                //response.data->  ResultBean<Page>   ResultBean.data -> Page对象  列表数据->Page对象.records
                this.pageInfo=response.data.data;
            }).catch(function (error){
                console.log(error);
            })
        },
        selectAll:function () {
            this.condition = {
                startDate:'',
                endDate:'',
                type:'',
                check:''
            };
            this.select(1,this.pageInfo.size);

        },
        toUpdate:function (id) {
            axios({
                url:`/manager/qualification/selectOne/${id}`,
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
                    content:'/manager/qualification/qualification-update.html',
                    area: ['80%','80%'],
                    end: () => {
                        this.select(this.pageInfo.current,this.pageInfo.size);
                    }
                })
            })


        }
    },
    created:function(){
        this.select(this.pageInfo.current,this.pageInfo.size)
    }

})