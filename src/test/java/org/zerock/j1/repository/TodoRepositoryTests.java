package org.zerock.j1.repository;

import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.j1.domain.Sample;
import org.zerock.j1.domain.Todo;
import org.zerock.j1.dto.TodoDTO;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class TodoRepositoryTests {

    @Autowired
    private ModelMapper modelMapper; // entity -> DTO로 변환
    
    @Autowired
    private TodoRepository todoRepository;

    @Test
    public void TestInsert(){

        IntStream.rangeClosed(1, 100).forEach(i -> {
            Todo todo = Todo.builder()
            .title("title" + i)
            .build();

            Todo result = todoRepository.save(todo);

            log.info(result);
        });

    }

    @Test
    public void testRead() {

        Long tno = 97L;

        Optional<Todo> result = todoRepository.findById(tno);

        Todo entity = result.orElseThrow();

        log.info("============================");
        log.info(entity);

        TodoDTO dto = modelMapper.map(entity, TodoDTO.class); // entity객체를 TodoDTO로 변환시키는 문법

        log.info("============================");
        log.info(dto);
    }

    @Test
    public void testDelete() {

        Long tno = 1L;

        todoRepository.deleteById(tno);
    }

    @Test
    public void Paging(){

        Pageable pageable = PageRequest.of(0, 10, 
        Sort.by("tno").descending());

        Page<Todo> result = todoRepository.findAll(pageable);

        log.info(result);
    }
}
