package com.bayztracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;

@ApiIgnore
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class IndexController {

    @GetMapping
    public RedirectView redirectToSwaggerUi(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache");
        return new RedirectView("/swagger-ui/index.html");
    }
}