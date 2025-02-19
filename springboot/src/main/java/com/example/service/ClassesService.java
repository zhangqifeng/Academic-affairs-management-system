package com.example.service;

import com.example.entity.Classes;
import com.example.mapper.ClassesMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 公告信息表业务处理
 **/
@Service
public class ClassesService {

    @Resource
    private ClassesMapper classesMapper;


    public void add(Classes classes) {
        classesMapper.insert(classes);
    }


    public void deleteById(Integer id) {
        classesMapper.deleteById(id);
    }


    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            classesMapper.deleteById(id);
        }
    }


    public void updateById(Classes classes) {
        classesMapper.updateById(classes);
    }

    public Classes selectById(Integer id) {
        return classesMapper.selectById(id);
    }

    public List<Classes> selectAll(Classes classes) {
        return classesMapper.selectAll(classes);
    }

    public PageInfo<Classes> selectPage(Classes classes, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Classes> list = classesMapper.selectAll(classes);
        return PageInfo.of(list);
    }

}
