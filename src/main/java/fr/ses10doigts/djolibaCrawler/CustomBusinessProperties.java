package fr.ses10doigts.djolibaCrawler;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;



@Configuration
@ConfigurationProperties(prefix = "fr.ses10doigts.business")
public class CustomBusinessProperties extends BusinessProperties {

    private static final long serialVersionUID = 1L;


}

