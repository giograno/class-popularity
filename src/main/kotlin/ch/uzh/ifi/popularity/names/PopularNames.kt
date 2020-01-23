package ch.uzh.ifi.popularity.names

import ch.uzh.ifi.popularity.api.ProjectsQuery
import ch.uzh.ifi.popularity.api.SourceQuery
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * Context that handles the extraction of the names.
 * Can use a different strategy, based on the implementation.
 * ParsingStrategy parses the file and look also for inner classes; FilenameStrategy only looks at the filename
 */
class PopularNames(private val strategy: NamesStrategy,
                   private var pageRepo: Int = 10,
                   private var pageSource: Int = 100) {

    private val sourceQuery = SourceQuery()

    fun getPopularNames(): Map<String, Int> {
        val popularityMap = mutableMapOf<String, Int>()
        val projectExtractor = ProjectsQuery()
        val repos = projectExtractor.getRepositories(page = pageRepo)
        logger.info { "Extracted ${repos.size} repositories" }

        for (repo in repos) {
            logger.info { "Processing \"${repo.name}\"" }
            val javaFiles = sourceQuery.getSourceFiles(repo=repo, page = pageSource)
            logger.info { "${javaFiles.size} Java files" }
            for (javaFile in javaFiles) {
                val classNames: List<String> = strategy.getClassNames(javaFile)
                logger.debug { "${classNames.size} extracted" }
                for (name in classNames)
                    popularityMap[name] = popularityMap.getOrDefault(name, 0)+1
            }
        }
        return popularityMap.toList().sortedByDescending { (_, value) -> value}.toMap()
    }
}

