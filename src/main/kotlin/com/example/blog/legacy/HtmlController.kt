package com.example.blog.legacy
//
//import com.example.blog.Article
//import com.example.blog.ArticleRepository
//import com.example.blog.User

//@RequestMapping(path= ["/legacy"])
//@Controller
//class HtmlController(private val repository: ArticleRepository,
//					 private val properties: ImagegalleryProperties
//) {
//
//	fun blog(model: Model): String {
//		model["title"] = properties.title
//		model["banner"] = properties.banner
//		model["articles"] = repository.findAllByOrderByAddedAtDesc().map { it.render() }
//		return "blog"
//	}
//
//
//	@GetMapping("/article/{slug}")
//	fun article(@PathVariable slug: String, model: Model): String {
//		val article = repository
//				.findBySlug(slug)
//				?.render()
//				?: throw ResponseStatusException(NOT_FOUND, "This article does not exist")
//		model["title"] = article.title
//		model["article"] = article
//		return "article"
//	}
//
//	fun Article.render() = RenderedArticle(
//			slug,
//			title,
//			headline,
//			content,
//			author,
//			addedAt.format()
//	)
//
//	data class RenderedArticle(
//		val slug: String,
//		val title: String,
//		val headline: String,
//		val content: String,
//		val author: User,
//		val addedAt: String)
//
//}
