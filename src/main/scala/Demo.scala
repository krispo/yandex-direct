import yandex.direct._

import play.api.libs.json._

object Demo {

  def main(args: Array[String]) {

    Global._APPLICATION_ID = "e3a82ab5b4054deda4bb917d2a537224"

    val direct = Direct(login = "krisp0", token = "15109642d26f452cae7a339776ecc30f")

    val ping = direct.pingAPI
    println(ping)

    val json_cl = direct.getCampaignsList()
    println(json_cl)
    val cl = json_cl \ "data" \\ "CampaignID" map (_.as[Int])
    println(cl)

    val balance = direct.getBalance(cl.toList)
    println(balance)

  }
}