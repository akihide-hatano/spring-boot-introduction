package com.example.todo.service.task;

import java.util.List;

import com.example.todo.controller.task.TaskDTO;

public class TaskService {

    public List<TaskDTO> find(){
        var task1 = new TaskDTO(1,
            "SpringBootを学ぼう",
            "todoアプリを作ろう",
            "todo");

        var task2 = new TaskDTO(2L,
            "Spring Securityについて学ぶ",
            "ログイン機能を作ってみる", "todo");

            //returrnにてリストを返すためにList.of()を使用して、task1とtask2をリストにまとめて返す
            return List.of(task1, task2);
        }
}