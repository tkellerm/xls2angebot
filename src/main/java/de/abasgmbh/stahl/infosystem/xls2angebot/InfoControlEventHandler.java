package de.abasgmbh.stahl.infosystem.xls2angebot;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;

import javax.management.BadAttributeValueExpException;

import de.abas.eks.jfop.remote.FOe;
import de.abas.erp.api.gui.TextBox;
import de.abas.erp.axi.event.EventException;
import de.abas.erp.axi.screen.ScreenControl;
import de.abas.erp.axi2.EventHandlerRunner;
import de.abas.erp.axi2.annotation.ButtonEventHandler;
import de.abas.erp.axi2.annotation.EventHandler;
import de.abas.erp.axi2.annotation.FieldEventHandler;
import de.abas.erp.axi2.event.ButtonEvent;
import de.abas.erp.axi2.event.FieldEvent;
import de.abas.erp.axi2.event.FieldFillEvent;
import de.abas.erp.axi2.type.ButtonEventType;
import de.abas.erp.axi2.type.FieldEventType;
import de.abas.erp.common.AbasException;
import de.abas.erp.common.type.AbasDate;
import de.abas.erp.db.DbContext;
import de.abas.erp.db.Query;
import de.abas.erp.db.exception.CommandException;
import de.abas.erp.db.infosystem.custom.owis.InfoControl;
import de.abas.erp.db.infosystem.custom.owis.InfoControl.Row;
import de.abas.erp.db.schema.customer.Customer;
import de.abas.erp.db.schema.customer.SelectableCustomer;
import de.abas.erp.db.schema.part.Product;
import de.abas.erp.db.schema.part.ProductEditor;
import de.abas.erp.db.schema.sales.Quotation;
import de.abas.erp.db.schema.sales.QuotationEditor;
import de.abas.erp.db.selection.ExpertSelection;
import de.abas.erp.db.selection.Selection;
import de.abas.erp.jfop.rt.api.annotation.RunFopWith;

@EventHandler(head = InfoControl.class, row = InfoControl.Row.class)
@RunFopWith(EventHandlerRunner.class)
public class InfoControlEventHandler {

	private ResourceBundle xls2angebotProp = ResourceBundle.getBundle("xls2angebot");

	@ButtonEventHandler(field = "start", type = ButtonEventType.BEFORE)
	public void startBefore(ButtonEvent event, ScreenControl screenControl, DbContext ctx, InfoControl head)
			throws EventException {
		// TODO Auto-generated method stub

	}

	@ButtonEventHandler(field = "start", type = ButtonEventType.AFTER)
	public void startAfter(ButtonEvent event, ScreenControl screenControl, DbContext ctx, InfoControl head)
			throws EventException {

		ExcelDateiHandling excelDateiHandling = new ExcelDateiHandling();
		try {
			java.util.List<FileObject> listFileObjects = excelDateiHandling.getFileObject(ctx, head.getYkdateiname());
			fillTableFromObject(head, ctx, screenControl, listFileObjects);

		} catch (BadAttributeValueExpException e) {

			TextBox textbox = new TextBox(ctx, "Fehler", e.toString());
			textbox.show();
		}
	}

