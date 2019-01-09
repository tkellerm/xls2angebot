package de.abasgmbh.stahl.infosystem.xls2angebot;

import java.util.Locale;
import java.util.ResourceBundle;

import de.abas.eks.jfop.remote.EKS;

public class Xsl2AngebotResourceBundle {
	private static Locale locale_de = Locale.GERMAN;
	public ResourceBundle xls2angebotProp;

	public Xsl2AngebotResourceBundle(ResourceBundle xls2angebotProp) {
		this.xls2angebotProp = xls2angebotProp;
	}

	private static Locale getLocale() {
		try {
			return EKS.getFOPSessionContext().getOperatingLangLocale();
		} catch (final NullPointerException e) {
			return locale_de;
		}
	}

	public static ResourceBundle getResourceBundleXLS2Angebot() {
		return ResourceBundle.getBundle("de.abasgmbh.stahl.infosystem.xls2angebot.xls2angebot", getLocale());
	}
}