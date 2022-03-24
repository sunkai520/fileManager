package com.sunkai.test.mapper;

import com.sunkai.test.bean.Filelist;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author sunkai
 * @since 2022-02-18
 */
@Mapper
public interface FilelistMapper extends BaseMapper<Filelist> {
    @SelectProvider(type = SqlBuilder.class,method = "fileListSql")
    List<Filelist> getFileList(@Param("startNo") Integer startNo,@Param("startCount") Integer startCount,Map map);
    @SelectProvider(type = SqlBuilder.class,method = "fileListCountSql")
    Integer getFileCount(Map map);
}