	/**
	 * @param head
	 * @param ctx
	 * @param screenControl
	 * @param listFileObjects
	 *            Diese Funktion f�llt den Kopf und die Tabelle
	 */
	private void fillTableFromObject(InfoControl head, DbContext ctx, ScreenControl screenControl,
			List<FileObject> listFileObjects) {
		head.table().clear();
		for (FileObject fileObject : listFileObjects) {

			// if (head.getYkkundename().isEmpty()) {
			String selectionString = "idno==" + fileObject.getKundenNummer() + ";@language=en";
			Selection<Customer> selection = ExpertSelection.create(Customer.class, selectionString);
			head.setYkkunde(selection);
			head.setYkkundename(fileObject.getKunde());
			head.setYkkundennummer(fileObject.getKundenNummer());

			// }
			List<FileObjectRow> table = fileObject.getTable();
			if (table != null) {
				boolean firstRow = true;
				for (FileObjectRow fileObjectRow : table) {
					// Zeile einfügen
					Row row = head.table().appendRow();
					// screenControl.setColor(row, Color.DEFAULT,
					// Color.DEFAULT);
					// wenn es die erste Zeile ist wird das Bool Feld
					// Ytneuesangebot gesetzt - daran erkennen wir dass wir ein
					// neues Angebot machen müssen
					if (firstRow) {
						row.setYtneuesangebot(true);
						firstRow = false;
					}
					row.setYtartikelbez(fileObjectRow.getArtikel());
					row.setYtkundenartnum(fileObjectRow.getKundenArtikelNummer());
					row.setYtzeichung(fileObjectRow.getZeichnung());
					row.setYtmge(fileObjectRow.getMenge());
					row.setYtlaenge(fileObjectRow.getDimX());
					row.setYtbreite(fileObjectRow.getDimM());
					row.setYthoehe(fileObjectRow.getDimS());
					row.setYtgewicht(fileObjectRow.getGewicht());
					row.setYtbeschart(fileObjectRow.getFarbton());
					row.setYtanfragenum(fileObjectRow.getAnfrageNum());
					AbasDate abasDate = new AbasDate(fileObjectRow.getDatum());
					row.setYtdatum(abasDate);
					row.setYtbearb(fileObjectRow.getMitarb());
					BigDecimal preis = fileObjectRow.getAngebotspreis();
					BigDecimal preisGerundet = preis.round(new MathContext(13));
					row.setYtpreis(preisGerundet);
					// Initial sollen die Kenner Angebot und Artikel anlegen auf
					// false stehen
					row.setYtartikelanlegen(false);
					row.setYtinangebot(false);
					// Der Artikel wird gesucht. Wenn er einmalig gefunden wird,
					// wird er in das Verweisfeld eingetragen
					Product artikel = null;
					try {
						artikel = artikelSuchen(ctx, row.getYtkundenartnum());
					} catch (AbasException e) {
						setFehler(row, screenControl, e);
						// screenControl.setProtection(row.getYtartikelverw(),
						// false);
						row.setYtartikelanlegen(false);
						row.setYtinangebot(false);
						TextBox textbox = new TextBox(ctx, "Fehler", e.getMessage());
						// textbox = new TextBox(ctx, "Fehler", e.getMessage());
						textbox.show();
					}
					if (artikel != null) {
						row.setYtartikelverw(artikel);
					}
					// Auch nach bereits erstellen Angeboten soll gesucht werden
					if (row.getYtartikelverw() != null) {
						Quotation angebot = null;
						angebot = angebotSuchen(ctx, head.getYkkunde(), row.getYtartikelverw(), row.getYtdatum(),
								row.getYtbeschart());
						if (angebot != null) {
							row.setYtangebotsverweis(angebot);
						}
					}

				}
			}
		}
	}

	private Quotation angebotSuchen(DbContext ctx, SelectableCustomer kunde, Product artikel, AbasDate datum,
			String beschart) {
		String selectionString = "0:customer==" + kunde + ";0:dateFrom==" + datum + ";1:product==" + artikel
				+ ";1:ytbeschichtungsart=" + beschart + ";@language=en;@rows=yes";
		Selection<Quotation.Row> selection = ExpertSelection.create(Quotation.Row.class, selectionString);
		Query<Quotation.Row> queryQuotation = ctx.createQuery(selection);
		int zaehl = 0;
		Quotation uebergabeQuotation = null;
		for (Quotation.Row quotation : queryQuotation) {
			zaehl++;
			uebergabeQuotation = quotation.header();
		}
		if (zaehl == 0) {
			return null;
		} else {
			return uebergabeQuotation;
		}
	}

