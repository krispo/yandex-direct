package yandex.direct

/**
 * --- YANDEX Global Information ---
 */

import java.text._

object Global {

  /* 
   * URIs for YANDEX DIRECT api 
   */
  var _HOST = "https://api.direct.yandex.ru/live/v4/json/" //works (recommended)
  // var HOST = "https://soap.direct.yandex.ru/json-api/v4/" //works  
  var _HOST_SANDBOX = "https://api-sandbox.direct.yandex.ru/json-api/v4/" //SANDBOX - for testing 

  /*
   * Set Global CONFIGURATIONS
   */
  var _LOGIN: String = ""
  var _TOKEN: String = ""
  var _APPLICATION_ID: String = ""
  var _LOCALE: String = "en"
  var _FINANCE_TOKEN: String = ""
  var _OPERATION_NUM: String = ""

  /* 
   * Date format 
   */
  var date_fmt = new SimpleDateFormat("yyyy-MM-dd")

}