package fr.ses10doigts.coursesCrawler.service.course;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.ses10doigts.coursesCrawler.model.course.AbstractEntity;
import fr.ses10doigts.coursesCrawler.model.course.entity.Arrivee;
import fr.ses10doigts.coursesCrawler.model.course.entity.Cote;
import fr.ses10doigts.coursesCrawler.model.course.entity.Course;
import fr.ses10doigts.coursesCrawler.model.course.entity.CourseComplete;
import fr.ses10doigts.coursesCrawler.model.course.entity.Partant;
import fr.ses10doigts.coursesCrawler.model.course.entity.Rapport;
import fr.ses10doigts.coursesCrawler.repository.course.ArriveeRepository;
import fr.ses10doigts.coursesCrawler.repository.course.CoteRepository;
import fr.ses10doigts.coursesCrawler.repository.course.CourseRepository;
import fr.ses10doigts.coursesCrawler.repository.course.PartantRepository;
import fr.ses10doigts.coursesCrawler.repository.course.RapportRepository;
import fr.ses10doigts.coursesCrawler.service.course.tool.Chrono;

@Component
public class Refactorer implements Runnable {

    @Autowired
    private transient ArriveeRepository	arriveeRepository;
    @Autowired
    private transient CoteRepository	coteRepository;
    @Autowired
    private transient PartantRepository partantRepository;
    @Autowired
    private transient RapportRepository rapportRepository;
    @Autowired
    private transient CourseRepository	courseRepository;
    @Autowired
    private transient RepositoryService repository;

    private static final Logger		logger	  = LoggerFactory.getLogger(Refactorer.class);
    private transient final int		cycleStep = 50;
    private static Long			from	  = null;
    private static boolean		running	  = true;
    private Thread			friend;

    public static void setFrom(Long from) {
	Refactorer.from = from;
    }

    public Refactorer() {
    }

    public void makeCourseComplete(Long fromCourseId) {
	from = fromCourseId;
	makeCourseComplete();
    }

    @Override
    public void run() {
	try {
	    if (friend != null) {
		friend.join();
	    }
	    makeCourseComplete();

	} catch (InterruptedException e) {
	    logger.error("Refactorer thread failed to wait for Crawler. Refactoring won't be done");
	}

    }

