package com.southwind.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.southwind.entity.Dormitory;
import com.southwind.entity.Student;
import com.southwind.form.SearchForm;
import com.southwind.mapper.BuildingMapper;
import com.southwind.mapper.StudentMapper;
import com.southwind.service.DormitoryService;
import com.southwind.mapper.DormitoryMapper;
import com.southwind.utils.ResultVOUtil;
import com.southwind.vo.DormitoryVO;
import com.southwind.vo.PageVO;
import com.southwind.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

/**
* @author 16176
* @description 针对表【dormitory】的数据库操作Service实现
* @createDate 2022-11-21 19:51:00
*/
@Service
public class DormitoryServiceImpl extends ServiceImpl<DormitoryMapper, Dormitory>
    implements DormitoryService{

    @Autowired
    private DormitoryMapper dormitoryMapper;
    @Autowired
    private BuildingMapper buildingMapper;
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public PageVO list(Integer page, Integer size) {
        Page<Dormitory> dormitoryPage = new Page<>(page, size);
        Page<Dormitory> resultPage = this.dormitoryMapper.selectPage(dormitoryPage, null);
        List<DormitoryVO> dormitoryVOList = new ArrayList<>();
        for (Dormitory dormitory : resultPage.getRecords()) {
            DormitoryVO dormitoryVO = new DormitoryVO();
            BeanUtils.copyProperties(dormitory,dormitoryVO);
            dormitoryVO.setBuildingName(this.buildingMapper.selectById(dormitory.getBuildingId()).getName());
            dormitoryVOList.add(dormitoryVO);
        }
        PageVO pageVO = new PageVO();
        pageVO.setData(dormitoryVOList);
        pageVO.setTotal(resultPage.getTotal());
        return pageVO;
    }

    @Override
    public PageVO search(SearchForm searchForm) {
        Page<Dormitory> dormitoryPage = new Page<>(searchForm.getPage(),searchForm.getSize());
        Page<Dormitory> resultPage;
        if (searchForm.getValue().equals("")) {
            resultPage = this.dormitoryMapper.selectPage(dormitoryPage,null);
        } else {
            QueryWrapper<Dormitory> queryWrapper = new QueryWrapper<>();
            queryWrapper.like(searchForm.getKey(),searchForm.getValue());
            resultPage = this.dormitoryMapper.selectPage(dormitoryPage,queryWrapper);
        }
        List<DormitoryVO> dormitoryVOList = new ArrayList<>();
        for (Dormitory dormitory : resultPage.getRecords()) {
            DormitoryVO dormitoryVO = new DormitoryVO();
            BeanUtils.copyProperties(dormitory,dormitoryVO);
            dormitoryVO.setBuildingName(this.buildingMapper.selectById(dormitory.getBuildingId()).getName());
            dormitoryVOList.add(dormitoryVO);
        }
        PageVO pageVO = new PageVO();
        pageVO.setTotal(resultPage.getTotal());
        pageVO.setData(dormitoryVOList);
        return pageVO;
    }

    @Override
    public Boolean deleteById(Integer id) {
        // 查询该宿舍中的所有学生
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("dormitory_id",id);
        List<Student> studentList = this.studentMapper.selectList(studentQueryWrapper);
        // 给每个学生分配一个新的宿舍
        for (Student student : studentList) {
            Integer availableDormitoryId = this.dormitoryMapper.findAvailableDormitoryId();
            student.setDormitoryId(availableDormitoryId);
            // 更新学生数据，更新宿舍数据
            try {
                this.studentMapper.updateById(student);
                this.dormitoryMapper.subAvailable(availableDormitoryId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 删除宿舍
        int delete = this.dormitoryMapper.deleteById(id);
        if (delete != 1) return false;
        return true;
    }
}




