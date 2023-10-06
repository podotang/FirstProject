package com.teamntp.firstproject.training.controllor;

import com.teamntp.firstproject.course.entity.Course;
import com.teamntp.firstproject.course.repository.CourseRepository;
import com.teamntp.firstproject.training.dto.TrainingDTO;
import com.teamntp.firstproject.training.repository.TrainingRepository;
import com.teamntp.firstproject.training.service.ExportPdfService;
import com.teamntp.firstproject.training.service.TrainingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/member/training/")
@Log4j2
@RequiredArgsConstructor // 의존성 자동 주입
public class TrainingController { //HTTP GET 요청

    private final TrainingService trainingService;
    private final CourseRepository courseRepository;
    private final TrainingRepository trainingRepository;
    private final ExportPdfService exportPdfService;

    // 오늘날짜 showDateAndDay매소드
    @GetMapping("/form")
    public String showDateAndDay(Model model,TrainingDTO trainingDTO) {
        log.info("/member/training/form...................................");

        // todo 훈련 과정 불러와야 함
        List<Course> courseList = courseRepository.getCourseListInProgress();
        log.info("aaaa");
        System.out.println(courseList);
        log.info("aaaa");

        model.addAttribute("courseList", courseList);

        //Model 객체를 통해 뷰로 데이터를 전달
        //LocalDate 클래스를 사용하여 현재 날짜
        LocalDate now = LocalDate.now();
        // "yyyy-MM-dd" 형식으로 포맷 ->  "yyyy년 MM월 dd일" 형식 출력
        String formattedDate = now.format(DateTimeFormatter.ofPattern(" yyyy년 MM월 dd일 ")) + getDayOfWeekInKorean(now);
        //formattedDate 값을 모델에 추가하여 뷰로 전달
        model.addAttribute("formattedDate", formattedDate);

        return "/member/training/form";
    }

    @PostMapping("/form")
    public String pform(Model model,TrainingDTO trainingDTO) {
        trainingService.save(trainingDTO);
        return "/member/training/form";
    }


    private String getDayOfWeekInKorean(LocalDate date) {
        // DayOfWeek는 월요일부터 일요일까지의 요일을 나타내는 상수값
        // 해당 요일의 텍스트 표현을 반환
        // 풀 네임의 요일 표현을 사용하도록 지정
        // 한국어로 표현
        return date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
    }

// 오류 :: error: variable trainingService might not have been initialized
//    }  :: 변수가 사용되기 전에 초기화되지 않았을 수 있음을 나타냄,
//    표시된 필드나 변수 final에 를 사용하고 있기 때문에 필요한 생성자 값이 할당되지 않은 경우에 자주 발생


}
