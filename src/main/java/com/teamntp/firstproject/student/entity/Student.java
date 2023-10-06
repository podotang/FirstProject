package com.teamntp.firstproject.student.entity;

import com.teamntp.firstproject.attendance.entity.Attendance;
import com.teamntp.firstproject.course.entity.Course;
import com.teamntp.firstproject.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode // 필수
@ToString(exclude = {"member","course"})
@IdClass(StudentPK.class) // 식별자 클래스에 아이디가 모두 존재해야 함
public class Student {

    // JPA 복합키 mapping
    // todo 복합키 기존(studentIdx,courseIdx) -> 변경(memberIdx, courseIdx)
    @Id
    @Column(name="student_idx")
    private Long studentIdx; // 학생코드-연도(4자리)과정(3자리)학생일련번호(4자리)

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="course_idx")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_idx")
    private Member member;

//    @OneToMany
//    @JoinColumn(name = "StudentStudentIdx")
//    private List<Attendance> attendanceList = new ArrayList<>();
}
