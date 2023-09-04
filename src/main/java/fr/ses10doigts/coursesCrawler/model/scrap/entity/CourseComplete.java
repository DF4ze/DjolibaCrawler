package fr.ses10doigts.coursesCrawler.model.scrap.entity;

import fr.ses10doigts.coursesCrawler.model.scrap.AbstractCourseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class CourseComplete extends AbstractCourseEntity{

    @Id
    private Long    courseID;
    private Integer nombrePartant;
    private Integer nombreChevauxInfCinq;
    private Double  rapGagnantGeny;
    private Double  rapPlacePremierGeny;
    private Double  rapPlaceDeuxiemeGeny;
    private Double  rapPlaceTroisiemeGeny;
    private Double  rapGagnantPmu;
    private Double  rapPlacePremierPmu;
    private Double  rapPlaceDeuxiemePmu;
    private Double  rapPlaceTroisiemePmu;
    private Integer numeroChvlPremier;
    private Integer numeroChvlDeuxieme;
    private Integer numeroChvlTroisieme;
    private Float   totalPourcent;
    private Float   cotePremierFavoris;
    private Float   rapportPremierProbableGeny;
    private Float   pourcentPremierFavori;
    private Integer numeroPremierFavori;
    private Float   coteDeuxiemeFavoris;
    private Float   rapportDeuxiemeProbableGeny;
    private Float   pourcentDeuxiemeFavori;
    private Integer numeroDeuxiemeFavori;
    private Float   coteTroisiemeFavoris;
    private Float   rapportTroisiemeProbableGeny;
    private Float   pourcentTroisiemeFavori;
    private Integer numeroTroisiemeFavori;
    private Integer nombreChvlFavoriPlace;
    private Float   cotePremierAvant;
    private Float   cotePremierDepart;
    private Float   coteDeuxiemeAvant;
    private Float   coteDeuxiemeDepart;
    private Float   coteTroisiemeAvant;
    private Float   coteTroisiemeDepart;
    private Float   pourcentPremierAvant;
    private Float   pourcentPremierDepart;
    private Float   pourcentDeuxiemeAvant;
    private Float   pourcentDeuxiemeDepart;
    private Float   pourcentTroisiemeAvant;
    private Float   pourcentTroisiemeDepart;
    private String  dateCourse;
    private Integer numeroCourse;
    private Integer numeroReunion;
    private String  hippodrome;
    private String  prime;
    private String  typeCourse;
    private String  ageSexChvlPremier;
    private String  autoStart;
    private String  musiquePremier;
    private String  nomChvlPremier;
    private Integer gainChvlPremier;
    private String  musiqueMeilleurGains;
    private Integer numeroMeilleurGains;
    private String  p1er;

    public CourseComplete() {
    }

    public Integer getNombrePartant() {
	return nombrePartant;
    }
    public void setNombrePartant(Integer nombrePartant) {
	this.nombrePartant = nombrePartant;
    }

    public Integer getNombreChevauxInfCinq() {
	return nombreChevauxInfCinq;
    }
    public void setNombreChevauxInfCinq(Integer nombreChevauxInfCinq) {
	this.nombreChevauxInfCinq = nombreChevauxInfCinq;
    }

    public Double getRapGagnantGeny() {
	return rapGagnantGeny;
    }

    public void setRapGagnantGeny(Double rapGagnantGeny) {
	this.rapGagnantGeny = rapGagnantGeny;
    }

    public Double getRapPlacePremierGeny() {
	return rapPlacePremierGeny;
    }

    public void setRapPlacePremierGeny(Double rapPlacePremierGeny) {
	this.rapPlacePremierGeny = rapPlacePremierGeny;
    }

    public Double getRapPlaceDeuxiemeGeny() {
	return rapPlaceDeuxiemeGeny;
    }

    public void setRapPlaceDeuxiemeGeny(Double rapPlaceDeuxiemeGeny) {
	this.rapPlaceDeuxiemeGeny = rapPlaceDeuxiemeGeny;
    }

    public Double getRapPlaceTroisiemeGeny() {
	return rapPlaceTroisiemeGeny;
    }

    public void setRapPlaceTroisiemeGeny(Double rapPlaceTroisiemeGeny) {
	this.rapPlaceTroisiemeGeny = rapPlaceTroisiemeGeny;
    }

    public Double getRapGagnantPmu() {
	return rapGagnantPmu;
    }

    public void setRapGagnantPmu(Double rapGagnantPmu) {
	this.rapGagnantPmu = rapGagnantPmu;
    }

    public Double getRapPlacePremierPmu() {
	return rapPlacePremierPmu;
    }

    public void setRapPlacePremierPmu(Double rapPlacePremierPmu) {
	this.rapPlacePremierPmu = rapPlacePremierPmu;
    }

    public Double getRapPlaceDeuxiemePmu() {
	return rapPlaceDeuxiemePmu;
    }

    public void setRapPlaceDeuxiemePmu(Double rapPlaceDeuxiemePmu) {
	this.rapPlaceDeuxiemePmu = rapPlaceDeuxiemePmu;
    }

    public Double getRapPlaceTroisiemePmu() {
	return rapPlaceTroisiemePmu;
    }

    public void setRapPlaceTroisiemePmu(Double rapPlaceTroisiemePmu) {
	this.rapPlaceTroisiemePmu = rapPlaceTroisiemePmu;
    }

    public void setNumeroMeilleurGains(Integer numeroMeilleurGains) {
	this.numeroMeilleurGains = numeroMeilleurGains;
    }

    public Integer getNumeroChvlPremier() {
	return numeroChvlPremier;
    }
    public void setNumeroChvlPremier(Integer numeroChvlPremier) {
	this.numeroChvlPremier = numeroChvlPremier;
    }

    public Integer getNumeroChvlDeuxieme() {
	return numeroChvlDeuxieme;
    }
    public void setNumeroChvlDeuxieme(Integer numeroChvlDeuxieme) {
	this.numeroChvlDeuxieme = numeroChvlDeuxieme;
    }

    public Integer getNumeroChvlTroisieme() {
	return numeroChvlTroisieme;
    }
    public void setNumeroChvlTroisieme(Integer numeroChvlTroisieme) {
	this.numeroChvlTroisieme = numeroChvlTroisieme;
    }

    public Float getTotalPourcent() {
	return totalPourcent;
    }
    public void setTotalPourcent(Float totalPourcent) {
	this.totalPourcent = totalPourcent;
    }

    public Float getPourcentPremierFavori() {
	return pourcentPremierFavori;
    }
    public void setPourcentPremierFavori(Float pourcentPremierFavori) {
	this.pourcentPremierFavori = pourcentPremierFavori;
    }

    public Integer getNumeroPremierFavori() {
	return numeroPremierFavori;
    }
    public void setNumeroPremierFavori(Integer numeroPremierFavori) {
	this.numeroPremierFavori = numeroPremierFavori;
    }

    public Float getPourcentDeuxiemeFavori() {
	return pourcentDeuxiemeFavori;
    }
    public void setPourcentDeuxiemeFavori(Float pourcentDeuxiemeFavori) {
	this.pourcentDeuxiemeFavori = pourcentDeuxiemeFavori;
    }

    public Integer getNumeroDeuxiemeFavori() {
	return numeroDeuxiemeFavori;
    }
    public void setNumeroDeuxiemeFavori(Integer numeroDeuxiemeFavori) {
	this.numeroDeuxiemeFavori = numeroDeuxiemeFavori;
    }

    public Float getPourcentTroisiemeFavori() {
	return pourcentTroisiemeFavori;
    }
    public void setPourcentTroisiemeFavori(Float pourcentTroisiemeFavori) {
	this.pourcentTroisiemeFavori = pourcentTroisiemeFavori;
    }

    public Integer getNumeroTroisiemeFavori() {
	return numeroTroisiemeFavori;
    }
    public void setNumeroTroisiemeFavori(Integer numeroTroisiemeFavori) {
	this.numeroTroisiemeFavori = numeroTroisiemeFavori;
    }

    public Integer getNombreChvlFavoriPlace() {
	return nombreChvlFavoriPlace;
    }
    public void setNombreChvlFavoriPlace(Integer nombreChvlFavoriPlace) {
	this.nombreChvlFavoriPlace = nombreChvlFavoriPlace;
    }

    public String getDateCourse() {
	return dateCourse;
    }
    public void setDateCourse(String dateCourse) {
	this.dateCourse = dateCourse;
    }

    public Integer getNumeroCourse() {
	return numeroCourse;
    }
    public void setNumeroCourse(Integer numeroCourse) {
	this.numeroCourse = numeroCourse;
    }

    public Integer getNumeroReunion() {
	return numeroReunion;
    }
    public void setNumeroReunion(Integer numeroReunion) {
	this.numeroReunion = numeroReunion;
    }

    public String getHippodrome() {
	return hippodrome;
    }
    public void setHippodrome(String hippodrome) {
	this.hippodrome = hippodrome;
    }

    public String getTypeCourse() {
	return typeCourse;
    }
    public void setTypeCourse(String typeCourse) {
	this.typeCourse = typeCourse;
    }

    public String getAgeSexChvlPremier() {
	return ageSexChvlPremier;
    }
    public void setAgeSexChvlPremier(String ageSexChvl) {
	this.ageSexChvlPremier = ageSexChvl;
    }

    public String getAutoStart() {
	return autoStart;
    }
    public void setAutoStart(String autoStart) {
	this.autoStart = autoStart;
    }

    public String getMusiquePremier() {
	return musiquePremier;
    }
    public void setMusiquePremier(String musiquePremier) {
	this.musiquePremier = musiquePremier;
    }

    public String getPrime() {
	return prime;
    }

    public void setPrime(String prime) {
	this.prime = prime;
    }

    public Float getRapportPremierProbableGeny() {
	return rapportPremierProbableGeny;
    }

    public void setRapportPremierProbableGeny(Float rapportPremierProbableGeny) {
	this.rapportPremierProbableGeny = rapportPremierProbableGeny;
    }

    public Float getRapportDeuxiemeProbableGeny() {
	return rapportDeuxiemeProbableGeny;
    }

    public void setRapportDeuxiemeProbableGeny(Float rapportDeuxiemeProbableGeny) {
	this.rapportDeuxiemeProbableGeny = rapportDeuxiemeProbableGeny;
    }

    public Float getRapportTroisiemeProbableGeny() {
	return rapportTroisiemeProbableGeny;
    }

    public void setRapportTroisiemeProbableGeny(Float rapportTroisiemeProbableGeny) {
	this.rapportTroisiemeProbableGeny = rapportTroisiemeProbableGeny;
    }

    public String getNomChvlPremier() {
	return nomChvlPremier;
    }
    public void setNomChvlPremier(String nomChvlPremier) {
	this.nomChvlPremier = nomChvlPremier;
    }

    public String getMusiqueMeilleurGains() {
	return musiqueMeilleurGains;
    }
    public void setMusiqueMeilleurGains(String musiqueMeilleurGains) {
	this.musiqueMeilleurGains = musiqueMeilleurGains;
    }

    public Integer getNumeroMeilleurGains() {
	return numeroMeilleurGains;
    }
    public void setNumeroMeilleurGains(int numeroMeilleurGains) {
	this.numeroMeilleurGains = numeroMeilleurGains;
    }

    public Integer getGainChvlPremier() {
	return gainChvlPremier;
    }
    public void setGainChvlPremier(Integer integer) {
	this.gainChvlPremier = integer;
    }

    public Float getCotePremierFavoris() {
	return cotePremierFavoris;
    }

    public void setCotePremierFavoris(Float cotePremierFavoris) {
	this.cotePremierFavoris = cotePremierFavoris;
    }

    public Float getCoteDeuxiemeFavoris() {
	return coteDeuxiemeFavoris;
    }

    public void setCoteDeuxiemeFavoris(Float coteDeuxiemeFavoris) {
	this.coteDeuxiemeFavoris = coteDeuxiemeFavoris;
    }

    public Float getCoteTroisiemeFavoris() {
	return coteTroisiemeFavoris;
    }

    public void setCoteTroisiemeFavoris(Float coteTroisiemeFavoris) {
	this.coteTroisiemeFavoris = coteTroisiemeFavoris;
    }

    public Float getCotePremierAvant() {
	return cotePremierAvant;
    }

    public void setCotePremierAvant(Float cotePremierAvant) {
	this.cotePremierAvant = cotePremierAvant;
    }

    public Float getCotePremierDepart() {
	return cotePremierDepart;
    }

    public void setCotePremierDepart(Float cotePremierDepart) {
	this.cotePremierDepart = cotePremierDepart;
    }

    public Float getCoteDeuxiemeAvant() {
	return coteDeuxiemeAvant;
    }

    public void setCoteDeuxiemeAvant(Float coteDeuxiemeAvant) {
	this.coteDeuxiemeAvant = coteDeuxiemeAvant;
    }

    public Float getCoteDeuxiemeDepart() {
	return coteDeuxiemeDepart;
    }

    public void setCoteDeuxiemeDepart(Float coteDeuxiemeDepart) {
	this.coteDeuxiemeDepart = coteDeuxiemeDepart;
    }

    public Float getCoteTroisiemeAvant() {
	return coteTroisiemeAvant;
    }

    public void setCoteTroisiemeAvant(Float coteTroisiemeAvant) {
	this.coteTroisiemeAvant = coteTroisiemeAvant;
    }

    public Float getCoteTroisiemeDepart() {
	return coteTroisiemeDepart;
    }

    public void setCoteTroisiemeDepart(Float coteTroisiemeDepart) {
	this.coteTroisiemeDepart = coteTroisiemeDepart;
    }

    public Float getPourcentPremierAvant() {
	return pourcentPremierAvant;
    }

    public void setPourcentPremierAvant(Float pourcentPremierAvant) {
	this.pourcentPremierAvant = pourcentPremierAvant;
    }

    public Float getPourcentPremierDepart() {
	return pourcentPremierDepart;
    }

    public void setPourcentPremierDepart(Float pourcentPremierDepart) {
	this.pourcentPremierDepart = pourcentPremierDepart;
    }

    public Float getPourcentDeuxiemeAvant() {
	return pourcentDeuxiemeAvant;
    }

    public void setPourcentDeuxiemeAvant(Float pourcentDeuxiemeAvant) {
	this.pourcentDeuxiemeAvant = pourcentDeuxiemeAvant;
    }

    public Float getPourcentDeuxiemeDepart() {
	return pourcentDeuxiemeDepart;
    }

    public void setPourcentDeuxiemeDepart(Float pourcentDeuxiemeDepart) {
	this.pourcentDeuxiemeDepart = pourcentDeuxiemeDepart;
    }

    public Float getPourcentTroisiemeAvant() {
	return pourcentTroisiemeAvant;
    }

    public void setPourcentTroisiemeAvant(Float pourcentTroisiemeAvant) {
	this.pourcentTroisiemeAvant = pourcentTroisiemeAvant;
    }

    public Float getPourcentTroisiemeDepart() {
	return pourcentTroisiemeDepart;
    }

    public void setPourcentTroisiemeDepart(Float pourcentTroisiemeDepart) {
	this.pourcentTroisiemeDepart = pourcentTroisiemeDepart;
    }

    @Override
    public Long getCourseID() {
	return courseID;
    }

    @Override
    public void setCourseID(Long courseID) {
	this.courseID = courseID;
    }

    @Override
    public Long getId() {
	return courseID;
    }

    @Override
    public String toString() {
	return "CourseComplete [courseID=" + courseID + ", numeroCourse=" + numeroCourse + ", numeroReunion="
		+ numeroReunion + ", hippodrome=" + hippodrome + "]";
    }

    public void setP1er(String string) {
	p1er = string;

    }

    public String getP1er() {
	return p1er;

    }

}
