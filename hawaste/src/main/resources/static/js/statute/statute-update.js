let vm = new Vue({
    el: '.main-content',
    data: {
        statute:{},
        ueditorConfig:{//自定义VueUeditorWrap配置项
            // ueditor.config.js 的UEDITOR_HOME_URL: "/static/ueditor/" ->
            // 覆盖为ueUeditorWrap的  UEDITOR_HOME_URL:"/static/UEditor/" (默认配置)   ->
            //覆盖为 ueditorConfig 的  UEDITOR_HOME_URL:"/ueditor/"          （自定义配置）
            UEDITOR_HOME_URL:"/ueditor/",  //前端资源默认读取路径  注意结束/不能省  是拼接
            // 服务器统一请求接口（服务器接口测试用，后修改为下面我们自己的服务器接口地址）
            // serverUrl:'http://35.201.165.105:8000/controller.php'
            serverUrl:'/ueditor/exec',
            maximumWords:500000   /*设置支持最大编辑富文本字符个数*/
        }
    },
    methods: {
        doUpdate:function () {
            axios({
                url:'/manager/statute/saveOrUpdate',
                data:this.statute,
                method:'post'
            }).then(response => {
                if (response.data.code!=200) {
                    layer.msg(response.data.msg)
                    return;
                }
                parent.layer.msg(response.data.msg);//通过父窗口layer对象弹框
                // console.log('关闭当前窗口....');
                let index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                parent.layer.close(index); //再通过父窗口layer对象执行关闭,关闭当前子窗口
            }).catch(error =>{
                layer.msg(error.message);
            })
        }

    },
    created: function () {
        //初始化app  从父窗口的layer对象中获取obj属性
        this.statute = parent.layer.obj;
    },
    mounted:function(){
        jeDate({
            dateCell: '#modifydate',
            format: 'YYYY-MM-DD',
            zIndex: 999999999,
            choosefun: val  => { //注意jeDate函数中的this是jeDate对象，需要使用箭头函数转换this为vue上下文
                //选中日期后的回调(查看jedate.js源码195行可以看到config对象使用文档)
                // val:组件中的日期数据
                //由于该组件生成的input的值是直接js写入到dom节点的，并不是vue双向绑定的，需要通过事件来给data中的statute的日期赋值
                this.statute.pubDate = val;
            }
        });
    },
    /*vue组件属性，用于引入一些已经封装好的vue组件对象*/
    components:{
        VueUeditorWrap
    }
});