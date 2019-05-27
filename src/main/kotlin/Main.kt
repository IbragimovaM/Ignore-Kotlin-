import java.io.File
import java.io.IOException
import java.nio.file.Paths



fun main() {
    val curDir = File(System.getProperty("user.dir"))
    val curDirAbsPath = Paths.get(curDir.absolutePath)

    val files: List<File>
    try {
        files = IgnoreUtils.getNotIgnoredFiles(curDir)
    } catch (e: IOException) {
        println("Error occurred while retrieving ignore list: " + e.message)
        return
    }

    for (file in files) {
        System.out.println("./" + curDirAbsPath.relativize(file.toPath()))
    }
}