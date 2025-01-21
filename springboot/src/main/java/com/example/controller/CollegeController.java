package com.example.controller;

import com.example.common.Result;
import com.example.entity.College;
import com.example.service.CollegeService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 公告信息表前端操作接口
 **/
@RestController
@RequestMapping("/college")
public class CollegeController {

    @Resource
    private CollegeService collegeService;


    @PostMapping("/add")
    public Result add(@RequestBody College college) {
        collegeService.add(college);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        collegeService.deleteById(id);
        return Result.success();
    }


    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        collegeService.deleteBatch(ids);
        return Result.success();
    }


    @PutMapping("/update")
    public Result updateById(@RequestBody College college) {
        collegeService.updateById(college);
        return Result.success();
    }


    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        College college = collegeService.selectById(id);
        return Result.success(college);
    }


    @GetMapping("/selectAll")
    public Result selectAll(College college ) {
        List<College> list = collegeService.selectAll(college);
        return Result.success(list);
    }

    @GetMapping("/selectPage")
    public Result selectPage(College college,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<College> page = collegeService.selectPage(college, pageNum, pageSize);
        return Result.success(page);
    }

}