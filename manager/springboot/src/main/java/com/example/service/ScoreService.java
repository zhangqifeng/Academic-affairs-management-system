package com.example.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Score;
import com.example.exception.CustomException;
import com.example.mapper.CourseMapper;
import com.example.mapper.ScoreMapper;
import com.example.mapper.StudentMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 公告信息表业务处理
 **/
@Service
public class ScoreService {

    @Resource
    private ScoreMapper scoreMapper;
    @Resource
    private CourseMapper courseMapper;
    @Resource
    private StudentMapper studentMapper;

    /**
     * 新增
     */
    public void add(Score score) {
        Score dbScore = scoreMapper.selectByCourseIdAndStudentId(score.getCourseId(),score.getStudentId());
        if (ObjectUtil.isNotEmpty(dbScore)){
            throw new CustomException(ResultCodeEnum.SCORE_ALREADY_ERROR);
        }
        double total = score.getOrdinaryScore() * 0.3 + score.getExamScore() * 0.7;
        score.setScore(total);
        scoreMapper.insert(score);

        if (total >= 60){
            Course course = courseMapper.selectById(score.getCourseId());
            Student student = studentMapper.selectById(score.getStudentId());
            student.setScore(student.getScore() + course.getScore());
            studentMapper.updateById(student);

        }
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        Score score = scoreMapper.selectById(id);
        scoreMapper.deleteById(id);
//        扣除学生对应的学分
        Student student = studentMapper.selectById(score.getStudentId());
        Course course = courseMapper.selectById(score.getCourseId());
        student.setScore(student.getScore() - course.getScore());
        studentMapper.updateById(student);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            scoreMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Score score) {
        scoreMapper.updateById(score);
    }

    /**
     * 根据ID查询
     */
    public Score selectById(Integer id) {
        return scoreMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Score> selectAll(Score score) {
        return scoreMapper.selectAll(score);
    }

    /**
     * 分页查询
     */
    public PageInfo<Score> selectPage(Score score, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.TEACHER.name().equals(currentUser.getRole())){
            score.setTeacherId(currentUser.getId());
        }
        if (RoleEnum.STUDENT.name().equals(currentUser.getRole())){
            score.setStudentId(currentUser.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Score> list = scoreMapper.selectAll(score);
        return PageInfo.of(list);
    }

}