package com.example.todo.controller.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.todo.service.task.TaskService;

@Controller
public class TaskController {

    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    //TaskServiceクラスのインスタンスを作成
    private final TaskService taskService = new TaskService();

    //ハンドラー名
    @GetMapping("/tasks")
    public String list(Model model){
        log.info("list()が呼び出されました");


        model.addAttribute("taskList", taskService.find());
        log.debug("model attribute taskList='{}' を設定しました", taskService.find());
        log.debug("taskLists/list.html を表示します", taskService.find());
        log.info("list()の処理が終了しました");
        return "tasks/list";
    }
}