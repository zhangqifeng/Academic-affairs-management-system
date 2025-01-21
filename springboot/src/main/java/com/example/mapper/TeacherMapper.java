package com.example.mapper;

import com.example.entity.Teacher;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 操作teacher相关数据接口
 */
public interface TeacherMapper {


    int insert(Teacher teacher);


    int deleteById(Integer id);


    int updateById(Teacher teacher);


    Teacher selectById(Integer id);


    List<Teacher> selectAll(Teacher teacher);

    @Select("select * from teacher where username = #{username}")
    Teacher selectByUsername(String username);
}