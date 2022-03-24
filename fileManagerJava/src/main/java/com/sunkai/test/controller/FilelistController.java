package com.sunkai.test.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sunkai.test.bean.Filelist;
import com.sunkai.test.bean.Ffile;
import com.sunkai.test.bean.Result;
import com.sunkai.test.service.IFilelistService;
import com.sunkai.test.service.IFilesService;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sunkai
 * @since 2022-02-18
 */
@RestController
public class FilelistController {
    @Autowired
    IFilelistService iFilelistService;
    @Autowired
    IFilesService iFilesService;
    @RequestMapping(value = "/createFile",method = RequestMethod.POST)
    public Result createFiles(@RequestBody Map map){
        Filelist filelist = new Filelist();
//        filelist.setFileList((List<Map>) map.get("fileList"));
        filelist.setTitle((String) map.get("title"));
        filelist.setDescri((String) map.get("descri"));
        Integer id = (Integer) map.get("id");
        filelist.setTagsName(map.get("tagsName").toString());
        ArrayList<Integer> yt =  (ArrayList<Integer>) map.get("tags");
        ArrayList<String> tt = new ArrayList<String>();
        yt.forEach((t)->{
            tt.add(t.toString());
        });
        filelist.setTags(StringUtils.join(tt));
        if(id==null){
            Date d = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            filelist.setDate(dateFormat.format(d));
            Boolean bool = iFilelistService.save(filelist);
        }else{
            filelist.setId(id);
            Boolean bool = iFilelistService.updateById(filelist);
        }
        List<Map> list =(List) map.get("fileList");
        List<Ffile> ffList = new ArrayList<>();
        for (int i=0;i<list.size();i++){
            System.out.println(list.get(i) instanceof Object);
            Ffile ffile = new Ffile();
            Map<String,Object> mapp =(Map<String, Object>) list.get(i);
            mapp.put("fileId",filelist.getId());
            ffile.setFileId(filelist.getId());
            ffile.setDate(list.get(i).get("date").toString());
            ffile.setName(list.get(i).get("name").toString());
            ffile.setSize(list.get(i).get("size").toString());
            ffile.setUrl(list.get(i).get("url").toString());
            ffile.setType(list.get(i).get("type").toString());
            ffList.add(ffile);
        }
        iFilesService.saveBatch(ffList);
        Result result = new Result();
        return  result;
    }
    @RequestMapping(value="/getFileList",method = RequestMethod.POST)
    public Result getFileList(@RequestBody Map obj){
        Integer mpage = Integer.parseInt(obj.get("page").toString());
        Integer pageSize =Integer.parseInt(obj.get("pageSize").toString());
        QueryWrapper<Filelist> queryWrapper = new QueryWrapper<Filelist>();
        List list = iFilelistService.getFileList((mpage-1)*pageSize,pageSize,obj);
//        List<Filelist> data = pageMaps.getRecords();
        Integer count =  iFilelistService.getFileCount(obj);
        Map map = new HashMap();
        map.put("total",count);
        map.put("data",list);
        Result result = new Result();
        result.setData(map);
        return result;
    }
    @RequestMapping(value="/removeList",method = RequestMethod.POST)
    public Result removeList(@RequestBody Map obj){
        Result result = new Result();
        QueryWrapper<Ffile> queryWrapper = new QueryWrapper<Ffile>();
        queryWrapper.eq("fileId",obj.get("id"));
        List<Ffile> list = iFilesService.list(queryWrapper);
        System.out.println(list+"list");
        Boolean bool;
        if(list.size()==0){
           bool = iFilelistService.removeById(obj.get("id").toString());

        }else{
            ArrayList lit = new ArrayList();
            list.forEach(item->{
                File file = new File(item.getUrl());
                if (file.exists()) {
//                    Boolean boo = iFilesService.removeById(item.getId());
//                    if(boo){
//                        file.delete();
//                        System.out.println("===========删除成功=================");
//                    }
                    file.delete();
                    lit.add(item.getId());
                } else {
                    System.out.println("===============删除失败==============");
                }
            });
            iFilesService.removeByIds(lit);
            bool = iFilelistService.removeById(obj.get("id").toString());
        }
        if(!bool){
            result.setCode(201);
            result.setMsg("删除失败");
        }
        return result;
    }
    @RequestMapping(value="/upDateList",method = RequestMethod.POST)
    public Result upDateList(@RequestBody Map obj){
        Result result = new Result();
        return result;
    }
}
