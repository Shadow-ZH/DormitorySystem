package com.southwind.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName moveout
 */
@TableName(value ="moveout")
@Data
public class Moveout implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private Integer studentId;

    /**
     * 
     */
    private Integer dormitoryId;

    /**
     * 
     */
    private String reason;

    /**
     * 
     */
    private String createDate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}