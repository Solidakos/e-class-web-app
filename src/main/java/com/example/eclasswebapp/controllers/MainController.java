package com.example.eclasswebapp.controllers;


import javax.validation.Valid;

import com.example.eclasswebapp.mysql.course.Course;
import com.example.eclasswebapp.mysql.course.CourseRepository;
import com.example.eclasswebapp.mysql.professor.Professor;
import com.example.eclasswebapp.mysql.professor.ProfessorRepository;
import com.example.eclasswebapp.mysql.role.RoleRepository;
import com.example.eclasswebapp.mysql.student.Student;
import com.example.eclasswebapp.mysql.student.StudentRepository;
import com.example.eclasswebapp.mysql.user.User;
import com.example.eclasswebapp.mysql.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MainController {
  @Autowired
  private ProfessorRepository professorRepository;
  @Autowired
  private StudentRepository studentRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private RoleRepository roleRepository;
  @Autowired
  private CourseRepository courseRepository;

  @GetMapping(value = { "", "/index", "/login" })
  public String viewHomePage(Model model) {
    model.addAttribute("user", new User());
    model.addAttribute("professor", new Professor());
    model.addAttribute("student", new Student());
    model.addAttribute("roles", roleRepository.findAll());
    return "login";
  }

  @PostMapping("/process_register")
  public String processRegister(@Valid User user, BindingResult result, Model model,
          @RequestParam(required = true) String fname,
          @RequestParam(required = true) String lname,
          @RequestParam(required = false) String pos,
          @RequestParam(required = false) String am,
          @RequestParam(required = false) String admyear) {
    model.addAttribute("roles", roleRepository.findAll());
    if (result.hasErrors()) {
      return "login";
    }
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    String encodedPassword = passwordEncoder.encode(user.getPassword());
    user.setPassword(encodedPassword);
    user.setVerified(false);

    int count = userRepository.doesUserExist(user.getUsername());

    if (count == 0) {
      if (user.getRole().getId() == 2) {
        Professor p = new Professor();
        p.setFname(fname);
        p.setLname(lname);
        p.setPos(pos);
        p.setUser(user);
        professorRepository.save(p);
      } else if (user.getRole().getId() == 3) {
        Student s = new Student();
        s.setFname(fname);
        s.setLname(lname);
        s.setAM(Long.parseLong(am));
        s.setAdmyear(Integer.parseInt(admyear));
        s.setUser(user);
        studentRepository.save(s);
      }
    }

    return "login";
  }

  @GetMapping("/insert")
  public String insert(Model model) {
    model.addAttribute("course", new Course());
    model.addAttribute("courses", courseRepository.findAll());
    return "insert";
  }

  @PostMapping("/addcourse")
  public String addCourse(Course course, Model model) {
    model.addAttribute("courses", courseRepository.findAll());
    int count = courseRepository.doesCourseExist(course.getName());
    if (count == 0) {
      courseRepository.save(course);
    }
    return "/insert";
  }

  @GetMapping("/view/{id}")
  public String viewCourse(@PathVariable Integer id, Model model, User user) {
    model.addAttribute("professors", professorRepository.findAll());
    model.addAttribute("course", courseRepository.findById(id).get());
    model.addAttribute("user", user);
    return "view";
  }

  @PostMapping("/changename")
  public String changeName() {
    return "changename";
  }

  @GetMapping("/users")
  public String listUsers(Model model) {
    model.addAttribute("listUsers", userRepository.findAll());

    return "users";
  }
}