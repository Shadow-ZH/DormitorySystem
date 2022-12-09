package com.southwind.service;

import com.southwind.entity.Moveout;
import com.baomidou.mybatisplus.extension.service.IService;
import com.southwind.form.SearchForm;
import com.southwind.vo.PageVO;

/**
* @author 16176
* @description 针对表【moveout】的数据库操作Service
* @createDate 2022-11-21 19:51:00
*/
public interface MoveoutService extends IService<Moveout> {

    public PageVO list(Integer page, Integer size);

    public PageVO search(SearchForm searchForm);

    Boolean moveout(Integer id, String reason);

    public PageVO moveoutList(Integer page, Integer size);

    public PageVO moveoutSearch(SearchForm searchForm);
}
