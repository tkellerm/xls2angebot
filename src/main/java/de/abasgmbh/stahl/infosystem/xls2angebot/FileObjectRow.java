package de.abasgmbh.stahl.infosystem.xls2angebot;

import java.math.BigDecimal;
import java.util.Date;

public class FileObjectRow {

	String artikel;
	String kundenArtikelNummer;
	String zeichnung;
	BigDecimal menge;
	// BigDecimal einzelpreis;
	// BigDecimal rabatt;
	BigDecimal angebotspreis;
	BigDecimal dimX;
	BigDecimal dimM;
	BigDecimal dimS;
	BigDecimal gewicht;
	BigDecimal maxDicke;
	BigDecimal pulverPreis;
	BigDecimal pulverVerbrauch;
	BigDecimal dicke;
	BigDecimal preis;
	BigDecimal wtpb300;
	BigDecimal wtpb600;
	BigDecimal wtpb620;
	BigDecimal aufabzeit300;
	BigDecimal aufabzeit600;
	BigDecimal aufabzeit620;
	BigDecimal staffelmenge;

	public BigDecimal getStaffelmenge() {
		return staffelmenge;
	}

	public void setStaffelmenge(BigDecimal staffelmenge) {
		this.staffelmenge = staffelmenge;
	}

	public BigDecimal getStaffelpreis() {
		return staffelpreis;
	}

	public void setStaffelpreis(BigDecimal staffelpreis) {
		this.staffelpreis = staffelpreis;
	}

	BigDecimal staffelpreis;
	String ytpulverlack;
	String farbton;
	String lackbezeichnung;
	String anfrageNum;
	String Sonderaufwandbeschreibung;
	String wstoff;
	String abteilung;
	String schichtdicke;

	Date datum;
	String mitarb;

	public BigDecimal getDicke() {
		return dicke;
	}

	public void setDicke(BigDecimal dicke) {
		this.dicke = dicke;
	}

	public BigDecimal getPreis() {
		return preis;
	}

	public void setPreis(BigDecimal preis) {
		this.preis = preis;
	}

	public BigDecimal getWtpb300() {
		return wtpb300;
	}

	public void setWtpb300(BigDecimal wtpb300) {
		this.wtpb300 = wtpb300;
	}

	public BigDecimal getWtpb600() {
		return wtpb600;
	}

	public void setWtpb600(BigDecimal wtpb600) {
		this.wtpb600 = wtpb600;
	}

	public BigDecimal getWtpb620() {
		return wtpb620;
	}

	public void setWtpb620(BigDecimal wtpb620) {
		this.wtpb620 = wtpb620;
	}

	public BigDecimal getAufabzeit300() {
		return aufabzeit300;
	}

	public void setAufabzeit300(BigDecimal aufabzeit300) {
		this.aufabzeit300 = aufabzeit300;
	}

	public BigDecimal getAufabzeit600() {
		return aufabzeit600;
	}

	public void setAufabzeit600(BigDecimal aufabzeit600) {
		this.aufabzeit600 = aufabzeit600;
	}

	public BigDecimal getAufabzeit620() {
		return aufabzeit620;
	}

	public void setAufabzeit620(BigDecimal aufabzeit620) {
		this.aufabzeit620 = aufabzeit620;
	}

	public String getYtpulverlack() {
		return ytpulverlack;
	}

	public void setYtpulverlack(String ytpulverlack) {
		this.ytpulverlack = ytpulverlack;
	}

	public String getSonderaufwandbeschreibung() {
		return Sonderaufwandbeschreibung;
	}

	public void setSonderaufwandbeschreibung(String sonderaufwandbeschreibung) {
		Sonderaufwandbeschreibung = sonderaufwandbeschreibung;
	}

	public String getWstoff() {
		return wstoff;
	}

	public void setWstoff(String wstoff) {
		this.wstoff = wstoff;
	}

	public String getAbteilung() {
		return abteilung;
	}

	public void setAbteilung(String abteilung) {
		this.abteilung = abteilung;
	}

	public String getSchichtdicke() {
		return schichtdicke;
	}

	public void setSchichtdicke(String schichtdicke) {
		this.schichtdicke = schichtdicke;
	}

