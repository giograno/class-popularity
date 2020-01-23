import ch.uzh.ifi.popularity.names.FilenameStrategy
import ch.uzh.ifi.popularity.names.NamesStrategy
import ch.uzh.ifi.popularity.names.ParsingFileStrategy
import ch.uzh.ifi.popularity.names.PopularNames
import mu.KotlinLogging
import java.io.File

private val logger = KotlinLogging.logger {}
fun main(args: Array<String>) {

    val strategy = args.getOrElse(0) { "filename" }
    val pageRepo = args.getOrElse(1) { "1" }.toInt()
    val pageSource = args.getOrElse(2) { "10" }.toInt()

    val concreteStrategy: NamesStrategy = if (strategy == "parsing") ParsingFileStrategy() else FilenameStrategy()
    logger.info { "Using \"$strategy\" strategy with $pageRepo and $pageSource pages for repository and sources"  }
    val context = PopularNames(strategy = concreteStrategy, pageRepo = pageRepo, pageSource = pageSource)
    val popularNames: Map<String, Int> = context.getPopularNames()
    File("./results.csv").printWriter().use { out ->
        popularNames.filter {
            it.value > 5
        }
        .forEach {
            out.println("${it.key}, ${it.value}")
        }
    }
}
