package com.example.eclasswebapp.controllers;

import java.util.ArrayList;

import com.example.eclasswebapp.mysql.course.Course;
import com.example.eclasswebapp.mysql.course.CourseRepository;
import com.example.eclasswebapp.mysql.user.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NavBarController {
    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/changename")
    public String viewChangeName(User user, Model model) {
        model.addAttribute("user", user);
        return "changename";
    }

    @PostMapping
    public String changeName(){
        return "changename";
    }

    @GetMapping("/changepass")
    public String viewChangePass() {
        return "changepass";
    }

    @GetMapping("/courses")
    public String viewCourses(Model model) {
        model.addAttribute("courses", courseRepository.findAll());
        return "courses";
    }

    @GetMapping("/navbar")
    public String viewNavBar(User user, Model model) {
        model.addAttribute("user", user);
        return "navbar.html";
    }

    @GetMapping("/portfolio")
    public String viewPortfolio(Model model) {
        return "portfolio";
    }

}
