package com.example.blog

import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.nio.channels.Channels
import java.nio.channels.ReadableByteChannel
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class WebscraperImagesFromUnsplash {
    private var linksToCategories: MutableList<String> = mutableListOf()
    private var linksToPhotoPreviews: MutableList<String> = mutableListOf()
    private var linksToDownload: MutableList<String> = mutableListOf()

    fun init() {

        getLinksFromStartingPageToCategories()
        getLinksFromCategoriesToPhotos()
        linksToPhotoPreviews.forEach(System.out::println)
        println("4) --------------------------------------------")
        getLinksFromPhotosToDownload()
        linksToDownload.forEach(System.out::println)
        println("5) --------------------------------------------")


//        downloadImages()
//        print("I am done downloading")

    }


    private fun getLinksFromStartingPageToCategories() {
        val url = "https://unsplash.com/";
        val startString = "https://unsplash.com/t/"

        linksToCategories = givenTheUrlAndStartStringReturnListWithLinks(url, startString)

    }

    private fun getLinksFromCategoriesToPhotos() {
        val startString = "https://unsplash.com/photos/";
        for (urlToCategory in linksToCategories) {
            println("2) I am doing Something")
            //ToDo: deleteLater
            if (urlToCategory.contains("wallpapers") || urlToCategory.contains("experimental")) { //ToDo: deleteLater
                linksToPhotoPreviews.addAll(givenTheUrlAndStartStringReturnListWithLinks(urlToCategory, startString))
                linksToPhotoPreviews.removeIf { linkToRemove -> linkToRemove.endsWith("download?force=true") }
            }
        }
    }

    private fun getLinksFromPhotosToDownload() {
        val startString = "https://unsplash.com/photos/";
        for (urlToPhotoPreview in linksToPhotoPreviews) {
            println("3) I am doing Something")
            linksToDownload.addAll(givenTheUrlAndStartStringReturnListWithLinks(urlToPhotoPreview, startString))
        }
    }

    private fun downloadImages() {
        var cnt = 0
        val dir = File("images/wallpapers")
        dir.mkdirs()
        for (imageLink in linksToDownload) {
            println("4) I am doing Something")
            cnt++
            val rbc = Channels.newChannel(URL(imageLink).openStream())
            val fos = FileOutputStream("$dir\\$cnt.jpg")
            fos.channel.transferFrom(rbc, 0, Long.MAX_VALUE)

            rbc.close()
            fos.close()
        }


//        FileUtils.copyURLToFile(url, "output.txt")
    }

    private fun givenTheUrlAndStartStringReturnListWithLinks(url: String, startString: String): MutableList<String> {
        val links: MutableList<String> = mutableListOf()
        for (anchor in givenTheUrlGetAnchors(url)) {
            println("1) I am doing Something")
            val link = anchor.attr("abs:href")

            if (link.startsWith(startString))
                links.add(link)
        }
        return links;
    }

    private fun givenTheUrlGetAnchors(url: String): Elements {
        val doc = Jsoup.connect(url).get()
        println(doc)
        // ToDO: hier im Breakpint gucken, ob irgendwo sowas wie https://unsplash.com/photos/bR4lTpjKW2o/download?force=true&w=640 steht
        val a = doc.select("a")
        print(a)
        return a
    }
}
