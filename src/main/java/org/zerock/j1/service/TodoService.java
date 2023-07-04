package org.zerock.j1.service;

import org.springframework.transaction.annotation.Transactional;
import org.zerock.j1.dto.PageResponseDTO;
import org.zerock.j1.dto.TodoDTO;

@Transactional
public interface TodoService {

    PageResponseDTO<TodoDTO> getList();

    // 등록
    TodoDTO register(TodoDTO dto);

    // 조회
    TodoDTO getOne(Long tno);

    // 삭제
    void remove(Long tno);

    // 수정
    void modify(TodoDTO dto);


    
}