	private void setFehler(Row row, ScreenControl screenControl, AbasException e) {
		row.setYtfehlertext(e.getMessage());
		// screenControl.setColor(row, Color.DEFAULT, Color.RED);

	}

	@ButtonEventHandler(field = "ykartikelanlegen", type = ButtonEventType.AFTER)
	public void ykartikelanlegenAfter(ButtonEvent event, ScreenControl screenControl, DbContext ctx, InfoControl head)
			throws EventException {
		Iterable<Row> rows = head.table().getRows();
		for (Row row : rows) {
			if (row.getYtartikelanlegen() || row.getYtinangebot()) {
				// wenn der Kenner gesetzt ist, wird der Artikel erst gesucht,
				// -> wenn er nicht gefunden wird, wird ein neuer Artikel
				// angelegt
				try {
					Product artikel = artikelSuchen(ctx, row.getYtkundenartnum());
					if (artikel == null) {
						artikel = artikelAnlegen(ctx, row);
					}
					row.setYtartikelverw(artikel);
				} catch (AbasException e) {
					TextBox textbox = new TextBox(ctx, "Fehler", e.toString());
					textbox.show();
				}
			}
		}

	}

	@ButtonEventHandler(field = "ykangeboteanlegen", type = ButtonEventType.AFTER)
	public void ykangeboteanlegenAfter(ButtonEvent event, ScreenControl screenControl, DbContext ctx, InfoControl head)
			throws EventException {
		ykartikelanlegenAfter(event, screenControl, ctx, head);
		QuotationEditor angebotsKopf = null;
		Iterable<Row> rows = head.table().getRows();

		for (Row row : rows) {
			if (row.getYtinangebot()) {
				try {
					// sollte eine Position ausgewählt worden sein, die den
					// Kenner Ytneuesangebot nicht hat es aber noch keinen
					// Angebotskopf geben, wird ebenfalls ein neuer erzeugt
					if (row.getYtneuesangebot() || (angebotsKopf == null && !row.getYtneuesangebot())) {
						// Wenn ein Angebot geladen ist, wird dieses gespeichert
						// und das Objekt geleert
						if (angebotsKopf != null) {
							angebotsKopf.commit();

						}
						// Es wird ein neues Angebot erstellt
						angebotsKopf = angebotsKopfAnlegen(ctx, head.getYkkunde(), row);
						// die aktuelle Zeile wird als Angebotsposition
						// hinzugefügt

						row.setYtangebotsverweis(angebotsPosiHinzufuegen(angebotsKopf, ctx, row));

					} else if (angebotsKopf != null) {
						// Die aktuelle Zeile wird als neue Angebotsposition zum
						// Angebotsobjekt hinzugefügt
						row.setYtangebotsverweis(angebotsPosiHinzufuegen(angebotsKopf, ctx, row));

					}

				} catch (CommandException e) {
					setFehler(row, screenControl, e);
				}
			}

		}

		// nach kompletten Durchlauf der Tabelle, wird das letzte bearbeitete
		// Angebot noch gespeichert
		if (angebotsKopf != null) {
			angebotsKopf.commit();

		}

	}

	private Selection<Quotation> angebotsPosiHinzufuegen(QuotationEditor angebot, DbContext ctx, Row row)
			throws CommandException {
		de.abas.erp.db.schema.sales.QuotationEditor.Row quotationRow = angebot.table().appendRow();
		quotationRow.setProduct(row.getYtartikelverw());
		quotationRow.setUnitQty(row.getYtmge());
		quotationRow.setPrice(row.getYtpreis());
		quotationRow.setYtbeschichtungsart(row.getYtbeschart());
		angebot.commitAndReopen();
		// SelectableSales angebotreturn = angebot.getId();
		// Quotation test = (Quotation) angebot.getId();
		String selectionString = "id=" + angebot.getId().toString() + ";@filingmode=both;@language=en";
		Selection<Quotation> selection = ExpertSelection.create(Quotation.class, selectionString);

		return selection;

	}

