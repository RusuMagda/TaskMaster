package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name="tasks")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private Integer estimatedTime;
    private Integer remainingTime;
    private Integer realTime;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User assignedUser;
    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private TaskStatus status;

}

