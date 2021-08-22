package com.example.blog.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class PrivacyController {
@GetMapping("/privacypolicy")
    fun showPrivacyPolicy(): String {
        return "privacypolicy"
    }
}
