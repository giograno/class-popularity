package ch.uzh.ifi.popularity.names

import ch.uzh.ifi.popularity.api.SourceQuery
import ch.uzh.ifi.popularity.data.SourceFile
import mu.KotlinLogging

/**
 * Returns the list of names parsing the file content and looking for any class, interface and enum declaration
 */
private val logger = KotlinLogging.logger {}
class ParsingFileStrategy: NamesStrategy() {

    override fun getClassNames(source: SourceFile): List<String> {
        val sourceQuery = SourceQuery()
        val rawCode = sourceQuery.getRawText(source)
        var names = getNamesFromRaw(rawCode).toMutableList()
        var fromFile = getNameFromFileName(source=source)
        logger.debug { "From Code = $names" }
        logger.debug { "From File = $fromFile"}
        names.add(fromFile)
        return names.map { camelCase(it) }.flatten().map { stemWord(it) }
    }

    /**
     * Returns a list of String with the names of classed in the raw text.
     * The list can be null if anything is found (should never be the case)
     */
    fun getNamesFromRaw(rawText: String): List<String> {
        val regex = Regex("(?<=\\n|\\A|\\s)(?:public\\s)?(class|interface|enum)\\s(\\b([A-Z])(\\S*?)\\b)")
        val matches = regex.findAll(rawText)
        val names = matches.map { it.groupValues[2] }.joinToString(",")
        return names.split(",")

    }
}