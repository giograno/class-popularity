package ch.uzh.ifi.popularity.api

import ch.uzh.ifi.popularity.data.Repo
import ch.uzh.ifi.popularity.data.SourceFile
import ch.uzh.ifi.popularity.names.ParsingFileStrategy
import org.junit.Test

import org.junit.jupiter.api.Assertions

class SourceQueryTest {

    @Test
    fun testGetSourceFiles() {
        val repo = Repo(name="JavaGuide",
            html_url="https://github.com/Snailclimb/JavaGuide",
            url="https://api.github.com/repos/Snailclimb/JavaGuide",
            full_name = "Snailclimb/JavaGuide")
        val sourceQuery = SourceQuery()
        val files = sourceQuery.getSourceFiles(repo=repo)
        Assertions.assertEquals(5, files.size)
    }

    @Test
    fun testGetSourceContent() {
        val source = SourceFile(name="PackageMetrics",
            path = "src/it/unisa/sesa/repominer/metrics/PackageMetrics.java",
            url = "not important",
            html_url = "not important",
            repo_full_name = "mattmezza/repominerEvo",
            ownerType = "User",
            defaultBranch = "master")
        val sourceQuery = SourceQuery()
        val code = sourceQuery.getRawText(source)
        Assertions.assertNotNull(code)
    }

    @Test
    fun testRegex() {
        val code = "/**\n" +
                " *\n" +
                " * @author XXXX\n" +
                " * Introduction: A common interface that judges all kinds of algorithm tags.\n" +
                " * some other comment\n" +
                " */\n" +
                "public class TagMatchingInterface \n" +
                "{\n" +
                "  // content\n" +
                "public interface Int\n" +
                " public class InnerClazz{\n" +
                "class NoPublic\n" +
                "    // content\n" +
                "  }\n" +
                "}"

        val parsing = ParsingFileStrategy()
        val names = parsing.getNamesFromRaw(code)
        Assertions.assertEquals(mutableListOf("TagMatchingInterface", "Int", "InnerClazz", "NoPublic"), names)
    }
}