package fr.ses10doigts.coursesCrawler.service.course;

public interface HtmlVisitor {
    void indexify(String url, String archiveBody);
}
