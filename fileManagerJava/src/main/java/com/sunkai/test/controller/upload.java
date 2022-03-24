package com.sunkai.test.controller;

import ch.qos.logback.core.util.FileUtil;
import com.sunkai.test.bean.Result;
import com.sunkai.test.utils.UploadFileUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class upload {
    @RequestMapping(value = "/downLoadFile")
    public Result  downLoadFile(@RequestParam String url,@RequestParam String name, HttpServletResponse response) {
        Result result = new Result();
        File file = new File(url);
        if (file.exists()) {
            System.out.println(name);
            response.setContentType("application/octet-stream");
            // 读取文件
            BufferedInputStream bi = null;
            try {
                byte[] bytes = FileCopyUtils.copyToByteArray(file);
                // 文件名乱码, 使用new String() 进行反编码
                String downname = new String(name.getBytes("gbk"),"iso8859-1");
                response.addHeader("Content-Disposition", "attachment;fileName=" + downname);
                OutputStream outputStream = response.getOutputStream();
                outputStream.write(bytes);
                result.setMsg("下载成功");
            } catch (Exception e) {
                result.setMsg("下载失败"+e);
                result.setCode(201);
            }

        }else{
            result.setMsg("文件已经不存在!");
            result.setCode(201);
        }
        return result;
    }
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public Result uploadFilemm(@RequestParam("file") MultipartFile[] multipartFiles){
        Result result = new Result();
        List<Map> list = new ArrayList<Map>();
        if(multipartFiles.length>0){
           for (int i=0;i<multipartFiles.length;i++){
               if(multipartFiles[i].isEmpty()){
                   result.setCode(201);
                   result.setData("");
                   result.setMsg("文件为空!");
                   return  result;
               }
               System.out.println(multipartFiles.length+"*/*/*/*/*");
               UploadFileUtils uploadFileUtils = new UploadFileUtils();
               Map map = uploadFileUtils.uploadFile(multipartFiles[i]);
               Date d = new Date();
               SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
               map.put("date",dateFormat.format(d));
               list.add(map);
           }
        }

        result.setData(list);
        return result;
    }

}
