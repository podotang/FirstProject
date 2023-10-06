package com.teamntp.firstproject.test;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
@Log4j2
public class TestController {
    @GetMapping("/summernoteTest")
    public void summernoteTest() {
        log.info("/summernoteTest");
    }
}
