package com.trendyol.shipment;

import java.util.List;

public class Basket {

    private List<Product> products;
    private final DefaultShipmentSizeCalculator defaultShipmentSizeCalculator = new DefaultShipmentSizeCalculator();
    public ShipmentSize getShipmentSize() {
        return defaultShipmentSizeCalculator.calculateShipmentSize(this.products);
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
