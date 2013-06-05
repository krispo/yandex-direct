package yandex.direct

import Global._

import play.api.libs.json._
import scala.concurrent.{ Future, Await }
import scala.concurrent.duration._
import play.api.libs.concurrent.Execution.Implicits._
import java.util.Date

import play.api.libs.ws._

case class Direct(
  val login: String = _LOGIN,
  val token: String = _TOKEN,
  val application_id: String = _APPLICATION_ID,
  val locale: String = _LOCALE,
  val url: String = _HOST) {

  /* 
   * Generate post request to Yandex Direct API as JSON String
   * and return response as JSON String 
   */

  def post(method: String, param: JsValue = JsNull): JsValue = {
    // Generate input data 
    val jsData =
      Json.toJson(
        Json.obj(
          "login" -> login,
          "token" -> token,
          "application_id" -> application_id,
          "locale" -> locale,
          "method" -> method,
          "param" -> param))

    val futureResponse = WS.url(url).post[JsValue](jsData)

    Await.result(futureResponse, Duration.Inf).json
  }

  def postFinance(finance_token: String, operation_num: Int, method: String, param: JsValue = JsNull): JsValue = {
    // Generate input data for Finance operations 
    val jsData =
      Json.toJson(
        Json.obj(
          "login" -> login,
          "token" -> token,
          "application_id" -> application_id,
          "locale" -> locale,
          "finance_token" -> finance_token,
          "operation_num" -> operation_num,
          "method" -> method,
          "param" -> param))

    val futureResponse = WS.url(url).post[JsValue](jsData)

    Await.result(futureResponse, Duration.Inf).json
  }

  /**
   * Campaigns
   */

  /*
   * Archives the campaign.
   */
  def archiveCampaign(param: JsValue) = post("ArchiveCampaign", param)
  def archiveCampaign(campaignID: Int): JsValue = archiveCampaign(Json.obj("CampaignID" -> campaignID))

  /* 
   * Creates a campaign with the specified parameters, or changes the parameters of an existing campaign.
   * For editing campaigns, no more than 100 calls per day per campaign. 
   * For creating campaigns, no more than 100 calls per day. 
   */
  def createOrUpdateCampaign(param: JsValue) = post("CreateOrUpdateCampaign", param)

  /*
   * Deletes the campaign.
   */
  def deleteCampaign(param: JsValue) = post("DeleteCampaign", param)
  def deleteCampaign(campaignID: Int): JsValue = deleteCampaign(Json.obj("CampaignID" -> campaignID))

  /*
   * Returns the campaign parameters.
   */
  def getCampaignsParams(param: JsValue) = post("GetCampaignsParams", param)
  def getCampaignsParams(campaignIDS: List[Int]): JsValue = getCampaignsParams(Json.obj("CampaignIDS" -> campaignIDS))

  /*
   * Returns a list of campaigns with brief information about them.
   */
  def getCampaignsList(param: JsValue = JsNull) = post("GetCampaignsList", param)
  def getCampaignsList(logins: List[String]): JsValue = getCampaignsList(Json.toJson(logins))

  /*
   * Returns a list of campaigns that meet the filter conditions with brief information about these campaigns.
   */
  def getCampaignsListFilter(param: JsValue) = post("GetCampaignsListFilter", param)

  /*
   * Allows displays of campaign ads.
   */
  def resumeCampaign(param: JsValue) = post("ResumeCampaign", param)
  def resumeCampaign(campaignID: Int): JsValue = resumeCampaign(Json.obj("CampaignID" -> campaignID))

  /*
   * Stops displaying the ads in the campaign.
   */
  def stopCampaign(param: JsValue) = post("StopCampaign", param)
  def stopCampaign(campaignID: Int): JsValue = stopCampaign(Json.obj("CampaignID" -> campaignID))

  /*
   * Removes the campaign from the archive.
   */
  def unArchiveCampaign(param: JsValue) = post("UnArchiveCampaign", param)
  def unArchiveCampaign(campaignID: Int): JsValue = unArchiveCampaign(Json.obj("CampaignID" -> campaignID))

  /**
   * Ads
   */

  /*
   * Archives the ad.
   */
  def archiveBanners(param: JsValue) = post("ArchiveBanners", param)
  def archiveBanners(bannerIDS: List[Int]): JsValue = archiveBanners(Json.obj("BannerIDS" -> bannerIDS))

  /* 
   * Creates an ad and phrases, or changes an existing ad and phrases.
   * The method can be called for a single campaign no more than 1000 times per day. 
   * Points are used when this method is invoked.
   * Each campaign may have a maximum of 1000 ads. The number of phrases per ad is not restricted, 
   * but the total length of phrases is limited to 4096 bytes per ad. 
   */
  def createOrUpdateBanners(param: JsValue) = post("CreateOrUpdateBanners", param)

  /*
   * Deletes ads.
   */
  def deleteBanners(param: JsValue) = post("DeleteBanners", param)
  def deleteBanners(bannerIDS: List[Int]): JsValue = deleteBanners(Json.obj("BannerIDS" -> bannerIDS))

  /*
   * Returns parameters of ads and phrases.
   */
  def getBanners(param: JsValue) = post("GetBanners", param)

  /*
   * Returns information about phrases.
   */
  def getBannerPhrases(param: JsValue) = post("GetBannerPhrases", param)
  def getBannerPhrases(bannerIDS: List[Int]): JsValue = getBannerPhrases(Json.toJson(bannerIDS))

  /*
   * Returns information about phrases and lets you limit what is included in returned data.
   */
  def getBannerPhrasesFilter(param: JsValue) = post("GetBannerPhrasesFilter", param)

  /*
   * Submits an ad with "Draft" status for moderation.
   */
  def moderateBanners(param: JsValue) = post("ModerateBanners", param)
  def moderateBanners(campaignID: Int, bannerIDS: List[Int]): JsValue =
    moderateBanners(Json.obj("CampaignID" -> campaignID, "BannerIDS" -> bannerIDS))

  /*
   * Allows ad displays.
   */
  def resumeBanners(param: JsValue) = post("ResumeBanners", param)
  def resumeBanners(bannerIDS: List[Int]): JsValue = resumeBanners(Json.obj("BannerIDS" -> bannerIDS))

  /*
   * Stops displaying ads.
   */
  def stopBanners(param: JsValue) = post("StopBanners", param)
  def stopBanners(bannerIDS: List[Int]): JsValue = stopBanners(Json.obj("BannerIDS" -> bannerIDS))

  /*
   * Removes an ad from the archive.
   */
  def unArchiveBanners(param: JsValue) = post("UnArchiveBanners", param)
  def unArchiveBanners(bannerIDS: List[Int]): JsValue = unArchiveBanners(Json.obj("BannerIDS" -> bannerIDS))

  /**
   * Bids
   */

  /*
   * Sets bids for phrases, or calculates them based on an algorithm.
   * No more than 100 method calls per campaign per day.
   */
  def setAutoPrice(param: JsValue) = post("SetAutoPrice", param)

  /* 
   * Sets bids for the specified phrases and changes Autobudget and Autobroker parameters.
   * No more than 3000 method calls per campaign per day. 
   * You can set prices for a maximum of 1000 phrases in a single request. 
   */
  def updatePrices(param: JsValue) = post("UpdatePrices", param)

  /**
   * Reports
   */

  /* 
   * Statistics 
   */

  /*
   * Notifies you of the campaign balance and the total amount transferred to the campaign over the course of its existence
   */
  def getBalance(param: JsValue) = post("GetBalance", param)
  def getBalance(campaignIDS: List[Int]): JsValue = getBalance(Json.toJson(campaignIDS))

  /*
   * Returns statistics for the specified campaign for a period no longer than 7 days 
   */

  def getBannersStat(param: JsValue) = post("GetBannersStat", param)
  def getBannersStat(campaignID: Int, startDate: Date, endDate: Date, groupByColumns: List[String] = List("clPhrase")): JsValue =
    getBannersStat(Json.obj(
      "CampaignID" -> campaignID,
      "StartDate" -> date_fmt.format(startDate),
      "EndDate" -> date_fmt.format(endDate),
      "GroupByColumns" -> groupByColumns))

  /* 
   * Returns statistics for the specified campaigns for each day of the specified period.
   * Up to 100 method calls per day for a single campaign.
   * The number of requested campaigns multiplied by the number of days 
   * in the selected period must not exceed 1000 
   */
  def getSummaryStat(param: JsValue) = post("GetSummaryStat", param)
  def getSummaryStat(campaignIDS: List[Int], startDate: Date, endDate: Date): JsValue =
    getSummaryStat(Json.obj(
      "CampaignIDS" -> campaignIDS,
      "StartDate" -> date_fmt.format(startDate),
      "EndDate" -> date_fmt.format(endDate)))

  /*
   * Generates a campaign statistics report on the server.
   * For a single campaign, a maximum of 300 calls of the method are allowed per day.
   */
  def createNewReport(param: JsValue) = post("CreateNewReport", param)
  def createNewReport(campaignID: Int, startDate: Date, endDate: Date, groupByColumns: List[String] = List("clBanner", "clPhrase")): JsValue =
    createNewReport(Json.obj(
      "CampaignID" -> campaignID,
      "StartDate" -> date_fmt.format(startDate),
      "EndDate" -> date_fmt.format(endDate),
      "GroupByColumns" -> groupByColumns))

  /* 
   * Download XML report
   */
  def getXMLreport(reportUrl: String): xml.Elem = { //won't require login and token 
    val futureResponse = WS.url(reportUrl).get()

    Await.result(futureResponse, Duration.Inf).xml
  }

  /*
   * Deletes a campaign statistics report from the server.
   */
  def deleteReport(param: JsValue) = post("DeleteReport", param)
  def deleteReport(reportID: Int): JsValue = deleteReport(JsNumber(reportID))

  /*
   * Returns a list of campaign statistics reports that have been generated or are being generated.
   */
  def getReportList = post("GetReportList")

  /* 
   * Keywords 
   */

  /*
   * Generates a search query statistics report on the server.
   * A single user can get search query statistics for 1000 phrases per day.
   */
  def createNewWordstatReport(param: JsValue) = post("CreateNewWordstatReport", param)
  def createNewWordstatReport(phrases: List[String], geoID: List[Int] = Nil): JsValue =
    createNewWordstatReport(Json.obj("Phrases" -> phrases, "GeoID" -> geoID))

  /*
   * Deletes a search query statistics report.
   */
  def deleteWordstatReport(param: JsValue) = post("DeleteWordstatReport", param)
  def deleteWordstatReport(reportID: Int): JsValue = deleteWordstatReport(JsNumber(reportID))

  /*
   * Returns a search query statistics report.
   */
  def getWordstatReport(param: JsValue) = post("GetWordstatReport", param)
  def getWordstatReport(reportID: Int): JsValue = getWordstatReport(JsNumber(reportID))

  /*
   * Returns a list of query statistics reports that have been generated or are being generated.
   */
  def getWordstatReportList = post("GetWordstatReportList")

  /*
   * Returns suggestions for keywords.
   */
  def getKeywordsSuggestion(param: JsValue) = post("GetKeywordsSuggestion", param)
  def getKeywordsSuggestion(keywords: List[String]): JsValue =
    getKeywordsSuggestion(Json.obj("Keywords" -> keywords))

  /* 
   * Budget forecasting 
   */

  /*
   * Generates a forecast on the server for impressions, clicks and expenses.
   */
  def createNewForecast(param: JsValue) = post("CreateNewForecast", param)

  /*
   * Deletes a report about the projected number of impressions, clicks and campaign spending.
   */
  def deleteForecastReport(param: JsValue) = post("DeleteForecastReport", param)
  def deleteForecastReport(reportID: Int): JsValue = deleteForecastReport(JsNumber(reportID))

  /*
   * Returns the generated forecast for impressions, clicks and expenses by its ID.
   */
  def getForecast(param: JsValue) = post("GetForecast", param)
  def getForecast(ID: Int): JsValue = getForecast(JsNumber(ID))

  /*
   * Returns a list of reports that have been generated or are being generated about 
   * the projected number of impressions, clicks and campaign spending.
   */
  def getForecastList = post("GetForecastList")

  /**
   * Ad tags
   */

  /*
   * Returns IDs of tags that are assigned to ads.
   */
  def getBannersTags(param: JsValue) = post("GetBannersTags", param)
  def getBannersTags(bannerIDS: List[Int]): JsValue = getBannersTags(Json.obj("BannerIDS" -> bannerIDS))

  /*
   * Returns a list of tags for the specified campaign or multiple campaigns.
   */
  def getCampaignsTags(param: JsValue) = post("GetCampaignsTags", param)
  def getCampaignsTags(campaignIDS: List[Int]): JsValue = getCampaignsTags(Json.obj("CampaignIDS" -> campaignIDS))

  /*
   * Associates tags with ads.
   */
  def updateBannersTags(param: JsValue) = post("UpdateBannersTags", param)
  def updateBannersTags(bannerID: Int, tagIDS: List[Int]): JsValue =
    updateBannersTags(Json.obj("BannerID" -> bannerID, "TagIDS" -> tagIDS))

  /*
   * Generates a list of tags for the specified campaign, or lists for multiple campaigns.
   */
  def updateCampaignsTags(param: JsValue) = post("UpdateCampaignsTags", param)

  /**
   * Retargeting
   */

  /*
   * Returns the IDs of available Yandex.Metrica goals.
   */
  def getRetargetingGoals(param: JsValue) = post("GetRetargetingGoals", param)
  def getRetargetingGoals(logins: List[String]): JsValue = getRetargetingGoals(Json.obj("Logins" -> logins))

  /* 
   * Creates, modifies, deletes, or returns a retargeting condition.
   * No more than 1000 retargeting conditions can be created per login.
   * A retargeting condition can contain no more than 50 groups.
   * A group can contain up to 250 goals.
   * A condition must contain at least one group with the "all" or "or" type (see the Type parameter).
   * */
  def retargetingCondition(param: JsValue) = post("RetargetingCondition", param)

  /*
   * Creates, modifies, deletes, or returns retargeting parameters for an ad.
   * No more than 1000 retargeting objects can be created per ad.
   */
  def retargeting(param: JsValue) = post("Retargeting", param)

  /**
   * Financial transactions
   */

  /*
   * Generates an invoice for one or more campaigns in HTML format.
   * A single user can call the method 1000 times per day.
   * A single campaign can have no more than 30 transactions per day
   */
  def createInvoice(finance_token: String, operation_num: Int, param: JsValue) =
    postFinance(finance_token, operation_num, "CreateInvoice", param)

  /* 
   * Returns information about credit provided to an advertising agency for campaign payments.
   * The method can be called on behalf of a single user up to 1000 times per day.
   */
  def getCreditLimits(finance_token: String, operation_num: Int) =
    postFinance(finance_token, operation_num, "GetCreditLimits")

  /*
   * Paying for campaigns using an advertising agency's credit limit, or the advertiser's overdraft.
   * A single user can call the method 1000 times per day.
   * A single campaign can have no more than 30 transactions per day
   */
  def payCampaigns(finance_token: String, operation_num: Int, param: JsValue) =
    postFinance(finance_token, operation_num, "PayCampaigns", param)

  /*
   * Transfers funds between campaigns.
   * A single user can call the method 1000 times per day.
   * A single campaign can have funds added to it no more than 30 times per day. 
   * This restriction does not apply to deducting funds.
   */
  def transferMoney(finance_token: String, operation_num: Int, param: JsValue) =
    postFinance(finance_token, operation_num, "TransferMoney", param)

  /**
   * Clients
   */

  /* 
   * Registers a client of an advertising agency in Yandex and creates an account in Yandex.Direct.
   * This method is available only to advertising agencies, not to direct advertisers. 
   * For agencies, access is issued on request.
   * No more than 100 clients can be registered per day.
   */
  def createNewSubclient(param: JsValue) = post("CreateNewSubclient", param)

  /*
   * Returns Yandex.Direct accounts.
   */
  def getClientInfo(param: JsValue) = post("GetClientInfo", param)
  def getClientInfo(logins: List[String]): JsValue = getClientInfo(Json.toJson(logins))

  /*
   * Returns the accounts of all clients of the advertising agency.
   * This method is available only to advertising agencies, not to direct advertisers.
   */
  def getClientsList(param: JsValue) = post("GetClientsList", param)

  /*
   * Returns the number of points the user has.
   */
  def getClientsUnits(param: JsValue) = post("GetClientsUnits", param)
  def getClientsUnits(logins: List[String]): JsValue = getClientsUnits(Json.toJson(logins))

  /* 
   * Returns a list of subclients of the specified user.
   * For an advertising agency, returns a list of clients and delegates. 
   * For a direct advertiser, returns a list of delegates.
   */
  def getSubClients(param: JsValue) = post("GetSubClients", param)

  /*
   * Changes Yandex.Direct accounts.
   */
  def updateClientInfo(param: JsValue) = post("UpdateClientInfo", param)

  /**
   * Other methods
   */

  /*
   * Returns a list of API versions that are currently supported.
   */
  def getAvailableVersions = post("GetAvailableVersions")

  /*
   * Checks for changes in campaigns and ads, as well as in the region and time zone directories, 
   * and in the Yandex.Catalog.
   */
  def getChanges(param: JsValue) = post("GetChanges", param)

  /*
   * Returns entries from the events log.
   */
  def getEventsLog(param: JsValue) = post("GetEventsLog", param)

  /*
   * Returns a list of regions registered in Yandex.Direct.
   */
  def getRegions = post("GetRegions")

  /*
   * Returns a list of Yandex.Catalog categories.
   */
  def getRubrics = post("GetRubrics")

  /*
   * Returns information about the Yandex.Metrica goals that are available for the campaign.
   */
  def getStatGoals(param: JsValue) = post("GetStatGoals", param)
  def getStatGoals(campaignIDS: List[Int]): JsValue = getStatGoals(Json.obj("CampaignIDS" -> campaignIDS))

  /*
   * Returns a list of time zones.
   */
  def getTimeZones = post("GetTimeZones")

  /*
   * Returns the number of the latest API version.
   */
  def getVersion = post("GetVersion")

  /*
   * Checks API availability and whether the user was successfully authorized.
   */
  def pingAPI = post("PingAPI")
}