package fr.ses10doigts.djolibaCrawler;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import fr.ses10doigts.djolibaCrawler.model.crawl.enumerate.Agressivity;
import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "fr.ses10doigts.crawler")
public class CustomCrawlProperties {

    private Integer	maxHop;
    private String	seedsFile;
    private String	authorizedFile;
    private Integer	maxRetry;
    private Agressivity	agressivity;
    private boolean	doCrawl;
    private boolean	doRefacto;



}
