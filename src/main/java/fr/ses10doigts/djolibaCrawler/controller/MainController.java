package fr.ses10doigts.djolibaCrawler.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.ses10doigts.djolibaCrawler.model.business.entity.Drum;
import fr.ses10doigts.djolibaCrawler.model.business.entity.MainTableFiltersDto;
import fr.ses10doigts.djolibaCrawler.model.business.entity.RulesDto;
import fr.ses10doigts.djolibaCrawler.model.crawl.CrawlConfiguration;
import fr.ses10doigts.djolibaCrawler.model.crawl.entity.Report;
import fr.ses10doigts.djolibaCrawler.model.scrap.entity.Skin;
import fr.ses10doigts.djolibaCrawler.model.scrap.entity.enumarate.WoodType;
import fr.ses10doigts.djolibaCrawler.service.business.BusinessConfigurationService;
import fr.ses10doigts.djolibaCrawler.service.business.BusinessService;
import fr.ses10doigts.djolibaCrawler.service.crawl.CrawlService;
import fr.ses10doigts.djolibaCrawler.service.web.CrawlConfigurationService;
import fr.ses10doigts.djolibaCrawler.service.web.LaunchService;


@Controller
public class MainController {

    @Autowired
    private CrawlConfigurationService	 crawlConfigurationService;
    @Autowired
    private BusinessConfigurationService businessConfigurationService;
    @Autowired
    private BusinessService		 businessService;
    @Autowired
    private CrawlService		 crawlService;
    @Autowired
    private LaunchService		 launcher;

    private static final Logger		 logger	= LoggerFactory.getLogger(MainController.class);

    @GetMapping("/")
    public String home(Model model) {

	model.addAttribute("businessConf", businessConfigurationService.getBusinessConfiguration());
	model.addAttribute("skinList", businessService.getAllActivSkins(null, null));
	model.addAttribute("frameList", businessService.getAllActivFrames(null, null, null));

	model.addAttribute("animals", businessService.getDistinctAnimal(null, null));
	model.addAttribute("frameSizes", businessService.getDistinctFrameSize(null, null, null));
	model.addAttribute("frameWoods", businessService.getDistinctFrameWood());

	return "home";
    }

    @PostMapping("/updateRules")
    public String updateRules(@ModelAttribute RulesDto dto) {
	boolean b = businessConfigurationService.saveConfiguration(dto);
	logger.debug("Update Rules Status : " + b);

	return "empty";

    }

    @PostMapping("/changeTableFilters")
    public ModelAndView changeFiltersMainTable(@ModelAttribute MainTableFiltersDto dto) {
	logger.debug("User change filters: " + dto);

	if (dto.getAnimal() != null && dto.getAnimal().equals("aucun")) {
	    dto.setAnimal(null);
	}
	if (dto.getAvailable() != null && dto.getAvailable() == false) {
	    dto.setAvailable(null);
	}
	WoodType wt = null;
	if (!dto.getFrameWood().equals("aucun")) {
	    wt = WoodType.valueOf(dto.getFrameWood());
	}
	if (dto.getFrameSize() == 0) {
	    dto.setFrameSize(null);
	}


	List<Skin> allActivSkins = businessService.getAllActivSkins(dto.getAnimal(), dto.getAvailable());

	List<Integer> distinctFrameSize = businessService.getDistinctFrameSize(dto.getFrameSize(), wt,
		dto.getAvailable());

	Map<String, List<Drum>> result = businessService.buildDrumsFromFilters(dto.getAnimal(), dto.getFrameSize(),
		wt, dto.getAvailable());

	ModelAndView mav = new ModelAndView("mainTable");
	mav.addObject("mainTable", new HashMap<>());
	mav.addObject("skinList", allActivSkins);
	mav.addObject("frameSizes", distinctFrameSize);
	mav.addObject("mainTable", result);

	return mav;
    }

    @GetMapping("/skins")
    public String skins(Model model) {

	return "skins";
    }

    @GetMapping("/crawl")
    public String crawl(Model model) {
	model.addAttribute("configuration", crawlConfigurationService.getCrawlConfiguration());
	model.addAttribute("crawlReport", crawlService.getReportCurrentCrawl());
	model.addAttribute("refactReport", null);

	return "crawl";
    }

    @GetMapping("/generate")
    public String generateXls(Model model) {

	//	excelService.extractCourseCompletes();

	model.addAttribute("configuration", crawlConfigurationService.getCrawlConfiguration());
	model.addAttribute("crawlReport", crawlService.getReportCurrentCrawl());
	model.addAttribute("refactReport", null);

	return "redirect:/";
    }

    @PostMapping(value = "/crawl", params = "action=save")
    public ModelAndView saveConfig(@ModelAttribute CrawlConfiguration dto) {
	logger.info("User ask to save config : " + dto);

	crawlConfigurationService.saveConfiguration(dto);
	return new ModelAndView("redirect:crawl");
    }

    @PostMapping(value = "/crawl", params = "action=launch")
    public ModelAndView launchCrawl(@ModelAttribute CrawlConfiguration dto) {
	logger.info("User ask to launch with config : " + dto);

	crawlConfigurationService.saveConfiguration(dto);
	Report crawlReport = launcher.manageLaunch();

	ModelAndView mav = new ModelAndView("crawl");
	mav.addObject("configuration", crawlConfigurationService.getCrawlConfiguration());
	mav.addObject("crawlReport", crawlReport);
	mav.addObject("refactReport", null);

	return mav;
    }

    @PostMapping(value = "/crawl", params = "action=stop")
    public ModelAndView stop() {
	logger.info("User ask to stop ");

	crawlService.stopCurrentCrawl();

	ModelAndView mav = new ModelAndView("crawl");
	mav.addObject("configuration", crawlConfigurationService.getCrawlConfiguration());
	mav.addObject("crawlReport", crawlService.getReportCurrentCrawl());
	mav.addObject("refactReport", null);

	return mav;
    }

    @PostMapping(value = "/", params = "action=test")
    public ModelAndView test() {
	logger.info("Testing connectivity ");

	crawlService.testConnectivity();

	ModelAndView mav = new ModelAndView("home");
	mav.addObject("configuration", crawlConfigurationService.getCrawlConfiguration());
	mav.addObject("crawlReport", crawlService.getReportCurrentCrawl());
	mav.addObject("refactReport", null);

	return mav;
    }

    @PostMapping(value = "/", params = "action=generate")
    public ModelAndView generate(@ModelAttribute CrawlConfiguration dto) {
	logger.info("User ask to generate ");

	String urls = crawlConfigurationService.generateUrlFromDates(dto.getStartGenDate(), dto.getEndGenDate());

	dto.setTxtSeeds(urls);

	crawlConfigurationService.saveConfiguration(dto);

	ModelAndView mav = new ModelAndView("crawl");
	mav.addObject("configuration", dto);
	mav.addObject("crawlReport", crawlService.getReportCurrentCrawl());
	mav.addObject("refactReport", null);

	return mav;
    }

}
