package com.example.blog

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping


import org.springframework.ui.set
import org.springframework.web.bind.annotation.PathVariable
import kotlin.streams.toList


@Controller
@RequestMapping(path = ["/"])
class ImageController {

    @RequestMapping(path = ["/"])
    fun helloKotlin(model: Model): String {
        val (namesLoL, tagsLoL) = WebscraperTagsFromPicJumbo().fileToPojos()
        model["namesLoL"] = namesLoL
        model["tagsLoL"] = tagsLoL.stream().map{ mutableList ->
            mutableList.joinToString(separator = " ").replace("[", "").replace("]", "").replace(",", "") }.toList()
        model["test"] = "teststring"
        return "portfolio-own"
    }

    @GetMapping("/{imagename}")
    fun showImage(@PathVariable("imagename") imagename: String, model: Model): String {
        val utils = Utils()
//        val imagePaths: List<String> = utils.readFileNamesFromResources()
        model["imagePath"] = imagename
        return "portfolio-own-single"
    }

    fun convertToUrlStyle(list: List<String>): List<String> {
        list.map { name -> name.toLowerCase(); name.replace(" ", "-") }
        return list
    }

}
