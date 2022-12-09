package com.southwind.service;

import com.southwind.entity.SystemAdmin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.southwind.form.RuleForm;
import com.southwind.vo.ResultVO;

/**
* @author 16176
* @description 针对表【system_admin】的数据库操作Service
* @createDate 2022-11-21 19:51:00
*/
public interface SystemAdminService extends IService<SystemAdmin> {

    public ResultVO login(RuleForm ruleForm);
}
