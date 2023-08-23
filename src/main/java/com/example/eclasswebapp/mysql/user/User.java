package com.example.eclasswebapp.mysql.user;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotEmpty;

import com.example.eclasswebapp.mysql.professor.Professor;
import com.example.eclasswebapp.mysql.role.Role;
import com.example.eclasswebapp.mysql.student.Student;

import org.hibernate.annotations.ColumnDefault;

@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotEmpty(message = "Username cannot be empty.")
  @Column(unique = true, nullable = false, length = 30)
  private String username;
  @NotEmpty(message = "Password cannot be empty.")
  @Column(nullable = false, length = 64)
  private String password;
  @Column(nullable = false)
  @ColumnDefault("false")
  private boolean verified;

  @ManyToOne
  private Role role;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  private Professor professor;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  private Student student;

  public Professor getProfessor() {
    return professor;
  }

  public void setProfessor(Professor professor) {
    this.professor = professor;
  }

  public Student getStudent() {
    return student;
  }

  public void setStudent(Student student) {
    this.student = student;
  }

  public User() {
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public boolean isVerified() {
    return verified;
  }

  public void setVerified(boolean verified) {
    this.verified = verified;
  }

  @Override
  public String toString() {
    return "User [id=" + id + ", password=" + password + ", role=" + role + ", username=" + username + "]";
  }

}