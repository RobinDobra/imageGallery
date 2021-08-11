package com.example.blog

import com.example.blog.utility.WebscraperTagsFromPicJumbo
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class SetupData(val imageTagRepository: ImageTagRepository) {


    @PostConstruct
    fun initValues() {
        val (namesLoL, tagsLoL) = WebscraperTagsFromPicJumbo().fileToLoLPair()

        namesLoL.forEachIndexed { index, mutableList ->
            val fileName: String = namesLoL[index][0]
            val tags: List<String> = tagsLoL[index]
            val image: Image = Image(fileName, tags)
            imageTagRepository.save(image)
        }
    }
}
