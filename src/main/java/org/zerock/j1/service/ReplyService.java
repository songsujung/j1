package org.zerock.j1.service;

import org.zerock.j1.dto.PageResponseDTO;
import org.zerock.j1.dto.ReplyDTO;
import org.zerock.j1.dto.ReplyPageRequestDTO;

import jakarta.transaction.Transactional;

@Transactional
public interface ReplyService {

    PageResponseDTO<ReplyDTO> list(ReplyPageRequestDTO requestDTO);

    // 댓글 등록
    Long register(ReplyDTO replyDTO);
    
    // 댓글 조회
    ReplyDTO read(Long rno);

    // 댓글 삭제
    void remove(Long rno);

    // 댓글 수정
    void modify(ReplyDTO replyDTO);
    
}
