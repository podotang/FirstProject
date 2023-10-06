package com.teamntp.firstproject.training.entity;


import com.teamntp.firstproject.course.entity.Course;
import com.teamntp.firstproject.training.dto.TrainingDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//@ToString(exclude = {"course"})
public class Training {

    // 훈련일지
    // 관계를 매핑하는 엔터티 클래스
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trainingIdx;

    @ManyToOne(fetch = FetchType.LAZY) // 훈련과정
    @JoinColumn(name = "course_idx")
    private Course course;

    @Transient
    private String courseName;
    @Transient
    private LocalDate courseStartDate;
    @Transient
    private LocalDate courseEndDate;

    private int totalDate;
    private int totalcourseTime;

    private int jaejuk;
    private int attend;
    private int miss;
    private int late;
    private int early;

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


    // todo 일단 임시 처리
    public static Training toSaveEntity(TrainingDTO trainingDTO){
        Training training = Training.builder()
                .courseStartDate(trainingDTO.getCourseStartDate())
                .courseEndDate(trainingDTO.getCourseEndDate())
                .totalDate(trainingDTO.getTotalDate())
                .totalcourseTime(trainingDTO.getTotalcourseTime())
                .dayFull(trainingDTO.getDayFull())
                .dayLess(trainingDTO.getDayLess())
                .dayJab(trainingDTO.getDayJab())
                .dayEtc(trainingDTO.getDayEtc())
                .daySum(trainingDTO.getDayEtc())

                .trainingCourse1(trainingDTO.getTrainingCourse1())
                .trainingTeacher1(trainingDTO.getTrainingTeacher1())
                .trainingContent1(trainingDTO.getTrainingContent1())

                .trainingCourse2(trainingDTO.getTrainingCourse2())
                .trainingTeacher2(trainingDTO.getTrainingTeacher2())
                .trainingContent2(trainingDTO.getTrainingContent2())

                .trainingCourse3(trainingDTO.getTrainingCourse3())
                .trainingTeacher3(trainingDTO.getTrainingTeacher3())
                .trainingContent3(trainingDTO.getTrainingContent3())

                .trainingCourse4(trainingDTO.getTrainingCourse4())
                .trainingTeacher4(trainingDTO.getTrainingTeacher4())
                .trainingContent4(trainingDTO.getTrainingContent4())

                .trainingCourse5(trainingDTO.getTrainingCourse5())
                .trainingTeacher5(trainingDTO.getTrainingTeacher5())
                .trainingContent5(trainingDTO.getTrainingContent5())

                .trainingCourse6(trainingDTO.getTrainingCourse6())
                .trainingTeacher6(trainingDTO.getTrainingTeacher6())
                .trainingContent6(trainingDTO.getTrainingContent6())

                .trainingCourse7(trainingDTO.getTrainingCourse7())
                .trainingTeacher7(trainingDTO.getTrainingTeacher7())
                .trainingContent7(trainingDTO.getTrainingContent7())

                .jaejuk(trainingDTO.getJaejuk())
                .attend(trainingDTO.getAttend())
                .miss(trainingDTO.getMiss())
                .late(trainingDTO.getLate())
                .early(trainingDTO.getEarly())

                .runningFull(trainingDTO.getRunningFull())
                .runningLess(trainingDTO.getRunningLess())
                .runningJab(trainingDTO.getRunningJab())
                .runningEtc(trainingDTO.getRunningEtc())
                .runningSum(trainingDTO.getRunningSum())

                .RemarksAbsent(trainingDTO.getRemarksAbsent())
                .RemarksLate(trainingDTO.getRemarksLate())
                .RemarksOff(trainingDTO.getRemarksOff())
                .RemarksContent(trainingDTO.getRemarksContent())
                .build();
        return training;
    }

}