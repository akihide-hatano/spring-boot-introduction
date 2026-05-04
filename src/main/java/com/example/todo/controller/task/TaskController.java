package com.example.todo.controller.task;

//リストを作成するためのクラス
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TaskController {

    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    //ハンドラー名
    @GetMapping("/tasks")
    public String list(Model model){
        log.info("list()が呼び出されました");
        //modelにSpringBootを学ぼうと入力
        var task1 = new TaskDTO(1,
            "SpringBootを学ぼう",
            "todoアプリを作ろう",
            "todo");

        var task2 = new TaskDTO(2L,
             "Spring Securityについて学ぶ",
             "ログイン機能を作ってみる", "todo");

        var taskList = List.of(task1, task2);

        model.addAttribute("taskList", taskList);
        log.debug("model attribute taskList='{}' を設定しました", taskList);
        log.debug("taskLists/list.html を表示します", taskList);
        log.info("list()の処理が終了しました");
        return "tasks/list";
    }
}