package com.teamntp.firstproject.teacher.repository;

import com.teamntp.firstproject.teacher.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long>  {
}