	// public FileObjectRow(String artikel, BigDecimal menge, BigDecimal
	// einzelpreis, BigDecimal rabatt,
	// BigDecimal angebotspreis, BigDecimal dimX, BigDecimal dimM, BigDecimal
	// dimS, BigDecimal maxDicke,
	// BigDecimal pulverPreis, BigDecimal pulverVerbrauch, String farbton,
	// String lackbezeichnung,
	// String anfrageNum, Date datum, String mitarb, Boolean beizen, Boolean
	// nurVbh, Boolean ktl,
	// Boolean pulverbeschichten, Boolean transport, Boolean sonderaufwand) {
	// super();
	// this.artikel = artikel;
	// this.menge = menge;
	// this.einzelpreis = einzelpreis;
	// this.rabatt = rabatt;
	// this.angebotspreis = angebotspreis;
	// this.dimX = dimX;
	// this.dimM = dimM;
	// this.dimS = dimS;
	// this.maxDicke = maxDicke;
	// this.pulverPreis = pulverPreis;
	// this.pulverVerbrauch = pulverVerbrauch;
	// this.farbton = farbton;
	// this.lackbezeichnung = lackbezeichnung;
	// this.anfrageNum = anfrageNum;
	// this.datum = datum;
	// this.mitarb = mitarb;
	// //
	// }

	public FileObjectRow() {
		// TODO Auto-generated constructor stub
	}

	public String getArtikel() {
		return artikel;
	}

	public void setArtikel(String artikel) {
		this.artikel = artikel;
	}

	public String getKundenArtikelNummer() {
		return kundenArtikelNummer;
	}

	public void setKundenArtikelNummer(String kundenArtikelNummer) {
		this.kundenArtikelNummer = kundenArtikelNummer;
	}

	public String getZeichnung() {
		return zeichnung;
	}

	public void setZeichnung(String zeichnung) {
		this.zeichnung = zeichnung;
	}

	public BigDecimal getMenge() {
		return menge;
	}

	public void setMenge(BigDecimal menge) {
		this.menge = menge;
	}

	// public BigDecimal getEinzelpreis() {
	// return einzelpreis;
	// }
	//
	// public void setEinzelpreis(BigDecimal einzelpreis) {
	// this.einzelpreis = einzelpreis;
	// }
	//
	// public BigDecimal getRabatt() {
	// return rabatt;
	// }
	//
	// public void setRabatt(BigDecimal rabatt) {
	// this.rabatt = rabatt;
	// }

	public BigDecimal getAngebotspreis() {
		return angebotspreis;
	}

	public void setAngebotspreis(BigDecimal angebotspreis) {
		this.angebotspreis = angebotspreis;
	}

	public BigDecimal getDimX() {
		return dimX;
	}

	public void setDimX(BigDecimal dimX) {
		this.dimX = dimX;
	}

	public BigDecimal getDimM() {
		return dimM;
	}

	public void setDimM(BigDecimal dimM) {
		this.dimM = dimM;
	}

	public BigDecimal getDimS() {
		return dimS;
	}

	public void setDimS(BigDecimal dimS) {
		this.dimS = dimS;
	}

	public BigDecimal getGewicht() {
		return gewicht;
	}

	public void setGewicht(BigDecimal gewicht) {
		this.gewicht = gewicht;
	}

	public BigDecimal getMaxDicke() {
		return maxDicke;
	}

	public void setMaxDicke(BigDecimal maxDicke) {
		this.maxDicke = maxDicke;
	}

	public BigDecimal getPulverPreis() {
		return pulverPreis;
	}

	public void setPulverPreis(BigDecimal pulverPreis) {
		this.pulverPreis = pulverPreis;
	}

	public BigDecimal getPulverVerbrauch() {
		return pulverVerbrauch;
	}

	public void setPulverVerbrauch(BigDecimal pulverVerbrauch) {
		this.pulverVerbrauch = pulverVerbrauch;
	}

	public String getFarbton() {
		return farbton;
	}

	public void setFarbton(String farbton) {
		this.farbton = farbton;
	}

	public String getLackbezeichnung() {
		return lackbezeichnung;
	}

	public void setLackbezeichnung(String lackbezeichnung) {
		this.lackbezeichnung = lackbezeichnung;
	}

	public String getAnfrageNum() {
		return anfrageNum;
	}

	public void setAnfrageNum(String anfrageNum) {
		this.anfrageNum = anfrageNum;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public String getMitarb() {
		return mitarb;
	}

	public void setMitarb(String mitarb) {
		this.mitarb = mitarb;
	}

}
