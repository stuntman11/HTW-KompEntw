package de.htwberlin.schbuet.application.errors;

public class TaxCouldNotBeCalculatedException extends RuntimeException {
	private static final long serialVersionUID = -5832853185293617407L;

	public TaxCouldNotBeCalculatedException() {
		this(null);
    }
	
	public TaxCouldNotBeCalculatedException(Throwable inner) {
        super("Taxes could not be calculated", inner);
    }
}
