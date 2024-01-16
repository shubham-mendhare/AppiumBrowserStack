package com.shubham.projects.AppTest.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;


public abstract class AppiumUtils {
	
	public AppiumDriverLocalService service;
	
	
	public Double getFormattedAmount(String amount)
	{
		Double price = Double.parseDouble(amount.substring(1));
		return price;
		
	}
	
	public List<HashMap<String, String>> getJsonData(String jsonFilePath) throws IOException {
//System.getProperty("user.dir")+"//src//test//java//org//user//testData//eCommerce.json"
		// convert json file content to json string
		String jsonContent = FileUtils.readFileToString(new File(jsonFilePath),StandardCharsets.UTF_8);

		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent,
				new TypeReference<List<HashMap<String, String>>>() {
				});

		return data;

	}
	
	public AppiumDriverLocalService startAppiumServer(String ipAddress,int port, String AppiumJSFilePath)
	{
		 service = new AppiumServiceBuilder().withAppiumJS(new File(AppiumJSFilePath))
				   // .withArgument(() -> "--use-plugins", "element-wait,device-farm,appium-dashboard,appium-reporter-plugin")
				 	.withArgument(() -> "--use-plugins", "element-wait,device-farm,appium-dashboard")
				    .withArgument(() -> "-ka", "800")
				    .withArgument(() -> "--plugin-device-farm-platform", "android")
				    .withTimeout(Duration.ofSeconds(120))
					.withIPAddress(ipAddress)
					.usingPort(port)
					.build();
				return service;
	}
	
	
	public void waitForElementToAppear(WebElement ele, AppiumDriver driver)
	{
		WebDriverWait wait =new WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.attributeContains((ele),"text" , "Cart"));
	}
	
	
	public String getScreenshotPath(String testCaseName, AppiumDriver driver) throws IOException
	{
		File source = driver.getScreenshotAs(OutputType.FILE);
		String destinationFile = System.getProperty("user.dir")+"//reports"+testCaseName+".png";
		FileUtils.copyFile(source, new File(destinationFile));
		return destinationFile;
		//1. capture and place in folder //2. extent report pick file and attach to report
	}
	
	public String pid;	
	public void startServer() {
	    String pid = null;
	    try {
	        // Command to be executed
	        String command = "appium server -ka 800 --use-plugins=device-farm,appium-dashboard --plugin-device-farm-platform=android";

	        // Add a unique identifier to the command
	        String uniqueIdentifier = "justcheck";
	        command = command + " && echo " + uniqueIdentifier;

	        // Create ProcessBuilder instance with command
	        ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "start", "cmd.exe", "/k", command);

	        // Start the process
	        Process process = processBuilder.start();

	        // Wait for a moment to ensure the new command prompt window has started
	        Thread.sleep(2000);

	        // Get the PID of the new command prompt window
	        Process wmic = Runtime.getRuntime().exec("wmic process where \"name='cmd.exe' and CommandLine like '%" + uniqueIdentifier + "%'\" get ProcessId");
	        BufferedReader reader = new BufferedReader(new InputStreamReader(wmic.getInputStream()));
	        String line;
	        while ((line = reader.readLine()) != null) {
	            line = line.trim();
	            if (line.matches("\\d+")) {
	                pid = line;
	                break;
	            }
	        }
	    } catch (IOException | InterruptedException e) {
	        e.printStackTrace();
	    }
	    this.pid = pid;
	}



    public void killProcess(String pid) {
        try {
            // Command to kill the process
            String command = "taskkill /F /PID " + pid + " /T";

            // Create ProcessBuilder instance with command
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", command);

            // Execute the command
            processBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public void stopServer(String pid) {
		killProcess(pid);
	}



	    public boolean isServerRunning(int PORT) {
	        boolean isServerRunning = false;
	        ServerSocket serverSocket;
	        try {
	            serverSocket = new ServerSocket(PORT);
	            serverSocket.close();
	        } catch (IOException e) {
	            isServerRunning = true;
	        }
	        return isServerRunning;
	    }
	    
	    public Properties prop;
		public FileInputStream fis;
		public FileOutputStream out;
		
	    public void InitconfigFile() throws IOException {
	    	prop = new Properties();
		    fis = new FileInputStream(System.getProperty("user.dir")+"//src//main//java//com//shubham//projects//AppTest//Resources//data.properties");
			prop.load(fis);
	    }
	    
	    public void exitConfigFile() throws IOException {
			 fis.close();
			 prop.setProperty("PID", pid);
			 out = new FileOutputStream(System.getProperty("user.dir")+"//src//main//java//com//shubham//projects//AppTest//Resources//data.properties");
	         prop.store(out, null);
	         out.close();
	    }
	        
	    public void EmailReport(String recipient, String sender, String usrname, String  usrpassword, String subj
	    		, String msg, String AttachmentPath, String smpthost, String smptport ) {

	        // Recipient's email ID needs to be mentioned.
	        String to = recipient;

	        // Sender's email ID needs to be mentioned
	        String from = sender;
	        final String username = usrname;//change accordingly
	        final String password = usrpassword;//change accordingly

	        // Assuming you are sending email through relay.jangosmtp.net
	        String host = smpthost;

	        Properties props = new Properties();
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.host", host);
	        props.put("mail.smtp.port", smptport);

	        // Get the Session object.
	        Session session = Session.getInstance(props,
	                new javax.mail.Authenticator() {
	                    protected PasswordAuthentication getPasswordAuthentication() {
	                        return new PasswordAuthentication(username, password);
	                    }
	                });

	        try {
	            // Create a default MimeMessage object.
	            MimeMessage message = new MimeMessage(session);

	            // Set From: header field of the header.
	            message.setFrom(new InternetAddress(from));

	            // Set To: header field of the header.
	            message.setRecipients(Message.RecipientType.TO,
	                    InternetAddress.parse(to));

	            // Set Subject: header field
	            message.setSubject(subj);

	            // Create the message part
	            MimeBodyPart messageBodyPart = new MimeBodyPart();

	            // Now set the actual message
	            messageBodyPart.setText(msg);

	            // Create a multipart message
	            MimeMultipart multipart = new MimeMultipart();

	            // Set text message part
	            multipart.addBodyPart(messageBodyPart);

	            // Part two is attachment
	            messageBodyPart = new MimeBodyPart();
	            String filename = AttachmentPath;
	            FileDataSource source = new FileDataSource(filename);
	            messageBodyPart.setDataHandler(new DataHandler(source));
	            messageBodyPart.setFileName(filename);
	            multipart.addBodyPart(messageBodyPart);

	            // Send the complete message parts
	            message.setContent(multipart);

	            // Send message
	            Transport.send(message);

	            System.out.println("Sent message successfully....");

	        } catch (MessagingException e) {
	            throw new RuntimeException(e);
	        }
	    }
}
