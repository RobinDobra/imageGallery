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

    fun init() {

//        getLinksFromStartingPageToCategories()
//        println("3) --------------------------------------------")
//        getLinksFromCategoriesToPhotos()
//        println("4) --------------------------------------------")
        getTagsFromPhotos()
//        tags.forEach(System.out::println)

//        removeDuplicatesFromTagsFile()
//        fileRowsToNameAndTagsSet()


    }

    private fun getLinksFromStartingPageToCategories() {
        val url = "https://picjumbo.com/";
        val startString = "https://picjumbo.com/free-stock-photos/"

        linksToCategories = givenTheUrlAndStartStringReturnListWithLinks(url, startString)

    }

    private fun givenTheUrlAndStartStringReturnListWithLinks(url: String, startString: String): MutableList<String> {
        val links: MutableList<String> = mutableListOf()
        for (anchor in givenTheUrlGetAnchors(url)) {
//            println("1) I am doing Something")
            val link = anchor.attr("abs:href")
            if (link.startsWith(startString)) {
                links.add(link)
            }
        }
        return links;
    }

    private fun givenTheUrlGetAnchors(url: String): Elements {
        val doc = Jsoup.connect(url).timeout(15000).get()
        return doc.select("a")
    }


    private fun getLinksFromCategoriesToPhotos() {
        val startString = "https://picjumbo.com/";

        // We are deleting any duplicate entries from our list by creating a set and writing its values to the list
        val set: Set<String> = HashSet<String>(linksToCategories)
        linksToCategories.clear()
        linksToCategories.addAll(set)
        linksToCategories.sort()

        var nextPaginationUrl: String? = "abc"
        for (urlToCategory in linksToCategories) {
            println("##################################################")
            println("Category: $urlToCategory")
//          if (urlToCategory.contains("abstract")) { //deleteLater
            getCategoryPagination(urlToCategory, startString)
//                }

        }
    }


    private fun getTagsFromPhotos() {
        val url = "https://picjumbo.com/";
        val startString = "https://picjumbo.com/free-photos/"

        val links: MutableList<String> = mutableListOf()
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


    private fun givenTheUrlAndStartStringGetTagsPerCategory(url: String, startString: String) {
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
        writer.flush();
        println("Contents added............")
    }

    private fun fileRowsToNameAndTagsSet() {
        var fileName = fileMinimized;
        val sc = Scanner(File(fileName))
        val result: MutableList<MutableList<String>> = mutableListOf()

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
                val imageTagsFromFileList : MutableList<String> = imageTagsFromFile.split(", ").toMutableList()

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



//                for (currentTag in imageTagsFromFileList) {
//                    // We are deleting out of order tags which we wronly parsed from the website
//                    if (currentTag < previousTag) {
//                        println("$currentTag is smaller than $previousTag")
//                        imageTagsFromFileList.remove(currentTag)
//                    }
//
//                    previousTag = currentTag
//                }

                fileNameAndTagsAsList.addAll(imageTagsFromFileList)
                result.add(fileNameAndTagsAsList)
            }
            println("test");
        }
        println("test");
    }
}
