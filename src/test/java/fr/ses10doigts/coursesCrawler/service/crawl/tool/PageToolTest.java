package fr.ses10doigts.coursesCrawler.service.crawl.tool;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import fr.ses10doigts.djolibaCrawler.model.crawl.entity.Page;
import fr.ses10doigts.djolibaCrawler.service.crawl.tool.PageTool;
import jakarta.annotation.Generated;

@Generated(value = "org.junit-tools-1.1.0")
public class PageToolTest {

    private PageTool createTestSubject() {
	return new PageTool();
    }

    @Test
    public void testFindUrl() throws Exception {
	PageTool testSubject;
	Page seed = new Page("http://test.fr", 1);
	Set<String> authorised = new HashSet<>();
	Set<Page> result;

	authorised.add("foo");
	authorised.add("bar");

	// @formatter:off
	String textContent = "<head>injection of some script <script href=\"http://scripts.com\"/></head>" +
		"<body>"+
		"<a href=\"http://undesiredlink.com\">" +
		"<a href=\"http://desired_foo_link.com\">"+
		"<a href=\"http://desired_bar_link.com\">" +
		"</body>";
	// @formatter:on

	// default test
	testSubject = createTestSubject();
	result = testSubject.findUrlsInContent(seed, textContent, authorised);

	assertEquals(result.size(), 2);
	for (Page page : result) {
	    assertEquals(page.getHop(), seed.getHop() + 1);
	}

    }

    @Test
    public void testUrl2Pages() throws Exception {
	PageTool tool;
	Set<String> urls = new HashSet<>();
	urls.add("http://google.fr");

	Set<Page> result;

	// default test
	tool = createTestSubject();
	result = tool.url2Pages(urls);

	assertEquals(result.size(), 1);
	for (Page page : result) {
	    assertEquals(page.getHop(), 0);
	    assertEquals(page.getNbRetry(), 0);
	    assertEquals(page.getUrl(), "http://google.fr");
	}
    }

    @Test
    public void testGetBaseUrl() {
	PageTool tool;
	String url = "http://test.fr/folder/subfolder";

	tool = createTestSubject();
	String base = tool.getBaseUrl(url);

	assertEquals(base, "http://test.fr");
    }
}