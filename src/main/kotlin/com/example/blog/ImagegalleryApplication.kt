package com.example.blog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ImagegalleryApplication

fun main(args: Array<String>) {
	runApplication<ImagegalleryApplication>(*args)

	val webscraperTagsFromPicJumbo = WebscraperTagsFromPicJumbo();
	webscraperTagsFromPicJumbo.init();
//	val webscraper = Webscraper()
//	webscraper.init()

}
