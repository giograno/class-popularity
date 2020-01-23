package ch.uzh.ifi.popularity.data

/**
 * Data class to store repo info
 */
data class Repo(val name: String,
                val full_name: String,
                val html_url: String,
                val url: String,
                val type: String = "User",
                val defaultBranch: String = "Master")


