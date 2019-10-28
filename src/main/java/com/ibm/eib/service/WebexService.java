package com.ibm.eib.service;

import org.springframework.beans.factory.annotation.Autowired;
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

			if (resultObject.access_token != null) {
				return generatePage(resultObject.access_token);
			} 
			return "<h1>access token is null!!</h1>";
			
		} catch (RestClientException exception) {
			System.out.println("REST and HTTP error" + exception.getMessage());
			return generateError(exception.getMessage());
		}
	}

	private String generateError(String exceptionMsg) {
		String out = "<h1>"+exceptionMsg+"</h1>";
	}

	private String generatePage(String access_token) {
		
		String allString = "<html>  <head>    <meta charset=\"utf8\">    <title>Space Widget Demo</title>    <link rel=\"stylesheet\" href=\"https://code.s4d.io/widget-space/production/main.css\">  </head>  <body>       <div style=\"width: 500px; height: 500px;\"         data-toggle=\"ciscospark-space\"        data-access-token=\"$xyz\"        data-destination-id=\"Y2lzY29zcGFyazovL3VzL1RFQU0vOWRhZWMxYjAtZWU5ZS0xMWU5LTg2NDAtMDVmYzcxNDFkMTk2\"         data-destination-type=\"spaceId\"      />    <script src=\"https://code.s4d.io/widget-space/production/bundle.js\"></script>  </body></html>";
		allString = allString.replace("$xyz", access_token);
		
		return allString;
	}

	private String createInputString(String code) {

		StringBuilder builder = new StringBuilder();
		builder.append("grant_type=authorization_code&");
		builder.append("client_id=Cba89729a684e25751b9a8a43bc881190b518c3b533c5f2f134db61b49626d71c&");
		builder.append("client_secret=a43d5625b89b0d81645a7594aa683b7ff6b1307a608dcc9ac98dc8c4b2463141&");
		builder.append("code="+code+"&");
		builder.append("redirect_uri=http://webex-interceptor-app-webex-interceptor.apps.ca-central-1.starter.openshift-online.com/hi/sayhi&");
		
		return builder.toString();
	}
private class AuthJson {

	public String access_token;
	private int expires_in;
	private String refresh_token;
	private int refresh_token_expires_in;
}
}
