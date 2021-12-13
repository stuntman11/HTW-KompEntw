package de.htwberlin.schbuet.application.service.geo;

public class GeoServiceException extends Exception {
	private static final long serialVersionUID = 462844803647170540L;
	
	public GeoServiceException(String msg) {
		super(msg);
	}
	
	public GeoServiceException(String msg, Throwable inner) {
		super(msg, inner);
	}
}
