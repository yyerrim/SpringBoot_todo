package com.example.todo.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.entity.TodoEntity;
import com.example.todo.repository.TodoRepository;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin // 접근거부되는 상황을 허용으로 바꿔줌
public class TodoController {
    @Autowired
    TodoRepository todoRepository;

    // @GetMapping("/add")
    // public String add() {
    // todoRepository.save(todo);
    // return "add";
    // }
    @PostMapping("/add")
    public TodoEntity add(
            @RequestBody TodoEntity todo,
            HttpServletRequest request) {
        String userIp = request.getRemoteAddr();
        todo.setUserIp(userIp);
        todo.setCreateDate(LocalDateTime.now());
        TodoEntity result = todoRepository.save(todo);
        return result;
    }

    @GetMapping("/read")
    public List<TodoEntity> read() {
        List<TodoEntity> list = todoRepository.findAll();
        return list;
    }

    @GetMapping("/modify")
    public String modify() {
        return "modify";
    }

    @GetMapping("/remove")
    public String remove() {
        return "remove";
    }
}
