package org.zerock.j1.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.j1.domain.Reply;
import org.zerock.j1.dto.PageResponseDTO;
import org.zerock.j1.dto.ReplyDTO;
import org.zerock.j1.dto.ReplyPageRequestDTO;
import org.zerock.j1.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService  {

    private final ReplyRepository replyRepository;

    private final ModelMapper modelMapper;

    @Override
    public PageResponseDTO<ReplyDTO> list(ReplyPageRequestDTO requestDTO) {

        boolean last = requestDTO.isLast();

        int pageNum = requestDTO.getPage();

        if(last){
            long totalCount = replyRepository.getCountBoard(requestDTO.getBno());

            pageNum = (int)(Math.ceil(totalCount/(double)requestDTO.getSize()));

            pageNum = pageNum <= 0 ? 1 : pageNum; // 댓글이 없는 경우 오류 발생 막기 위해
        }
        
        Pageable pageable = PageRequest.of(pageNum - 1, requestDTO.getSize(), Sort.by("rno").ascending());

        Page<Reply> result = replyRepository.listBoard(requestDTO.getBno(), pageable);

        log.info("-------------------------------------");

        long totalReplyCount = result.getTotalElements();

        List<ReplyDTO> dtoList = result.get()
            .map(en -> modelMapper.map(en, ReplyDTO.class))
            .collect(Collectors.toList());

        PageResponseDTO<ReplyDTO> responseDTO = new PageResponseDTO<>(dtoList, totalReplyCount, requestDTO);
        responseDTO.setPage(pageNum);

        return responseDTO;
    }

    // 댓글 등록
    @Override
    public Long register(ReplyDTO replyDTO) {
        
        Reply reply = modelMapper.map(replyDTO, Reply.class);

        log.info("reply...........");
        log.info(reply);

        Long newRno = replyRepository.save(reply).getRno();

        return newRno;

    }

    // 댓글 조회
    @Override
    public ReplyDTO read(Long rno) {

        Optional<Reply> result = replyRepository.findById(rno);

        Reply reply = result.orElseThrow();

        return modelMapper.map(reply, ReplyDTO.class);
    }

    // 댓글 삭제
    @Override
    public void remove(Long rno) {

        Optional<Reply> result = replyRepository.findById(rno);

        Reply reply =result.orElseThrow();

        reply.changeText("해당 글은 삭제되었습니다.");
        reply.changeFile(null); 

        replyRepository.save(reply);


    }

    // 댓글 수정
    @Override
    public void modify(ReplyDTO replyDTO) {
        
        Optional<Reply> result = replyRepository.findById(replyDTO.getRno());

        Reply reply =result.orElseThrow();

        reply.changeText(replyDTO.getReplyText());
        reply.changeFile(replyDTO.getReplyFile()); 

        replyRepository.save(reply);

    }
    
}
