package com.southwind.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName absent
 */
@TableName(value ="absent")
@Data
public class Absent implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private Integer buildingId;

    /**
     * 
     */
    private Integer dormitoryId;

    /**
     * 
     */
    private Integer studentId;

    /**
     * 
     */
    private Integer dormitoryAdminId;

    /**
     * 
     */
    private String createDate;

    /**
     * 
     */
    private String reason;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}