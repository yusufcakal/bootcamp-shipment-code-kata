package com.trendyol.shipment;

public class EmptyBasketException extends RuntimeException {

	public EmptyBasketException() {
		super("The basket is empty!");
	}
}
