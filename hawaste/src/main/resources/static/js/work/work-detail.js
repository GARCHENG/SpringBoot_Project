new Vue({
    el: '#main-container',
    data:{
        work:''
    },
    methods:{
        selectDetail:function () {
            // alert(this.work);
            // alert(this.work.id)
            axios.get('/manager/work/showDetails',{
                params:{
                    id : this.work.id
                }
            }).then(response=>{
                if(response.data.code==200){
                    this.work = response.data.data;
                }
            })

        }


    },
    created:function (){
        //在vue管理的data初始化后，获取父窗口的layer对象中绑定的数据传递过来
        this.work = parent.layer.obj;
        this.selectDetail();
    }
})

