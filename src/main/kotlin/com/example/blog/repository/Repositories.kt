package com.example.blog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ImageTagRepository : PagingAndSortingRepository<Image, Long> {

	@Query("SELECT i FROM Image i JOIN i.tags t WHERE t = LOWER(:tag)")
	fun findAllWithTag(@Param("tag") tag: String, pageable: Pageable): Page<Image>
}
