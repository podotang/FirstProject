package com.teamntp.firstproject.teacher.controller;

import com.teamntp.firstproject.course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member/teacher")
@Log4j2
@RequiredArgsConstructor
public class TeacherController {

    private final CourseRepository courseRepository;

    @GetMapping("/register")
    public void register(Model model) {
        log.info("/member/student/register..........");
        // 현재 진행 중인 과정 목록 불러와서 model 에 담기
        model.addAttribute("result", courseRepository.getCourseListInProgress());
    }
}