	private QuotationEditor angebotsKopfAnlegen(DbContext ctx, SelectableCustomer customer, Row row) {
		QuotationEditor angebotsKopf = ctx.newObject(QuotationEditor.class);
		angebotsKopf.setCustomer(customer);
		angebotsKopf.setDateFrom(row.getYtdatum());
		angebotsKopf.setSubject(row.getYtanfragenum());
		return angebotsKopf;
	}

	private Product artikelAnlegen(DbContext ctx, Row row) {
		ProductEditor productEditor = ctx.newObject(ProductEditor.class);
		productEditor.setSwd(row.getYtkundenartnum());
		productEditor.setDescr(row.getYtartikelbez());
		productEditor.setPackDimLength(row.getYtlaenge());
		productEditor.setPackDimWidth(row.getYtbreite());
		productEditor.setPackDimHeight(row.getYthoehe());
		productEditor.setWeight(row.getYtgewicht());
		productEditor.setDrawingNorm(row.getYtzeichung());
		// productEditor.setReceiptLoc();
		productEditor.commit();
		return productEditor.objectId();
	}

	private Product artikelSuchen(DbContext ctx, String ytkundenartnum) throws AbasException {
		String selectionString = "swd==" + ytkundenartnum + ";@language=en";
		Selection<Product> selection = ExpertSelection.create(Product.class, selectionString);
		Query<Product> queryProduct = ctx.createQuery(selection);
		int zaehl = 0;
		Product uebergabeProd = null;
		for (Product product : queryProduct) {
			zaehl++;
			uebergabeProd = product;
		}
		if (zaehl == 1) {
			return uebergabeProd;
		} else if (zaehl == 0) {
			return null;
		} else {
			throw new AbasException("Es wurden mehrere Artikel mit dem selben Suchwort gefunden.");
		}

	}

	@FieldEventHandler(field = "ykdateiname", type = FieldEventType.FILL)
	public void ykdateinameFill(FieldFillEvent event, ScreenControl screenControl, DbContext ctx, InfoControl head)
			throws EventException {
		// TODO Auto-generated method stub
	}

	@FieldEventHandler(field = "ykdateiname", type = FieldEventType.VALIDATION)
	public void ykdateinameValidation(FieldEvent event, ScreenControl screenControl, DbContext ctx, InfoControl head)
			throws EventException {
		// TODO Auto-generated method stub
	}

	@FieldEventHandler(field = "ykdateiname", type = FieldEventType.EXIT)
	public void ykdateinameExit(FieldEvent event, ScreenControl screenControl, DbContext ctx, InfoControl head)
			throws EventException, IOException {
		// <uk 11.01.16> Es kann jetzt ein beliebiger Pfad angegeben werden. Die
		// Datei wird im Hintergrund auf den Mandanten kopiert
		String orgykdateiname = head.getYkdateiname();

		String workdir = xls2angebotProp.getString("xls2angebot.workdir");
		// Dateiname ermitteln
		String fileName = orgykdateiname.substring(orgykdateiname.lastIndexOf("\\") + 1);
		String newykdateiname = workdir + fileName;
		// zuerst wird versucht ohne die Option -PC zu kopieren (Datei liegt
		// nicht im Mandanten).
		// Sollte dies nicht funktionieren, wird eine andere Varinate des
		// Kopierens verwendet(Java basiert - ,
		// StandardCopyOption.REPLACE_EXISTING hat nicht funktioniert
		if (!FOe.pc_copy("-BIN -QUIET " + orgykdateiname + " " + newykdateiname)) {
			String replace = orgykdateiname.replace("\\", "/");
			String inklWin = "win/" + replace;
			Path copySourcePath = Paths.get(inklWin);
			Path copyTargetPath = Paths.get(newykdateiname);
			Files.delete(copyTargetPath);
			Files.copy(copySourcePath, copyTargetPath);
		}
		head.setYkdateiname(newykdateiname);

	}

}
