package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.Constants;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.common.enums.SegmentEnum;
import com.example.common.enums.WeekEnum;
import com.example.entity.Account;
import com.example.entity.Choice;
import com.example.entity.Course;
import com.example.entity.Curriculum;
import com.example.exception.CustomException;
import com.example.mapper.ChoiceMapper;
import com.example.mapper.CourseMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChoiceService {

    @Resource
    private ChoiceMapper choiceMapper;
    @Resource
    private CourseMapper courseMapper;

    /**
     * 新增
     */
    public void add(Choice choice) {
        Course course = courseMapper.selectById(choice.getCourseId());
        List<Choice> list =choiceMapper.selectByCourseId(choice.getCourseId());
        if (course.getNum().equals(list.size())) {
            throw new CustomException(ResultCodeEnum.COURSE_NUM_ERROR);
        }

        List<Choice> sList =choiceMapper.selectByStudentId(choice.getStudentId());
        for(Choice dbChoice : sList) {
            Course tmpCourse = courseMapper.selectById(dbChoice.getCourseId());
            if(course.getWeek().equals(tmpCourse.getWeek()) && choice.getSegment().equals(tmpCourse.getSegment())) {
                throw new CustomException("-1","您之前已经选过" + tmpCourse.getNum() + ",与该门课的上课时间冲突，请重新选择");
            }
        }
        choiceMapper.insert(choice);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        choiceMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            choiceMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Choice choice) {
        choiceMapper.updateById(choice);
    }

    /**
     * 根据ID查询
     */
    public Choice selectById(Integer id) {
        return choiceMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Choice> selectAll(Choice choice) {
        return choiceMapper.selectAll(choice);
    }

    /**
     * 分页查询
     */
    public PageInfo<Choice> selectPage(Choice choice, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        if(RoleEnum.TEACHER.name().equals(currentUser.getRole())) {
            choice.setTeacherId(currentUser.getId());
        }
        if(RoleEnum.STUDENT.name().equals(currentUser.getRole())) {
            choice.setStudentId(currentUser.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Choice> list = choiceMapper.selectAll(choice);
        return PageInfo.of(list);
    }

    public List<Curriculum> getCurriculum() {
        Account currentUser = TokenUtils.getCurrentUser();
        Choice choice = new Choice();
        choice.setStudentId(currentUser.getId());
        List<Choice> choiceList = choiceMapper.selectAll(choice);

        List<Curriculum> list = new ArrayList<>();

        Curriculum first = new Curriculum();
        first.setSegment(SegmentEnum.FIRST.segment);
        processWeek(first, getWeekChoiceList(choiceList,SegmentEnum.FIRST.segment));
        list.add(first);

        Curriculum second = new Curriculum();
        second.setSegment(SegmentEnum.SECOND.segment);
        processWeek(second,getWeekChoiceList(choiceList,SegmentEnum.SECOND.segment));
        list.add(second);

        Curriculum third = new Curriculum();
        third.setSegment(SegmentEnum.THIRD.segment);
        processWeek(third,getWeekChoiceList(choiceList,SegmentEnum.THIRD.segment));
        list.add(third);

        Curriculum forth = new Curriculum();
        forth.setSegment(SegmentEnum.FORTH.segment);
        processWeek(forth,getWeekChoiceList(choiceList,SegmentEnum.FORTH.segment));
        list.add(forth);

        Curriculum fifth = new Curriculum();
        fifth.setSegment(SegmentEnum.FIFTH.segment);
        processWeek(fifth,getWeekChoiceList(choiceList,SegmentEnum.FIFTH.segment));
        list.add(fifth);



        return list;
    }

    private List<Choice> getWeekChoiceList(List<Choice> choiceList,String segment) {
        return choiceList.stream().filter(x -> x.getSegment().equals(segment)).collect(Collectors.toList());
    }

    private void processWeek(Curriculum curriculum,List<Choice> choiceList) {
        Optional<Choice> first = choiceList.stream().filter(x ->x.getWeek().equals(WeekEnum.MONDAY.week)).findFirst();
        first.ifPresent(choice -> curriculum.setMonday(choice.getName() + "(" + choice.getTeacherName() + ")"));

        Optional<Choice> second = choiceList.stream().filter(x ->x.getWeek().equals(WeekEnum.TUESDAY.week)).findFirst();
        second.ifPresent(choice -> curriculum.setTuesday(choice.getName() + "(" + choice.getTeacherName() + ")"));

        Optional<Choice> third = choiceList.stream().filter(x ->x.getWeek().equals(WeekEnum.WEDNESDAY.week)).findFirst();
        third.ifPresent(choice -> curriculum.setWednesday(choice.getName() + "(" + choice.getTeacherName() + ")"));

        Optional<Choice> forth = choiceList.stream().filter(x ->x.getWeek().equals(WeekEnum.THURSDAY.week)).findFirst();
        forth.ifPresent(choice -> curriculum.setTuesday(choice.getName() + "(" + choice.getTeacherName() + ")"));

        Optional<Choice> fifth = choiceList.stream().filter(x ->x.getWeek().equals(WeekEnum.FRIDAY.week)).findFirst();
        fifth.ifPresent(choice -> curriculum.setFriday(choice.getName() + "(" + choice.getTeacherName() + ")"));

        Optional<Choice> sixth = choiceList.stream().filter(x ->x.getWeek().equals(WeekEnum.SATURDAY.week)).findFirst();
        sixth.ifPresent(choice -> curriculum.setSaturday(choice.getName() + "(" + choice.getTeacherName() + ")"));

        Optional<Choice> seventh = choiceList.stream().filter(x ->x.getWeek().equals(WeekEnum.SUNDAY.week)).findFirst();
        seventh.ifPresent(choice -> curriculum.setSunday(choice.getName() + "(" + choice.getTeacherName() + ")"));


    }
}
