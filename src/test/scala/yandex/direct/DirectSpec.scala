package yandex.direct

import org.specs2.mutable._
import org.specs2.specification._

import play.api.libs.json._
import org.joda.time.DateTime

class DirectSpec extends Specification with AllExpectations {

  //initialize Direct parameters
  Global._APPLICATION_ID = "e3a82ab5b4054deda4bb917d2a537224"

  val direct = Direct(login = "krisp0", token = "15109642d26f452cae7a339776ecc30f")

  /**
   * Methods
   */

  "pingAPI" should {
    sequential

    "send TRUE request" in {
      val res = direct.pingAPI
      res \ "data" must_== (JsNumber(1))
    }
  }

  "getCampaignsList" should {
    sequential

    "send TRUE request" in {
      val res = direct.getCampaignsList()
      val cl = res \ "data" \\ "CampaignID" map (_.as[Int])
      cl must_== (List(5728507))
    }
  }

  "getBalance" should {
    sequential

    "send TRUE request" in {
      val res = direct.getBalance(List(5728507))
      res \ "data" \\ "CampaignID" map (_.as[Int]) must_== (List(5728507))
      (res \ "data" \\ "Rest").length must_== (1)
    }
  }

  "getSummaryStat" should {
    sequential

    "send TRUE request" in {
      val dt = new DateTime
      val res = direct.getSummaryStat(campaignIDS = List(5728507), startDate = dt.minusYears(1).toDate(), endDate = dt.toDate())

      res \ "data" must_== (JsArray())
    }
  }

  "getBanners" should {
    sequential

    "send TRUE request" in {
      val dt = new DateTime

      val param = Json.obj(
        "CampaignIDS" -> List(5728507),
        "GetPhrases" -> "WithPrices")

      val res = direct.getBanners(param)

      val l = (res \ "data").as[List[JsValue]]
      l.length must_== (1)
      l.map(a => (a \ "CampaignID").as[Int]) must_== (List(5728507))

      val phl = l.head \ "Phrases" \\ "PhraseID" map (_.as[Int])
      phl.length must_== (51)
    }
  }
}