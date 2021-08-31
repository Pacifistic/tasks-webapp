package com.redarmdevs.taskswebapp.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank
    @Size(max = 50)
    private String name;

    @Column(name = "description")
    @Size(max = 250)
    private String desc;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;

    //@Column(columnDefinition = "TIMESTAMP")
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //private LocalDateTime start;

    //private Boolean repeating;

    private Duration frequency;

    @Column(columnDefinition = "TIMESTAMP")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastTime;

//    @Column(columnDefinition = "TIMESTAMP")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime endTime;

    public Task(String name, String desc, Duration frequency) {
        this.name = name;
        this.desc = desc;
        this.frequency = frequency;
    }

    public Task(String name, Duration frequency) {
        this.name = name;
        this.frequency = frequency;
    }

    public Duration getFrequency() {
        return frequency;
    }

    public void setFrequency(Duration frequency) {
        this.frequency = frequency;
    }

    public LocalDateTime getLastTime() {
        return lastTime;
    }

    public void setLastTime(LocalDateTime lastTime) {
        this.lastTime = lastTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Task() {
    }
}
