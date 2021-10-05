package com.redarmdevs.taskswebapp.models;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

    public Role() {

    }

    public Role(ERole name) {
        this.name = name;
    }

    public Role(String name){
        switch (name){
            case "ROLE_USER":
                this.name = ERole.ROLE_USER;
                break;
            case "ROLE_ADMIN":
                this.name = ERole.ROLE_ADMIN;
                break;
            default:
                throw new RuntimeException("INVALID ROLE NAME: " + name);
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }
}
