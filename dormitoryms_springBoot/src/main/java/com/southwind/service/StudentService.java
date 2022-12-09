package com.southwind.service;

import com.southwind.entity.Student;
import com.baomidou.mybatisplus.extension.service.IService;
import com.southwind.form.SearchForm;
import com.southwind.form.StudentForm;
import com.southwind.vo.PageVO;

/**
* @author 16176
* @description 针对表【student】的数据库操作Service
* @createDate 2022-11-21 19:51:00
*/
public interface StudentService extends IService<Student> {

    public Boolean saveStudent(Student student);
    public PageVO list(Integer page, Integer size);

    public PageVO search(SearchForm searchForm);

    public Boolean update(StudentForm studentForm);

    public Boolean deleteById(Integer id);
}
