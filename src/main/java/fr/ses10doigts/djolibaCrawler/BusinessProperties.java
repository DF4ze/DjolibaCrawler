package fr.ses10doigts.djolibaCrawler;

import java.io.Serializable;

import lombok.Data;

@Data
public class BusinessProperties implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer	      moFrame;
    private Integer	      moSkin;
    private Integer	      moBuild;
    private Integer	      marge;

    private String	      confPath;

    private Integer	      skinBorder;
    private Integer	      nbMaxFrame;

    @Override
    public BusinessProperties clone() {
	BusinessProperties bp = new BusinessProperties();
	bp.moFrame = moFrame;
	bp.moSkin = moSkin;
	bp.moBuild = moBuild;
	bp.marge = marge;
	bp.confPath = confPath;
	bp.skinBorder = skinBorder;
	bp.nbMaxFrame = nbMaxFrame;

	return bp;
    }

    public CustomBusinessProperties cloneToCustom() {
	CustomBusinessProperties bp = new CustomBusinessProperties();
	bp.setMoFrame(moFrame);
	bp.setMoSkin(moSkin);
	bp.setMoBuild(moBuild);
	bp.setMarge(marge);
	bp.setConfPath(confPath);
	bp.setSkinBorder(skinBorder);
	bp.setNbMaxFrame(nbMaxFrame);

	return bp;
    }
}
