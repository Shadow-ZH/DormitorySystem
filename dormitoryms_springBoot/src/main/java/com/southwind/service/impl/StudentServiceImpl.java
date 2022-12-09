package com.southwind.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.southwind.entity.Dormitory;
import com.southwind.entity.Student;
import com.southwind.form.SearchForm;
import com.southwind.form.StudentForm;
import com.southwind.mapper.DormitoryMapper;
import com.southwind.service.StudentService;
import com.southwind.mapper.StudentMapper;
import com.southwind.utils.CommonUtil;
import com.southwind.vo.PageVO;
import com.southwind.vo.StudentVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author 16176
* @description 针对表【student】的数据库操作Service实现
* @createDate 2022-11-21 19:51:00
*/
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService{

    @Autowired(required = false)
    private StudentMapper studentMapper;
    @Autowired(required = false)
    private DormitoryMapper dormitoryMapper;

    @Override
    public Boolean saveStudent(Student student) {
        // 添加学生数据
        student.setCreateDate(CommonUtil.createDate());
        int insertStudent = this.studentMapper.insert(student);
        if(insertStudent != 1) return false;
        // 修改宿舍数据
        Dormitory dormitory = this.dormitoryMapper.selectById(student.getDormitoryId()); //根据学生要添加的宿舍查询宿舍
        // 出于安全考虑, 其实这里可以不写的，因为前端的接口已经先查询了将可用宿舍（有床位的宿舍展示，没有床位的宿舍不展示）。所以可以不写
        if (dormitory.getAvailable() == 0) return false;
        dormitory.setAvailable(dormitory.getAvailable() - 1);
        int updateDormitory = this.dormitoryMapper.updateById(dormitory);
        if (updateDormitory != 1) return false;
        return true;
    }

    @Override
    public PageVO list(Integer page, Integer size) {
        Page<Student> studentPage = this.studentMapper.selectPage(new Page<>(page, size), null);
        List<Student> studentList = studentPage.getRecords();
        // VO转换
        List<StudentVO> studentVOList = new ArrayList<>();
        for (Student student : studentList) {
            StudentVO studentVO = new StudentVO();
            BeanUtils.copyProperties(student,studentVO);
            Dormitory dormitory = this.dormitoryMapper.selectById(student.getDormitoryId());
            studentVO.setDormitoryName(dormitory.getName());
            studentVOList.add(studentVO);
        }
        PageVO pageVO = new PageVO();
        pageVO.setData(studentVOList);
        pageVO.setTotal(studentPage.getTotal());
        return pageVO;
    }

    @Override
    public PageVO search(SearchForm searchForm) {
        Page<Student> studentPage = new Page<>(searchForm.getPage(), searchForm.getSize());
        Page<Student> resultPage = null;
        if(searchForm.getValue().equals("")) {
            resultPage = this.studentMapper.selectPage(studentPage,null);
        } else {
            QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
            queryWrapper.like(searchForm.getKey(),searchForm.getValue());
            resultPage = this.studentMapper.selectPage(studentPage,queryWrapper);
        }
        List<Student> studentList = resultPage.getRecords();
        // VO转换
        List<StudentVO> studentVOList = new ArrayList<>();
        for (Student student : studentList) {
            StudentVO studentVO = new StudentVO();
            BeanUtils.copyProperties(student,studentVO);
            Dormitory dormitory = this.dormitoryMapper.selectById(student.getDormitoryId());
            studentVO.setDormitoryName(dormitory.getName());
            studentVOList.add(studentVO);
        }
        PageVO pageVO = new PageVO();
        pageVO.setData(studentVOList);
        pageVO.setTotal(resultPage.getTotal());
        return pageVO;
    }

    @Override
    public Boolean update(StudentForm studentForm) {
        // 更新学生信息
        Student student = new Student();
        BeanUtils.copyProperties(studentForm,student);
        int update = this.studentMapper.updateById(student);
        if (update != 1) return false;
        // 更新宿舍数据
        if (!studentForm.getDormitoryId().equals(studentForm.getOldDormitoryId())) {
            //old+1，new-1
            try {
                this.dormitoryMapper.addAvailable(studentForm.getOldDormitoryId());
                this.dormitoryMapper.subAvailable(studentForm.getDormitoryId());
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Boolean deleteById(Integer id) {
        // 修改宿舍数据
        Student student = this.studentMapper.selectById(id);
        try {
            Dormitory dormitory = this.dormitoryMapper.selectById(student.getDormitoryId());
            if(dormitory.getType() > dormitory.getAvailable()){
                this.dormitoryMapper.addAvailable(student.getDormitoryId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 删除学生数据
        int delete = this.studentMapper.deleteById(id);
        if (delete != 1) return false;
        return true;
    }


}




