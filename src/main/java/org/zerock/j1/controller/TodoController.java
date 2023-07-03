package org.zerock.j1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.j1.dto.PageResponseDTO;
import org.zerock.j1.dto.TodoDTO;
import org.zerock.j1.service.TodoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/list")
    public PageResponseDTO<TodoDTO> list(){
        
        return todoService.getList();
    }
    
}
