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
import fr.ses10doigts.coursesCrawler.service.course.tool.RefactorerReport;

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
    private RefactorerReport		report	  = RefactorerReport.getInstance();
    private Chrono			chrono;

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
		logger.debug("Refactorer waiting for crawl to end");
		friend.join();
	    }
	    chrono = new Chrono();
	    chrono.pick();
	    makeCourseComplete();

	} catch (InterruptedException e) {
	    logger.error("Refactorer thread failed to wait for Crawler. Refactoring won't be done");
	}

    }

    public void makeCourseComplete() {
	if (!running) {
	    return;
	}

	logger.debug("************************* Refactorer start");
	report.startRefacto();

	List<Course> coursesList = null;

	int stepDone = 0;
	// long lastCourse = from;

	Chrono stepChrono = new Chrono();

	//	while (stepDone != 0) {
	//	    stepDone = 0;
	stepChrono.pick();

	// from = lastCourse;
	Collection<AbstractEntity> computedBuffer = new ArrayList<>();
	if (from != null) {
	    logger.info("Starting from id " + from);
	    coursesList = courseRepository.findAllFrom(from);
	} else {
	    coursesList = courseRepository.findAll();
	}

	report.setTotalCourse(coursesList.size());

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
	    Set<Rapport> rapportsList = rapportRepository.findByCourseID(course.getId());


	    if (rapportsList.size() == 0) {
		logger.debug("Skip => Missing rapport for " + course.getId());
		report.addSkipped(1);
		continue;
	    }


	    for (Rapport unRap : rapportsList) {
		if (!running) {
		    return;
		}

		if (unRap.getArrivee() == 1) {
		    cc.setNumeroChvlPremier(unRap.getNumCheval());
		    cc.setRapGagnantGeny(unRap.getGagnantGeny());
		    cc.setRapPlacePremierGeny(unRap.getPlaceGeny());
		    cc.setRapGagnantPmu(unRap.getGagnantPmu());
		    cc.setRapPlacePremierPmu(unRap.getPlacePmu());

		} else if (unRap.getArrivee() == 2) {
		    cc.setNumeroChvlDeuxieme(unRap.getNumCheval());
		    cc.setRapPlaceDeuxiemeGeny(unRap.getPlaceGeny());
		    cc.setRapPlaceDeuxiemePmu(unRap.getPlacePmu());


		} else if (unRap.getArrivee() == 3) {
		    cc.setNumeroChvlTroisieme(unRap.getNumCheval());
		    cc.setRapPlaceTroisiemeGeny(unRap.getPlaceGeny());
		    cc.setRapPlaceTroisiemePmu(unRap.getPlacePmu());


		}

	    }
	    rapportsList.clear();
	    rapportsList = null;
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

	    Set<Cote> cotesList = coteRepository.findByCourseID(course.getId());

	    if (cotesList.size() == 0) {
		logger.debug("Skip => Missing cote for " + course.getId());
		report.addSkipped(1);
		continue;
	    }

	    cc.setNombrePartant(cotesList.size());

	    // On recupere les 3 favoris : cote < 5
	    // et les 3 meilleurs pourcentage
	    int nbCoteInf5 = 0;
	    for (Cote uneCote : cotesList) {
		if (!running) {
		    return;
		}

		// Gestion des cote avant/départ
		if (uneCote.getNumCheval().equals(cc.getNumeroChvlPremier())) {
		    cc.setCotePremierAvant(uneCote.getCoteAvant());
		    cc.setCotePremierDepart(uneCote.getCoteDepart());
		    cc.setPourcentPremierAvant(uneCote.getEnjeuxAvant());
		    cc.setPourcentPremierDepart(uneCote.getEnjeuxDepart());

		} else if (uneCote.getNumCheval().equals(cc.getNumeroChvlDeuxieme())) {
		    cc.setCoteDeuxiemeAvant(uneCote.getCoteAvant());
		    cc.setCoteDeuxiemeDepart(uneCote.getCoteDepart());
		    cc.setPourcentDeuxiemeAvant(uneCote.getEnjeuxAvant());
		    cc.setPourcentDeuxiemeDepart(uneCote.getEnjeuxDepart());

		} else if (uneCote.getNumCheval().equals(cc.getNumeroChvlTroisieme())) {
		    cc.setCoteTroisiemeAvant(uneCote.getCoteAvant());
		    cc.setCoteTroisiemeDepart(uneCote.getCoteDepart());
		    cc.setPourcentTroisiemeAvant(uneCote.getEnjeuxAvant());
		    cc.setPourcentTroisiemeDepart(uneCote.getEnjeuxDepart());
		}

		if (uneCote.getCoteDepart() < 5) {
		    nbCoteInf5++;
		}
		if (coteFavPreum == null) {
		    coteFavPreum = uneCote;
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
		    cotePrCtPreum = uneCote;
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

	    cc.setNombreChvlFavoriPlace(nbInf5Place);




	    ///////////////////////////
	    // Arrivees
	    Set<Arrivee> arrivees = arriveeRepository.findByCourseID(course.getId());

	    if (arrivees.isEmpty()) {
		logger.debug("Skip => Missing arrivee for " + course.getId());
		report.addSkipped(1);
		continue;
	    }

	    Map<Integer, String> resultats = getNomFromPlace(arrivees);

	    try {
		cc.setP1er(resultats.get(1));
		//		    cc.setP2eme(resultats.get(2));
		//		    cc.setP3eme(resultats.get(3));
		//		    cc.setP4eme(resultats.get(4));
		//		    cc.setP5eme(resultats.get(5));
		//		    cc.setP6eme(resultats.get(6));
		//		    cc.setP7eme(resultats.get(7));
		//		    cc.setP8eme(resultats.get(8));
		//		    cc.setP9eme(resultats.get(9));
		//		    cc.setP10eme(resultats.get(10));
		//		    cc.setP11eme(resultats.get(11));
		//		    cc.setP12eme(resultats.get(12));
		//		    cc.setP13eme(resultats.get(13));
		//		    cc.setP14eme(resultats.get(14));
		//		    cc.setP15eme(resultats.get(15));
	    } catch (Exception e2) {

	    }
	    resultats.clear();
	    resultats = null;

	    //////////////////////////////
	    // Info partant
	    int ageMin = 30;
	    int ageMax = 0;

	    Set<Partant> partantsListe = partantRepository.findByCourseID(course.getId());

	    if (partantsListe.isEmpty()) {
		logger.debug("Skip => Missing partant for " + course.getId());
		report.addSkipped(1);
		continue;
	    }

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

		    if (unPart.getNumCheval().equals(cc.getNumeroChvlPremier())) {
			cc.setGainChvlPremier(unPart.getiGains());
			cc.setRapportPremierProbableGeny(unPart.getProbableGeny());

		    } else if (unPart.getNumCheval().equals(cc.getNumeroChvlDeuxieme())) {
			cc.setRapportDeuxiemeProbableGeny(unPart.getProbableGeny());

		    } else if (unPart.getNumCheval().equals(cc.getNumeroChvlTroisieme())) {
			cc.setRapportTroisiemeProbableGeny(unPart.getProbableGeny());
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

	    ///////////////////////////
	    // envoi BDD
	    //repository.add(cc);
	    computedBuffer.add(cc);
	    logger.debug("Computed ok for courseID : " + course.getId());

	    cc = null;

	    //		stepDone++;
	    //		if (stepDone > cycleStep) {
	    //		    from = course.getCourseID();
	    //		    break;
	    //		}
	    stepDone++;
	    report.addTreated(1);
	}

	if (!running) {
	    return;
	}
	repository.addAll(computedBuffer);
	computedBuffer.clear();

	logger.info("Computed saved");
	long time = stepChrono.compare();

	logger.debug(stepDone + " records in " + (time / cycleStep) + "ms / step");
	report.setTime(time);
	report.stopRefacto();

	coursesList.clear();
	coursesList = null;
	System.gc();
	try {
	    Thread.sleep(50);
	} catch (InterruptedException e) {
	}

	//	    if( stepDone < cycleStep ) {
	//		break;
	//	    }

	// }

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

    public RefactorerReport getReport() {
	return report;
    }

    public boolean getRunning() {

	return running;
    }

    public Chrono getChrono() {
	return chrono;
    }

}
