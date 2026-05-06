package com.example.todo.service.task;

import com.example.todo.repository.task.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//DIに変更する
@Service
@RequiredArgsConstructor

public class TaskService {

    private final TaskRepository taskRepository;

    public List<TaskEntity> find(){
        return taskRepository.selectAll();
        }

    public Optional<TaskEntity> findById(long taskId) {
        return taskRepository.selectById(taskId);
    }
}