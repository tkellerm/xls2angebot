package de.abasgmbh.stahl.infosystem.xls2angebot;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.management.BadAttributeValueExpException;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import de.abas.erp.api.gui.TextBox;
import de.abas.erp.db.DbContext;
import de.abas.erp.db.exception.FormatException;

public class ExcelDateiHandling {

	// String filename;
	private Integer beginTableRow;
	private org.apache.poi.ss.usermodel.Workbook workbook;
	private FileInputStream excelFileInputStream;
	private ResourceBundle xls2angebotProp;
	private List<FileObject> listFileObject;

	// public static void main(String[] args) {
	// String filename = "J:\\stahl\\test.xls";
	// }

	public ExcelDateiHandling() {
		super();

		// Propertie Datei wird geladen
		this.xls2angebotProp = ResourceBundle.getBundle("xls2angebot");
		// Startzeile der Tabelle wird aus der Propertie Datei gelesen
		// Da die Prop Datei Strings liefert, muss hier ein Typecast stattfinden
		// - macht jetzt die Funktion getIntProp
		this.beginTableRow = getIntProp("xls2angebot.startzeile");
		this.listFileObject = new ArrayList<FileObject>();
	}

	private void getWorkbook(String fileName) throws FileNotFoundException, IOException {
		// ob die datei vorhanden ist wird automatisch geprüft - wenn nicht wird
		// die fileNotFound Exception geschmissen
		// ist es neuer oder alter Datentyp (xls oder xlsx)
		this.excelFileInputStream = new FileInputStream(fileName);
		if (fileName.contains(".xlsx")) {
			this.workbook = new XSSFWorkbook(excelFileInputStream);
		} else if (fileName.contains(".xls")) {
			this.workbook = new HSSFWorkbook(excelFileInputStream);
		} else
			throw new FormatException("Es k�nnen nur .xls oder .xlsx-Dateien importiert werden!");

	}

