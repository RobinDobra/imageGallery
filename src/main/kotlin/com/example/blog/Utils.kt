package com.example.blog

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Collectors

class Utils {

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

    fun tagsOnFrontPags(): Array<String> {
        return arrayOf(
            "animals",
            "blue-water",
            "business",
            "close-up",
            "fashion",
            "girl",
            "health",
            "nature",
            "spring",
            "wallpapers",
            "woman"
        )
    }

}
