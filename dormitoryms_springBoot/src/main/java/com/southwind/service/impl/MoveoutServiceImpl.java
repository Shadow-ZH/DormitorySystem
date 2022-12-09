package com.southwind.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.southwind.entity.Dormitory;
import com.southwind.entity.Moveout;
import com.southwind.entity.Student;
import com.southwind.form.SearchForm;
import com.southwind.mapper.DormitoryMapper;
import com.southwind.mapper.StudentMapper;
import com.southwind.service.MoveoutService;
import com.southwind.mapper.MoveoutMapper;
import com.southwind.utils.CommonUtil;
import com.southwind.vo.MoveoutVO;
import com.southwind.vo.PageVO;
import com.southwind.vo.StudentVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author 16176
* @description 针对表【moveout】的数据库操作Service实现
* @createDate 2022-11-21 19:51:00
*/
@Service
public class MoveoutServiceImpl extends ServiceImpl<MoveoutMapper, Moveout> implements MoveoutService{

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private DormitoryMapper dormitoryMapper;
    @Autowired
    private MoveoutMapper moveoutMapper;

    @Override
    public PageVO list(Integer page, Integer size) {
        Page<Student> studentPage = new Page<>(page, size);
        // 查询所有状态为入住的学生
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state","入住");
        Page<Student> resultPage = this.studentMapper.selectPage(studentPage, queryWrapper);
        List<Student> studentList = resultPage.getRecords();
        // VO转换
        List<StudentVO> studentVOList = new ArrayList<>();
        for (Student student : studentList) {
            StudentVO studentVO = new StudentVO();
            BeanUtils.copyProperties(student,studentVO);
            studentVO.setDormitoryName(this.dormitoryMapper.selectById(student.getDormitoryId()).getName());
            studentVOList.add(studentVO);
        }
        PageVO pageVO = new PageVO();
        pageVO.setData(studentVOList);
        pageVO.setTotal(resultPage.getTotal());
        return pageVO;
    }

    @Override
    public PageVO search(SearchForm searchForm) {
        Page<Student> studentPage = new Page<>(searchForm.getPage(),searchForm.getSize());
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state","入住");
        Page<Student> resultPage = null;
        if (searchForm.getValue().equals("")) {
            resultPage = this.studentMapper.selectPage(studentPage,queryWrapper);
        } else {
            queryWrapper.like(searchForm.getKey(),searchForm.getValue());
            resultPage = this.studentMapper.selectPage(studentPage,queryWrapper);
        }
        List<Student> studentList = resultPage.getRecords();
        // VO转换
        List<StudentVO> studentVOList = new ArrayList<>();
        for (Student student : studentList) {
            StudentVO studentVO = new StudentVO();
            BeanUtils.copyProperties(student,studentVO);
            studentVO.setDormitoryName(this.dormitoryMapper.selectById(student.getDormitoryId()).getName());
            studentVOList.add(studentVO);
        }
        PageVO pageVO = new PageVO();
        pageVO.setData(studentVOList);
        pageVO.setTotal(resultPage.getTotal());
        return pageVO;
    }

    @Override
    public Boolean moveout(Integer id, String reason) {
        Moveout moveout = new Moveout();
        Student student = this.studentMapper.selectById(id);
        moveout.setStudentId(id);
        moveout.setDormitoryId(student.getDormitoryId());
        moveout.setReason(reason);
        moveout.setCreateDate(CommonUtil.createDate());
        int insert = this.moveoutMapper.insert(moveout);
        if (insert != 1) return false;
        student.setState("迁出");
        int update = this.studentMapper.updateById(student);
        if (update != 1) return false;
        try {
            this.dormitoryMapper.addAvailable(student.getDormitoryId());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public PageVO moveoutList(Integer page, Integer size) {
        Page<Moveout> moveoutPage = new Page<>(page, size);
        Page<Moveout> resultPage = this.moveoutMapper.selectPage(moveoutPage, null);
        PageVO pageVO = new PageVO();
        pageVO.setTotal(resultPage.getTotal());
        List<MoveoutVO> moveoutVOList = new ArrayList<>();
        for (Moveout moveout : resultPage.getRecords()) {
            MoveoutVO moveoutVO = new MoveoutVO();
            BeanUtils.copyProperties(moveout, moveoutVO);
            Student student = this.studentMapper.selectById(moveout.getStudentId());
            Dormitory dormitory = this.dormitoryMapper.selectById(moveout.getDormitoryId());
            moveoutVO.setStudentName(student.getName());
            moveoutVO.setDormitoryName(dormitory.getName());
            moveoutVOList.add(moveoutVO);
        }
        pageVO.setData(moveoutVOList);
        return pageVO;
    }

    @Override
    public PageVO moveoutSearch(SearchForm searchForm) {
        Page<Moveout> moveoutPage = new Page<>(searchForm.getPage(), searchForm.getSize());
        Page<Moveout> resultPage = null;
        if(searchForm.getValue().equals("")){
            resultPage = this.moveoutMapper.selectPage(moveoutPage, null);
        } else {
            QueryWrapper<Moveout> queryWrapper = new QueryWrapper<>();
            if(searchForm.getKey().equals("studentName")){
                QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
                studentQueryWrapper.like("name", searchForm.getValue());
                List<Student> studentList = this.studentMapper.selectList(studentQueryWrapper);
                List<Integer> idList = new ArrayList<>();
                for (Student student : studentList) {
                    idList.add(student.getId());
                }
                queryWrapper.in("student_id", idList);
            }
            if(searchForm.getKey().equals("dormitoryName")){
                QueryWrapper<Dormitory> dormitoryQueryWrapper = new QueryWrapper<>();
                dormitoryQueryWrapper.like("name", searchForm.getValue());
                List<Dormitory> dormitoryList = this.dormitoryMapper.selectList(dormitoryQueryWrapper);
                List<Integer> idList = new ArrayList<>();
                for (Dormitory dormitory : dormitoryList) {
                    idList.add(dormitory.getId());
                }
                queryWrapper.in("dormitory_id", idList);
            }
            resultPage = this.moveoutMapper.selectPage(moveoutPage, queryWrapper);
        }
        PageVO pageVO = new PageVO();
        pageVO.setTotal(resultPage.getTotal());
        List<MoveoutVO> moveoutVOList = new ArrayList<>();
        for (Moveout moveout : resultPage.getRecords()) {
            MoveoutVO moveoutVO = new MoveoutVO();
            BeanUtils.copyProperties(moveout, moveoutVO);
            Student student = this.studentMapper.selectById(moveout.getStudentId());
            Dormitory dormitory = this.dormitoryMapper.selectById(moveout.getDormitoryId());
            moveoutVO.setStudentName(student.getName());
            moveoutVO.setDormitoryName(dormitory.getName());
            moveoutVOList.add(moveoutVO);
        }
        pageVO.setData(moveoutVOList);
        return pageVO;
    }
}




