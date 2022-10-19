package com.gec.hawaste.controller;

import com.gec.hawaste.config.OssProperties;
import com.gec.hawaste.utils.UploadOssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("ueditor")
public class UeditorController {

    @Autowired
    private OssProperties ossProperties;

    @Value("${pic.prefix}")
    private String prefixPath;


    /*
     *
     * 富文本编辑器的统一请求接口
     * */
    @RequestMapping("exec")
    public String execute(String action, HttpServletRequest request, MultipartFile upfile) {
        String result = null;
        String rootPath = request.getServletContext().getRealPath("/");
        if ("config".equals(action)) {//富文本编辑器初始化后会自动请求访问统一接口action=config，返回配置json

            result = new com.baidu.ueditor.ActionEnter(request, rootPath).exec();//调用ueditorapi读取config.json，转换成json字符串
        } else if ("uploadimage".equals(action)) {//文件上传操作  逻辑可以参考BinaryUploader.save方法

            /**
             * 1.封装对象存储工具
             * 2.在服务的接口上添加CommonsMultipartFile对象
             * 3.执行文件上传
             * 4.返回执行响应json字符串
             */
            /*获取配置config.json中的配置信息*/
            com.baidu.ueditor.ConfigManager configManager = com.baidu.ueditor.ConfigManager.getInstance(rootPath, request.getContextPath(), request.getRequestURI());
            Map<String, Object> conf = configManager.getConfig(com.baidu.ueditor.define.ActionMap.UPLOAD_IMAGE);
            String originFileName = upfile.getOriginalFilename();
            String suffix = com.baidu.ueditor.define.FileType.getSuffixByFilename(originFileName);
            originFileName = originFileName.substring(0,
                    originFileName.length() - suffix.length());
            String savePath = (String) conf.get("savePath");
            /*校验格式*/
            if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
                return new com.baidu.ueditor.define.BaseState(false, com.baidu.ueditor.define.AppInfo.NOT_ALLOW_FILE_TYPE).toJSONString();
            }
            /*处理保存路径*/
            savePath = savePath + suffix;
            savePath = com.baidu.ueditor.PathFormat.parse(savePath, originFileName);

            // 这里是上传方法
            UploadOssUtil.uploadFile(upfile, savePath, ossProperties);
            com.baidu.ueditor.define.BaseState state = new com.baidu.ueditor.define.BaseState(true);
            state.putInfo( "size", upfile.getSize() );
            state.putInfo( "title", originFileName+suffix );
            state.putInfo("url", /*prefixPath+*/savePath);
            state.putInfo("type", suffix);
            state.putInfo("original", originFileName + suffix);
            result = state.toJSONString();
        }

        return result;
    }

    private static boolean validType(String type, String[] allowTypes) {
        List<String> list = Arrays.asList(allowTypes);
        return list.contains(type);
    }

}
