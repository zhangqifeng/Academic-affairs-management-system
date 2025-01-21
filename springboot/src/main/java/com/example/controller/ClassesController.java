package com.example.controller;

import com.example.common.Result;
import com.example.entity.Classes;
import com.example.service.ClassesService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/classes")
public class ClassesController {

    @Resource
    private ClassesService classesService;

    @PostMapping("/add")
    public Result add(@RequestBody Classes classes) {
        classesService.add(classes);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        classesService.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        classesService.deleteBatch(ids);
        return Result.success();
    }


    @PutMapping("/update")
    public Result updateById(@RequestBody Classes classes) {
        classesService.updateById(classes);
        return Result.success();
    }

    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Classes classes = classesService.selectById(id);
        return Result.success(classes);
    }

    @GetMapping("/selectAll")
    public Result selectAll(Classes classes ) {
        List<Classes> list = classesService.selectAll(classes);
        return Result.success(list);
    }

    @GetMapping("/selectPage")
    public Result selectPage(Classes classes,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Classes> page = classesService.selectPage(classes, pageNum, pageSize);
        return Result.success(page);
    }

}