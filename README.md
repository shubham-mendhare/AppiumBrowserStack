Framework dependancy
**1. Latest JAVA installed
2. Node
3. Appium Server with plugins element-wait, device-farm, appium-dashboard & appium-reporter-plugin(optional)
4. Maven
5. Android studio**

Emulator bat file (Applicable for windows)
cd /d C:\Users\shubh\AppData\Local\Android\sdk\tools
 emulator @Pixel_3a_API_34_extension_level_7_x86_64

Once appium server start running and script executed you can check below urls 
http://localhost:4723/device-farm/
http://localhost:4723/dashboard/session/d62ec028-3e09-4f27-8aef-94fa512aa44d


To run script go to project directory and from there open cmd or terminal
**mvn test -PRegression**

To stop executing script on browserstack go to pom.xml and comment below line of code

**<argLine>
    -javaagent:${com.browserstack:browserstack-java-sdk:jar}
 </argLine>**

Sample app-release.apk is already added project directory
