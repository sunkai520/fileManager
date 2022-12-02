package com.sunkai.test.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sunkai.test.bean.Bklist;
import com.sunkai.test.bean.Ffile;
import com.sunkai.test.bean.Result;
import com.sunkai.test.service.IFilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sunkai
 * @since 2022-02-21
 */
@RestController
public class FilesController {
    @Autowired
    IFilesService iFilesService;
    @RequestMapping(value="/getFfList",method = RequestMethod.POST)
    public Result getFfList(@RequestBody Map obj){
        Result result = new Result();
        QueryWrapper<Ffile> queryWrapper = new QueryWrapper<Ffile>();
        queryWrapper.eq("fileId",obj.get("fileId"));
        if(obj.get("type").equals("other")){
            queryWrapper.notLike("type","word");
            queryWrapper.notLike("type","excel");
            queryWrapper.notLike("type","ppt");
            queryWrapper.notLike("type","zip");
        }else{
            queryWrapper.like("type",obj.get("type"));
        }

        List list = iFilesService.list(queryWrapper);
        result.setData(list);
        return  result;
    }
    @RequestMapping(value = "delFile",method = RequestMethod.POST)
    public Result delFile(@RequestBody Map map){
        Result result = new Result();
        File file = new File(map.get("filePath").toString());
        if (file.exists()) {
            Boolean boo = iFilesService.removeById(map.get("id").toString());
            if(boo){
                file.delete();
                System.out.println("===========删除成功=================");
                result.setMsg("删除成功");
            }else{
                result.setCode(201);
                result.setMsg("删除失败,请检查路径下文件是否存在!");
            }
        } else {
            System.out.println("===============删除失败==============");
            result.setCode(201);
            result.setMsg("删除失败,请检查路径下文件是否存在!");
        }
        return result;
    }
}
