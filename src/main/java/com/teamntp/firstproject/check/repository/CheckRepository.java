package com.teamntp.firstproject.check.repository;

import com.teamntp.firstproject.check.entity.CCheck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckRepository extends JpaRepository<CCheck, Integer> {


}
