package org.zerock.j1.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.j1.dto.PageResponseDTO;
import org.zerock.j1.dto.TodoDTO;
import org.zerock.j1.service.TodoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/todos/")
@RequiredArgsConstructor
@CrossOrigin
@Log4j2
public class TodoController {

    private final TodoService todoService;

    @GetMapping("list")
    public PageResponseDTO<TodoDTO> list(){
        
        return todoService.getList();
    }

    @GetMapping("/{tno}")
    public TodoDTO get(@PathVariable Long tno){

        return todoService.getOne(tno);
    }

    @PostMapping("/")
    public TodoDTO register(@RequestBody TodoDTO todoDTO){ // Post로 받은 JSON 데이터를 TodoDTO객체로 변환

        log.info("register===================");
        log.info(todoDTO);

        return todoService.register(todoDTO);
    }

    @DeleteMapping("/{tno}")
    public Map<String, String> delete(@PathVariable("tno") Long tno) {

        todoService.remove(tno);

        return Map.of("result", "success");
    }

    @PutMapping("/{tno}")
    public Map<String, String> update(@PathVariable("tno") Long tno, @RequestBody TodoDTO todoDTO){

        todoDTO.setTno(tno); // 안전장치

        todoService.modify(todoDTO);

        return Map.of("result", "success");
    }
    
}
