package com.southwind.service;

import com.southwind.entity.Dormitory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.southwind.form.SearchForm;
import com.southwind.vo.PageVO;

/**
* @author 16176
* @description 针对表【dormitory】的数据库操作Service
* @createDate 2022-11-21 19:51:00
*/
public interface DormitoryService extends IService<Dormitory> {

    public PageVO list(Integer page, Integer size);

    public PageVO search(SearchForm searchForm);

    public Boolean deleteById(Integer id);
}
