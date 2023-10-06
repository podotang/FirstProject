package com.teamntp.firstproject.attendance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDTO {
    private int id;
    private long studentIdx;
    private long memberIdx;
    private long courseIdx;
    private String name;
    private LocalDate regDate;
    private int cperiod;
    private String status;

    // todo 생성자 정리
    public AttendanceDTO (long studentIdx, int id, String name,LocalDate regDate) {
        this.studentIdx = studentIdx;
        this.id = id;
        this.name = name;
        this.regDate = regDate;
    }

    public AttendanceDTO(long studentIdx, int id, String name) {
        this.studentIdx = studentIdx;
        this.id = id;
        this.name = name;
    }

}
