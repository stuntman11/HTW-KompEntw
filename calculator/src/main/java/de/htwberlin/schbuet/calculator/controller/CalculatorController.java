package de.htwberlin.schbuet.calculator.controller;

import de.htwberlin.schbuet.calculator.data.Tax;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculatorController {

	@GetMapping("/tax")
	public Tax tax(@RequestParam("priceInCents") int priceInCents) {
		return new Tax(priceInCents);
	}
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleMissingParams(MissingServletRequestParameterException ex) {
	    String param = ex.getParameterName();
	    return "required query parameter '" + param + "' is missing";
	}
}
