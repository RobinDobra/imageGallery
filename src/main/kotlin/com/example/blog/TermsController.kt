package com.example.blog

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class TermsController {

    @GetMapping("/terms")
    fun showImage(): String {
        return "terms"
    }

}
