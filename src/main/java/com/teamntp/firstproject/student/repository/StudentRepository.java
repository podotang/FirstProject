package com.teamntp.firstproject.student.repository;

import com.teamntp.firstproject.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("select distinct s.studentIdx from Student s where s.member.memberIdx=:memberIdx")
    Long getStudentIdxByMemberIdx(Long memberIdx);

    Optional<Student> findByMember_MemberIdxAndCourse_CourseIdx(Long memberIdx, Long courseIdx);


    // todo 출석부 해당 과정에 속한 학생들 가져와야함
    @Query("select distinct s , m from Student s inner join Member m on s.member = m where s.course.courseIdx=:courseIdx")
    List<Student> getStudentsByCourseIdx(Long courseIdx);
}
