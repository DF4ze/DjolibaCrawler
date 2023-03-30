package fr.ses10doigts.coursesCrawler.service.crawl.tool;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.springframework.stereotype.Service;

@Service
public class LineWriter {

    private String	filePath;



    public void StringToFile(String txt) throws IOException {

	if (filePath == null) {
	    throw new RuntimeException("FilePath not set");
	}

	Path path = Paths.get(filePath);
	if (txt == null || txt.trim().isEmpty()) {
	    new FileOutputStream(filePath).close();
	} else {
	    new FileOutputStream(filePath).close();
	    Files.writeString(path, txt, StandardOpenOption.CREATE);
	}

    }

    public String getFilePath() {
	return filePath;
    }

    public void setFilePath(String filePath) {
	this.filePath = filePath;
    }

}
