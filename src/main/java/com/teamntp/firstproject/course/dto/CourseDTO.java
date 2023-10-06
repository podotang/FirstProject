package com.teamntp.firstproject.course.dto;

import com.teamntp.firstproject.course.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
//@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {

    // 과정 일련번호(3자리)
    private static Long serialNum = 100L;

    // 훈련 과정 코드 변수
    private Long courseIdx;

    @Builder.Default // 임시 처리
    private Long courseClassification = 20001L; // 훈련과정분류 20001: 직업교육훈련

    @Builder.Default // 임시 처리
    private Long centerName = 10101L; // 센터명 10101: 대구남부여성새로일하기센터

    private String courseName; // 훈련과정명

    private LocalDate startDate; // 과정 시작일

    private LocalDate endDate; // 과정 종료일

    @Builder.Default
    private List<SyllabusDTO> syllabusDTOList = new ArrayList<>(); // List 이용해서 강의계획서 담기

    // 추가
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    // model mapper
    private static ModelMapper modelMapper = new ModelMapper();
    public static CourseDTO of(Course course) {
        return modelMapper.map(course, CourseDTO.class);
    }

    public CourseDTO() { // 기본 생성자에서 코드 변수 처리
        this.courseIdx = serialNum;
        serialNum++;
    }
}
