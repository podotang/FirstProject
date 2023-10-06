package com.teamntp.firstproject.check.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CCheck extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cid;

    private String title;
    private String writer;
    private String filePath;



}

