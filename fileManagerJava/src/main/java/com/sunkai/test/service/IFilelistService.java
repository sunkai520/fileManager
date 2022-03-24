package com.sunkai.test.service;

import com.sunkai.test.bean.Filelist;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sunkai
 * @since 2022-02-18
 */
public interface IFilelistService extends IService<Filelist> {
    List<Filelist> getFileList(Integer startNo,Integer startCount,Map map);
    Integer getFileCount(Map map);
}
