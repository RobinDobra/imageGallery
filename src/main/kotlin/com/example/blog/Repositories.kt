package com.example.blog

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface ArticleRepository : CrudRepository<Article, Long> {
	fun findBySlug(slug: String): Article?
	fun findAllByOrderByAddedAtDesc(): Iterable<Article>
}

interface UserRepository : CrudRepository<User, Long> {
	fun findByLogin(login: String): User?
}

interface ImageRepository : JpaRepository<Image, Long> {

	@Query("SELECT i FROM Image i JOIN i.tags t WHERE t = LOWER(:tag)")
	fun retrieveByTag(@Param("tag") tag: String?): List<Image?>?
}
