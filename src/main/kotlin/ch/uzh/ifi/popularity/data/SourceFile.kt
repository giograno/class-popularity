package ch.uzh.ifi.popularity.data

/**
 * Data class to store the info about the Java files
 */
data class SourceFile(var name: String,
                      var path: String,
                      var url: String,
                      var html_url: String,
                      var repo_full_name: String,
                      var ownerType: String = "User",
                      var defaultBranch: String = "master")