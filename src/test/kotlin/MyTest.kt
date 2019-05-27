import IgnoreUtils.getNotIgnoredFiles
import IgnoreUtils.getPathsToIgnore
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

import java.io.File
import java.nio.file.Paths
import java.util.*

import java.util.Arrays.*

class MyTest {
    private val testDataDir = File("testdata/test/")

    private var ignoredEntriesTestExpected: Set<File>? = null
    private var notIgnoredFilesTestExpected: List<File>? = null

    @Before
    fun init() {
        ignoredEntriesTestExpected = TreeSet(asList(
                testDataDir.toPath().resolve(Paths.get("keys")).toFile().absoluteFile,
                testDataDir.toPath().resolve(Paths.get("config/local.txt")).toFile().absoluteFile,
                testDataDir.toPath().resolve(Paths.get("someDir/innerDir3/innerFile31.txt")).toFile().absoluteFile
        ))

        notIgnoredFilesTestExpected = ArrayList(asList(
                File("testdata/test/.ignore"),
                File("testdata/test/someDir/innerDir2/innerDir2.txt"),
                File("testdata/test/someDir/innerDir3/innerFile32.csv"),
                File("testdata/test/someDir/file2.txt"),
                File("testdata/test/someDir/innerDir1/innerFile1.txt"),
                File("testdata/test/someDir/file1.txt"),
                File("testdata/test/someDir/file3.cfg"),
                File("testdata/test/config/global.txt"),
                File("testdata/test/someFIle.csv")
        ))
    }

    @Test
    @Throws(Exception::class)
    fun testIgnoredEntries() {
        assertEquals(ignoredEntriesTestExpected, getPathsToIgnore(testDataDir))
    }

    @Test
    @Throws(Exception::class)
    fun testCollectNotIgnoredFiles() {
        assertEquals(notIgnoredFilesTestExpected, getNotIgnoredFiles(testDataDir))
    }
}