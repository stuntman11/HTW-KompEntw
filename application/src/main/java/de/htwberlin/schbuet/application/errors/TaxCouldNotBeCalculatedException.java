package de.htwberlin.schbuet.application.errors;

public class TaxCouldNotBeCalculatedException extends Exception{

    public TaxCouldNotBeCalculatedException() {
        super("Taxes could not be calculated");
    }
}
