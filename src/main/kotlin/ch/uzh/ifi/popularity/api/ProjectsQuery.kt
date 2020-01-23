package ch.uzh.ifi.popularity.api

import ch.uzh.ifi.popularity.data.Repo

class ProjectsQuery {

    /**
     * Handles the calls for repository search.
     * You can specify the number of pages and the results per page to get (100 by default)
     */
    fun getRepositories(page: Int = 1, perPage: Int = 100): List<Repo> {
        val repos = mutableListOf<Repo>()

        val caller = ApiCall()
        for (i in 1..page) {
            val call = "https://api.github.com/search/repositories?q=language:Java&sort=stars&order=desc" +
                    "&page=$i" +
                    "&per_page=$perPage"
            val pageRepo = caller.fetchRepo(call)
            if (pageRepo.isEmpty())
                break
            repos.addAll(pageRepo)
        }
        return repos
    }
}