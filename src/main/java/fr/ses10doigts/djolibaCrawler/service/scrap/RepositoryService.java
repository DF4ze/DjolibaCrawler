package fr.ses10doigts.djolibaCrawler.service.scrap;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.ses10doigts.djolibaCrawler.model.scrap.AbstractEntity;
import fr.ses10doigts.djolibaCrawler.model.scrap.EntitiesList;
import fr.ses10doigts.djolibaCrawler.model.scrap.entity.Frame;
import fr.ses10doigts.djolibaCrawler.model.scrap.entity.Skin;
import fr.ses10doigts.djolibaCrawler.repository.scrap.FrameRepository;
import fr.ses10doigts.djolibaCrawler.repository.scrap.SkinRepository;

@Component
public class RepositoryService {

    @Autowired
    private SkinRepository	skinRepository;
    @Autowired
    private FrameRepository	frameRepository;

    private static final Logger	     logger = LoggerFactory.getLogger(RepositoryService.class);


    public void addAll(Collection<AbstractEntity> entities) {

	Collection<Skin> skins = new ArrayList<>();
	Collection<Frame> frames = new ArrayList<>();

	for (AbstractEntity entity : entities) {
	    if (entity instanceof Skin) {
		Skin a = (Skin) entity;
		skins.add(a);

	    } else if (entity instanceof Frame) {
		Frame new_name = (Frame) entity;
		frames.add(new_name);

	    }
	}
	try {
	    for (Skin skin : skins) {
		if (skin.getSku() != null) {
		    Skin exists = skinRepository.findBySku(skin.getSku());
		    if (exists != null && exists.isActif()) {
			exists.setDate(skin.getDate());
			exists.setAvailable(skin.getAvailable());
			exists.setPrice(skin.getPrice());
			exists.setUrl(skin.getUrl());
			exists.setName(skin.getName());
			exists.setAnimal(skin.getAnimal());
			exists.setSize(skin.getSize());
			exists.setSkinFormat(skin.getSkinFormat());
			exists.setUrlImg(skin.getUrlImg());

			skinRepository.save(exists);
		    } else {
			skin.setActif(true);
			skinRepository.save(skin);
		    }
		}
	    }


	} catch (Exception e) {
	    logger.error(e.getMessage());
	}
	try {
	    frameRepository.saveAll(frames);
	} catch (Exception e) {
	    logger.error(e.getMessage());
	}

    }

    public void addAll(EntitiesList list) {
	addAll(list.get());
    }


}
