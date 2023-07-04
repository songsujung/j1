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
import org.zerock.j1.domain.Board;
import org.zerock.j1.domain.Todo;
import org.zerock.j1.dto.PageResponseDTO;
import org.zerock.j1.dto.TodoDTO;
import org.zerock.j1.repository.TodoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor // 자동으로 의존성 주입(final로 선언된 객체를)
public class TodoServiceImpl implements TodoService {
    
    private final ModelMapper modelMapper;
        
    private final TodoRepository todoRepository;

    @Override
    public PageResponseDTO<TodoDTO> getList() {

        Pageable pageable = PageRequest.of(0,20,
        Sort.by("tno").descending());

        Page<Todo> result = todoRepository.findAll(pageable);

        //  todo를 todoDTO로 변환시키고, 변환 된 객체들을 리스트형식으로 모아 dtoList에 저장
        List<TodoDTO> dtoList = result.getContent().stream()
        .map(todo -> modelMapper.map(todo, TodoDTO.class)).collect(Collectors.toList());

        // PageResponseDTO 객체를 생성 후 rseponse에 할당
        PageResponseDTO<TodoDTO> response = new PageResponseDTO<>();

        response.setDtoList(dtoList);
        return response;
    }

    // 등록
    @Override
    public TodoDTO register(TodoDTO dto) {
        
        Todo entity = modelMapper.map(dto, Todo.class); // title값만 있는 상태

        Todo result = todoRepository.save(entity); // db에 저장되면서 tno값이 생성(autoIncrement를 해줬기 때문)

        return modelMapper.map(result, TodoDTO.class); // tno + title 값을 사용하기 위해 TodoDTO형식으로 다시 변환
    }

    // 조회
    @Override
    public TodoDTO getOne(Long tno) {
        
        Optional<Todo> result = todoRepository.findById(tno);

        Todo todo = result.orElseThrow();

        TodoDTO dto = modelMapper.map(todo, TodoDTO.class);

        return dto;
    }

    // 삭제
    @Override
    public void remove(Long tno) {

        todoRepository.deleteById(tno);
    }

    // 수정
    @Override
    public void modify(TodoDTO dto) {
        
        Optional<Todo> result = todoRepository.findById(dto.getTno());

        Todo todo = result.orElseThrow();

        todo.changeTitle(dto.getTitle());

        todoRepository.save(todo);
    }
    
}
