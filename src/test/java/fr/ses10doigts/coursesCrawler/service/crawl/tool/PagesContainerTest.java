package fr.ses10doigts.coursesCrawler.service.crawl.tool;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Queue;

import org.junit.jupiter.api.Test;

import fr.ses10doigts.djolibaCrawler.model.crawl.entity.Page;
import fr.ses10doigts.djolibaCrawler.service.crawl.tool.PagesContainer;
import jakarta.annotation.Generated;

@Generated(value = "org.junit-tools-1.1.0")
public class PagesContainerTest {

    private PagesContainer createTestSubject() {
	PagesContainer p = new PagesContainer();
	p.add(new Page("test1", 0));
	p.add(new Page("test2", 0));
	p.add(new Page("test3", 0));
	return p;
    }

    @Test
    public void testGetPages() throws Exception {
	PagesContainer testSubject;
	Queue<Page> result;

	// default test
	testSubject = createTestSubject();
	result = testSubject.getPages();

	for (Page page; (page = result.poll()) != null;) {
	    System.out.println(page.getUrl());
	    ;
	}

	assertEquals(0, result.size());
    }
}