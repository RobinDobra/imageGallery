package com.example.blog

import com.example.blog.legacy.BlogProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(BlogProperties::class)
class BlogApplication

fun main(args: Array<String>) {
	runApplication<BlogApplication>(*args)

	val webscraperTagsFromPicJumbo = WebscraperTagsFromPicJumbo();
	webscraperTagsFromPicJumbo.init();
//	val webscraper = Webscraper()
//	webscraper.init()

}
