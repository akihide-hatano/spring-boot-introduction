package com.example.todo.controller.task;

import com.example.todo.service.task.TaskEntity;
import com.example.todo.service.task.TaskStatus;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.todo.service.task.TaskService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    //タスクの詳細を表示するハンドラー
    //@PathVariableを使用してurlを取得する
    @GetMapping("/tasks/{id:\\d+}")
    public  String showDetail(@PathVariable("id") long taskId, Model model){
        //taskId->taskEntity
        var taskEntity = taskService.findById(taskId)
                .orElseThrow(()->new IllegalArgumentException("指定されたIDのタスクが見つかりません。id=" + taskId));
        model.addAttribute("task",TaskDTO.toDTO(taskEntity));
        return "tasks/detail";
    }

    //GET /tasks/creationForm
    @GetMapping("/tasks/creationForm")
    public String showCreationForm(Model model) {
        model.addAttribute("mode", "CREATE");
        model.addAttribute("taskForm", new TaskForm(null, null, "TODO"));
        return "tasks/form";
    }

    //POST /tasks
    @PostMapping("/tasks")
    public String create(TaskForm form) {
        log.info("create()が呼び出されました taskForm={}", form);
        var newEntity =new TaskEntity(null,form.summary(),form.description(), TaskStatus.valueOf(form.status()));
        taskService.create(newEntity);
        // TODO: Serviceに作成処理を追加したら、ここで taskService.create(taskForm) を呼ぶ
        return "redirect:/tasks";
    }
}