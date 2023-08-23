package com.example.eclasswebapp.mysql.student;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.example.eclasswebapp.mysql.user.User;

@Entity
public class Student {
    @Id
    private Integer id;

    @Column(unique = true, length = 10)
    private Long AM;

    @Column(nullable = false)
    private String fname;
    @Column(nullable = false)
    private String lname;
    @Column(length = 4, nullable = false)
    private Integer admyear;

    @OneToOne
    @MapsId
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getAM() {
        return AM;
    }

    public void setAM(Long AM) {
        this.AM = AM;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public Integer getAdmyear() {
        return admyear;
    }

    public void setAdmyear(Integer admyear) {
        this.admyear = admyear;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
