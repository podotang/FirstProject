package com.teamntp.firstproject.student.service;

import com.teamntp.firstproject.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService{
    private final StudentRepository studentRepository;
}