	public List<FileObject> getFileObject(DbContext ctx, String fileName) throws BadAttributeValueExpException {
		try {
			// Workbook (das Excel File) holen
			getWorkbook(fileName);

			// Sheet ( Tabellenblatt) holen
			Sheet sheet = workbook.getSheetAt(getIntProp("xls2angebot.sheet.erfassung"));

			// FileObject anlegen
			FileObject fileObject = initAndFillFileObject(sheet);

			// Sprung auf "erste Tabellenzeile"

			// mit Schleife die Tabellendaten durchlaufen

			// Schleifenende -> fertig weil ende Tabelle
			FileObjectRow fileObjectRow = null;

			int rowStart = getIntProp("xls2angebot.startzeile");
			int rowEnd = sheet.getLastRowNum();

			for (Integer rowNum = rowStart; rowNum < rowEnd; rowNum++) {
				Row row = sheet.getRow(rowNum);
				// wenn eines der beiden Artikelfelder gefüllt ist ...
				String artikel = getCellValue(row, "xls2angebot.spalte.artikel");
				String kundenArtikelNummer = getCellValue(row, "xls2angebot.spalte.kundenArtikelNummer");
				if (!getCellValue(row, "xls2angebot.spalte.artikel").isEmpty()
						|| !getCellValue(row, "xls2angebot.spalte.kundenArtikelNummer").isEmpty()) {

					if (fileObjectRow != null) {
						// wenn neue Anfragenumme dann altes FileObject an die
						// Liste h�ngen und neues erzeugen
						if (!fileObjectRow.getAnfrageNum().equals(getCellValue(row, "xls2angebot.spalte.anfrageNum"))) {
							fileObject = initAndFillFileObject(sheet);
						}

					}
					// FileObjectRow datensatz anlegen
					fileObjectRow = new FileObjectRow();
					// Zeilendaten in FileobjectRow Element schreiben
					fillFileObjectRow(fileObjectRow, row);
					fileObject.getTable().add(fileObjectRow);

				} else {
					// break;
				}

			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			TextBox textbox = new TextBox(ctx, "Fehler", e.toString());
			textbox.show();
		} finally {
			if (this.workbook != null) {
				try {
					this.excelFileInputStream.close();

				} catch (IOException e) {
					// Nichts machen
				}
			}

		}

		return listFileObject;
	}

	private FileObject initAndFillFileObject(Sheet sheet) throws BadAttributeValueExpException {
		FileObject fileObject = new FileObject();
		// FileObject an Liste h�ngen
		this.listFileObject.add(fileObject);
		// Kopfdaten auslesen
		fileObject.setKunde(
				getZellenInhaltString(sheet, getIntProp("xls2angebot.kunde.x"), getIntProp("xls2angebot.kunde.y")));
		fileObject.setKundenNummer(getZellenInhaltString(sheet, getIntProp("xls2angebot.kundenNummer.x"),
				getIntProp("xls2angebot.kundenNummer.y")));

		return fileObject;
	}

	private void fillFileObjectRow(FileObjectRow fileObjectRow, Row row)
			throws ParseException, BadAttributeValueExpException {

		fileObjectRow.setArtikel(getCellValue(row, "xls2angebot.spalte.artikel"));
		fileObjectRow.setKundenArtikelNummer(getCellValue(row, "xls2angebot.spalte.kundenArtikelNummer"));
		fileObjectRow.setZeichnung(getCellValue(row, "xls2angebot.spalte.zeichnung"));
		fileObjectRow.setDimX(getCellValueBigDezimal(row, "xls2angebot.spalte.dimX"));
		fileObjectRow.setDimM(getCellValueBigDezimal(row, "xls2angebot.spalte.dimM"));
		fileObjectRow.setDimS(getCellValueBigDezimal(row, "xls2angebot.spalte.dimS"));
		fileObjectRow.setGewicht(getCellValueBigDezimal(row, "xls2angebot.spalte.gewicht"));
		// fileObjectRow.setMaxDicke(getCellValueBigDezimal(row,
		// "xls2angebot.spalte.maxDicke"));
		fileObjectRow.setMenge(getCellValueBigDezimal(row, "xls2angebot.spalte.menge"));
		fileObjectRow.setPulverPreis(getCellValueBigDezimal(row, "xls2angebot.spalte.pulverPreis"));
		fileObjectRow.setPulverVerbrauch(getCellValueBigDezimal(row, "xls2angebot.spalte.pulverVerbrauch"));
		fileObjectRow.setFarbton(getCellValue(row, "xls2angebot.spalte.farbton"));
		fileObjectRow.setLackbezeichnung(getCellValue(row, "xls2angebot.spalte.lackbezeichnung"));
		fileObjectRow.setAnfrageNum(getCellValue(row, "xls2angebot.spalte.anfrageNum"));
		if (!getCellValue(row, "xls2angebot.spalte.datum").isEmpty()) {
			fileObjectRow.setDatum(convertStringtoDate(getCellValue(row, "xls2angebot.spalte.datum")));
		} else {
			// <uk 28.09.15> Da eine Übergabe eines leeren Datums zu einem
			// Fehler führt, wird als Default-Wert der 1.1.2000 übergaben
			fileObjectRow.setDatum(convertStringtoDate("Sat Jan 01 12:00:00 BST 2000"));
		}
		fileObjectRow.setMitarb(getCellValue(row, "xls2angebot.spalte.mitarb"));
		fileObjectRow.setBeizen(convertStringToBool(getCellValue(row, "xls2angebot.spalte.beizen")));
		fileObjectRow.setNurVbh(convertStringToBool(getCellValue(row, "xls2angebot.spalte.nurVbh")));
		fileObjectRow.setKtl(convertStringToBool(getCellValue(row, "xls2angebot.spalte.ktl")));
		fileObjectRow
				.setPulverbeschichten(convertStringToBool(getCellValue(row, "xls2angebot.spalte.pulverbeschichten")));
		fileObjectRow.setTransport(convertStringToBool(getCellValue(row, "xls2angebot.spalte.transport")));
		fileObjectRow.setSonderaufwand(convertStringToBool(getCellValue(row, "xls2angebot.spalte.sonderaufwand")));
		fileObjectRow.setEinzelpreis(getCellValueBigDezimal(row, "xls2angebot.spalte.einzelpreis"));
		fileObjectRow.setRabatt(getCellValueBigDezimal(row, "xls2angebot.spalte.rabatt"));
		fileObjectRow.setAngebotspreis(getCellValueBigDezimal(row, "xls2angebot.spalte.angebotspreis"));

	}

	private Boolean convertStringToBool(String string) {
		if (string.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	private Date convertStringtoDate(String dateString) throws ParseException {

		// DateFormat dateformat = new SimpleDateFormat("dd.MM.yy");
		DateFormat dateformat = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
		return dateformat.parse(dateString);

	}

	private String getCellValue(Row row, String string) throws BadAttributeValueExpException {
		return getZellenInhaltString(row.getSheet(), getIntProp(string), row.getRowNum());
	}

	private BigDecimal getCellValueBigDezimal(Row row, String string) throws BadAttributeValueExpException {
		String cellstring = getZellenInhaltString(row.getSheet(), getIntProp(string), row.getRowNum());

		try {
			return new BigDecimal(cellstring);
		} catch (NumberFormatException e) {
			if (cellstring.isEmpty()) {
				return new BigDecimal(0);
			} else {
				throw new BadAttributeValueExpException(
						"Die Zelle mit den Koordinaten Spalte =" + getIntProp(string) + " Zeile =" + row.getRowNum()
								+ " existiert in der gelesenen Datei nicht oder hat einen falschen Wert");
			}

		}

	}

	private int getIntProp(String string) {
		String tmpString = xls2angebotProp.getString(string);
		return new Integer(tmpString);
	}

	private void createNewFileObject() {
		// TODO Auto-generated method stub

	}

	private int getMaxCol(org.apache.poi.ss.usermodel.Sheet sheet) {
		// Anzahl Spalten(Cols) in der 2. Zeile (entspricht 1)
		Integer maxcol = sheet.getRow(1).getPhysicalNumberOfCells();
		return maxcol;
	}

	private int getMaxRow(org.apache.poi.ss.usermodel.Sheet sheet) {
		// TODO Auto-generated method stub

		return sheet.getLastRowNum();
	}

	private String getZellenInhaltString(org.apache.poi.ss.usermodel.Sheet sheet, int x, int y)
			throws BadAttributeValueExpException {
		// Hier werden alle Inhaltsmöglichkeiten einer Zelle in einen String
		// umgewandelt
		org.apache.poi.ss.usermodel.Cell cell = sheet.getRow(y).getCell(x);
		if (cell != null) {

			if (cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING) {
				return cell.getStringCellValue();
			} else {

				if (cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC) {

					if (HSSFDateUtil.isCellDateFormatted(cell)) {
						Date date = cell.getDateCellValue();
						return date.toString();

					} else {
						Double nummericvalue = cell.getNumericCellValue();
						Integer intvalue = nummericvalue.intValue();
						if (intvalue.doubleValue() == nummericvalue) {
							return intvalue.toString();
						} else {
							return nummericvalue.toString();
						}
					}

				} else {
					if (cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BOOLEAN) {
						if (cell.getBooleanCellValue() == true) {
							return "ja";
						} else {
							return "nein";
						}
					} else {
						if (cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_FORMULA) {
							if (cell.getCachedFormulaResultType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING) {
								return cell.getStringCellValue();
							} else {
								if (cell.getCachedFormulaResultType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC) {
									Double nummericvalue = cell.getNumericCellValue();
									return nummericvalue.toString();
								} else {
									if (cell.getCachedFormulaResultType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BOOLEAN) {
										if (cell.getBooleanCellValue() == true) {
											return "ja";
										} else {
											return "nein";
										}
									} else {
										if (cell.getCachedFormulaResultType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BLANK) {
											return "";
										} else {
											if (cell.getCachedFormulaResultType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_ERROR) {
												return null;
											}
										}
									}
								}
							}
						} else {
							if (cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BLANK) {
								return "";
							} else {
								if (cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_ERROR) {
									return null;
								}

							}

						}

					}
				}
			}

		}
		// Falls irgendein Fall vergessen wurde wird null übertragen

		// throw new BadAttributeValueExpException("Die Zelle mit den
		// Koordinaten Spalte =" + (x+1) +" Zeile=" + (y+1) + " existiert in der
		// gelesenen Datei nicht" );
		return "";

	}
}
