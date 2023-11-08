package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="projects")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;
    private String title;
    private Date startDate;
    private Date endDate;
    private String description;
    @ManyToMany
    @JoinTable(
            name = "project_members",
            joinColumns = @JoinColumn(name = "projectId"),
            inverseJoinColumns = @JoinColumn(name = "userId")
    )
    private List<User> members;
    @ManyToOne
    @JoinColumn(name = "leader_user_id")
    private User leader;
    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private ProjectStatus status;
}
