package de.htwberlin.schbuet.calculator.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaxTest {

    @Test
    void testCalculateTax() {
        var tax = new Tax(100);
        assertEquals(119, tax.getPriceWithTax());
        assertEquals(19, tax.getIncludedTax());
        assertEquals(100, tax.getBasePrice());
        assertEquals(0.19, tax.getTaxRate(), 0.01);
    }

    @Test
    void testCalculateTaxAndRound() {
        var tax = new Tax(1);
        assertEquals(1, tax.getPriceWithTax());
        assertEquals(0, tax.getIncludedTax());
        assertEquals(1, tax.getBasePrice());
        assertEquals(0.19, tax.getTaxRate(), 0.01);
    }

    @Test
    void testCalculateAbsoluteValue() {
        var tax = new Tax(-100);
        assertEquals(119, tax.getPriceWithTax());
        assertEquals(19, tax.getIncludedTax());
        assertEquals(100, tax.getBasePrice());
        assertEquals(0.19, tax.getTaxRate(), 0.01);
    }
}