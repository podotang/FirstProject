package com.teamntp.firstproject.training.repository;

import com.teamntp.firstproject.training.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingRepository extends JpaRepository<Training, Long>{

        // 해당 엔터티에 대한 Spring Data JPA 리포지토리 인터페이스를 생성
        // 필요한 쿼리 메서드 선언


}

