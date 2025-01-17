package com.example.todo.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
    public TodoEntity modify(@RequestParam Long id) {
        // TodoEntity todo = new TodoEntity();
        // new를 하면 빈 껍데기가 만들어짐 ===> save 하면 기존의 데이터가 사라지게됨
        // ===> 조회를 해준 다음에 필요한 부분만 save
        Optional<TodoEntity> opt = todoRepository.findById(id);
        TodoEntity todo = opt.get();
        Boolean checked = todo.getChecked();
        todo.setChecked(!checked);
        TodoEntity result = todoRepository.save(todo);
        return result;
    }

    // @GetMapping("/remove")
    // public String remove(@RequestParam Long id) {
    // todoRepository.deleteById(id);
    // return "삭제 완료";
    // }
    // json 사용하고 싶을때
    @GetMapping("/remove")
    public Map<String, Object> remove(@RequestParam Long id) {
        todoRepository.deleteById(id);
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "삭제 완료");
        return map;
    }
}
