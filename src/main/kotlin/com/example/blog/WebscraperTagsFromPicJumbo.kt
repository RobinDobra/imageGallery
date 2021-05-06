package com.example.blog

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import org.springframework.core.codec.Hints.merge
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.io.FileReader

import java.io.BufferedReader
import java.io.FileWriter

import java.io.BufferedWriter
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.HashSet
import java.util.ArrayList

class WebscraperTagsFromPicJumbo {
    private var file = "tags.txt";
    private var fileMinimized = "tagsWithoutDuplicates.txt"
    private var linksToCategories: MutableList<String> = mutableListOf()
    private var linksToPhotoPreviews: MutableList<String> = mutableListOf()
    private var tags: MutableList<String> = mutableListOf()

    val tagManagerList: MutableList<MutableList<String>> = mutableListOf()

    fun init() {

//        getLinksFromStartingPageToCategories()
////        println("3) --------------------------------------------")
//        getLinksFromCategoriesToPhotos()
////        println("4) --------------------------------------------")
//        getTagsFromPhotos()
//        tags.forEach(System.out::println)
//
//        removeDuplicatesFromTagsFile()
//        fileToPojos()
        print("done")


    }

    private fun getLinksFromStartingPageToCategories() {
        println(" ##########################" + object : Any() {}.javaClass.enclosingMethod.name + "########################## ")
        val url = "https://picjumbo.com/";
        val startString = "https://picjumbo.com/free-stock-photos/"

        linksToCategories = givenTheUrlAndStartStringReturnListWithLinks(url, startString)

    }

    private fun givenTheUrlAndStartStringReturnListWithLinks(url: String, startString: String): MutableList<String> {
        println(" ##########################" + object : Any() {}.javaClass.enclosingMethod.name + "########################## ")

        val links: MutableList<String> = mutableListOf()
        for (anchor in givenTheUrlGetAnchors(url)) {
            val link = anchor.attr("abs:href")
            if (link.startsWith(startString)) {
                links.add(link)
            }
        }
        return links;
    }

    private fun givenTheUrlGetAnchors(url: String): Elements {
        println(" ##########################" + object : Any() {}.javaClass.enclosingMethod.name + "########################## ")
        val doc = Jsoup.connect(url).timeout(15000).get()
        return doc.select("a")
    }

    private fun givenTheUrlGetItempropAnchor(url: String): Elements {
        println(" ##########################" + object : Any() {}.javaClass.enclosingMethod.name + "########################## ")

        val doc = Jsoup.connect(url).timeout(15000).get()
        return doc.select("span a[title]")
    }

    private fun getLinksFromCategoriesToPhotos() {
        println(" ##########################" + object : Any() {}.javaClass.enclosingMethod.name + "########################## ")

        val startString = "https://picjumbo.com/";

        // We are deleting any duplicate entries from our list by creating a set and writing its values to the list
        val set: Set<String> = HashSet<String>(linksToCategories)
        linksToCategories.clear()
        linksToCategories.addAll(set)
        linksToCategories.sort()

        for (urlToCategory in linksToCategories) {
            println("##################################################")
            println("Category: $urlToCategory")
            if (urlToCategory.contains("abstract")) { //deleteLater
                getCategoryPagination(urlToCategory, startString)
            }

        }
    }

    private fun getTagsFromPhotos() {
        println(" ##########################" + object : Any() {}.javaClass.enclosingMethod.name + "########################## ")

        val url = "https://picjumbo.com/";
        val startString = "https://picjumbo.com/free-photos/"

        val tagsFile = initialiseFile()


        for (photoPreview in linksToPhotoPreviews) {
            println("7) PhotoPreview Link: $photoPreview")

//            var tagsForPhotoPreview: MutableList<String> = givenTheUrlAndStartStringReturnListWithLinks(photoPreview, startString)
            var tagsForPhotoPreview: MutableList<String> = mutableListOf()

            for (anchor in givenTheUrlGetItempropAnchor(photoPreview)) {
                val link = anchor.attr("abs:href")
                if (link.startsWith(startString)) {
                    tagsForPhotoPreview.add(link)
                }
            }

            val photoPreviewRawName = photoPreview.substring(photoPreview.substring(0, photoPreview.lastIndexOf("/")).lastIndexOf("/") + 1, photoPreview.length - 1)
            tagsForPhotoPreview = urlToRawName(tagsForPhotoPreview)

            tags.addAll(tagsForPhotoPreview)
            tagsFile.appendText("$photoPreviewRawName ")
            tagsFile.appendText("${tagsForPhotoPreview}\n\n")
        }
    }

