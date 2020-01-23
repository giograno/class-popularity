package ch.uzh.ifi.popularity.names

import ch.uzh.ifi.popularity.data.SourceFile
import opennlp.tools.stemmer.PorterStemmer
import org.apache.commons.lang3.StringUtils

abstract class NamesStrategy {

    private val stemmer: PorterStemmer = PorterStemmer()

    /**
     * Given a SourceFile, returns a list of names.
     * Applies camel-case split and stemming.
     */
    abstract fun getClassNames(source: SourceFile): List<String>

    /**
     * Camel-case split. It discard the words with length leq 3
     */
    fun camelCase(name: String) :List<String> {
        val splitByCharacterTypeCamelCase = StringUtils.splitByCharacterTypeCamelCase(name)
        val words = mutableListOf<String>()
        for (word in splitByCharacterTypeCamelCase)
            if (word.length > 2)
                words.add(word.toLowerCase())
        return words
    }

    /**
     * Just return a class name from the name of the source file
     */
    fun getNameFromFileName(source: SourceFile): String {
        var name: String = source.name
        if (name.endsWith(".java", ignoreCase = true))
            name = name.replaceFirst(".java","")
        return name
    }

    /**
     * Returns the stemming of a word
     */
    fun stemWord(name: String) :String {
        return stemmer.stem(name)
    }
}