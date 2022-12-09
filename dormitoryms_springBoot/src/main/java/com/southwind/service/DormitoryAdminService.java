package com.southwind.service;

import com.southwind.entity.DormitoryAdmin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.southwind.form.RuleForm;
import com.southwind.form.SearchForm;
import com.southwind.vo.PageVO;
import com.southwind.vo.ResultVO;

/**
* @author 16176
* @description 针对表【dormitory_admin】的数据库操作Service
* @createDate 2022-11-21 19:51:00
*/
public interface DormitoryAdminService extends IService<DormitoryAdmin> {

    public ResultVO login(RuleForm ruleForm);
    public PageVO list(Integer page, Integer size);
    public PageVO search(SearchForm searchForm);
}
