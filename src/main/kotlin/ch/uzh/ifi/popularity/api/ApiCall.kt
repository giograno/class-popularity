package ch.uzh.ifi.popularity.api

import ch.uzh.ifi.popularity.data.Repo
import ch.uzh.ifi.popularity.data.SourceFile
import ch.uzh.ifi.popularity.exception.NoTokenException
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result


class ApiCall {

    /**
     * Calls the GitHub API on the given URL and returns a list of SourceFile
     * Sleeps for 1500 milliseconds to avoid to hit search API limits
     */
    fun fetchJavaFiles(url: String, repo: Repo): List<SourceFile> {
        Thread.sleep(1500L)
        var entireJson: String? = fetch(url) ?: return emptyList()
        val mapper = ObjectMapper()
        val jsonNode: JsonNode = mapper.readTree(entireJson)

        return jsonNode.get("items").map { node ->

            val name = node.get("name").asText("")
            var path = node.get("path").asText("")
            val url = node.get("url").asText("")
            val htmlUrl = node.get("html_url").asText("")

            SourceFile(name = name,
                path = path,
                url = url,
                html_url = htmlUrl,
                repo_full_name = repo.full_name,
                ownerType = repo.type,
                defaultBranch = repo.defaultBranch)
        }
    }

    /**
     * Calls the GitHub API on the given url and returns a list Repo
     */
    fun fetchRepo(url: String): List<Repo> {
        Thread.sleep(1500L)
        var entireJson: String? = fetch(url) ?: return emptyList()
        val mapper = ObjectMapper()
        val jsonNode: JsonNode = mapper.readTree(entireJson)

        return jsonNode.get("items").map { node ->

            val name = node.get("name").asText("")
            val fullName = node.get("full_name").asText("")
            val htmlUrl = node.get("html_url").asText("")
            val url = node.get("url").asText("")
            val ownerType = node.get("owner").get("type").asText("User")
            val defaultBranch = node.get("default_branch").asText("master")

            Repo(name = name,
                full_name = fullName,
                html_url = htmlUrl,
                url = url,
                type = ownerType,
                defaultBranch = defaultBranch)
        }
    }

    /**
     * Calls the raw GitHub API to get the plain text of a source file
     */
    fun fetchRawText(url: String): String {
        return fetch(url) ?: return ""
    }

    /**
     * Performs the actual call
     */
    private fun fetch(url: String): String? {
        val token: String = System.getenv("GITHUB_TOKEN")
            ?: throw NoTokenException("GitHub API token missing!\n" +
                    "Define a GITHUB_TOKEN environment variable")

        val (_, response, result) = url.httpGet()
            .useHttpCache(true)
            .header("Accept", "application/vnd.github.v3+json")
            .authentication().bearer(token)
            .responseString()
        var entireJson = ""

        val status = response.header("Status").elementAtOrNull(0)
        val cache = response.header("Cache-Control")
        val link = response.header("Link").elementAtOrNull(0)
        val remaining = response.header("X-RateLimit-Remaining").elementAtOrNull(0)

        when (result) {
            is Result.Failure -> {
                return null
            }
            is Result.Success -> {
                entireJson = result.get()
            }
        }
        return entireJson
    }
}