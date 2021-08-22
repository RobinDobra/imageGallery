package com.example.blog

import javax.persistence.*

@Entity
data class Image(
	var filename: String,
	@ElementCollection var tags : List<String>,
	@Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Long? = null)
