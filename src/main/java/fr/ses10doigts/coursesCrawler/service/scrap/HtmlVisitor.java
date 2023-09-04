package fr.ses10doigts.coursesCrawler.service.scrap;

public interface HtmlVisitor {
    void indexify(String url, String archiveBody);
}