    public void makeCourseComplete() {

	List<Course> coursesList = null;

	int stepDone = 1;
	// long lastCourse = from;

	Chrono stepChrono = new Chrono();

	while (stepDone != 0) {
	    stepDone = 0;
	    stepChrono.pick();

	    // from = lastCourse;
	    Collection<AbstractEntity> computedBuffer = new ArrayList<>();
	    if (from != null) {
		logger.info("Starting from id " + from);
		coursesList = courseRepository.findAllFrom(from);
	    } else {
		coursesList = courseRepository.findAll();
	    }

	    for (Course course : coursesList) {
		if (!running) {
		    return;
		}

		CourseComplete cc = new CourseComplete();

		///////////////////////////////////
		// Infos Course
		cc.setCourseID(course.getId());
		cc.setDateCourse(course.getDate());
		cc.setNumeroReunion(course.getReunion());
		cc.setNumeroCourse(course.getCourse());
		cc.setHippodrome(course.getHippodrome());
		cc.setPrime(course.getPrime());
		cc.setTypeCourse(course.getType());
		cc.setAutoStart(course.getDepart());

		////////////////////////////////////
		// Infos Rapport
		Chrono actChrono = new Chrono();
		actChrono.pick();
		Set<Rapport> rapportsList = rapportRepository.findByCourseID(course.getId());
		logger.debug("Read rapport in " + actChrono.compare());


		if (rapportsList.size() == 0) {
		    continue;
		}

		actChrono.pick();
		for (Rapport unRap : rapportsList) {
		    if (!running) {
			return;
		    }

		    if (unRap.getArrivee() == 1) {
			cc.setNumeroChvlPremier(unRap.getNumCheval());
			cc.setRapGagnant(unRap.getGagnant());
			cc.setRapPlacePremier(unRap.getPlace());

		    } else if (unRap.getArrivee() == 2) {
			cc.setNumeroChvlDeuxieme(unRap.getNumCheval());
			cc.setRapPlaceDeuxieme(unRap.getPlace());

		    } else if (unRap.getArrivee() == 3) {
			cc.setNumeroChvlTroisieme(unRap.getNumCheval());
			cc.setRapPlaceTroisieme(unRap.getPlace());

		    }

		}
		rapportsList.clear();
		rapportsList = null;
		logger.debug("Rapport compute in " + actChrono.compare());
		if (!running) {
		    return;
		}

		////////////////////////////////////
		// Infos Cote
		Cote coteFavPreum = null;
		Cote coteFavDeuz = null;
		Cote coteFavTroiz = null;

		Cote cotePrCtPreum = null;
		Cote cotePrCtDeuz = null;
		Cote cotePrCtTroiz = null;

		actChrono.pick();
		Set<Cote> cotesList = coteRepository.findByCourseID(course.getId());
		logger.debug("Read cote in " + actChrono.compare());

		if (cotesList.size() == 0) {
		    continue;
		}

		actChrono.pick();
		cc.setNombrePartant(cotesList.size());

		// On recupere les 3 favoris : cote < 5
		// et les 3 meilleurs pourcentage
		int nbCoteInf5 = 0;
		for (Cote uneCote : cotesList) {
		    if (!running) {
			return;
		    }

		    if (uneCote.getCoteDepart() < 5) {
			nbCoteInf5++;
		    }
		    if (coteFavPreum == null) {
			coteFavPreum = uneCote; // Cloner??????????????
		    } else {

			if (coteFavPreum.getCoteDepart() > uneCote.getCoteDepart()) {
			    coteFavTroiz = coteFavDeuz;
			    coteFavDeuz = coteFavPreum;
			    coteFavPreum = uneCote;

			} else if (coteFavDeuz == null) {
			    coteFavDeuz = uneCote;

			} else {
			    if (coteFavDeuz.getCoteDepart() > uneCote.getCoteDepart()) {
				coteFavTroiz = coteFavDeuz;
				coteFavDeuz = uneCote;

			    } else if (coteFavTroiz == null) {
				coteFavTroiz = uneCote;

			    } else if (coteFavTroiz.getCoteDepart() > uneCote.getCoteDepart()) {
				coteFavTroiz = uneCote;
			    }
			}
		    }

		    if (cotePrCtPreum == null) {
			cotePrCtPreum = uneCote; // Cloner??????????????
		    } else {

			if (cotePrCtPreum.getEnjeuxDepart() < uneCote.getEnjeuxDepart()) {
			    cotePrCtTroiz = cotePrCtDeuz;
			    cotePrCtDeuz = cotePrCtPreum;
			    cotePrCtPreum = uneCote;

			} else if (cotePrCtDeuz == null) {
			    cotePrCtDeuz = uneCote;

			} else {
			    if (cotePrCtDeuz.getEnjeuxDepart() < uneCote.getEnjeuxDepart()) {
				cotePrCtTroiz = cotePrCtDeuz;
				cotePrCtDeuz = uneCote;

			    } else if (cotePrCtTroiz == null) {
				cotePrCtTroiz = uneCote;

			    } else if (cotePrCtTroiz.getEnjeuxDepart() < uneCote.getEnjeuxDepart()) {
				cotePrCtTroiz = uneCote;
			    }
			}
		    }

		}
		cotesList.clear();
		cotesList = null;

		cc.setNombreChevauxInfCinq(nbCoteInf5);

		if (coteFavPreum != null) {
		    cc.setNumeroPremierFavori(coteFavPreum.getNumCheval());

		}
		if (coteFavDeuz != null) {
		    cc.setNumeroDeuxiemeFavori(coteFavDeuz.getNumCheval());

		}
		if (coteFavTroiz != null) {
		    cc.setNumeroTroisiemeFavori(coteFavTroiz.getNumCheval());

		}

		cc.setPourcentPremierFavori((cotePrCtPreum == null ? null : cotePrCtPreum.getEnjeuxDepart()));
		cc.setPourcentDeuxiemeFavori((cotePrCtDeuz == null ? null : cotePrCtDeuz.getEnjeuxDepart()));
		cc.setPourcentTroisiemeFavori((cotePrCtTroiz == null ? null : cotePrCtTroiz.getEnjeuxDepart()));

		// calcul de la somme des enjeux/prcent
		Float somPrCent = (cotePrCtPreum == null ? 0f : cotePrCtPreum.getEnjeuxDepart())
			+ (cotePrCtDeuz == null ? 0f : cotePrCtDeuz.getEnjeuxDepart())
			+ (cotePrCtTroiz == null ? 0f : cotePrCtTroiz.getEnjeuxDepart());
		cc.setTotalPourcent(somPrCent);

		// nb chv cote < 5 arrivé placé => dans les 3 1er
		int nbInf5Place = 0;

		if (coteFavPreum != null) {
		    if (coteFavPreum.getNumCheval() == cc.getNumeroChvlPremier()
			    || coteFavPreum.getNumCheval() == cc.getNumeroChvlDeuxieme()
			    || coteFavPreum.getNumCheval() == cc.getNumeroChvlTroisieme()) {
			nbInf5Place++;
		    }
		}

		if (coteFavDeuz != null) {
		    if (coteFavDeuz.getNumCheval() == cc.getNumeroChvlPremier()
			    || coteFavDeuz.getNumCheval() == cc.getNumeroChvlDeuxieme()
			    || coteFavDeuz.getNumCheval() == cc.getNumeroChvlTroisieme()) {
			nbInf5Place++;
		    }
		}

		if (coteFavTroiz != null) {
		    if (coteFavTroiz.getNumCheval() == cc.getNumeroChvlPremier()
			    || coteFavTroiz.getNumCheval() == cc.getNumeroChvlDeuxieme()
			    || coteFavTroiz.getNumCheval() == cc.getNumeroChvlTroisieme()) {
			nbInf5Place++;
		    }
		}

		// if( coteFavPreum.getNumCheval() == cc.getNumeroChvlPremier()
		// ||
		// coteFavDeuz.getNumCheval() == cc.getNumeroChvlPremier() ||
		// coteFavTroiz.getNumCheval() == cc.getNumeroChvlPremier() )
		// nbInf5Place ++;
		// if( coteFavPreum.getNumCheval() == cc.getNumeroChvlDeuxieme()
		// ||
		// coteFavDeuz.getNumCheval() == cc.getNumeroChvlDeuxieme() ||
		// coteFavTroiz.getNumCheval() == cc.getNumeroChvlDeuxieme() )
		// nbInf5Place ++;
		// if( coteFavPreum.getNumCheval() ==
		// cc.getNumeroChvlTroisieme() ||
		// coteFavDeuz.getNumCheval() == cc.getNumeroChvlTroisieme() ||
		// coteFavTroiz.getNumCheval() == cc.getNumeroChvlTroisieme() )
		// nbInf5Place ++;
		cc.setNombreChvlFavoriPlace(nbInf5Place);

		logger.debug("Cote compute in " + actChrono.compare());



		///////////////////////////
		// Arrivees
		actChrono.pick();
		Set<Arrivee> arrivees = arriveeRepository.findByCourseID(course.getId());
		logger.debug("Read arrivee in " + actChrono.compare());

		actChrono.pick();
		Map<Integer, String> resultats = getNomFromPlace(arrivees);

		try {
		    cc.setP1er(resultats.get(1));
		    cc.setP2eme(resultats.get(2));
		    cc.setP3eme(resultats.get(3));
		    cc.setP4eme(resultats.get(4));
		    cc.setP5eme(resultats.get(5));
		    cc.setP6eme(resultats.get(6));
		    cc.setP7eme(resultats.get(7));
		    cc.setP8eme(resultats.get(8));
		    cc.setP9eme(resultats.get(9));
		    cc.setP10eme(resultats.get(10));
		    cc.setP11eme(resultats.get(11));
		    cc.setP12eme(resultats.get(12));
		    cc.setP13eme(resultats.get(13));
		    cc.setP14eme(resultats.get(14));
		    cc.setP15eme(resultats.get(15));
		} catch (Exception e2) {

		}
		resultats.clear();
		resultats = null;
		logger.debug("Arrivee compute in " + actChrono.compare());

		//////////////////////////////
		// Info partant
		int ageMin = 30;
		int ageMax = 0;

		actChrono.pick();
		Set<Partant> partantsListe = partantRepository.findByCourseID(course.getId());
		logger.debug("Read partant in " + actChrono.compare());

		actChrono.pick();
		Partant ChvBestGains = null;
		if (partantsListe.size() != 0) {
		    // on fait le tour de tout les partant de cette course
		    for (Partant unPart : partantsListe) {
			if (!running) {
			    return;
			}

			// si le numero du cheval en cours est celui du 1er
			// favoris
			if (unPart.getNumCheval() == cc.getNumeroPremierFavori()) {
			    cc.setMusiquePremier(unPart.getMusique());
			    // cc.setAgeSexChvlPremier(unPart.getAgeSexe());
			    cc.setNomChvlPremier(unPart.getNomCheval());
			    // cc.setGainsPremier(unPart.getGains());
			    // break;
			}

			if (unPart.getNomCheval().equals(cc.getP1er())) {
			    cc.setGainChvlPremier(unPart.getiGains());
			}

			// min et max age
			try {
			    String text = unPart.getAgeSexe().replace("F", "");
			    text = text.replace("H", "");
			    text = text.replace("M", "");

			    int age = Integer.parseInt(text);

			    if (age > ageMax) {
				ageMax = age;
			    }
			    if (age < ageMin) {
				ageMin = age;
			    }

			} catch (Exception e) {
			    String texte = "Pb parse Age (" + unPart.getAgeSexe() + "), course " + course.getId()
			    + " : " + e.getMessage();
			    logger.error(texte);

			}

			// musique du cheval qui a le plus gros gain
			if (ChvBestGains == null && unPart.getiGains() != null) {

			    ChvBestGains = unPart.clone();
			} else if (unPart.getiGains() != null) {
			    if (unPart.getiGains() > ChvBestGains.getiGains()) {
				ChvBestGains = unPart.clone();
			    }
			}

		    }
		    cc.setAgeSexChvlPremier(ageMin + "-" + ageMax);
		    if (ChvBestGains != null) {
			cc.setMusiqueMeilleurGains(ChvBestGains.getMusique());
			cc.setNumeroMeilleurGains(ChvBestGains.getNumCheval());
		    }
		}
		partantsListe.clear();
		partantsListe = null;
		logger.debug("Partants compute in " + actChrono.compare());

		///////////////////////////
		// envoi BDD
		//repository.add(cc);
		computedBuffer.add(cc);
		logger.debug("Computed ok for courseID : " + course.getId());

		cc = null;

		stepDone++;
		if (stepDone > cycleStep) {
		    from = course.getCourseID();
		    break;
		}
	    }

	    if (!running) {
		return;
	    }
	    repository.addAll(computedBuffer);
	    computedBuffer.clear();

	    logger.info("Computed saved");
	    long time = stepChrono.compare();

	    logger.debug(cycleStep + " steps in " + (time / cycleStep) + "ms / step");


	    coursesList.clear();
	    coursesList = null;
	    System.gc();
	    try {
		Thread.sleep(50);
	    } catch (InterruptedException e) {
	    }

	    if( stepDone < cycleStep ) {
		break;
	    }

	}

    }

    private static Map<Integer, String> getNomFromPlace(Set<Arrivee> arrivees) {
	Map<Integer, String> ret = new HashMap<Integer, String>();

	for (Arrivee arrivee : arrivees) {
	    ret.put(arrivee.getNumArrivee(), arrivee.getNomChv());
	}

	return ret;
    }

    public void stop() {
	running = false;

    }

    public void setFriend(Thread t) {
	friend = t;
    }

}
