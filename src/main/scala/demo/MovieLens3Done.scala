package demo

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel
import java.io.File
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender
import scala.collection.JavaConversions._
import org.apache.mahout.cf.taste.impl.model.{GenericUserPreferenceArray, PlusAnonymousConcurrentUserDataModel}

object MovieLens3Done extends App {
  val baseModel = new FileDataModel(
    new File("/Users/adamw/projects/mahout-pres/ml-100k/ua.base"))
  val model = new PlusAnonymousConcurrentUserDataModel(baseModel, 10)

  val userSimilarity = new PearsonCorrelationSimilarity(model)
  val neighborhood = new NearestNUserNeighborhood(10, userSimilarity, model)
  val recommender = new GenericUserBasedRecommender(model, neighborhood, userSimilarity)

  val userId = model.takeAvailableUser()
  try {
    val tempPrefs = new GenericUserPreferenceArray(11)

    def setPref(idx: Int, item: Long, value: Float) {
      tempPrefs.setItemID(idx, item)
      tempPrefs.setValue(idx, value)
    }

    setPref(0, 2, 5.0f)     // GoldenEye
    setPref(1, 28, 5.0f)    // Apollo 13
    setPref(2, 17, 5.0f)    // From Dusk Till Dawn
    setPref(3, 155, 1.0f)   // Dirty Dancing
    setPref(4, 197, 4.0f)   // Graduate, The
    setPref(5, 225, 1.0f)   // 101 Dalmatian
    setPref(6, 135, 5.0f)   // 2001: A Space Odyssey
    setPref(7, 144, 5.0f)   // Die Hard
    setPref(8, 755, 3.0f)   // Jumanji
    setPref(9, 879, 4.0f)   // Peacemaker
    setPref(10, 895, 1.0f)  // Scream 2

    model.setTempPrefs(tempPrefs, userId)
    val recommendations = recommender.recommend(userId, 20)
    RecPrinter.print(recommendations)
  } finally {
    model.releaseUser(userId)
  }
}
