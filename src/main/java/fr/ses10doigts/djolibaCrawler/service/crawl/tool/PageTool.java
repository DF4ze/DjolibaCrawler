package fr.ses10doigts.djolibaCrawler.service.crawl.tool;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import fr.ses10doigts.djolibaCrawler.model.crawl.entity.Page;

@Service
public class PageTool {

    private static final Logger logger = LoggerFactory.getLogger(PageTool.class);

    public Set<Page> url2Pages(Set<String> urls) {
	Set<Page> pages = new HashSet<>();

	for (String url : urls) {
	    pages.add( new Page(url, 0));
	}

	return pages;
    }

    public String getBaseUrl(String url) {
	String regex = "https?://[-a-zA-Z0-9+&@#%?=~_|!:,.;]*";
	Pattern pattern = Pattern.compile(regex);
	Matcher matcher = pattern.matcher(url);

	String baseUrl = "";
	if (matcher.find()) {
	    baseUrl = matcher.group();
	}
	return baseUrl;
    }

    public Set<Page> findUrlsInContent(Page seed, String content, Set<String> authorized) {
	//	String regex = "https?://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
	String regex = "href=\\\"((https?://)?[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|])\\\"";
	Pattern pattern = Pattern.compile(regex);
	Matcher matcher = pattern.matcher(content);

	String baseUrl = getBaseUrl(seed.getUrl());
	Set<String> potentialUrls = new HashSet<>();

	if (logger.isDebugEnabled()) {
	    String auth = "";
	    for (String string : authorized) {
		auth += string + ", ";
	    }
	    logger.debug("Searching urls in " + seed.getUrl() + " containing keys : " + auth);
	}

	while (matcher.find()) {
	    String foundedUrl = matcher.group(1);

	    // check if relative or absolute path
	    if (!foundedUrl.startsWith("http")) {
		foundedUrl = baseUrl + foundedUrl;

	    }

	    boolean matchAuthorised = false;
	    // Does foundedUrl pass the authorized URL
	    for (String pat : authorized) {
		if (Pattern.matches(".*" + pat + ".*", foundedUrl)) {
		    matchAuthorised = true;
		}
	    }
	    // if no filter, authorised all
	    if (authorized.size() == 0) {
		matchAuthorised = true;
	    }

	    if (matchAuthorised) {
		potentialUrls.add(foundedUrl);

	    }
	}

	///////////
	// Updating status
	// * On new Pages
	Set<Page> newPages = url2Pages(potentialUrls);
	String urlList = "";
	for (Page page : newPages) {
	    // Defining HOP
	    page.setHop(seed.getHop() + 1);
	    urlList += page.getUrl() + "\n";
	}
	logger.debug("Url added : \n" + urlList);



	return newPages;
    }


}
