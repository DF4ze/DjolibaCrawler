package fr.ses10doigts.djolibaCrawler.service.scrap;

import fr.ses10doigts.djolibaCrawler.model.scrap.EntitiesList;

public interface HtmlParser {


    public EntitiesList parse( String url, String body );

    Long parse_numCourse();

    EntitiesList parse_course();
    EntitiesList parse_rapport();
    EntitiesList parse_arrivee();
    EntitiesList parse_cote();
    EntitiesList parse_partant();

}
