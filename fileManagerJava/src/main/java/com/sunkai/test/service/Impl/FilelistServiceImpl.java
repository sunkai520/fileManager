package com.sunkai.test.service.Impl;

import com.sunkai.test.bean.Filelist;
import com.sunkai.test.mapper.FilelistMapper;
import com.sunkai.test.service.IFilelistService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sunkai
 * @since 2022-02-18
 */
@Service
public class FilelistServiceImpl extends ServiceImpl<FilelistMapper, Filelist> implements IFilelistService {
    @Autowired
    FilelistMapper filelistMapper;
    public List<Filelist> getFileList(Integer startNo,Integer startCount,Map map) {
        return filelistMapper.getFileList(startNo,startCount,map);
    }
    public Integer getFileCount(Map map){
        return filelistMapper.getFileCount(map);
    }
}
