package com.example.blog.controller


import com.example.blog.Image
import com.example.blog.ImageTagRepository
import com.example.blog.utility.WebscraperTagsFromPicJumbo
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.*
import java.util.stream.Collectors
import java.util.stream.IntStream
import kotlin.streams.toList


@Controller
@RequestMapping(path = ["/"])
@RequiredArgsConstructor
class ImageController(val imageTagRepository: ImageTagRepository) {

    @RequestMapping(path = ["/loadtagfiletoram"])
    fun loadFileToRam(model: Model): String {
        val (namesLoL, tagsLoL) = WebscraperTagsFromPicJumbo().fileToLoLPair()

        model["namesLoL"] = namesLoL
        model["tagsLoL"] = tagsLoL.stream().map { mutableList ->
            mutableList.joinToString(separator = " ").replace("[", "").replace("]", "").replace(",", "")
        }.toList()
        return "index"
    }

    @RequestMapping(path = ["/"])
    fun mainPageWorkflow(model: Model, @RequestParam("page") page: Optional<Int>, @RequestParam("size") size: Optional<Int>): String {
        val currentPage = page.orElse(1)
        val pageSize = size.orElse(2)
        val imageTagPage: Page<Image> = imageTagRepository.findAll(PageRequest.of(currentPage - 1, pageSize))

        model["imageObjects"] = imageTagPage

        val totalPages: Int = imageTagPage.totalPages
        if (totalPages > 0) {
            val pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList())
            model.addAttribute("pageNumbers", pageNumbers)
        }
        return "index"
    }

    @RequestMapping(path = ["/{tag}"])
    fun getImagesByTag(model: Model, @PathVariable("tag") tag: String, @RequestParam("page") page: Optional<Int>, @RequestParam("size") size: Optional<Int>): String {
        val currentPage = page.orElse(1)
        val pageSize = size.orElse(1)

        val imageTagPage: Page<Image> = imageTagRepository.findAllWithTag(tag, PageRequest.of(currentPage - 1, pageSize))

        model["imageObjects"] = imageTagPage

        val totalPages: Int = imageTagPage.totalPages
        if (totalPages > 0) {
            val pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList())
            model.addAttribute("pageNumbers", pageNumbers)
        }
        return "index"
    }
}
