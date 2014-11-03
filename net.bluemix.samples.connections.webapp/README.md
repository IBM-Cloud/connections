Simple IBM Bluemix Sample accessing IBM Connections Social Cloud
================================================================================

This simple sample shows how to authenticate a user against IBM Connections
Social Cloud via basic authentication and how to invoke a REST API from 
Java to find out user profile information about the current user.

You can use an Connections Social Cloud test organization and test users
to run this sample without additional setup. Alternatively you can request
your own [test environment](https://developer.ibm.com/social/cloud/#envs). Note that you might have to change the endpoint URL in managed-beans.xml to point to your organization.

For more information check out [IBM Connections Developers](https://developer.ibm.com/social).

Author: Niklas Heidloff ([@nheidloff](http://twitter.com/nheidloff))


Setup of Eclipse IDE, Liberty and sample
----------------------------------------------------------------------------------

**Install Eclipse and Liberty**
 
[Download and install Eclipse for Java EE Developers and Liberty profile](https://developer.ibm.com/wasdev/downloads/liberty-profile-using-eclipse)

[Find out more about Liberty](https://www.ng.bluemix.net/docs/#starters/liberty/index.html) 

**Get the sample from GitHub**

You can either download the zip file or clone the project from Eclipse. Then import as Maven project. If you haven't installed the [Eclipse Maven plugin](http://www.eclipse.org/m2e/download/) do so.

[https://github.com/IBM-Bluemix/connections.git](https://github.com/IBM-Bluemix/connections.git)
[https://github.com/IBM-Bluemix/connections/archive/master.zip](https://github.com/IBM-Bluemix/connections/archive/master.zip)
[https://github.com/IBM-Bluemix/connections](https://github.com/IBM-Bluemix/connections)

**Configure the Liberty server (server.xml)**

For http:

    <server description="new server">
         <featureManager>
            <feature>jsp-2.2</feature>
            <feature>localConnector-1.0</feature>
            <feature>appSecurity-2.0</feature>
            <feature>jaxrs-1.1</feature>
        </featureManager>
        <httpEndpoint httpPort="9080" httpsPort="9443" id="defaultHttpEndpoint"/>
        <applicationMonitor updateTrigger="mbean"/>
        <webApplication id="net.bluemix.samples.connections.webapp" 
        	location="net.bluemix.samples.connections.webapp.war" 
        	name="net.bluemix.samples.connections.webapp"/>
    </server>

For https:

       <server description="new server">
           <featureManager>
               <feature>jsp-2.2</feature>
               <feature>localConnector-1.0</feature>
               <feature>ssl-1.0</feature>
               <feature>appSecurity-2.0</feature>
               <feature>jaxrs-1.1</feature>
           </featureManager>
           <httpEndpoint httpPort="9080" httpsPort="9443" id="defaultHttpEndpoint"/>
           <applicationMonitor updateTrigger="mbean"/>
           <sslDefault/>    
           <keyStore id="defaultKeyStore" password="yourPassword"/>
           <webApplication id="net.bluemix.samples.connections.webapp" 
        	   location="net.bluemix.samples.connections.webapp.war" 
        	   name="net.bluemix.samples.connections.webapp"/>
       </server>

**Run sample locally**

Start Liberty and invoke the following URLs. 
1. 'userprofile' invokes the servlet which prompts for authentication if necessary and returns the user profile information of the current user
2. 'index.html' invokes a sample HTML page that reads user profile information via AngularJS REST calls

For http:
[http://localhost:9080/net.bluemix.samples.connections.webapp/index.html](http://localhost:9080/net.bluemix.samples.connections.webapp/index.html)
[http://localhost:9080/net.bluemix.samples.connections.webapp/userprofile](http://localhost:9080/net.bluemix.samples.connections.webapp/userprofile)

For https:
[https://localhost:9443/net.bluemix.samples.connections.webapp/index.html](https://localhost:9443/net.bluemix.samples.connections.webapp/index.html)
[https://localhost:9443/net.bluemix.samples.connections.webapp/userprofile](https://localhost:9443/net.bluemix.samples.connections.webapp/userprofile)

You can use the following two test users:
[https://apps.na.collabservtest.lotus.com](https://apps.na.collabservtest.lotus.com)
samantha_daryn@zetains.com, pw: icsappdev2014
harry_greene@zetains.com, pw: icsappdev2014

Deploy updated sample to Bluemix
----------------------------------------------------------------------------------

**Sign up for Bluemix**

[Sign up for Bluemix](https://apps.admin.ibmcloud.com/manage/trial/bluemix.html)

**Install the Cloud Foundry command line tool**

[Install the Cloud Foundry command line tool](https://github.com/cloudfoundry/cli#downloads)

**Build the sample (war file)**

The sample is a Maven project. To generate the war file select the project in the Eclipse explorer and choose export war file.

Alternatively you can use the external build tool [Maven](http://maven.apache.org/download.cgi). To build the app change to the project's directory in a command line window and run ...
> mvn

**Push app to Bluemix**

In a command prompt run the following commands the same directory that contains the war file.

> cf api https://api.ng.bluemix.net
> cf login

You need to use your IBM id and password (Bluemix credentials)
 
> cf push [connectionsSamples] -p [net.bluemix.samples.connections.webapp.war] -m 512M

[connectionsSamples] - this is the name of your Bluemix app. needs to be unique
[net.bluemix.samples.connections.webapp.war] - name of the exported/built war file

Alternatively you can use the [Cloud Foundry Maven Plugin](https://github.com/cloudfoundry/cf-java-client/tree/master/cloudfoundry-maven-plugin) to build and push in one step. In this case you can deploy your application simply via ...
> mvn -P deploy

**Run sample on Bluemix**

Invoke the following URLs:
https://[connectionssamples].mybluemix.net
https://[connectionssamples].mybluemix.net/userprofile

e.g.
[https://connectionssamples.mybluemix.net](https://connectionssamples.mybluemix.net)
[https://connectionssamples.mybluemix.net/userprofile](https://connectionssamples.mybluemix.net/userprofile)

You can use the following two test users:
[https://apps.na.collabservtest.lotus.com](https://apps.na.collabservtest.lotus.com)
samantha_daryn@zetains.com, pw: icsappdev2014
harry_greene@zetains.com, pw: icsappdev2014