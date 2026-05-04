//paakageの指定
package com.example.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    //ルートパスにアクセスしたときにindex.htmlを返す
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
