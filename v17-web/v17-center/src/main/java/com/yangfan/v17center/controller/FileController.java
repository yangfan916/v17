package com.yangfan.v17center.controller;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.yangfan.common.pojo.ResultBean;
import com.yangfan.v17center.pojo.ResultWangEditor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author yangfan
 * @version 1.0
 * @description 处理文件上传的操作
 */
@Slf4j
@RestController
@RequestMapping("file")
public class FileController {

    @Autowired
    private FastFileStorageClient client;

    @Value("${image.server}")
    private String imageServer;

    @PostMapping("upload")
    public ResultBean<String> upload(MultipartFile file) {
        //1. 获取到文件的后缀
        String originalFilename = file.getOriginalFilename();
        String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        //2. 使用client上传图片到fastdfs
        try {
            StorePath storePath = client.uploadImageAndCrtThumbImage(file.getInputStream(), file.getSize(), extName, null);
            String fullPath = storePath.getFullPath();
            StringBuilder sb = new StringBuilder(imageServer).append(fullPath);
            log.info("上传的文件路径：" + sb);
            return new ResultBean<>("200", sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return new ResultBean<>("500", "当前服务器繁忙，请稍后再试");
        }
    }

    @PostMapping("batchFileUpLoad")
    public ResultWangEditor batchFileUpLoad(MultipartFile[] files) {
        String[] data = new String[files.length];
        try{
            for(int i = 0; i < files.length; i++){
                String originalFilename = files[i].getOriginalFilename();
                String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
                StorePath storePath = client.uploadImageAndCrtThumbImage(files[i].getInputStream(), files[i].getSize(), extName, null);
                StringBuilder sb = new StringBuilder(imageServer).append(storePath.getFullPath());
                data[i] = sb.toString();
            }
            return new ResultWangEditor("0", data);
        }catch (IOException e){
            e.printStackTrace();
            return new ResultWangEditor("500", null);
        }
    }
}
