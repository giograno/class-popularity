package ch.uzh.ifi.popularity.api

import ch.uzh.ifi.popularity.data.Repo
import ch.uzh.ifi.popularity.data.SourceFile
import ch.uzh.ifi.popularity.names.FilenameStrategy
import org.junit.Test
import org.junit.jupiter.api.Assertions

class ClassNameExtractorTest {

    private val extractor = FilenameStrategy()

    @Test
    fun testClassExtraction() {
        val repo = Repo(name="JavaGuide",
            html_url="https://github.com/Snailclimb/JavaGuide",
            url="https://api.github.com/repos/Snailclimb/JavaGuide",
            full_name = "Snailclimb/JavaGuide"
        )
        val sourceQuery = SourceQuery()
        val file: SourceFile = sourceQuery.getSourceFiles(repo=repo)[0]
        val res = extractor.getClassNames(file)
        Assertions.assertEquals(mutableListOf("callabl", "demo"), res)
    }

    @Test
    fun testStemming() {
        val word = "caresses"
        val outcome = "caress"
        Assertions.assertEquals(outcome, extractor.stemWord(word))
    }

    @Test
    fun testCamelCase() {
        val word = "ThisIsATest"
        val outcome = mutableListOf("this", "test")
        Assertions.assertEquals(outcome, extractor.camelCase(word))
    }
}