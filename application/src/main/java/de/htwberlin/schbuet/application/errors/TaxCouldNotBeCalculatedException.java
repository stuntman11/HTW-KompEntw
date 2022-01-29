package de.htwberlin.schbuet.application.errors;

public class TaxCouldNotBeCalculatedException extends Exception{
	private static final long serialVersionUID = -5832853185293617407L;

	public TaxCouldNotBeCalculatedException() {
        super("Taxes could not be calculated");
    }
}
