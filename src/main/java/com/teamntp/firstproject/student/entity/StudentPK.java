package com.teamntp.firstproject.student.entity;

import lombok.*;

import java.io.Serializable;


@Getter @Setter
@EqualsAndHashCode // 필수
@NoArgsConstructor
@AllArgsConstructor
public class StudentPK implements Serializable {

    private Long studentIdx;
    private Long course;
}
