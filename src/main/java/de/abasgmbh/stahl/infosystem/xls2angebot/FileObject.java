package de.abasgmbh.stahl.infosystem.xls2angebot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileObject {
	String kunde;
	String kundenNummer;
	Date datum;
	List<FileObjectRow> table ;
	
	public String getKundenNummer() {
		return kundenNummer;
	}


	public void setKundenNummer(String kundenNummer) {
		this.kundenNummer = kundenNummer;
	}


	public String getKunde() {
		return kunde;
	}


	public void setKunde(String kunde) {
		this.kunde = kunde;
	}


	public Date getDatum() {
		return datum;
	}


	public void setDatum(Date datum) {
		this.datum = datum;
	}


	public List<FileObjectRow> getTable() {
		return table;
	}


	public void setTable(List<FileObjectRow> table) {
		this.table = table;
	}

	public FileObject() {
		super();
		this.table = new ArrayList<FileObjectRow>();
	}


	public FileObject(String kunde, Date datum) {
		super();
		this.kunde = kunde;
		this.datum = datum;
		this.table = new ArrayList<FileObjectRow>();
	}
	
	
	

}
