package fr.ses10doigts.djolibaCrawler.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.ses10doigts.djolibaCrawler.model.scrap.EntitiesList;
import fr.ses10doigts.djolibaCrawler.model.scrap.entity.Frame;
import fr.ses10doigts.djolibaCrawler.model.scrap.entity.enumarate.FrameFormat;
import fr.ses10doigts.djolibaCrawler.model.scrap.entity.enumarate.WoodType;
import fr.ses10doigts.djolibaCrawler.service.scrap.RepositoryService;

@RestController
public class ApiController {

    @Autowired
    private RepositoryService rs;

    private static String     URL_ASH = "https://djoliba.com/fr/instruments-de-musique/2840-cadre-ash-wood-pour-tambour-sur-cadre-chamane.html";
    private static String     URL_HET = "https://djoliba.com/fr/instruments-musicotherapie/1435-cadre-tambour-chamane.html";

    @GetMapping("/populateFrame")
    public String getPage() {

	FrameFormat ff = FrameFormat.ROUND;
	EntitiesList el = new EntitiesList();

	// Cadres ASH
	Frame f = new Frame();
	f.setActif(true);
	f.setDate(new Date());
	f.setDisponibility(true);
	f.setUrl(URL_ASH);
	f.setFormat(ff);
	f.setWoodType(WoodType.FRENE);
	f.setSku("CADRE10A");
	f.setPrice(21d);
	f.setSizeCm(25);
	f.setSizeInch(10);
	el.add(f);
	rs.addAll(el);

	f = new Frame();
	f.setActif(true);
	f.setDate(new Date());
	f.setDisponibility(true);
	f.setUrl(URL_ASH);
	f.setFormat(ff);
	f.setWoodType(WoodType.FRENE);
	f.setSku("CADRE11");
	f.setPrice(23d);
	f.setSizeCm(30);
	f.setSizeInch(12);
	el = new EntitiesList();
	el.add(f);
	rs.addAll(el);

	f = new Frame();
	f.setActif(true);
	f.setDate(new Date());
	f.setDisponibility(true);
	f.setUrl(URL_ASH);
	f.setFormat(ff);
	f.setWoodType(WoodType.FRENE);
	f.setSku("CADRE10");
	f.setPrice(27d);
	f.setSizeCm(35);
	f.setSizeInch(14);
	el = new EntitiesList();
	el.add(f);
	rs.addAll(el);

	f = new Frame();
	f.setActif(true);
	f.setDate(new Date());
	f.setDisponibility(true);
	f.setUrl(URL_ASH);
	f.setFormat(ff);
	f.setWoodType(WoodType.FRENE);
	f.setSku("CADRE6");
	f.setPrice(35d);
	f.setSizeCm(40);
	f.setSizeInch(16);
	el = new EntitiesList();
	el.add(f);
	rs.addAll(el);

	f = new Frame();
	f.setActif(true);
	f.setDate(new Date());
	f.setDisponibility(true);
	f.setUrl(URL_ASH);
	f.setFormat(ff);
	f.setWoodType(WoodType.FRENE);
	f.setSku("CADRE7");
	f.setPrice(37d);
	f.setSizeCm(45);
	f.setSizeInch(18);
	el = new EntitiesList();
	el.add(f);
	rs.addAll(el);

	f = new Frame();
	f.setActif(true);
	f.setDate(new Date());
	f.setDisponibility(true);
	f.setUrl(URL_ASH);
	f.setFormat(ff);
	f.setWoodType(WoodType.FRENE);
	f.setSku("CADRE8");
	f.setPrice(42d);
	f.setSizeCm(50);
	f.setSizeInch(20);
	el = new EntitiesList();
	el.add(f);
	rs.addAll(el);

	f = new Frame();
	f.setActif(true);
	f.setDate(new Date());
	f.setDisponibility(true);
	f.setUrl(URL_ASH);
	f.setFormat(ff);
	f.setWoodType(WoodType.FRENE);
	f.setSku("CADRE9");
	f.setPrice(46d);
	f.setSizeCm(55);
	f.setSizeInch(22);
	el = new EntitiesList();
	el.add(f);
	rs.addAll(el);

	// Cadres Hetre
	f = new Frame();
	f.setActif(true);
	f.setDate(new Date());
	f.setDisponibility(true);
	f.setUrl(URL_HET);
	f.setFormat(ff);
	f.setWoodType(WoodType.HETRE);
	f.setSku("CADRE1-B");
	f.setPrice(39d);
	f.setSizeCm(30);
	f.setSizeInch(12);
	el = new EntitiesList();
	el.add(f);
	rs.addAll(el);

	f = new Frame();
	f.setActif(true);
	f.setDate(new Date());
	f.setDisponibility(true);
	f.setUrl(URL_HET);
	f.setFormat(ff);
	f.setWoodType(WoodType.HETRE);
	f.setSku("CADRE1-A");
	f.setPrice(48d);
	f.setSizeCm(35);
	f.setSizeInch(14);
	el = new EntitiesList();
	el.add(f);
	rs.addAll(el);

	f = new Frame();
	f.setActif(true);
	f.setDate(new Date());
	f.setDisponibility(true);
	f.setUrl(URL_HET);
	f.setFormat(ff);
	f.setWoodType(WoodType.HETRE);
	f.setSku("CADRE1");
	f.setPrice(50d);
	f.setSizeCm(40);
	f.setSizeInch(16);
	el = new EntitiesList();
	el.add(f);
	rs.addAll(el);

	f = new Frame();
	f.setActif(true);
	f.setDate(new Date());
	f.setDisponibility(true);
	f.setUrl(URL_HET);
	f.setFormat(ff);
	f.setWoodType(WoodType.HETRE);
	f.setSku("CADRE2");
	f.setPrice(52d);
	f.setSizeCm(45);
	f.setSizeInch(18);
	el = new EntitiesList();
	el.add(f);
	rs.addAll(el);

	f = new Frame();
	f.setActif(true);
	f.setDate(new Date());
	f.setDisponibility(true);
	f.setUrl(URL_HET);
	f.setFormat(ff);
	f.setWoodType(WoodType.HETRE);
	f.setSku("CADRE3");
	f.setPrice(55d);
	f.setSizeCm(50);
	f.setSizeInch(20);
	el = new EntitiesList();
	el.add(f);
	rs.addAll(el);

	f = new Frame();
	f.setActif(true);
	f.setDate(new Date());
	f.setDisponibility(true);
	f.setUrl(URL_HET);
	f.setFormat(ff);
	f.setWoodType(WoodType.HETRE);
	f.setSku("CADRE4");
	f.setPrice(69d);
	f.setSizeCm(55);
	f.setSizeInch(22);
	el = new EntitiesList();
	el.add(f);
	rs.addAll(el);

	return "ok";
    }







}