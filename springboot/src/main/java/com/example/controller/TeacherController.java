package com.example.controller;

import com.example.common.Result;
import com.example.entity.Teacher;
import com.example.service.TeacherService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 管理员前端操作接口
 **/
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Resource
    private TeacherService teacherService;

    @PostMapping("/add")
    public Result add(@RequestBody Teacher teacher) {
        teacherService.add(teacher);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        teacherService.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        teacherService.deleteBatch(ids);
        return Result.success();
    }

    @PutMapping("/update")
    public Result updateById(@RequestBody Teacher teacher) {
        teacherService.updateById(teacher);
        return Result.success();
    }


    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Teacher teacher = teacherService.selectById(id);
        return Result.success(teacher);
    }


    @GetMapping("/selectAll")
    public Result selectAll(Teacher teacher ) {
        List<Teacher> list = teacherService.selectAll(teacher);
        return Result.success(list);
    }


    @GetMapping("/selectPage")
    public Result selectPage(Teacher teacher,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Teacher> page = teacherService.selectPage(teacher, pageNum, pageSize);
        return Result.success(page);
    }

}