package com.sunkai.test.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author sunkai
 * @since 2022-02-18
 */
@Getter
@Setter
@TableName(autoResultMap = true)
public class Filelist implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String title;
//    @TableField(typeHandler = FastjsonTypeHandler.class)
//    private List fileList;
    private String date;
    private String tagsName;
//    @TableField(typeHandler = FastjsonTypeHandler.class)
    private String tags;
    private Integer cnt;
    private String descri;
}
