package com.teamntp.firstproject.check.controller;

import com.teamntp.firstproject.check.repository.CheckRepository;
import com.teamntp.firstproject.check.service.CheckService;
import com.teamntp.firstproject.check.dto.CheckDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/member/check/")
@RequiredArgsConstructor
@Log4j2
public class CheckController {

    private final CheckService checkService;
    private final CheckRepository checkRepository;

    @GetMapping("/list")
    public void index(Model model, @PageableDefault(
            size = 12
            , sort = "cid"
            , direction = Sort.Direction.ASC
            , page = 0) Pageable pageable) {

        List<CheckDTO> clist = checkService.list(pageable);
        System.out.println("============= clist =============");
        System.out.println(clist);
        model.addAttribute("clist", clist);

    }

    @GetMapping("/Delete")
    public String gdelete(CheckDTO checkDto) {
        checkRepository.deleteById(checkDto.getCid());
        return "redirect:/member/check/list";
    }

    @GetMapping("/CUpdateForm")
    public void updateForm(@ModelAttribute @Valid CheckDTO checkDto
            , BindingResult bindingResult
            , Model model) {
        System.out.println(checkDto);
        CheckDTO cDto = checkService.getRow(checkDto);
        model.addAttribute("checkDto", cDto);
    }

    @PostMapping("/CUpdateForm")
    public String cUpdateForm(@ModelAttribute @Valid CheckDTO checkDto
            , BindingResult bindingResult
            , Model model) {
        if (bindingResult.hasErrors()) {
            return "redirect:/member/check/CUpdateForm";
        } else {
            System.out.println(checkDto);
            boolean result = checkService.insert(checkDto);
            if (result)
                return "redirect:/member/check/list";
        }
        return "redirect:/member/check/CUpdateForm";
    }

    @GetMapping("/CWriteForm")
    public  void gregister(CheckDTO checkDto) {
        log.info("/check/register................");
    }

    private static final String UPLOAD_DIR = "uploads";

    @PostMapping("/CWriteForm")
    public String pregister(CheckDTO checkDto, MultipartFile file, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/member/check/CWriteForm";
        }

        try {
            byte[] bytes = file.getBytes();
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            String filename = file.getOriginalFilename();
            Path filePath = uploadPath.resolve(filename);
            Files.write(filePath, bytes);

            checkDto.setFilePath(filePath.getFileName().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("==================================================");
        log.info("CheckDto: "+checkDto);
        log.info("==================================================");

        // save 처리
        checkService.insert(checkDto);

        // 일회성 반환
//        redirectAttributes.addFlashAttribute("msg",courseIdx);

        return "redirect:/member/check/list"; // 훈련과정 등록 후 목록 페이지로 이동

    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(String filename) throws IOException {
        Path filePath = Paths.get(UPLOAD_DIR).resolve(filename);
        Resource resource = new UrlResource(filePath.toUri());

        if (resource.exists()) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", filename);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
        } else {
            // Handle file not found case
            return ResponseEntity.notFound().build();
        }
    }

}

