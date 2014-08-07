package demo.mahout

import java.io.File

import org.apache.mahout.cf.taste.eval.RecommenderBuilder
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity
import org.apache.mahout.cf.taste.model.DataModel

object MovieLens2Done extends App {
  val builder = new RecommenderBuilder() {
    def buildRecommender(dataModel: DataModel) = {
      val userSimilarity = new PearsonCorrelationSimilarity(dataModel)
      val neighborhood = new NearestNUserNeighborhood(25, userSimilarity, dataModel)
      new GenericUserBasedRecommender(dataModel, neighborhood, userSimilarity)
    }
  }

  val model = new FileDataModel(
    new File("/Users/adamw/projects/mahout-pres/ml-100k/ua.base"))

  val evaluator1 = new AverageAbsoluteDifferenceRecommenderEvaluator()
  val score1 = evaluator1.evaluate(builder, null, model, 0.9, 1.0)

  println(score1)
}
