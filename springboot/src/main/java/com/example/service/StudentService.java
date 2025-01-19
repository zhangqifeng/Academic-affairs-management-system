package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.Constants;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Student;
import com.example.exception.CustomException;
import com.example.mapper.StudentMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StudentService {

    @Resource
    private StudentMapper studentMapper;

    /**
     * 新增
     */
    public void add(Student student) {
        Student dbStudent = studentMapper.selectByUsername(student.getUsername());
        if (ObjectUtil.isNotNull(dbStudent)) {
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        if (ObjectUtil.isEmpty(student.getPassword())) {
            student.setPassword(Constants.USER_DEFAULT_PASSWORD);
        }
        if (ObjectUtil.isEmpty(student.getName())) {
            student.setName(student.getUsername());
        }
        student.setRole(RoleEnum.STUDENT.name());
        studentMapper.insert(student);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        studentMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            studentMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Student student) {
        studentMapper.updateById(student);
    }

    /**
     * 根据ID查询
     */
    public Student selectById(Integer id) {
        return studentMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Student> selectAll(Student student) {
        return studentMapper.selectAll(student);
    }

    /**
     * 分页查询
     */
    public PageInfo<Student> selectPage(Student student, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Student> list = studentMapper.selectAll(student);
        return PageInfo.of(list);
    }
}
