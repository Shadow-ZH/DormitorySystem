package com.southwind.service;

import com.southwind.entity.Absent;
import com.baomidou.mybatisplus.extension.service.IService;
import com.southwind.form.SearchForm;
import com.southwind.vo.PageVO;

/**
* @author 16176
* @description 针对表【absent】的数据库操作Service
* @createDate 2022-11-21 19:51:00
*/
public interface AbsentService extends IService<Absent> {

    public PageVO list(Integer page, Integer size);

    public PageVO search(SearchForm searchForm);
}
