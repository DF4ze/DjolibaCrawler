package fr.ses10doigts.coursesCrawler.service.crawl.tool;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import fr.ses10doigts.coursesCrawler.model.crawl.Page;

public class PagesContainer {

    private Queue<Page> pages;

    public PagesContainer() {
	super();
	this.pages = new LinkedList<>();
    }

    public PagesContainer(Set<Page> pages) {
	super();
	for (Page page : pages) {
	    this.pages.add(page);
	}
    }

    public void add(Page page) {
	if (pages.contains(page)) {
	    pages.remove(page);
	}
	pages.add(page);
    }

    public void addAll(Set<Page> pages) {
	for (Page page : pages) {
	    add(page);
	}
    }

    public Queue<Page> getPages() {
	return pages;
    }

    public int size() {
	return pages.size();
    }
}
