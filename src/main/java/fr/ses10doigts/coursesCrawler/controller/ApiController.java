package fr.ses10doigts.coursesCrawler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.ses10doigts.coursesCrawler.model.crawl.CrawlReport;
import fr.ses10doigts.coursesCrawler.model.crawl.Page;
import fr.ses10doigts.coursesCrawler.service.course.RefactorerService;
import fr.ses10doigts.coursesCrawler.service.crawl.CrawlService;

@RestController
public class ApiController {

    @Autowired
    private CrawlService crawlService;
    @Autowired
    private RefactorerService refactoService;



    @GetMapping("/test")
    public String getPage() {

	Page p = new Page("\"https://www.geny.com/reunions-courses-pmu?date=2021-03-28\"", 0);
	String content = crawlService.getPage(p.getUrl());
	// p.setContent(content);

	return content;
    }




    @GetMapping("/launchRefacto")
    public void launchRefacto() {
	refactoService.launchRefactorer();
    }

    public void refactorer() {

    }

    @GetMapping("/report")
    public CrawlReport reportCrawl() {

	return crawlService.getReportCurrentCrawl();

    }



}