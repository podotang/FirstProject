package com.teamntp.firstproject.attendance.controller;

import com.teamntp.firstproject.attendance.dto.AttendanceDTO;
import com.teamntp.firstproject.attendance.repository.AttendanceRepository;
import com.teamntp.firstproject.attendance.service.AttendanceService;
import com.teamntp.firstproject.course.dto.CourseDTO;
import com.teamntp.firstproject.course.entity.Course;
import com.teamntp.firstproject.course.repository.CourseRepository;
import com.teamntp.firstproject.member.dto.MemberDTO;
import com.teamntp.firstproject.student.entity.Student;
import com.teamntp.firstproject.student.repository.StudentRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/member/attendance/")
@RequiredArgsConstructor
@Log4j2
public class AttendanceController {
    private final AttendanceService attendanceService;
    private final AttendanceRepository attendanceRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    @GetMapping("/form")
    public String saveform(CourseDTO courseDTO
            , Model model
            , @RequestParam(required = false, defaultValue = "") String campgZoneCd1
            , @RequestParam(required = false, defaultValue = "") String campgZoneCd2) {
        log.info("/attendance/form...................................");

        LocalDate startdate = LocalDate.now();
        LocalDate enddate = null;
        DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter dateTimeFormatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd (E)");

        List<String> dataList = new ArrayList<>();

        if (campgZoneCd1 == null || campgZoneCd1.equals("")) {
            startdate = LocalDate.now(); // 시작일을 오늘로 세팅
        } else {
            startdate = LocalDate.of(
                    Integer.parseInt(campgZoneCd1.split("-")[0]),
                    Integer.parseInt(campgZoneCd1.split("-")[1]),
                    Integer.parseInt(campgZoneCd1.split("-")[2]));
        }
        // startdate 에서 월요일 찾기
//        LocalDate monday = startdate.minusDays(startdate.getDayOfWeek().getValue()-1);

        // 시작날짜 지정
        campgZoneCd1 = startdate.format(dateTimeFormatter1); // 시작일 포맷팅

        DayOfWeek dayOfWeek = startdate.getDayOfWeek();
        int dayOfWeekNumber = dayOfWeek.getValue();
        if (dayOfWeekNumber == 1 || dayOfWeekNumber == 6 || dayOfWeekNumber == 7) { // 월, 토, 일
            for (int i = 0; i < 5; i++) {
                enddate = startdate.plusDays(i);
                String temp = enddate.format(dateTimeFormatter2);
                dataList.add(temp);
            }
        } else {
            if (dayOfWeekNumber == 2) { // 화
                for (int i = 0; i < 4; i++) {
                    enddate = startdate.plusDays(i);
                    String temp = enddate.format(dateTimeFormatter2);
                    dataList.add(temp);
                }
                enddate = startdate.plusDays(6);
                String temp = enddate.format(dateTimeFormatter2);
                dataList.add(temp);
            }
            if (dayOfWeekNumber == 3) { // 수
                for (int i = 0; i < 3; i++) {
                    enddate = startdate.plusDays(i);
                    String temp = enddate.format(dateTimeFormatter2);
                    dataList.add(temp);
                }
                for (int i = 5; i < 7; i++) {
                    enddate = startdate.plusDays(i);
                    String temp = enddate.format(dateTimeFormatter2);
                    dataList.add(temp);
                }
            }
            if (dayOfWeekNumber == 4) { // 목
                for (int i = 0; i < 2; i++) {
                    enddate = startdate.plusDays(i);
                    String temp = enddate.format(dateTimeFormatter2);
                    dataList.add(temp);
                }
                for (int i = 4; i < 7; i++) {
                    enddate = startdate.plusDays(i);
                    String temp = enddate.format(dateTimeFormatter2);
                    dataList.add(temp);
                }
            }
            if (dayOfWeekNumber == 5) { // 금
                enddate = startdate.plusDays(0);
                String temp = enddate.format(dateTimeFormatter2);
                dataList.add(temp);
                for (int i = 2; i < 6; i++) {
                    enddate = startdate.plusDays(i);
                    temp = enddate.format(dateTimeFormatter2);
                    dataList.add(temp);
                }
            }
        }

        // 종료날짜 지정
        campgZoneCd2 = enddate.format(dateTimeFormatter1);

        // 훈련 과정 불러오기
        List<Course> courseList = courseRepository.getCourseListInProgress();
        model.addAttribute("courseList", courseList);

        // todo 학생 목록 불러와야 함, member에 있는 것만 필요한가???... 쌤 더미 데이터 memberIdx, id(번호 인덱스), name
        List<Student> studentList = studentRepository.getStudentsByCourseIdx(courseDTO.getCourseIdx());
        List<MemberDTO> memberList = new ArrayList<>();
        //        System.out.println(result);
        // result 를 attendanceDTO로 바꿔서
        // model 에 담고 form 에 출력해야함
        if( studentList.size() !=0 ) {
            for ( Student student : studentList){
                MemberDTO memberDTO = MemberDTO.toDTO(student.getMember());
                memberDTO.setStudentIdx(student.getStudentIdx());
                memberList.add(memberDTO);
            }
//            System.out.println(student);
//            System.out.println(member);
        }
        model.addAttribute("studentList", memberList);
        System.out.println(studentList);


        model.addAttribute("campgZoneCd1", campgZoneCd1);
        model.addAttribute("campgZoneCd2", campgZoneCd2);
        model.addAttribute("dataList", dataList);
        return "member/attendance/form";
    }

    @PostMapping("/form")
    public @ResponseBody HashMap<String, Object> save(HttpServletRequest request) {
        System.out.println("일로오나");

        String jsonStr = request.getParameter("list");
        System.out.println(jsonStr);

        try {

            JSONArray jsonArray = new JSONArray(jsonStr);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                String cperiod = (String) jsonObj.get("cperiod");
                String name = (String) jsonObj.get("name");
                String member_idx = (String) jsonObj.get("member_idx");
                String regDate = (String) jsonObj.get("regDate");
                String status = (String) jsonObj.get("status");

                AttendanceDTO attendanceDTO = AttendanceDTO.builder()
                        .cperiod(Integer.parseInt(cperiod))
                        .status(status)
                        .courseIdx(1l)
                        .studentIdx(Long.parseLong(member_idx))
                        .regDate(LocalDate.parse(regDate))
                        .name(name)
                        .build();
                System.out.println(attendanceDTO);
                attendanceService.save(attendanceDTO);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println(e);
        }
        HashMap<String, Object> retVal = new HashMap<>();
        retVal.put("message", "success");

        return retVal;
    }
}
