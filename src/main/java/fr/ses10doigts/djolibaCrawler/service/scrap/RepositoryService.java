package fr.ses10doigts.djolibaCrawler.service.scrap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import fr.ses10doigts.djolibaCrawler.model.scrap.AbstractEntity;
import fr.ses10doigts.djolibaCrawler.model.scrap.EntitiesList;
import fr.ses10doigts.djolibaCrawler.model.scrap.entity.Arrivee;
import fr.ses10doigts.djolibaCrawler.model.scrap.entity.Cote;
import fr.ses10doigts.djolibaCrawler.model.scrap.entity.Course;
import fr.ses10doigts.djolibaCrawler.model.scrap.entity.CourseComplete;
import fr.ses10doigts.djolibaCrawler.model.scrap.entity.Partant;
import fr.ses10doigts.djolibaCrawler.model.scrap.entity.Rapport;
import fr.ses10doigts.djolibaCrawler.repository.course.ArriveeRepository;
import fr.ses10doigts.djolibaCrawler.repository.course.CoteRepository;
import fr.ses10doigts.djolibaCrawler.repository.course.CourseCompleteRepository;
import fr.ses10doigts.djolibaCrawler.repository.course.CourseRepository;
import fr.ses10doigts.djolibaCrawler.repository.course.PartantRepository;
import fr.ses10doigts.djolibaCrawler.repository.course.RapportRepository;

@Component
public class RepositoryService {

    @Autowired
    private ArriveeRepository	     arriveeRepository;
    @Autowired
    private CoteRepository	     coteRepository;
    @Autowired
    private CourseCompleteRepository courseCompleteRepository;
    @Autowired
    private CourseRepository	     courseRepository;
    @Autowired
    private PartantRepository	     partantRepository;
    @Autowired
    private RapportRepository	     rapportRepository;

    private static final Logger	     logger = LoggerFactory.getLogger(RepositoryService.class);


    public void addAll(Collection<AbstractEntity> entities) {

	Collection<Arrivee> arrivees = new ArrayList<>();
	Collection<Cote> cotes = new ArrayList<>();
	Collection<CourseComplete> coursesCompletes = new ArrayList<>();
	Collection<Course> courses = new ArrayList<>();
	Collection<Partant> partants = new ArrayList<>();
	Collection<Rapport> rapports = new ArrayList<>();
	for (AbstractEntity entity : entities) {
	    if (entity instanceof Arrivee) {
		Arrivee a = (Arrivee) entity;
		arrivees.add(a);

	    } else if (entity instanceof Cote) {
		Cote new_name = (Cote) entity;
		cotes.add(new_name);

	    } else if (entity instanceof CourseComplete) {
		CourseComplete new_name = (CourseComplete) entity;
		coursesCompletes.add(new_name);

	    } else if (entity instanceof Course) {
		Course new_name = (Course) entity;
		courses.add(new_name);

	    } else if (entity instanceof Partant) {
		Partant new_name = (Partant) entity;
		partants.add(new_name);

	    } else if (entity instanceof Rapport) {
		Rapport new_name = (Rapport) entity;
		rapports.add(new_name);

	    }
	}
	try {
	    arriveeRepository.saveAll(arrivees);
	} catch (/*DataIntegrityViolationException*/ Exception e) {
	    logger.error(e.getMessage());
	}
	try {
	    coteRepository.saveAll(cotes);
	} catch (Exception e) {
	    logger.error(e.getMessage());
	}
	try {
	    courseCompleteRepository.saveAll(coursesCompletes);
	} catch (Exception e) {
	    logger.error(e.getMessage());
	}
	try {
	    courseRepository.saveAll(courses);
	} catch (Exception e) {
	    logger.error(e.getMessage());
	}
	try {
	    partantRepository.saveAll(partants);
	} catch (Exception e) {
	    logger.error(e.getMessage());
	}
	try {
	    rapportRepository.saveAll(rapports);
	} catch (Exception e) {
	    logger.error(e.getMessage());
	}
    }

    public void addAll(EntitiesList list) {
	addAll(list.get());
    }

    @Transactional
    public List<?> listAll(Class<?> clazz) {
	List<?> list = new ArrayList<>();

	if (clazz.isAssignableFrom(Arrivee.class)) {
	    list = arriveeRepository.findAll();

	} else if (clazz.isAssignableFrom(Cote.class)) {
	    list = coteRepository.findAll();

	} else if (clazz.isAssignableFrom(CourseComplete.class)) {
	    list = courseCompleteRepository.findAll();

	} else if (clazz.isAssignableFrom(Partant.class)) {
	    list = partantRepository.findAll();

	} else if (clazz.isAssignableFrom(Course.class)) {
	    list = courseRepository.findAll();

	} else if (clazz.isAssignableFrom(Rapport.class)) {
	    list = rapportRepository.findAll();

	}

	return list;
    }

}
