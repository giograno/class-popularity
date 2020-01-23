# Class Names Popularity

This small programs relies on [GitHub REST API v3](https://developer.github.com/v3/) to extract the most occurrent words
in Java class names.

Given each class names, it applies first camel-case split and then stemming of the words.

The extraction of the names can be done in two ways:
* simply looking at the filename;
* parsing the content of the Java files (to get eventual inner classes, for instance)

The jar can take the following parameters:

```
java -jar popularity.jar <parsing|filename> <#pageRepo> <#pageSource>
```

The default parameters are "filename", 1 and 10.
The results per page are 100 by default.
Thus, the default execution processes 1000 Java files for 100 repositories.

The GitHub API search API returns a maximum of 1000 result with pagination.
A trick to get more results is to use a date window for file creation in the search and move such a window to get more 
result.

## Auth Token
A [personal access token](https://github.com/settings/tokens) is needed to use the program.
The programs reads the token from an environment variable named `GITHUB_TOKEN`.
Use the command `export GITHUB_TOKEN="<token>"` to set such a variable.

## Result
The file `results.csv` contains the result of a search for 1000 repositories (sorted in order of popularity)
and 1000 Java files for each repository.

The top 5 occurring terms are:

```
test, 41524
util, 7476
activ, 6312
servic, 6187
info, 5946
```

