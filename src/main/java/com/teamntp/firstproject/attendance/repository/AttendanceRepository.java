package com.teamntp.firstproject.attendance.repository;

import com.teamntp.firstproject.attendance.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

}
