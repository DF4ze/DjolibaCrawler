package fr.ses10doigts.djolibaCrawler.service.scrap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import fr.ses10doigts.djolibaCrawler.model.scrap.EntitiesList;
import fr.ses10doigts.djolibaCrawler.model.scrap.entity.Skin;
import fr.ses10doigts.djolibaCrawler.model.scrap.entity.enumarate.SkinFormat;
import fr.ses10doigts.djolibaCrawler.model.scrap.entity.enumarate.SkinSize;
import fr.ses10doigts.djolibaCrawler.service.scrap.tool.XPathTool;

@Component
public class DjolibaParser implements HtmlParser {

    private String url;
    private String body;
    private Document doc;
    private XPathTool xPathTool;

    private static final Logger	logger = LoggerFactory.getLogger(DjolibaParser.class);

    DjolibaParser() {
    }



    @Override
    public EntitiesList parse(String url, String body){
	this.url = url;
	this.body = body == null ? "" : body;

	EntitiesList beansList = new EntitiesList();
	init();

	EntitiesList beanListSkin = parseSkin();
	if (beanListSkin != null) {
	    beansList.addAll(beanListSkin);
	}

	EntitiesList beansListFrame = parseFrame();
	if (beansListFrame != null) {
	    beansList.addAll(beansListFrame);
	}


	try {
	    Thread.sleep(10l);
	} catch (InterruptedException e) {}

	return beansList;
    }

    private void init(){
	doc = Jsoup.parse(body);
	xPathTool = new XPathTool();
    }


    private EntitiesList parseSkin() {
	logger.debug("=================================== Parse Skin");
	logger.info(url);

	EntitiesList bl = new EntitiesList();
	boolean b = false;

	Skin skin = new Skin();
	skin.setDate(new Date());
	skin.setUrl(url);

	Elements elements = xPathTool.getElements(doc, "/h1[@itemprop='name']");
	if (elements != null && elements.size() > 0) {
	    String txt = elements.get(0).text().trim();
	    logger.debug("titre : " + txt);
	    skin.setName(txt);
	}

	elements = xPathTool.getElements(doc, "/span[@itemprop='sku']");
	if( elements!=null && elements.size() > 0 ){
	    String txt = elements.get(0).text().trim();
	    if (txt == null || txt.isBlank()) {
		String attr = elements.get(0).attr("content");
		if (attr != null && !attr.isBlank()) {
		    txt = attr;
		}
	    }
	    logger.debug("sku : " + txt);
	    skin.setSku(txt);
	}

	elements = xPathTool.getElements(doc, "/span[@itemprop='price']");
	if (elements != null && elements.size() > 0) {
	    String txt = elements.get(0).text().trim();
	    logger.debug("price : " + txt);
	    txt = txt.replace(",", ".");
	    txt = txt.replace("<b>", "");
	    txt = txt.replace("</b>", "");
	    txt = txt.replace(" ", "");
	    txt = txt.replace("€", "");
	    txt = txt.replaceAll("[^\\d\\.\\,\\-]", "");

	    logger.debug("processed price : " + txt);
	    skin.setPrice(Double.parseDouble(txt));
	}

	elements = xPathTool.getElements(doc, "/span[@id='availability_value']");
	skin.setAvailable(true);
	if (elements != null && elements.size() > 0) {
	    String txt = elements.get(0).text().trim();
	    logger.debug("availability : " + txt);

	    b = Pattern.compile("Ce produit n'est plus en stock.*").matcher(txt).matches();
	    if (b) {
		skin.setAvailable(false);
	    }

	    logger.debug("availability : " + skin.getAvailable());
	}


	if (skin.getName() != null && !skin.getName().isBlank()) {
	    SkinSize size = extractSizeFromText(skin.getName());

	    SkinSize size2 = null;
	    elements = xPathTool.getElements(doc, "/div[@class='page-product-box']");
	    if (elements != null && elements.size() > 0) {
		String txt = elements.get(0).text().trim();
		size2 = extractSizeFromText(txt);
	    }

	    if (size != null && size2 != null) {
		if (size.getHeight() > size2.getHeight()) {
		    size.setHeight(size2.getHeight());
		}

		if (size.getWidth() < size2.getWidth()
			|| (size.getHeight() == size.getWidth() && size.getWidth() != size2.getWidth())) {
		    size.setWidth(size2.getWidth());
		}
	    } else if (size == null && size2 != null) {
		size = size2;
	    } else {
		logger.warn("Unable to retreive size on url : " + url);
	    }

	    skin.setSize(size);
	    logger.debug("size : " + size);

	}


	if (skin.getName() != null && !skin.getName().isBlank()) {
	    Matcher m = Pattern.compile("Peau( [a-zéèêëâäïî]*)? de ([a-zéèêëâäïî]+)", Pattern.CASE_INSENSITIVE)
		    .matcher(skin.getName());
	    if (m.find()) {
		skin.setAnimal(m.group(2).toLowerCase());
	    }
	    logger.debug("Animal : " + skin.getAnimal());
	}


	if (skin.getSize() != null) {
	    if (skin.getSize().getHeight().equals(skin.getSize().getWidth())) {
		skin.setSkinFormat(SkinFormat.CUTTEDSKIN);
	    } else {
		skin.setSkinFormat(SkinFormat.FULLSKIN);
	    }
	}
	elements = xPathTool.getElements(doc, "/img[@id='bigpic']");
	if (elements != null && elements.size() > 0) {
	    String txt = elements.get(0).absUrl("src");
	    logger.debug("Url pic : " + txt);

	    skin.setUrlImg(txt);
	}
	skin.setFatness(null);

	bl.add(skin);

	return bl;
    }

    /**
     * Depuis un texte, va extraire la taille de la peau
     *
     * @param txt
     * @return
     */
    private SkinSize extractSizeFromText(String txt) {
	SkinSize ss = null;

	List<String> dims = new ArrayList<>();
	List<String> units = new ArrayList<>();
	Matcher m = Pattern.compile("([0-9]+,?[0-9]*) ?(m|cm)").matcher(txt);
	while (m.find()) {
	    String group = m.group(1);
	    dims.add(group);
	    String group2 = m.group(2);
	    units.add(group2);
	}

	int i = 0;
	int nbMesures = units.size();
	if (nbMesures > 0) {
	    ss = new SkinSize();
	}

	for (String unit : units) {
	    String size = dims.get(i).trim().replaceAll(",", ".");
	    Double iSize = null;
	    try {
		if (unit.toLowerCase().indexOf("cm") == -1) {
		    iSize = Double.parseDouble(size) * 100;
		}else {
		    iSize = Double.parseDouble(size);
		}
	    } catch (Exception e) {
		logger.error("error parsing integer size : " + e.getMessage());
	    }

	    if (i == 0 && iSize != null) {
		ss.setDiameter(iSize);

	    } else if (i == nbMesures - 1 && iSize != null) {
		ss.setWidth(iSize);
	    }
	    i++;
	}

	return ss;
    }

    private EntitiesList parseFrame() {
	return null;
    }



}
