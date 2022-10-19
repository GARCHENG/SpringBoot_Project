let vm = new Vue({
    el: '.main-content',
    data: {
        pageInfo: {
            current: 1,
            size: 5
        },//接受后台页对象
        type: '',
        statute: {},
        ueditorConfig: {//自定义VueUeditorWrap配置项
            // ueditor.config.js 的UEDITOR_HOME_URL: "/static/ueditor/" ->
            // 覆盖为ueUeditorWrap的  UEDITOR_HOME_URL:"/static/UEditor/" (默认配置)   ->
            //覆盖为 ueditorConfig 的  UEDITOR_HOME_URL:"/ueditor/"          （自定义配置）
            UEDITOR_HOME_URL: "/ueditor/",  //前端资源默认读取路径  注意结束/不能省  是拼接
            // 服务器统一请求接口（服务器接口测试用，后修改为下面我们自己的服务器接口地址）
            // serverUrl:'http://35.201.165.105:8000/controller.php'
            serverUrl: '/ueditor/exec',
            maximumWords: 500000   /*设置支持最大编辑富文本字符个数*/
        }
    },
    mounted: function () {
        jeDate({
            dateCell: '#indate',
            format: 'YYYY-MM-DD',
            zIndex: 999999999,
            choosefun: val => { //注意jeDate函数中的this是jeDate对象，需要使用箭头函数转换this为vue上下文
                //选中日期后的回调(查看jedate.js源码195行可以看到config对象使用文档)
                // val:组件中的日期数据
                //由于该组件生成的input的值是直接js写入到dom节点的，并不是vue双向绑定的，需要通过事件来给data中的statute的日期赋值
                this.statute.pubDate = val;
            }
        });
    },
    methods: {
        save: function () {
            axios.post('/manager/statute/saveOrUpdate', this.statute).then(response => {
                if (response.data.code == 200) {
                    this.statute = {};
                    layer.msg(response.data.msg);
                    this.select(1, this.pageInfo.size);
                }
            })
        },
        select: function (pageNum, pageSize) {
            //分页查询返回信息放入pageInfo
            //分页查询返回信息放入pageInfo
            axios({
                url: `/manager/statute/select/${pageNum}/${pageSize}`,
                params: {type: this.type}
            }).then(response => {
                // console.log(response);
                this.pageInfo = response.data.data;
            }).catch(error => {
                layer.msg(error.message);
            })
        },
        toUpdate: function (status_id) {
            axios({
                url: '/manager/statute/selectOne',
                params: {id: status_id}
            }).then(response => {
                if (response.data.code == 500) {
                    layer.msg(response.data.msg)
                    return;
                }
                // console.log(response.data);
                //返回数据，绑定到当前父窗口layer的obj上，传递给子窗口
                //对一些实时性要求不高的数据更新，可以不查询数据，直接方法传参对象，传递到layer子窗口
                layer.obj = response.data.data;
                // console.log(layer);
                let index = layer.open({
                    type: 2,
                    title: '更新statute',
                    content: '/manager/statute/statute-update.html',
                    area: ['60%', '80%'],
                    end: () => {//将上下文中的this传递到end的回调函数中
                        //刷新页面数据
                        this.select(this.pageInfo.current, this.pageInfo.size);
                    }
                });
            })
        },
        doDelete: function (sid) {
            // alert(sid);
            _this = this;
            layer.confirm('真的删除行么', function (index) {
                axios.get('/manager/statute/deleteOne', {
                    params: {
                        id: sid
                    }
                }).then(response => {
                    if (response.data.code == 200) {
                        layer.msg(response.data.msg);
                        _this.select(_this.pageInfo.current, _this.pageInfo.size);
                    }
                })


            })

        }
    },
    created: function () {
        this.select(1, this.pageInfo.size);
    },
    /*vue组件属性，用于引入一些已经封装好的vue组件对象*/
    components: {
        VueUeditorWrap
    }
});
