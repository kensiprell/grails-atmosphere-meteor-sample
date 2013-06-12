## Sample App for the Grails atmosphere-meteor Plugin

[grails-atmosphere-meteor](https://github.com/kensiprell/grails-atmosphere-meteor)

If you have a question, problem, suggestion, or want to report a bug, please submit an [issue](https://github.com/kensiprell/grails-atmosphere-meteor-sample/issues?state=open). I will reply as soon as I can.

If prompted, do not update the jquery plugin after giving the run-app command.

```
git clone git://github.com/kensiprell/grails-atmosphere-meteor-sample.git

cd grails-atmosphere-meteor-sample

grails run-app
```

You will have a simple application that performs the following tasks out of the box. Please note that this sample is not production ready. It merely incorporates some of the lessons I have learned and provides a point of departure for your own application.

* Chat (open two different browsers on your computer and start chatting)

* Sends a one-time, client-triggered notification to subscribers

* Automatically updates the web page at predefined intervals

You can review the files below to understand how it all works. 

* grails-app/atmosphere/org/grails/plugins/atmosphere_meteor_sample/DefaultMeteorHandler.groovy

* grails-app/atmosphere/org/grails/plugins/atmosphere_meteor_sample/DefaultMeteorServlet.groovy

* grails-app/conf/AtmosphereMeteorConfig.groovy

* grails-app/controllers/org/grails/plugins/atmosphere_meteor_sample/AtmosphereTestController.groovy

* grails-app/services/org/grails/plugins/atmosphere_meteor_sample/AtmosphereTestService.groovy

* grails-app/views/AtmosphereTest/index.gsp: This file contains all internal JavaScript.



