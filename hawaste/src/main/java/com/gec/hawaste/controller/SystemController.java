package com.gec.hawaste.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SystemController {
    //一级路径
    @RequestMapping("{url}.html")
    public String page1(@PathVariable("url") String url){
        return url;
    }

    //二级路径
    @RequestMapping("{moudel}/{url}.html")
    public String page2(@PathVariable("moudel") String moudel,
                        @PathVariable("url") String url){
        return moudel+"/"+url;
    }

    //三级路径跳转
    @RequestMapping("{moudel1}/{moudel2}/{url}.html")
    public String page2(@PathVariable("moudel1") String moudel1,
                        @PathVariable("moudel2") String moudel2,
                        @PathVariable("url") String url){
        return moudel1+"/"+moudel2+"/"+url;
    }

    //四级路径跳转
    @RequestMapping("{moudel1}/{moudel2}/{moudel3}/{url}.html")
    public String page3(@PathVariable("moudel1") String moudel1,
                        @PathVariable("moudel2") String moudel2,
                        @PathVariable("moudel3") String moudel3,
                        @PathVariable("url") String url){
        return moudel1+"/"+moudel2+"/"+moudel3+"/"+url;
    }
}
