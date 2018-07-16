package de.abasgmbh.stahl.infosystem.xls2angebot;

import java.math.BigDecimal;
import java.util.Date;

public class FileObjectRow {

	String artikel;
	String kundenArtikelNummer;
	String zeichnung;
	BigDecimal menge;
	BigDecimal einzelpreis;
	BigDecimal rabatt;
	BigDecimal angebotspreis;
	BigDecimal dimX;
	BigDecimal dimM;
	BigDecimal dimS;
	BigDecimal gewicht;
	BigDecimal maxDicke;
	BigDecimal pulverPreis;
	BigDecimal pulverVerbrauch;
	String farbton;
	String lackbezeichnung;
	String anfrageNum;
	Date datum;
	String mitarb;
	Boolean beizen;
	Boolean nurVbh;
	Boolean ktl;
	Boolean pulverbeschichten;
	Boolean transport;
	Boolean sonderaufwand;
	
	public FileObjectRow(String artikel, BigDecimal menge,
			BigDecimal einzelpreis, BigDecimal rabatt,
			BigDecimal angebotspreis, BigDecimal dimX, BigDecimal dimM,
			BigDecimal dimS, BigDecimal maxDicke, BigDecimal pulverPreis,
			BigDecimal pulverVerbrauch, String farbton, String lackbezeichnung,
			String anfrageNum, Date datum, String mitarb, Boolean beizen,
			Boolean nurVbh, Boolean ktl, Boolean pulverbeschichten,
			Boolean transport, Boolean sonderaufwand) {
		super();
		this.artikel = artikel;
		this.menge = menge;
		this.einzelpreis = einzelpreis;
		this.rabatt = rabatt;
		this.angebotspreis = angebotspreis;
		this.dimX = dimX;
		this.dimM = dimM;
		this.dimS = dimS;
		this.maxDicke = maxDicke;
		this.pulverPreis = pulverPreis;
		this.pulverVerbrauch = pulverVerbrauch;
		this.farbton = farbton;
		this.lackbezeichnung = lackbezeichnung;
		this.anfrageNum = anfrageNum;
		this.datum = datum;
		this.mitarb = mitarb;
		this.beizen = beizen;
		this.nurVbh = nurVbh;
		this.ktl = ktl;
		this.pulverbeschichten = pulverbeschichten;
		this.transport = transport;
		this.sonderaufwand = sonderaufwand;
	}

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

	public BigDecimal getEinzelpreis() {
		return einzelpreis;
	}

	public void setEinzelpreis(BigDecimal einzelpreis) {
		this.einzelpreis = einzelpreis;
	}

	public BigDecimal getRabatt() {
		return rabatt;
	}

	public void setRabatt(BigDecimal rabatt) {
		this.rabatt = rabatt;
	}

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

	public Boolean getBeizen() {
		return beizen;
	}

	public void setBeizen(Boolean beizen) {
		this.beizen = beizen;
	}

	public Boolean getNurVbh() {
		return nurVbh;
	}

	public void setNurVbh(Boolean nurVbh) {
		this.nurVbh = nurVbh;
	}

	public Boolean getKtl() {
		return ktl;
	}

	public void setKtl(Boolean ktl) {
		this.ktl = ktl;
	}

	public Boolean getPulverbeschichten() {
		return pulverbeschichten;
	}

	public void setPulverbeschichten(Boolean pulverbeschichten) {
		this.pulverbeschichten = pulverbeschichten;
	}

	public Boolean getTransport() {
		return transport;
	}

	public void setTransport(Boolean transport) {
		this.transport = transport;
	}

	public Boolean getSonderaufwand() {
		return sonderaufwand;
	}

	public void setSonderaufwand(Boolean sonderaufwand) {
		this.sonderaufwand = sonderaufwand;
	}
		

}
