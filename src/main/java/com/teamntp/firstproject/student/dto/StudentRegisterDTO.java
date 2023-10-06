package com.teamntp.firstproject.student.dto;

import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
//@NoArgsConstructor
@Data
public class StudentRegisterDTO {


    private static Long serialNum = 1000L; // 학생 일련번호(4자리)

    @Builder.Default
    private static Long year = (long) LocalDate.now().getYear(); // 연도(4자리)

    // todo 학생코드-연도(4자리)과정(3자리)학생일련번호(4자리)
    private Long studentIdx;

    private Long memberIdx;

    private Long courseIdx; // 과정코드(3자리)

    private String courseName;

    private LocalDate startDate;

    private LocalDate endDate;

    public Long createStudentIdx(long courseIdx) { // todo 학생 코드 변수 처리
        String year = String.valueOf(LocalDate.now().getYear());
        String strCourseIdx = String.valueOf(courseIdx);
        String serialNum = String.valueOf(this.serialNum);

        String strStudentIdx = year + strCourseIdx + serialNum;

        long studentIdx = Long.parseLong(strStudentIdx);
        this.serialNum++;

        return studentIdx;

    }
}
