
#55News 

This app reproduces a bug found by fifty-five in Google Tag Manager for mobile apps.


###Issue summary:
We are using Google Tag Manager to track our application. 
We have built a library called Cargo that embded 3rd party SDK in Google Tag Manager. 
Cargo is using both FunctionCallback and MacroCallBack features. 
One of our macroCallBack called `userGoogleId` retrieves the client Id (cid) from Google Analytics SDK. Then we used this macro to send the Google Analytics client id to other SDK. 
However, in somes cases, when we are using this macro and a Google Analytics tag the app freezes. 

###Steps to reproduce issue:
Install the app and run it. 
```bash
gradle clean build installDebug
```
You can see that the app freezes during the first install of the application.

###Expected output:
The app should load a web page showing an ecommerce web site. 
If you take a look in the logs, you should see the line 
```
fifty-five | This line will never be logged
````

###Actual results:
During the `UserGoogleIdMacroHandler` evaluation, the method 
```java
String client_id = tracker.get("&cid");
```
freezes the application 
We tried to catch an error without success. 

###Notes:
We had a lot of pain to reproduce this bug. During our inquiry we found that : 
- This bug does not occur when we use a Custom Image Tag instead of a Function Call 
- Tag's name, and so Tag order has an impact on the bug 
- This bug first occured with Google Play Services 7.3.0 
- This bug occurs when the same rule fires a google analytics tag and a function call tag using our macro userGoogleId

###Description of the container 
The container used contains the following objects 

|Tag type	| Tag name	| Rule 	| Parameter 	|
|---		|------		|------		|---		|
|Universal Analytics| A - Track splashScreen| event = applicationStart | screenName|
|Function Call| B - Send Client Id to Tune Servers| event = applicationStart | userGoogleId |

