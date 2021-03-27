package com.example.blog

import java.nio.file.Path
import java.time.LocalDateTime
import javax.persistence.*
import java.util.HashSet

import javax.persistence.ManyToMany

import javax.persistence.GenerationType

import javax.persistence.GeneratedValue

@Entity
class Article(
		var title: String,
		var headline: String,
		var content: String,
		@ManyToOne var author: User,
		var slug: String = title.toSlug(),
		var addedAt: LocalDateTime = LocalDateTime.now(),
		@Id @GeneratedValue var id: Long? = null)

@Entity
class User(
		var login: String,
		var firstname: String,
		var lastname: String,
		var description: String? = null,
		@Id @GeneratedValue var id: Long? = null)

@Entity
data class Image(
	var filename: String,
	@ManyToMany(cascade = [CascadeType.ALL])
	@JoinTable(name = "image_tags", joinColumns = [JoinColumn(name = "image_id", referencedColumnName = "id")], inverseJoinColumns = [JoinColumn(name = "tag_id", referencedColumnName = "id")])
	var tags : MutableSet<Tag> = HashSet(),
	@Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Long? = null)

@Entity
data class Tag(
	@Id	@GeneratedValue(strategy = GenerationType.AUTO)	val id: Long? = null,
	var name: String? = null,

	@ManyToMany(mappedBy = "tags")
	var images: MutableSet<Image> = HashSet())

//@Entity
//data class Image(
//	var filename: String,
//	var tags : ArrayList<String>)
