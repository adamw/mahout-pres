package demo

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel
import java.io.File
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender
import scala.collection.JavaConversions._
import org.apache.mahout.cf.taste.recommender.IDRescorer

object MovieLens5Done extends App {
  val model = new FileDataModel(
    new File("/Users/adamw/projects/mahout-pres/ml-100k/ua.base"))

  val userSimilarity = new PearsonCorrelationSimilarity(model)
  val neighborhood = new NearestNUserNeighborhood(25, userSimilarity, model)
  val recommender = new GenericUserBasedRecommender(model, neighborhood,
    userSimilarity)

  val idRescorer = new IDRescorer {
    def rescore(id: Long, originalScore: Double) = {
      originalScore*2
    }

    def isFiltered(id: Long) = id % 2 == 0
  }

  val recommendations = recommender.recommend(925, 20, idRescorer)
  RecPrinter.print(recommendations)
}
