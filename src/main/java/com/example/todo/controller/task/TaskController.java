package com.example.todo.controller.task;

import com.example.todo.service.task.TaskEntity;
import com.example.todo.service.task.TaskStatus;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.todo.service.task.TaskService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tasks")

public class TaskController {

    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    //TaskServiceクラスのインスタンスを作成
    private final TaskService taskService;

    //ハンドラー名
    @GetMapping
    public String list(TaskSearchForm searchForm,Model model){
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
    @GetMapping("/{id:\\d+}")
    public  String showDetail(@PathVariable("id") long taskId, Model model){
        //taskId->taskEntity
        var taskEntity = taskService.findById(taskId)
                .orElseThrow(TaskNotFoundException::new);
        model.addAttribute("task",TaskDTO.toDTO(taskEntity));
        return "tasks/detail";
    }

    //GET /tasks/creationForm
    @GetMapping("/creationForm")
    public String showCreationForm(@ModelAttribute  TaskForm form, Model model) {
        model.addAttribute("mode","CREATE");
        return "tasks/form";
    }

    //POST /tasks
    @PostMapping
    public String create(@Validated  TaskForm form, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            return showCreationForm(form, model);
        }
        taskService.create(form.toEntity());
        // TODO: Serviceに作成処理を追加したら、ここで taskService.create(taskForm) を呼ぶ
        return "redirect:/tasks";
    }

    // GET /tasks/{id}/editForm
    @GetMapping("/{id:\\d+}/editForm")
    public String showEditForm(@PathVariable("id") long id, Model model) {
        var taskEntity = taskService.findById(id)
                .orElseThrow(TaskNotFoundException::new);

        model.addAttribute("mode", "EDIT");
        model.addAttribute("id", id);
        model.addAttribute("taskForm", TaskForm.fromEntity(taskEntity));
        return "tasks/form";
    }

    // PUT /tasks/{id}
    @PutMapping("{id}")
    public String update(@PathVariable("id") long id,
                         @Validated @ModelAttribute  TaskForm form,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()){
            model.addAttribute("mode", "EDIT");
            return "tasks/form";
        }

        //update機能を追加
        var entity = form.toEntity(id);
        taskService.update(entity);
        return "redirect:/tasks/{id}";
    }

    // POST /tasks/1 (hidden: _method :delete)
    //-> DELETE /tasks/1
    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") long id) {
        taskService.delete(id);
        return "redirect:/tasks";
    }
}