package com.teamntp.firstproject.check.service;

import com.teamntp.firstproject.check.dto.CheckDTO;
import com.teamntp.firstproject.check.entity.CCheck;
import com.teamntp.firstproject.check.repository.CheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CheckService {
    @Autowired
    CheckRepository checkRepository;

    public List<CheckDTO> list(Pageable pageable) {
        Page<CCheck> pagelist = checkRepository.findAll(pageable);
        List<CheckDTO> dtolist = new ArrayList<>();
        for (CCheck CCheck : pagelist){
            CheckDTO dto = CheckDTO.of(CCheck);
            dtolist.add(dto);
        }
        return dtolist;
    }

    public boolean insert(CheckDTO checkDto) {
        CCheck CCheckEntity = checkRepository.findById(checkDto.getCid()).orElse(new CCheck());
        CCheckEntity.setCid(checkDto.getCid());
        CCheckEntity.setTitle(checkDto.getTitle());
        CCheckEntity.setWriter(checkDto.getWriter());
        CCheckEntity.setFilePath(checkDto.getFilePath());
        checkRepository.save(CCheckEntity);
        return true;
    }

    public CheckDTO getRow(CheckDTO checkDto) {
        CCheck CCheck = checkRepository.findById(checkDto.getCid())
                .orElse(new CCheck());
        return CheckDTO.of(CCheck);
    }
}

