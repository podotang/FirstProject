package com.teamntp.firstproject.attendance.entity;

import com.teamntp.firstproject.attendance.dto.AttendanceDTO;
import com.teamntp.firstproject.course.entity.Course;
import com.teamntp.firstproject.member.entity.Member;
import com.teamntp.firstproject.student.entity.Student;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
//@ToString(exclude = "student")
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attendanceIdx;

    // todo 연관관계 mapping
//    @ManyToOne(fetch = FetchType.LAZY)
    private long studentIdx;

    private String name;

    private LocalDate regDate;

    private int cperiod;

    private String status;

    public static Attendance toSaveEntity(AttendanceDTO attendanceDTO){
        // 회원
        Member member = Member.builder()
                .memberIdx(attendanceDTO.getMemberIdx())
                .build();

        // 훈련과정
        Course course = Course.builder()
                .courseIdx(attendanceDTO.getCourseIdx())
                .build();

        // 학생
        Student student = Student.builder()
                .studentIdx(attendanceDTO.getStudentIdx())
                .member(member)
                .course(course)
                .build();

        // 출석부 처리
        Attendance attendance = Attendance.builder()
                .name(attendanceDTO.getName())
                .studentIdx(attendanceDTO.getStudentIdx())
//                .student(student)
                .regDate(attendanceDTO.getRegDate())
                .cperiod(attendanceDTO.getCperiod())
                .status(attendanceDTO.getStatus())
                .build();

        return attendance;
    }


}
