package ch.uzh.ifi.popularity.api

import ch.uzh.ifi.popularity.data.Repo
import ch.uzh.ifi.popularity.data.SourceFile

class SourceQuery {

    /**
     * Handles the calls that search for Java files in a given repo.
     * You can specify the number of pages and the results per page to get (100 by default)
     */
    fun getSourceFiles(repo: Repo, page: Int = 10, perPage: Int = 100): List<SourceFile> {
        val parameter: String = repo.full_name
        val files = mutableListOf<SourceFile>()

        val caller = ApiCall()
        for (i in 1..page) {
            val call = "https://api.github.com/search/code?q=repo:$parameter+language:java" +
                    "&page=$i" +
                    "&per_page=$perPage"
            val pageFiles = caller.fetchJavaFiles(call, repo)
            if (pageFiles.isEmpty())
                break
            files.addAll(pageFiles)
        }

        return files
    }

    /**
     * Calls the API to get the raw text of a SourceFile
     */
    fun getRawText(sourceFile: SourceFile): String {
        var call = "https://raw.githubusercontent.com/" +
                "${sourceFile.repo_full_name}/" +
                "${sourceFile.defaultBranch}/" +
                "${sourceFile.path}"
        val caller = ApiCall()
        val rawText = caller.fetchRawText(call)
        return rawText
    }
}