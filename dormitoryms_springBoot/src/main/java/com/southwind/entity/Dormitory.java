package com.southwind.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName dormitory
 */
@TableName(value ="dormitory")
@Data
public class Dormitory implements Serializable {
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
    private String name;

    /**
     * 
     */
    private Integer type;

    /**
     * 
     */
    private Integer available;

    /**
     * 
     */
    private String telephone;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}