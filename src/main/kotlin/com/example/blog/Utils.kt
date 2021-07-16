package com.example.blog

import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import java.util.stream.Collectors

class Utils {

    fun readFileNamesFromOutOfProjectResources(folder: String): List<String> {
        val outOfProjectResourcesPath = getOutOfProjectResourcesPath()
        val imagesDirectory = outOfProjectResourcesPath?.resolve(folder);

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




}
