package com.example.blog.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class TermsController {

    @GetMapping("/terms")
    fun showTermsAndConditions(): String {
        return "termsandconditions"
    }

}
