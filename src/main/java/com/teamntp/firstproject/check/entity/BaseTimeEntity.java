package com.teamntp.firstproject.check.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass   //여기에 있는 칼럼이 자식클래스에 들어간다!
@EntityListeners(AuditingEntityListener.class)  //어디팅이 jpa라이브러리에 생성한다~
public class BaseTimeEntity {

    @CreatedDate
    private LocalDateTime createDate;

}

