package fr.ses10doigts.djolibaCrawler;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "fr.ses10doigts.business")
public class CustomBusinessProperties {

    private Integer moFrame;
    private Integer moSkin;
    private Integer moBuild;
    private Integer marge;

    private String  confPath;

    private Integer SkinBorder;
}

