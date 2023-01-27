package fr.ses10doigts.coursesCrawler.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.ses10doigts.coursesCrawler.model.crawl.CrawlReport;
import fr.ses10doigts.coursesCrawler.model.web.Configuration;
import fr.ses10doigts.coursesCrawler.service.course.RefactorerService;
import fr.ses10doigts.coursesCrawler.service.crawl.CrawlService;
import fr.ses10doigts.coursesCrawler.service.web.ConfigurationService;
import fr.ses10doigts.coursesCrawler.service.web.LaunchService;


@Controller
public class MainController {

    @Autowired
    private ConfigurationService conf;
    @Autowired
    private CrawlService	 crawlService;
    @Autowired
    private RefactorerService	 refactoService;
    @Autowired
    private LaunchService	 launcher;

    private static final Logger	 logger	= LoggerFactory.getLogger(MainController.class);

    @GetMapping("/")
    public String home(Model model) {
	model.addAttribute("configuration", conf.getConfiguration());
	model.addAttribute("report", crawlService.getReportCurrentCrawl());

	return "home";
    }

    @PostMapping(value = "/launch", params = "action=save")
    public ModelAndView saveConfig(@ModelAttribute Configuration dto) {
	logger.info("User ask to save config : " + dto);

	conf.saveConfiguration(dto);
	return new ModelAndView("redirect:/");
    }

    @PostMapping(value = "/launch", params = "action=launch")
    public ModelAndView launchCrawl(@ModelAttribute Configuration dto) {
	logger.info("User ask to launch with config : " + dto);

	conf.saveConfiguration(dto);
	CrawlReport crawlReport = launcher.manageLaunch();

	ModelAndView mav = new ModelAndView("home");
	mav.addObject("configuration", conf.getConfiguration());
	mav.addObject("report", crawlReport);

	return mav;
    }

    @PostMapping(value = "/launch", params = "action=stop")
    public ModelAndView stop() {
	logger.info("User ask to stop ");

	crawlService.stopCurrentCrawl();
	refactoService.stopRefactorer();

	ModelAndView mav = new ModelAndView("home");
	mav.addObject("configuration", conf.getConfiguration());
	mav.addObject("report", crawlService.getReportCurrentCrawl());

	return mav;
    }

}
