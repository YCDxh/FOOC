package com.YCDxh.controller;


import com.YCDxh.service.SparkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class SparkController {

    @Autowired
    private SparkService sparkService;

    @GetMapping("/ask")
    public String ask(@RequestParam String question) throws Exception {
        return sparkService.askQuestion(question);
    }
}
