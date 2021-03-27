package com.example.blog

import com.example.blog.legacy.ImagegalleryProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(ImagegalleryProperties::class)
class ImagegalleryApplication

fun main(args: Array<String>) {
	runApplication<ImagegalleryApplication>(*args)

	val webscraperTagsFromPicJumbo = WebscraperTagsFromPicJumbo();
	webscraperTagsFromPicJumbo.init();
//	val webscraper = Webscraper()
//	webscraper.init()

}
