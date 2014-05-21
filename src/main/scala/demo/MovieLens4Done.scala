package demo

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel
import java.io.File
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood
import org.apache.mahout.cf.taste.impl.recommender.{GenericItemBasedRecommender, GenericUserBasedRecommender}
import scala.collection.JavaConversions._

object MovieLens4Done extends App {
  val model = new FileDataModel(
    new File("/Users/adamw/projects/mahout-pres/ml-100k/ua.base"))

  val itemSimilarity = new PearsonCorrelationSimilarity(model)
  val recommender = new GenericItemBasedRecommender(model, itemSimilarity)

  val recommendations = recommender.recommend(925, 20)
  RecPrinter.print(recommendations)
}
