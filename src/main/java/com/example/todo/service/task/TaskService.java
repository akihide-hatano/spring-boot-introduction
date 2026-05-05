package com.example.todo.service.task;

import org.springframework.stereotype.Service;

import java.util.List;

//DIに変更する
@Service

public class TaskService {

    public List<TaskEntity> find(){
        var task1 = new TaskEntity(1L,
            "SpringBootを学ぼう",
            "todoアプリを作ろう",
            TaskStatus.TODO);

        var task2 = new TaskEntity(2L,
            "Spring Securityについて学ぶ",
            "ログイン機能を作ってみる",
            TaskStatus.DOING);

            //returrnにてリストを返すためにList.of()を使用して、task1とtask2をリストにまとめて返す
            return List.of(task1, task2);
        }
}