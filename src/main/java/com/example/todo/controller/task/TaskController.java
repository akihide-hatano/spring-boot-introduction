package com.example.todo.controller.task;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.todo.service.task.TaskService;

@Controller
@RequiredArgsConstructor

public class TaskController {

    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    //TaskServiceクラスのインスタンスを作成
    private final TaskService taskService;



    //ハンドラー名
    @GetMapping("/tasks")
    public String list(Model model){
        log.info("list()が呼び出されました");

        //streamでmapでTaskEntityをTaskDTOに変換して、toList()でリストにまとめる
        var taskList = taskService.find()
                        .stream()
                        .map(TaskDTO::toDTO).toList();


        model.addAttribute("taskList", taskList);
        log.debug("model attribute taskList='{}' を設定しました", taskList);
        log.info("list()の処理が終了しました");
        return "tasks/list";
    }
}