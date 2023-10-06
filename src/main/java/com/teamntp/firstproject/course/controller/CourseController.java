package com.teamntp.firstproject.course.controller;

import com.teamntp.firstproject.common.dto.PageRequestDTO;
import com.teamntp.firstproject.course.dto.CourseDTO;
import com.teamntp.firstproject.course.repository.CourseRepository;
import com.teamntp.firstproject.course.repository.SyllabusRepository;
import com.teamntp.firstproject.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/member/course/")
@Log4j2
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final CourseRepository courseRepository;
    private final SyllabusRepository syllabusRepository;

    @GetMapping("/register")
    public  void register() {
        log.info("/course/register................");
    }

    @PostMapping("/register")
    public String register(CourseDTO courseDTO, RedirectAttributes redirectAttributes) {
        log.info("==================================================");
        log.info("CourseDTO: "+courseDTO);
        log.info("==================================================");

        // save 처리
        Long courseIdx = courseService.register(courseDTO);

        // 일회성 반환
        redirectAttributes.addFlashAttribute("msg",courseIdx);

        return "redirect:/member/course/list"; // 훈련과정 등록 후 목록 페이지로 이동

    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("/member/course/list................");
        log.info("pageRequestDTO: "+pageRequestDTO);

        model.addAttribute("result", courseService.getList(pageRequestDTO));
    }


    @GetMapping("/read")
    public void view(@ModelAttribute CourseDTO courseDTO, Model model) {

        System.out.println("idx = " + courseDTO.getCourseIdx());
        CourseDTO cDTO = courseService.getRow(courseDTO);

        model.addAttribute("cDTO", cDTO);
    }

    @GetMapping("/delete")
    @Transactional
    public String delete(long idx) {

        syllabusRepository.deleteByCourseCourseIdx(idx);
        courseRepository.deleteById(idx);

        return "redirect:/member/course/list";
    }


}
