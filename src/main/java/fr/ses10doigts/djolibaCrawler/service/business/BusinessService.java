package fr.ses10doigts.djolibaCrawler.service.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ses10doigts.djolibaCrawler.model.business.Drum;
import fr.ses10doigts.djolibaCrawler.model.scrap.entity.Frame;
import fr.ses10doigts.djolibaCrawler.model.scrap.entity.Skin;
import fr.ses10doigts.djolibaCrawler.model.scrap.entity.enumarate.SkinFormat;
import fr.ses10doigts.djolibaCrawler.repository.scrap.FrameRepository;
import fr.ses10doigts.djolibaCrawler.repository.scrap.SkinRepository;
import fr.ses10doigts.djolibaCrawler.service.CustomBusinessProperties;

@Service
public class BusinessService {

    @Autowired
    private FrameRepository	     frameRepository;
    @Autowired
    private SkinRepository	     skinRepository;
    @Autowired
    private CustomBusinessProperties props;


    public Drum makeDrum(String skinSKU, String frameSKU) {
	Skin skin = skinRepository.findBySku(skinSKU);
	if (skin == null) {
	    return null;
	}

	Frame frame = frameRepository.findBySku(frameSKU);
	if (frame == null) {
	    return null;
	}

	Drum drum = new Drum();
	drum.setSkin(skin);
	drum.setFrame(frame);
	drum.setSkinRope(skin.getSkinFormat().equals(SkinFormat.FULLSKIN));

	Long skinFitFrame = skinFitFrame(skin, frame);
	drum.setFonctionnal(skinFitFrame > 0);
	drum.setNbFrameInSkin(skinFitFrame);

	drum = calculateAllPrices(drum);

	return drum;
    }

    private Long skinFitFrame(Skin skin, Frame frame) {
	Integer sizeFrame = frame.getSizeCm();
	Integer sizeNeeded = sizeFrame + 15; // adding border to the frame size

	Double ratioH = skin.getSize().getHeight() / sizeNeeded;
	Double ratioW = skin.getSize().getWidth() / sizeNeeded;


	long ratioHR = Math.round(ratioH);
	long ratioWR = Math.round(ratioW);

	return ratioHR * ratioWR;

    }


    private Drum calculateAllPrices( Drum drum ) {
	Long inSkin = drum.getNbFrameInSkin();
	if (inSkin == null || inSkin < 1) {
	    inSkin = 1l;
	}

	Double priceBrut = drum.getSkin().getPrice() / inSkin + drum.getFrame().getPrice();
	Double priceBrutMoSkinMoFrame = priceBrut + props.getMoFrame() + props.getMoSkin();
	Double priceBrutAllMo = priceBrutMoSkinMoFrame + props.getMoBuild();

	Double priceWorkShop = priceBrutMoSkinMoFrame + (props.getMarge() * priceBrutMoSkinMoFrame / 100);
	Double priceBuild = priceBrutAllMo + (props.getMarge() * priceBrutAllMo / 100);

	drum.setPriceBrut(priceBrut);
	drum.setPriceBrutMoSkinMoFrame(priceBrutMoSkinMoFrame);
	drum.setPriceBrutAllMo(priceBrutAllMo);
	drum.setPriceWorkShop(priceWorkShop);
	drum.setPriceBuild(priceBuild);

	return drum;
    }


}
