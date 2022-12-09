package com.southwind.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.southwind.entity.Absent;
import com.southwind.entity.Dormitory;
import com.southwind.entity.Student;
import com.southwind.form.RuleForm;
import com.southwind.form.SearchForm;
import com.southwind.service.AbsentService;
import com.southwind.service.BuildingService;
import com.southwind.service.DormitoryService;
import com.southwind.service.StudentService;
import com.southwind.utils.ResultVOUtil;
import com.southwind.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Heylian
 * @since 2022-11-21
 */
@RestController
@RequestMapping("/absent")
public class AbsentController {

    @Autowired
    private AbsentService absentService;
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private DormitoryService dormitoryService;
    @Autowired
    private StudentService studentService;

    @GetMapping("/list/{page}/{size}")
    public ResultVO list(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return ResultVOUtil.success(this.absentService.list(page,size));
    }

    @GetMapping("/search")
    public ResultVO search(SearchForm searchForm) {
        return ResultVOUtil.success(this.absentService.search(searchForm));
    }

    @GetMapping("/buildingList")
    public ResultVO buildingList() {
        return ResultVOUtil.success(this.buildingService.list());
    }

    @GetMapping("/findDormitoryByBuildingId/{id}")
    public ResultVO findDormitoryByBuildingId(@PathVariable("id") Integer id) {
        QueryWrapper<Dormitory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("building_id",id);
        List<Dormitory> dormitoryList = this.dormitoryService.list(queryWrapper);
        return ResultVOUtil.success(dormitoryList);
    }

    @GetMapping("/findStudentByDormitoryId/{id}")
    public ResultVO findStudentByDormitoryId(@PathVariable("id") Integer id) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<Student>();
        queryWrapper.eq("dormitory_id",id);
        List<Student> studentList = this.studentService.list(queryWrapper);
        return ResultVOUtil.success(studentList);
    }

    @PostMapping("/save")
    public ResultVO save(@RequestBody Absent absent) {
        String createDate = absent.getCreateDate();
        createDate = createDate.substring(0,10);
        absent.setCreateDate(createDate);
        boolean save = this.absentService.save(absent);
        if (!save) return ResultVOUtil.fail();
        return ResultVOUtil.success(null);

    }
}
