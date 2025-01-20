package com.example.entity;

import java.io.Serializable;

/**
 * 管理员
*/
public class Score extends Account implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer studentId;
    private Integer courseId;
    private Integer teacherId;
    private Double ordinaryScore;
    private Double examScore;
    private Double score;


    private String studentName;
    private String courseName;
    private String teacherName;


    public Score() {
    }

    public Score(Integer id, Integer studentId, Integer courseId, Integer teacherId, Double ordinaryScore, Double examScore, Double score, String studentName, String courseName, String teacherName) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.teacherId = teacherId;
        this.ordinaryScore = ordinaryScore;
        this.examScore = examScore;
        this.score = score;
        this.studentName = studentName;
        this.courseName = courseName;
        this.teacherName = teacherName;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public Double getOrdinaryScore() {
        return ordinaryScore;
    }

    public void setOrdinaryScore(Double ordinaryScore) {
        this.ordinaryScore = ordinaryScore;
    }

    public Double getExamScore() {
        return examScore;
    }

    public void setExamScore(Double examScore) {
        this.examScore = examScore;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}