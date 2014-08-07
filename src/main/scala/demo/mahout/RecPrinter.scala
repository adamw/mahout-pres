package demo.mahout

import demo.MovieDB
import org.apache.mahout.cf.taste.recommender.RecommendedItem

object RecPrinter {
  def print(recommendations: Iterable[RecommendedItem]) {
    recommendations.foreach { r =>
      println("%60s [%3d] -> %.2f".format(MovieDB.movieTitle(r.getItemID), r.getItemID, r.getValue))
    }
  }
}
