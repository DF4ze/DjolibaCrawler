package fr.ses10doigts.djolibaCrawler.service.scrap;

public interface HtmlVisitor {
    void indexify(String url, String archiveBody);
}
