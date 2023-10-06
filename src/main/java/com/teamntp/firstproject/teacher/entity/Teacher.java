package com.teamntp.firstproject.teacher.entity;

import com.teamntp.firstproject.course.entity.Course;
import com.teamntp.firstproject.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"teacher","course"})
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teacherIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    private Course course;
}
