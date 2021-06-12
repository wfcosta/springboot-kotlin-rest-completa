package br.com.forum.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class HelloController{

    @RequestMapping("/")
    @ResponseBody
    fun hello():String{
        return "Hello World!"
    }

}