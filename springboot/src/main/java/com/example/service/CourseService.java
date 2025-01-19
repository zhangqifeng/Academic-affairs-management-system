package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.Constants;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Course;
import com.example.exception.CustomException;
import com.example.mapper.CourseMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CourseService {

    @Resource
    private CourseMapper courseMapper;

    /**
     * 新增
     */
    public void add(Course course) {
        Course dbCourse = courseMapper.selectByUsername(course.getUsername());
        if (ObjectUtil.isNotNull(dbCourse)) {
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        if (ObjectUtil.isEmpty(course.getPassword())) {
            course.setPassword(Constants.USER_DEFAULT_PASSWORD);
        }
        if (ObjectUtil.isEmpty(course.getName())) {
            course.setName(course.getUsername());
        }
        course.setRole(RoleEnum.STUDENT.name());
        courseMapper.insert(course);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        courseMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            courseMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Course course) {
        courseMapper.updateById(course);
    }

    /**
     * 根据ID查询
     */
    public Course selectById(Integer id) {
        return courseMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Course> selectAll(Course course) {
        return courseMapper.selectAll(course);
    }

    /**
     * 分页查询
     */
    public PageInfo<Course> selectPage(Course course, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        if(RoleEnum.TEACHER.name().equals(currentUser.getRole())) {
            course.setTeacherId(currentUser.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Course> list = courseMapper.selectAll(course);
        return PageInfo.of(list);
    }
}
