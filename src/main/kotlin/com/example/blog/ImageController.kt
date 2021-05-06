package com.example.blog

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping


import org.springframework.ui.set
import org.springframework.web.bind.annotation.PathVariable


@Controller
@RequestMapping(path= ["/"])
class ImageController {

    @RequestMapping(path= ["/"])
    fun helloKotlin(model : Model): String {
        val utils = Utils()
        val imagePaths: List<String> = utils.readFileNamesFromResources()
        println(imagePaths)
        model["tagsOnFrontPage"] = utils.tagsOnFrontPags()
        model["imagePaths"] = imagePaths

        return "portfolio-own"
    }
    @GetMapping("/{imagename}")
    fun showImage(@PathVariable("imagename") imagename : String, model : Model): String {
        val utils = Utils()
//        val imagePaths: List<String> = utils.readFileNamesFromResources()
        model["imagePath"] = imagename
        return "portfolio-own-single"
    }
//
//    @GetMapping("/hello")
//    fun hellooKotlin(model : Model): String {
//        val imagePaths = readFileNamesFromResources()
//        println(imagePaths)
//
//        model["imagePaths"] = imagePaths
//        return "portfolio-grid_o"
//    }
}