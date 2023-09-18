package fr.ses10doigts.djolibaCrawler.service.scrap;

import fr.ses10doigts.djolibaCrawler.model.scrap.EntitiesList;

public interface HtmlParser {


    public EntitiesList parse( String url, String body );


}
