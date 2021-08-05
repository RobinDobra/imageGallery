package com.example.blog

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class PrivacyController {
    @GetMapping("/privacypolicy")
    fun showPrivacyPolicy(): String {
        return "privacypolicy"
    }
}
