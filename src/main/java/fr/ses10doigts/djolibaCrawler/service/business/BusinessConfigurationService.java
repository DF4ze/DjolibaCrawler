package fr.ses10doigts.djolibaCrawler.service.business;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ses10doigts.djolibaCrawler.BusinessProperties;
import fr.ses10doigts.djolibaCrawler.CustomBusinessProperties;
import fr.ses10doigts.djolibaCrawler.service.scrap.tool.Chrono;

@Service
public class BusinessConfigurationService {
    @Autowired
    private CustomBusinessProperties	    props;

    private static CustomBusinessProperties configuration;
    private Chrono			    chrono;

    private static final Logger	     logger = LoggerFactory.getLogger(BusinessConfigurationService.class);


    public CustomBusinessProperties getBusinessConfiguration() {
	CustomBusinessProperties conf = null;

	try {
	    File file = new File(props.getConfPath());
	    if (!file.exists()) {

		conf = props;
		saveToFile(props);

	    } else {
		// pick chrono and update Readed conf
		BusinessProperties readed = null;
		if (chrono == null) {
		    chrono = new Chrono();
		    chrono.pick();
		    readed = readFromFile(props.getConfPath());
		}

		// if chrono delay passed : update reader
		if (chrono.compare() > 1000 * 60) {
		    readed = readFromFile(props.getConfPath());
		    chrono.pick();

		} else {// else we grab from memo
		    conf = configuration;
		}

		// if reader update : update conf and memo
		if (readed != null) {
		    conf = readed.cloneToCustom();
		    configuration = conf.cloneToCustom();
		}

		// if conf still null : load from property file and warn
		if (conf == null) {
		    conf = props;
		    logger.warn("Unable to load configuration from file");
		}
	    }
	} catch (Exception e) {
	    logger.warn("Error during manipulation of business conf file : " + e.getMessage());
	}
	return conf;
    }
    //
    //    @Deprecated
    //    private CustomBusinessProperties readPropertiesFromFile(LineReader reader) throws IOException {
    //	CustomBusinessProperties cbp = new CustomBusinessProperties();
    //
    //	Set<String> fileToSet = reader.fileToSet();
    //
    //	for (String string : fileToSet) {
    //	    String[] split = string.split("=");
    //	    if (split.length > 1) {
    //		if (split[0].equals("marge")) {
    //		    cbp.setMarge(Integer.parseInt(split[1]));
    //		} else if (split[0].equals("moBuild")) {
    //		    cbp.setMoBuild(Integer.parseInt(split[1]));
    //		} else if (split[0].equals("moFrame")) {
    //		    cbp.setMoFrame(Integer.parseInt(split[1]));
    //		} else if (split[0].equals("moSkin")) {
    //		    cbp.setMoSkin(Integer.parseInt(split[1]));
    //		} else if (split[0].equals("nbMaxFrame")) {
    //		    cbp.setNbMaxFrame(Integer.parseInt(split[1]));
    //		} else if (split[0].equals("skinBorder")) {
    //		    cbp.setSkinBorder(Integer.parseInt(split[1]));
    //		}
    //	    }
    //	}
    //
    //
    //	return cbp;
    //    }

    private BusinessProperties readFromFile(String path) {

	BusinessProperties objet = null;

	try (ObjectInputStream entree = new ObjectInputStream(new FileInputStream(path))) {
	    objet = (BusinessProperties) entree.readObject();
	} catch (IOException | ClassNotFoundException e) {
	    logger.error("Erreur de lecture du fichier de conf : " + e.getMessage());
	}

	return objet;

    }

    private boolean saveToFile(BusinessProperties cbp) {

	try (ObjectOutputStream sortie = new ObjectOutputStream(new FileOutputStream(cbp.getConfPath()))) {
	    sortie.writeObject(cbp.clone());
	} catch (IOException e) {
	    logger.error("Erreur d'écriture du fichier de conf : " + e.getMessage());
	    return false;
	}

	return true;
    }

    //    @Deprecated
    //    public void saveConfiguration(CustomBusinessProperties conf) throws IOException {
    //	writer.setFilePath(props.getConfPath());
    //
    //	String toW = "";
    //	toW += "marge=" + conf.getMarge() + "\n";
    //	toW += "moBuild=" + conf.getMoBuild() + "\n";
    //	toW += "moFrame=" + conf.getMoFrame() + "\n";
    //	toW += "moSkin=" + conf.getMoSkin() + "\n";
    //	toW += "nbMaxFrame=" + conf.getNbMaxFrame() + "\n";
    //	toW += "skinBorder=" + conf.getSkinBorder() + "\n";
    //
    //	writer.StringToFile(toW);
    //    }
}
