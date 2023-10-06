package com.teamntp.firstproject.student.controller;

import com.teamntp.firstproject.course.entity.Course;
import com.teamntp.firstproject.course.repository.CourseRepository;
import com.teamntp.firstproject.course.service.CourseService;
import com.teamntp.firstproject.member.entity.Member;
import com.teamntp.firstproject.security.dto.AuthMemberDTO;
import com.teamntp.firstproject.student.dto.StudentRegisterDTO;
import com.teamntp.firstproject.student.entity.Student;
import com.teamntp.firstproject.student.repository.StudentRepository;
import com.teamntp.firstproject.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/member/student/")
@Log4j2
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;
    private final CourseService courseService;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    @GetMapping("/register")
    public void register(Model model) {
        log.info("/member/student/register..........");
        // 현재 진행 중인 과정 목록 불러와서 model 에 담기
        model.addAttribute("result", courseRepository.getCourseListInProgress());
    }

    @PostMapping("/register")
    public  String registerPost(StudentRegisterDTO studentRegisterDTO
    , @AuthenticationPrincipal AuthMemberDTO authMemberDTO) {
        log.info("StudentRegisterDTO: " + studentRegisterDTO);
        log.info("AuthMemberDTO: " + authMemberDTO);
        // 실제 등록 처리
        // todo 중복 체크하는 부분 service 로 분리해서 정리하기
        Long memberCheck = studentRepository.getStudentIdxByMemberIdx(studentRegisterDTO.getMemberIdx());
        Optional<Student> courseCheck = studentRepository.findByMember_MemberIdxAndCourse_CourseIdx(studentRegisterDTO.getMemberIdx(), studentRegisterDTO.getCourseIdx());

        Long studentIdx = null;
        Long courseIdx = null;
        if(memberCheck != null) {
            studentIdx = courseCheck.get().getStudentIdx();
            studentRegisterDTO.setStudentIdx(studentIdx);
        }

        if(courseCheck.isPresent()) {
            courseIdx = courseCheck.get().getCourse().getCourseIdx();
            studentRegisterDTO.setCourseIdx(courseIdx);
        } else {
            studentIdx = studentRegisterDTO.createStudentIdx(studentRegisterDTO.getCourseIdx());
            studentRegisterDTO.setStudentIdx(studentIdx);
        }

        Member member = Member.builder()
                .memberIdx(studentRegisterDTO.getMemberIdx())
                .build();
        Course course = Course.builder()
                .courseIdx(studentRegisterDTO.getCourseIdx())
                .build();
        Student student = Student.builder()
                .studentIdx(studentRegisterDTO.getStudentIdx())
                .member(member)
                .course(course)
                .build();
        studentRepository.save(student);

        return "redirect:/member/student/register";
    }

}
