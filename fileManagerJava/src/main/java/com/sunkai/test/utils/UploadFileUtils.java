package com.sunkai.test.utils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UploadFileUtils {

    private final static String FILE_PATH=System.getProperty("user.dir")+"/images/";
    public Map uploadFile(MultipartFile multipartFile){
        //获取文件后缀
        String fileName = UUID.randomUUID().toString().trim().replaceAll("-","")+"."+multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".")+1);
        String filePath = FILE_PATH+fileName;
        Long size = multipartFile.getSize();
        String realName = multipartFile.getOriginalFilename();
        System.out.println(size+"size");
        File file = new File(filePath);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        try{
            multipartFile.transferTo(file);
//            FileCopyUtils.copy(file,new File(filePath,fileName));
        }catch (IllegalStateException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        Map map = new HashMap();
        map.put("name",realName);
        map.put("url","images/"+fileName);
        map.put("size",size);
        return map;
    }
}
