package com.kardex.infrastructure.out.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "status")
@Data
@NoArgsConstructor
public class StatusEntity {

    @Id
    @Column(name = "status_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statusId;

    @Column(name = "status_name", nullable = false)
    private String status;
}

