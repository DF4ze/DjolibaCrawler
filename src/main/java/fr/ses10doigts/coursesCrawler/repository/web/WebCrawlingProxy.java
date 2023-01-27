package fr.ses10doigts.coursesCrawler.repository.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class WebCrawlingProxy {

    public String getRawPage(String url) {

	RestTemplate restTemplate = new RestTemplate();
	ResponseEntity<String> response = restTemplate
		.exchange(
			url,
			HttpMethod.GET,
			null,
			new ParameterizedTypeReference<String>() {
			}
			);
	String body = response.getBody();
	return body;
    }

    public String getUrlContents(String theUrl) {
	StringBuilder content = new StringBuilder();
	// Use try and catch to avoid the exceptions
	try {
	    URL url = new URL(theUrl); // creating a url object
	    URLConnection urlConnection = url.openConnection(); // creating a urlconnection object

	    // wrapping the urlconnection in a bufferedreader
	    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
	    String line;
	    // reading from the urlconnection using the bufferedreader
	    while ((line = bufferedReader.readLine()) != null) {
		content.append(line + "\n");
	    }
	    bufferedReader.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	System.out.println(content);
	return content.toString();
    }
}