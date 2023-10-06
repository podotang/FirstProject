package com.teamntp.firstproject.check.dto;

import com.teamntp.firstproject.check.entity.CCheck;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CheckDTO {
    private int cid;

    @Size(max = 10, min = 2)
    private String title;

    private String writer;

    private LocalDateTime createDate;

    private String filePath;

    private static ModelMapper modelMapper = new ModelMapper();

    public static CheckDTO of(CCheck CCheck) {
        return modelMapper.map(CCheck, CheckDTO.class);
    }

}

