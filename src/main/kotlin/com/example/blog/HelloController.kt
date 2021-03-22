package com.example.blog

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import javax.imageio.ImageIO

import java.awt.image.BufferedImage
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths


import org.springframework.ui.set
import java.awt.Image
import java.util.stream.Collectors





@Controller
@RequestMapping(path= ["/"])
class HelloController {

    @GetMapping("/helloo")
    fun helloKotlin(model : Model): String {
        val imagePaths = readFileNamesFromResources()
        println(imagePaths)

        model["imagePaths"] = imagePaths


        return "portfolio-own"
    }

    fun readFileNamesFromResources(): List<String> {
        val outOfProjectResourcesPath = getOutOfProjectResourcesPath()
        val imagesDirectory = outOfProjectResourcesPath?.resolve("original_images");

        Files.walk(Paths.get(imagesDirectory.toString()), 2)
            .use { stream ->
            return stream
                .filter { file -> !Files.isDirectory(file) }
                .map { obj: Path -> obj.fileName }
                .map(Path::toString)
                .collect(Collectors.toList())
        }
    }

    fun getOutOfProjectResourcesPath(): Path? {
        val projectDirAbsolutePath : Path = Paths.get("").toAbsolutePath();
        return projectDirAbsolutePath.parent.resolve("out_of_project_resources");
    }

    @GetMapping("/hello")
    fun hellooKotlin(model : Model): String {
        val imagePaths = readFileNamesFromResources()
        println(imagePaths)

        model["imagePaths"] = imagePaths
        return "portfolio-grid_o"
    }
}
