package com.ibm.eib.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


@Service
public class WebexService {

	@Autowired
    ResourceLoader resourceLoader;
	
	private String authUrl = "https://api.ciscospark.com/v1/access_token";
	
	public String generateWidget(String code) {

		RestTemplate restTemplate = new RestTemplate();
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

			HttpEntity<String> request = new HttpEntity<String>(createInputString(code), headers);
			
			AuthJson resultObject = 
					restTemplate.postForObject(authUrl,request, AuthJson.class);

			if (resultObject.getAccess_token()!= null) {
				return generatePage(resultObject.getAccess_token());
			} 
			return "<h1>access token is null!!</h1>";
			
		} catch (RestClientException exception) {
			System.out.println("REST and HTTP error" + exception.getMessage());
//			throw exception;
			return generatePage("Alooooo");
		}
	}

	private String generatePage(String access_token) {

//		Resource resource = resourceLoader.getResource("classpath:templates/widget.html");
//
//		try {
//			InputStream input = resource.getInputStream();
//			File file = resource.getFile();
//			String allString = FileUtils.readFileToString(file,StandardCharsets.UTF_8);
//			allString = allString.replace("$accessToken", access_token);
//			return allString;
//		} catch (IOException e) {
//			e.printStackTrace();
//			return "<h1>failed to open the file</h1>";
//		}
		
		String allString = "<html>  <head>    <meta charset=\"utf8\">    <title>Space Widget Demo</title>    <link rel=\"stylesheet\" href=\"https://code.s4d.io/widget-space/production/main.css\">  </head>  <body>       <div style=\"width: 500px; height: 500px;\"         data-toggle=\"ciscospark-space\"        data-access-token=\"$xyz\"        data-destination-id=\"Y2lzY29zcGFyazovL3VzL1JPT00vNmM5ZWQ1ODAtZDg3Ni0xMWU5LTk3ZmQtMDkzOWRhNDAwZWMz\"         data-destination-type=\"spaceId\"      />    <script src=\"https://code.s4d.io/widget-space/production/bundle.js\"></script>  </body></html>";
		allString = allString.replace("$xyz", access_token);
		
		return allString;
	}

	private String createInputString(String code) {

		StringBuilder builder = new StringBuilder();
		builder.append("grant_type=authorization_code&");
		builder.append("client_id=Cba89729a684e25751b9a8a43bc881190b518c3b533c5f2f134db61b49626d71c&");
		builder.append("client_secret=0a72f86d4db087f17e68b5d37dd15ae2baed18cbf92fc5f5a4116c9a907983cb&");
		builder.append("code="+code+"&");
		builder.append("redirect_uri=http://spring-hi-trying-spring.apps.ca-central-1.starter.openshift-online.com/hi/sayhi&");
		
		return builder.toString();
	}

}
