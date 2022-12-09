package com.southwind.controller;


import com.southwind.form.SearchForm;
import com.southwind.service.MoveoutService;
import com.southwind.utils.ResultVOUtil;
import com.southwind.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Heylian
 * @since 2022-11-21
 */
@RestController
@RequestMapping("/moveout")
public class MoveoutController {

    @Autowired
    private MoveoutService moveoutService;

    // 查询所有入住学生的记录
    @GetMapping("/list/{page}/{size}")
    public ResultVO list(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return ResultVOUtil.success(this.moveoutService.list(page,size));
    }

    @GetMapping("/search")
    public ResultVO search(SearchForm searchForm) {
        return ResultVOUtil.success(this.moveoutService.search(searchForm));
    }

    @PutMapping("/moveout/{id}/{reason}")
    public ResultVO moveout(@PathVariable("id") Integer id,@PathVariable("reason") String reason) {
        Boolean moveout = this.moveoutService.moveout(id,reason);
        if(!moveout) return ResultVOUtil.fail();
        return ResultVOUtil.success(null);
    }

    // 查询迁出学生的记录
    @GetMapping("/moveoutList/{page}/{size}")
    public ResultVO moveoutList(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        return ResultVOUtil.success(this.moveoutService.moveoutList(page, size));
    }

    // 搜索查询迁出学生的记录
    @GetMapping("/moveoutSearch")
    public ResultVO moveoutSearch(SearchForm searchForm){
        return ResultVOUtil.success(this.moveoutService.moveoutSearch(searchForm));
    }


}
