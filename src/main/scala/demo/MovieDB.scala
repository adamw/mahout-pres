package demo

import scala.io.Source

object MovieDB {
  private val movieIdToTitle = (for {
    line <- Source.fromFile("/Users/adamw/projects/mahout-pres/ml-100k/u.item", "ISO-8859-1").getLines()
    if line.trim() != ""
  } yield {
    val split = line.split("\\|")
    split(0).toLong -> split(1)
  }).toMap

  def movieTitle(id: Long) = movieIdToTitle.getOrElse(id, "?")
}
