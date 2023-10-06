package com.teamntp.firstproject.training.service;

import com.teamntp.firstproject.course.service.CourseService;
import com.teamntp.firstproject.training.dto.TrainingDTO;
import com.teamntp.firstproject.training.entity.Training;
import com.teamntp.firstproject.training.repository.TrainingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService{
    private final TrainingRepository trainingRepository;
    private final CourseService courseService;

    public void save(TrainingDTO trainingDTO){
        Training training = Training.toSaveEntity(trainingDTO);
        trainingRepository.save(training);
    }




// course
//    private String courseName; // 훈련과정명
//    private LocalDate startDate; // 과정 시작일
//    private LocalDate endDate; // 과정 종료일

//  private String status;
// 상태
}

// 오류 :: 'toSaveEntity(TrainingDTO)' in 'com.teamntp.firstproject.training.entity.Training' cannot be applied to '(com.teamntp.firstproject.training.dto.TrainingDTO)'
// 그러나 이 메서드는 유형의 인수를 허용하도록 정의되지 않았습니다
// entity-Training :: import 후 해결