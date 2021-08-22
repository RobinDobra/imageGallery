package com.example.blog.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody


@Controller
class RobotsController {

    @RequestMapping(value = ["/robots.txt", "/robot.txt"])
    @ResponseBody
    fun getRobotsTxt(): String? {
        return """
            User-agent: *
            Allow: /
            """.trimIndent()
    }
}
