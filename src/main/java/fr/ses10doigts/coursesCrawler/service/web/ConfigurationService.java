package fr.ses10doigts.coursesCrawler.service.web;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.ses10doigts.coursesCrawler.CustomProperties;
import fr.ses10doigts.coursesCrawler.model.web.Configuration;
import fr.ses10doigts.coursesCrawler.service.crawl.tool.LineReader;
import fr.ses10doigts.coursesCrawler.service.crawl.tool.LineWriter;

@Component
public class ConfigurationService {
    @Autowired
    private LineReader	     reader;
    @Autowired
    private LineWriter	     writer;
    @Autowired
    private CustomProperties props;

    public Configuration getConfiguration() {
	Configuration conf = new Configuration();

	conf.setAgressivity(props.getAgressivity());
	conf.setMaxHop(props.getMaxHop());
	conf.setMaxRetry(props.getMaxRetry());
	conf.setLaunchCrawl(props.isDoCrawl());
	conf.setLaunchRefacto(props.isDoRefacto());

	try {
	    // Retrieve seeds
	    reader.setFilePath(props.getSeedsFile());
	    Set<String> urls = reader.fileToSet();
	    String txtUrl = "";
	    for (String url : urls) {
		txtUrl += url + "\n";
	    }
	    conf.setTxtSeeds(txtUrl);

	    // retrieve authorized words in url
	    reader.setFilePath(props.getAuthorizedFile());
	    Set<String> urlAuthorised = reader.fileToSet();
	    String txtAuth = "";
	    for (String url : urlAuthorised) {
		txtAuth += url + "\n";
	    }
	    conf.setAuthorized(txtAuth);

	} catch (Exception e) {

	}
	return conf;
    }

    public void saveConfiguration(Configuration dto) {
	try {
	    //	    // create and set properties into properties object
	    //	    Properties properties = new Properties();
	    //	    properties.setProperty("fr.ses10doigts.crawler.agressivity", dto.getAgressivity().name());
	    //	    properties.setProperty("fr.ses10doigts.crawler.maxHop", dto.getMaxHop() + "");
	    //	    properties.setProperty("fr.ses10doigts.crawler.maxRetry", dto.getMaxRetry() + "");
	    //	    properties.setProperty("fr.ses10doigts.crawler.doCrawl", Boolean.toString(dto.isLaunchCrawl()));
	    //	    properties.setProperty("fr.ses10doigts.crawler.doRefacto", Boolean.toString(dto.isLaunchRefacto()));
	    //
	    //	    // get or create the file
	    //	    File f = new File("application.properties");
	    //	    System.out.println(f.getAbsolutePath());
	    //	    //	    OutputStream out = new FileOutputStream(f);
	    //	    //	    // write into it
	    //	    //	    DefaultPropertiesPersister p = new DefaultPropertiesPersister();
	    //	    //	    p.store(properties, out, "Application own properties");
	    //	    FileWriter fw = new FileWriter(f);
	    //	    properties.store(fw, "Application's own properties");

	    props.setAgressivity(dto.getAgressivity());
	    props.setMaxHop(dto.getMaxHop());
	    props.setMaxRetry(dto.getMaxRetry());
	    props.setDoCrawl(dto.isLaunchCrawl());
	    props.setDoRefacto(dto.isLaunchRefacto());

	    writer.setFilePath(props.getSeedsFile());
	    writer.StringToFile(dto.getTxtSeeds());

	    writer.setFilePath(props.getAuthorizedFile());
	    writer.StringToFile(dto.getAuthorized());

	} catch (Exception e) {
	    e.printStackTrace();
	}

    }
}
