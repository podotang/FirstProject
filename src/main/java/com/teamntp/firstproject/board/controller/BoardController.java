package com.teamntp.firstproject.board.controller;

import com.teamntp.firstproject.board.dto.BoardDTO;
import com.teamntp.firstproject.common.dto.PageRequestDTO;
import com.teamntp.firstproject.board.service.BoardService;
import com.teamntp.firstproject.security.dto.AuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/member/board/")
@Log4j2
@RequiredArgsConstructor // 의존성 자동 주입
public class BoardController {
    private final BoardService boardService; // final 선언

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO
            , Model model) {
        log.info("list.........." + pageRequestDTO);
        model.addAttribute("result", boardService.getList(pageRequestDTO));

    }

    @GetMapping("/register")
    public void register() {

        log.info("register get.............");
    }

    @PostMapping("/register")
    public String registerPost(BoardDTO boardDTO, RedirectAttributes redirectAttributes
            , @AuthenticationPrincipal AuthMemberDTO authMemberDTO
            , Model model) {
        log.info("AuthMemberDTO: " + authMemberDTO);
        // AuthMemberDTO(memberIdx=1, loginId=kwontory, password=$2a$10$BC8Nt4pULmvOPWptskGrUew7S6ZkCjUWv6p.nCsc7.9da35A4o5bW, name=권지현, path=form, type=null, attr=null)
        // 로그인한 사용자 정보에서 memberIdx 를 받아와서 set 해준다.
        boardDTO.setWriterMno(authMemberDTO.getMemberIdx());
        // 새로 등록한 게시글 번호
        Long bno = boardService.register(boardDTO);

        log.info("BNO...." + bno);

        // 모달창 처리용 일회성 변수
        redirectAttributes.addFlashAttribute("msg", bno);

        return "redirect:/member/board/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO
            , Long bno
            , Model model) {
        log.info("BNO: " + bno);

        BoardDTO boardDTO = boardService.get(bno);
        log.info(boardDTO);

        model.addAttribute("dto", boardDTO);
    }

    @PostMapping("/remove")
    public String remove(long bno, RedirectAttributes redirectAttributes) {
        log.info("bno: " + bno);

        boardService.removeWithReplies(bno);
        redirectAttributes.addFlashAttribute("msg", bno);

        return "redirect:/member/board/list";
    }

    @PostMapping("/modify")
    public String modify(BoardDTO dto
            , @ModelAttribute("requestDTO") PageRequestDTO requestDTO
            , RedirectAttributes redirectAttributes) {
        log.info("post modify.....................");
        log.info("modify dto: " + dto);

        boardService.modify(dto);

        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());
        redirectAttributes.addAttribute("bno", dto.getBno());

        return "redirect:/member/board/read";
    }

}
