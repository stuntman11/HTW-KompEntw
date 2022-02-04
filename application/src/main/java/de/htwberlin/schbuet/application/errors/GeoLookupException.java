package de.htwberlin.schbuet.application.errors;

public class GeoLookupException extends Exception {
	private static final long serialVersionUID = 462844803647170540L;
	
	public GeoLookupException() {
		super("Geo service returned no results for the specified address");
	}

	public GeoLookupException(String msg) {
		super(msg);
	}
	
	public GeoLookupException(String msg, Throwable inner) {
		super(msg, inner);
	}
}
