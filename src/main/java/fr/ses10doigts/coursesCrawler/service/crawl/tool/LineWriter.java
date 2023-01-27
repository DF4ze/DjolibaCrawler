package fr.ses10doigts.coursesCrawler.service.crawl.tool;

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
	Files.writeString(path, txt, StandardOpenOption.TRUNCATE_EXISTING);

    }

    public String getFilePath() {
	return filePath;
    }

    public void setFilePath(String filePath) {
	this.filePath = filePath;
    }

}
