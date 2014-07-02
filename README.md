# Yandex.Direct

Scala client wrapper for [Yandex.Direct](http://api.yandex.com/direct/doc/concepts/About.xml) API, based on the stable [Live 4 version](http://api.yandex.com/direct/doc/concepts/Versions_live4.xml). 
This library provides easy-to-use wrap to work with campaigns, ads, bids, reports, ad tags, retargeting, 
financial transactions, clients and other methods.

The library supports only [JSON format](http://api.yandex.com/direct/doc/concepts/JSON.xml) and uses user authentication only via [tokens](http://api.yandex.com/direct/doc/concepts/auth-token.xml) issued by the Yandex [OAuth server](https://oauth.yandex.com/). 

Sing in via login (Your Yandex account) and token (acccess key for Your application).

It is assumed token is known by one of [those](http://api.yandex.com/oauth/doc/dg/reference/obtain-access-token.xml) ways.

## Dependency

For SBT, add these lines to Your SBT project definition:

```scala
libraryDependencies ++= Seq(
				// other dependencies here
				"com.github.krispo" %% "yandex-direct" % "0.1-SNAPSHOT"
				)				 
```
and
```scala
resolvers ++= Seq(
		// other resolvers here
		"Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
		)
```

## General usage concept

There is a general request structure for [all methods](http://api.yandex.com/direct/doc/reference/_AllMethods.xml) in Yandex Direct API. 
However, each method have a specific input parameters we need to specify every time, when this method is used.
So, the input request parameters we generally specify in JSON format, but for some methods we can use an explicit format.
Yandex Direct response is also in JSON format.

In this library we use an awesome [Play! JSON library](http://www.playframework.com/documentation/2.1.1/ScalaJson).
   
## Example

At first, import direct api library

```scala
import yandex.direct._
```

Then we can initialize new Direct class with a specified yandex login and token. We also need to specify `application_id` and some others parameters for normal working.

```scala
val direct = Direct(
	login = "krisp0", 
	token = "15109642d26f452cae7a339776ecc30f",
	application_id = "e3a82ab5b4054deda4bb917d2a537224")
```  

Or we can set the Global specification via `Global` object:

```scala
Global._LOGIN = "krisp0"
Global._TOKEN = "15109642d26f452cae7a339776ecc30f"
Global._APPLICATION_ID = "e3a82ab5b4054deda4bb917d2a537224"
Global._LOCALE = "en" // by default
```

and then simple initialize the Direct class:

```scala
val direct = Direct()
```  

Then, If we want to use the Direct with another user but with the same `application_id`, we can just initialize:
```scala
val newdirect = Direct(login = "some_login", token = "some_token")
```  

Now we can do simple requests for getting/setting data from/in Yandex.Direct. 
Just ping the service:

```scala
val ping = direct.pingAPI
```

for `ping` we get a JSON string:
 
```scala
{"data":1}
```

Now, we will get campaigns list for user "krisp0":
 
```scala
val json_cl = direct.getCampaignsList()
```

The `json_cl` as JSON is:
 
```scala
{
    "data": [
        {
            "Rest": 0,
            "Status": "The campaign is moved to the archive",
            "IsActive": "No",
            "StatusArchive": "Yes",
            "Login": "krisp0",
            "ContextStrategyName": "Default",
            "CampaignID": 5728507,
            "StatusShow": "No",
            "StartDate": "2012-12-12",
            "Sum": 0,
            "StatusModerate": "No",
            "Clicks": 0,
            "Shows": 0,
            "ManagerName": null,
            "StatusActivating": "Pending",
            "StrategyName": "HighestPosition",
            "SumAvailableForTransfer": 0,
            "AgencyName": null,
            "Name": "Campaign_0"
        }
    ]
}
```

If one need to find a list of CampaignIDs from Direct JSON string, we can simply write:

```scala
val cl = json_cl \ "data" \\ "CampaignID" map (_.as[Int])
```

and for `cl` we will get 

```scala
List(5728507)
```

For example, to get the campaign balance we write:

```scala
val balance = direct.getBalance(List(5728507))
```

as the result `balance` is equal to:

```scala
{
    "data": [
        {
            "Sum": 0,
            "Rest": 0,
            "SumAvailableForTransfer": 0,
            "CampaignID": 5728507
        }
    ]
}
```

More examples can be find in tests.

## License

This library is under the MIT license. Check the [LICENSE](https://github.com/krispo/yandex-direct/blob/master/LICENSE) file.