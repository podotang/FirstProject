package com.teamntp.firstproject.board.entity;

import com.teamntp.firstproject.common.entity.BaseEntity;
import com.teamntp.firstproject.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"board", "replyer"}) // @ToString 항상 exclude
public class Reply extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    private String text;

    @ManyToOne
    private Member replyer;

    @ManyToOne
    private Board board; // Board PK를 참조

}
