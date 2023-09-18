package fr.ses10doigts.djolibaCrawler.service.business;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ses10doigts.djolibaCrawler.CustomBusinessProperties;
import fr.ses10doigts.djolibaCrawler.model.business.Drum;
import fr.ses10doigts.djolibaCrawler.model.scrap.entity.Frame;
import fr.ses10doigts.djolibaCrawler.model.scrap.entity.Skin;
import fr.ses10doigts.djolibaCrawler.model.scrap.entity.enumarate.SkinFormat;
import fr.ses10doigts.djolibaCrawler.model.scrap.entity.enumarate.WoodType;
import fr.ses10doigts.djolibaCrawler.repository.scrap.FrameRepository;
import fr.ses10doigts.djolibaCrawler.repository.scrap.SkinRepository;

@Service
public class BusinessService {

    @Autowired
    private FrameRepository	     frameRepository;
    @Autowired
    private SkinRepository	     skinRepository;
    @Autowired
    private CustomBusinessProperties props;



    public List<Skin> getAllActivSkins(String animal, Boolean available) {
	return skinRepository.findByActifAndAnimalAndAvailable(true, animal, available);
    }

    public List<Frame> getAllActivFrames(Integer size, WoodType woodType, Boolean available) {
	return frameRepository.findByActifAndSizeCmAndWoodTypeAndAvailable(true, size, woodType, available);
    }

    public List<String> getDistinctAnimal(String animal, Boolean available) {
	List<Skin> skins = getAllActivSkins(animal, available);
	Set<String> animals = new HashSet<>();
	// retrieve differents animals
	for (Skin skin : skins) {
	    animals.add(skin.getAnimal());
	}

	// sort
	List<String> arrayList = new ArrayList<String>(animals);
	Collections.sort(arrayList);

	return arrayList;
    }

    public List<Integer> getDistinctFrameSize(Integer size, WoodType woodType, Boolean available) {
	List<Frame> frames = getAllActivFrames(size, woodType, available);
	Set<Integer> sizes = new HashSet<>();
	for (Frame frame : frames) {
	    sizes.add(frame.getSizeCm());
	}

	List<Integer> arrayList = new ArrayList<Integer>(sizes);
	Collections.sort(arrayList);

	return arrayList;
    }

    public List<String> getDistinctFrameWood() {
	return Arrays.asList(getNames(WoodType.class));
    }

    private static String[] getNames(Class<? extends Enum<?>> e) {
	return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
    }

    public Drum makeDrum(Skin skin, Frame frame) {
	//	Skin skin = skinRepository.findBySku(skinSKU);
	//	if (skin == null) {
	//	    return null;
	//	}
	//
	//	Frame frame = frameRepository.findBySku(frameSKU);
	//	if (frame == null) {
	//	    return null;
	//	}

	Drum drum = new Drum();
	drum.setSkin(skin);
	drum.setFrame(frame);
	drum.setSkinRope(skin.getSkinFormat().equals(SkinFormat.FULLSKIN));

	Long skinFitFrame = skinFitFrame(skin, frame);
	drum.setAvailable(skin.getAvailable() && frame.getAvailable());
	drum.setNbFrameInSkin(skinFitFrame);

	drum = calculateAllPrices(drum);

	return drum;
    }

    private Long skinFitFrame(Skin skin, Frame frame) {
	Integer sizeFrame = frame.getSizeCm();
	Integer sizeNeeded = sizeFrame + props.getSkinBorder(); // adding border to the frame size

	Double ratioH = Math.floor(skin.getSize().getHeight() / sizeNeeded);
	Double ratioW = Math.floor(skin.getSize().getWidth() / sizeNeeded);


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

    public Map<String, List<Drum>> buildDrumsFromFilters(
	    String animal, Integer size, WoodType woodType, Boolean available
	    ) {

	List<Skin> skins = skinRepository.findByActifAndAnimalAndAvailable(true, animal, available);
	List<Frame> frames = frameRepository.findByActifAndSizeCmAndWoodTypeAndAvailable(true, size, woodType,
		available);

	Map<String, List<Drum>> result = new HashMap<>();
	for (Skin skin : skins) {
	    for (Frame frame : frames) {
		String key = skin.getSku()+frame.getSizeCm();
		List<Drum> drums;

		if( result.containsKey(skin.getSku()+frame.getSizeCm()) ) {
		    drums = result.get(key);
		} else {
		    drums = new ArrayList<>();
		}

		Drum drum = makeDrum(skin, frame);
		drums.add(drum);
		result.put(key, drums);
	    }
	}

	return result;
    }

}
