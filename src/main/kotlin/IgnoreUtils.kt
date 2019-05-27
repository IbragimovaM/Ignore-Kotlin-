import java.io.File
import java.nio.file.Paths
import java.nio.file.Files
import java.io.IOException
import java.util.*


object IgnoreUtils {

    private const val DEFAULT_IGNORE_FILE_NAME = ".ignore"

    @Throws(IOException::class)
    fun getNotIgnoredFiles(dir: File, ignoreFileName: String = DEFAULT_IGNORE_FILE_NAME): List<File> {
        if (!dir.isDirectory)
            throw IllegalArgumentException("Not a directory")

        val result = ArrayList<File>()
        val ignored = getPathsToIgnore(dir, ignoreFileName)

        getFiles(dir, result, ignored)

        return result
    }

    @Throws(IOException::class)
    fun getPathsToIgnore(dir: File, ignoreFileName: String = DEFAULT_IGNORE_FILE_NAME): Set<File> {
        if (!dir.isDirectory)
            throw IllegalArgumentException("Not a directory")

        val ignoreList = getFileWithIgnoredList(dir, ignoreFileName)
        if (!(ignoreList.exists() && ignoreList.isFile))
            return HashSet()

        val sc = Scanner(ignoreList)

        val pathsToIgnore = TreeSet<File>()
        while (sc.hasNextLine()) {
            val tempLine = sc.nextLine().trim()
            if (tempLine.startsWith("#") || tempLine.isEmpty()) {
                continue
            }

            val tempFile = Paths.get(dir.path, tempLine).toAbsolutePath()
            if (!Files.exists(tempFile))
                continue

            val absoluteFile = tempFile.normalize().toFile().absoluteFile
            pathsToIgnore.add(absoluteFile)
        }

        return pathsToIgnore
    }

    private fun getFiles(curDir: File, collector: MutableList<File>, pathsToIgnore: Set<File>) {
        val children = curDir.listFiles() ?: return

        for (file in children) {
            if (pathsToIgnore.contains(file.absoluteFile))
                continue

            if (file.isFile) {
                collector.add(file)
            } else {
                getFiles(file, collector, pathsToIgnore)
            }
        }
    }

    private fun getFileWithIgnoredList(curDir: File, name: String): File {
        val ignorePath = Paths.get(curDir.path).resolve(name)
        return File(ignorePath.toUri())
    }
}