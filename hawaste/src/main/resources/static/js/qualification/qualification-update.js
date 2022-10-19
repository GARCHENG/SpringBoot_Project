new Vue({
    el: '#main-container',
    data: {
        qualification: {}
    },
    methods: {

        update: function (check) {
            this.qualification.check = check;

            axios.post('/manager/qualification/updateCheck',
                this.qualification
            ).then(response => {
                parent.layer.msg(response.data.msg); //通过父窗口layer对象弹框
                //parent.layer.msg('编辑成功');
                let index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                parent.layer.close(index); //通过父窗口layer对象执行关闭，关闭当前子窗口
            }).catch(function (error) {
                layer.msg(error.message)
            })


        }

    },
    created: function () {
        //在vue管理的data初始化后，获取父窗口的layer对象中绑定的数据传递过来
        this.qualification = parent.layer.obj;
    }
})