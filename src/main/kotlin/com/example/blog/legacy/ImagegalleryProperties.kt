package com.example.blog.legacy

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("imagegallery")
data class ImagegalleryProperties(var title: String, val banner: Banner) {
	data class Banner(val title: String? = null, val content: String)
}