package com.teamntp.firstproject.training.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainingDTO {

    // 훈련일지
    private long id;
    private long course_Idx;

    private int jaejuk;
    private int attend;
    private int miss;
    private int late;
    private int early;

    private String courseName;
    private LocalDate courseStartDate;
    private LocalDate courseEndDate;

    private int totalDate;
    private int totalcourseTime;

//    private String enrolled;
//    private String rolled;
//    private String less;
//    private String late;
//    private String off;

    private String trainingCourse1;
    private String trainingTeacher1;
    private String trainingContent1;

    private String trainingCourse2;
    private String trainingTeacher2;
    private String trainingContent2;

    private String trainingCourse3;
    private String trainingTeacher3;
    private String trainingContent3;

    private String trainingCourse4;
    private String trainingTeacher4;
    private String trainingContent4;

    private String trainingCourse5;
    private String trainingTeacher5;
    private String trainingContent5;

    private String trainingCourse6;
    private String trainingTeacher6;
    private String trainingContent6;

    private String trainingCourse7;
    private String trainingTeacher7;
    private String trainingContent7;

    private boolean dayToString;
    private String dayFull;
    private String dayLess;
    private String dayJab;
    private String dayEtc;
    private String daySum;

    private String runningFull;
    private String runningLess;
    private String runningJab;
    private String runningEtc;
    private String runningSum;

    private String RemarksAbsent;
    private String RemarksLate;
    private String RemarksOff;
    private String RemarksContent;

    // todo 생성자 정리  나중에 필요한거 정리

    public TrainingDTO(long course_Idx, int id, LocalDate courseStartDate, LocalDate courseEndDate, String courseName) {
        this.course_Idx = course_Idx;
        this.id = id;
        this.courseName= courseName;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
    }




}