    private fun givenTheUrlAndStartStringGetTagsPerCategory(url: String, startString: String) {
        println(" ##########################" + object : Any() {}.javaClass.enclosingMethod.name + "########################## ")

        val tagsFile = initialiseFile()


        for (photoPreview in linksToPhotoPreviews) {
            println("7) PhotoPreview Link: $photoPreview")
            var tagsForPhotoPreview: MutableList<String> = givenTheUrlAndStartStringReturnListWithLinks(photoPreview, startString)

            val photoPreviewRawName = photoPreview.substring(photoPreview.substring(0, photoPreview.lastIndexOf("/")).lastIndexOf("/") + 1, photoPreview.length - 1)
            tagsForPhotoPreview = urlToRawName(tagsForPhotoPreview)

            tags.addAll(tagsForPhotoPreview)
            tagsFile.appendText("$photoPreviewRawName ")
            tagsFile.appendText("${tagsForPhotoPreview}\n\n")
        }
    }

    private fun initialiseFile(): File {
        println(" ##########################" + object : Any() {}.javaClass.enclosingMethod.name + "########################## ")

        val fileName = File(file)
        val isNewFileThanCreate: Boolean = fileName.createNewFile()

        if (isNewFileThanCreate) {
            println("$fileName is created successfully.")
        } else {
            println("$fileName already exists.")
        }
        return fileName
    }

    private fun urlToRawName(tagsForPhotoPreview: MutableList<String>): MutableList<String> {
        println(" ##########################" + object : Any() {}.javaClass.enclosingMethod.name + "########################## ")

        val tagNamesOnly: MutableList<String> = mutableListOf()
        var previousTag = ""
        var currentTag = ""
        for (tag in tagsForPhotoPreview) {
            tagNamesOnly.add(tag.substring(tag.substring(0, tag.lastIndexOf("/")).lastIndexOf("/") + 1, tag.length - 1))
        }

        return tagNamesOnly
    }

    // ToDo: I know the first landing page of each category is not saved, but that is good because nobody will recognize the images from the second page onwards
    private fun getCategoryPagination(urlToCategory: String, startString: String): String? {
        println(" ##########################" + object : Any() {}.javaClass.enclosingMethod.name + "########################## ")

        var url: String? = urlToCategory
        while (url != null) {
            try {
                val doc: Document = Jsoup.connect(url).get()
                url = null
                for (urls in doc.getElementsByClass("next")) {
                    //perform your data extractions here.
                    url = urls?.absUrl("href").toString()
                    print("Current Pagination: ${url}")
                    linksToPhotoPreviews.addAll(givenTheUrlAndStartStringReturnListWithLinks(url, startString))
                    linksToPhotoPreviews.removeIf { linkToRemove ->
                        linkToRemove.contains("free-stock-photos") ||
                                linkToRemove.contains("premium") ||
                                linkToRemove.contains("free-photos")

                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return url
    }


    private fun removeDuplicatesFromTagsFile() {
        var fileName = file;
        val sc = Scanner(File(fileName))
        var input: String? = null
        val writer = FileWriter(fileMinimized)
        val set: MutableSet<String> = HashSet<String>()

        while (sc.hasNextLine()) {
            input = sc.nextLine()
            if (set.add(input)) {
                writer.append(
                    """
                $input
                
                """.trimIndent()
                )
            }
        }
        writer.flush()
        writer.close()
        sc.close()
        println("Contents added............")
    }

    private fun fileToPojos() {
        var fileName = fileMinimized;
        val sc = Scanner(File(fileName))

        // for every row
        while (sc.hasNextLine()) {
            val lineFromFile = sc.nextLine()
            val openingSquareBracketIndex = lineFromFile.indexOf("[") + 1
            val closingSquareBracketIndex = lineFromFile.indexOf("]")
            if (openingSquareBracketIndex > -1 && closingSquareBracketIndex > -1) {
                // imageName per Row (String)
                val imageNameFromFile = lineFromFile.substring(0, lineFromFile.indexOf(" "))
                val imageTagsFromFile = lineFromFile.substring(openingSquareBracketIndex, closingSquareBracketIndex)
                // imageTags per Row (arraylist)
                val imageTagsFromFileList: MutableList<String> = imageTagsFromFile.split(", ").toMutableList()
                // imageName per Row + imageTags per Row (arraylist)
                val fileNameAndTagsAsList: MutableList<String> = mutableListOf(imageNameFromFile)
                var previousTag = ""

                with(imageTagsFromFileList.listIterator()) {
                    forEach {
                        if (it < previousTag) {
                            remove()
                        }
                        previousTag = it
                    }
                }

                fileNameAndTagsAsList.addAll(imageTagsFromFileList)
                tagManagerList.add(fileNameAndTagsAsList)
            }
            println("test")
        }
        println("test")
        sc.close()
    }

}