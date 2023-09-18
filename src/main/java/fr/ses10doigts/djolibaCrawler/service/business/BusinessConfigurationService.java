package fr.ses10doigts.djolibaCrawler.service.business;

import java.io.IOException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ses10doigts.djolibaCrawler.CustomBusinessProperties;
import fr.ses10doigts.djolibaCrawler.service.crawl.tool.LineReader;
import fr.ses10doigts.djolibaCrawler.service.crawl.tool.LineWriter;

@Service
public class BusinessConfigurationService {
    @Autowired
    private CustomBusinessProperties props;
    @Autowired
    private LineReader		     reader;
    @Autowired
    private LineWriter		     writer;

    public CustomBusinessProperties getBusinessConfiguration() {
	CustomBusinessProperties conf = new CustomBusinessProperties();

	try {
	    if (!reader.setFilePath(props.getConfPath())) {

		conf.setMarge(props.getMarge());
		conf.setMoBuild(props.getMoBuild());
		conf.setMoFrame(props.getMoFrame());
		conf.setMoSkin(props.getMoSkin());

	    } else {
		conf = readPropertiesFromFile(reader);
	    }
	} catch (Exception e) {
	    // TODO: handle exception
	}
	return conf;
    }

    private CustomBusinessProperties readPropertiesFromFile(LineReader reader) throws IOException {
	CustomBusinessProperties cbp = new CustomBusinessProperties();

	Set<String> fileToSet = reader.fileToSet();

	for (String string : fileToSet) {
	    String[] split = string.split("=");
	    if (split.length > 1) {
		if (split[0].equals("marge")) {
		    cbp.setMarge(Integer.parseInt(split[1]));
		} else if (split[0].equals("moBuild")) {
		    cbp.setMoBuild(Integer.parseInt(split[1]));
		} else if (split[0].equals("moFrame")) {
		    cbp.setMoFrame(Integer.parseInt(split[1]));
		} else if (split[0].equals("moSkin")) {
		    cbp.setMoSkin(Integer.parseInt(split[1]));
		}
	    }
	}

	return cbp;
    }

    public void saveConfiguration(CustomBusinessProperties conf) throws IOException {
	writer.setFilePath(props.getConfPath());

	String toW = "";
	toW += "marge=" + conf.getMarge() + "\n";
	toW += "moBuild=" + conf.getMoBuild() + "\n";
	toW += "moFrame=" + conf.getMoFrame() + "\n";
	toW += "moSkin=" + conf.getMoSkin() + "\n";

	writer.StringToFile(toW);
    }
}
