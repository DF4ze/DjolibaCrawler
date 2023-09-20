package fr.ses10doigts.djolibaCrawler.model.business.entity;

import fr.ses10doigts.djolibaCrawler.model.scrap.entity.Frame;
import fr.ses10doigts.djolibaCrawler.model.scrap.entity.Skin;
import lombok.Data;

@Data
public class Drum {

    private Skin    skin;
    private Frame   frame;

    private Boolean available;
    private Long    nbFrameInSkin;
    private Boolean skinRope;

    private Double  priceBrut;
    private Double  priceBrutMoSkinMoFrame;
    private Double  priceBrutAllMo;

    private Double  priceWorkShop;
    private Double  priceBuild;
}
