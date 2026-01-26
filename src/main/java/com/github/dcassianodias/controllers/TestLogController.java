package com.github.dcassianodias.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestLogController {

    @GetMapping("/log-test")
    public String logTest() {
        log.debug("This is a DEBUG log message");
        log.info("This is a INFO log message");
        log.warn("This is a WARN log message");
        log.error("This is a ERROR log message");
        return "Log message printed to console";
    }
}
