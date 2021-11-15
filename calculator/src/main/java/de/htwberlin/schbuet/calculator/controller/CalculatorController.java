package de.htwberlin.schbuet.calculator.controller;

import de.htwberlin.schbuet.calculator.data.Tax;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Min;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class CalculatorController {

    @GetMapping("/tax-with-rate")
    public Tax taxWithCustomRate(@RequestParam("priceInCents") @Min(0) int priceInCents, @RequestParam("taxRate") Double taxRate) {
        if (taxRate == null) {
            taxRate = 0.0;
        }
        return new Tax(priceInCents, taxRate);
    }

    @GetMapping("/tax")
    public Tax tax(@RequestParam("priceInCents") @Min(0) int priceInCents) {
        return new Tax(priceInCents, 0.19);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMissingParams(MissingServletRequestParameterException ex) {
        String param = ex.getParameterName();
        return "required query parameter '" + param + "' is missing";
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleContraintViolation(ConstraintViolationException ex) {
    	return ex.getMessage();
    }
}
