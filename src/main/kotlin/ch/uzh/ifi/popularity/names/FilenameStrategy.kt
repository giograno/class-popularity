package ch.uzh.ifi.popularity.names

import ch.uzh.ifi.popularity.data.SourceFile

/**
 * Returns a list of names only looking at the filename of the Java file
 */
class FilenameStrategy: NamesStrategy() {

    override fun getClassNames(source: SourceFile): List<String> {
        val names = mutableListOf<String>()
        val name = getNameFromFileName(source=source)
        val camelCase: List<String> = this.camelCase(name)
        for (temp in camelCase)
            names.add(stemWord(temp))
        return names
    }
}