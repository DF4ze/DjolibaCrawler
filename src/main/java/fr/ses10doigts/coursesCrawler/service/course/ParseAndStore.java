package fr.ses10doigts.coursesCrawler.service.course;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.ses10doigts.coursesCrawler.model.course.EntitiesList;

@Component
public class ParseAndStore implements HtmlVisitor {

    @Autowired
    RepositoryService repository;

    @Autowired
    HtmlParser parser;


    ParseAndStore() {
    }

    @Override
    public void indexify(String url, String archiveBody) {

	EntitiesList bl = parser.parse(url, archiveBody);

	if (bl != null && !bl.get().isEmpty()) {
	    repository.addAll(bl);
	}
    }
}
