package com.southwind.mapper;

import com.southwind.entity.Dormitory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.southwind.form.SearchForm;
import com.southwind.vo.PageVO;

/**
* @author 16176
* @description 针对表【dormitory】的数据库操作Mapper
* @createDate 2022-11-21 19:51:00
* @Entity com.southwind.entity.Dormitory
*/
public interface DormitoryMapper extends BaseMapper<Dormitory> {

    public void addAvailable(Integer oldDormitoryId);
    public void subAvailable(Integer oldDormitoryId);

    public Integer findAvailableDormitoryId();

}




