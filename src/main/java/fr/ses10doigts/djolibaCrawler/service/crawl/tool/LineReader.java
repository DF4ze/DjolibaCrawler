package fr.ses10doigts.djolibaCrawler.service.crawl.tool;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class LineReader {

    private String	filePath;



    public Set<String> fileToSet() throws IOException {

	if (filePath == null) {
	    throw new RuntimeException("FilePath not set");
	}

	Path path = Paths.get(filePath);
	List<String> lines = Files.readAllLines(path);

	Set<String> urlList = new HashSet<>();
	for (String url : lines) {
	    if (!url.isBlank()) {
		urlList.add(url);
	    }
	}

	return urlList;
    }

    public String getFilePath() {
	return filePath;
    }

    public void setFilePath(String filePath) {
	this.filePath = filePath;
    }

}
