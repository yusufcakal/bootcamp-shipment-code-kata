package com.trendyol.shipment;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Basket {

    private static final int THRESHOLD_COUNT_OF_SAME_SHIPMENT_SIZE = 3;

    private List<Product> products;

    public ShipmentSize getShipmentSize() {

        if (isEmpty()) {
            throw new EmptyBasketException();
        }

        Map<Integer, Integer> shipmentSizeCountMap = generateShipmentSizeAndCountsMapInBasket();

        List<Integer> shipmentSizeOrderListInDescending = shipmentSizeCountMap.keySet()
                .stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        for (int shipmentSizeOrder : shipmentSizeOrderListInDescending) {
            int count = shipmentSizeCountMap.get(shipmentSizeOrder);
            if (count >= THRESHOLD_COUNT_OF_SAME_SHIPMENT_SIZE) {
                return shipmentSizeOrder == ShipmentSize.getMaxOrder() ?
                        ShipmentSize.getShipmentSizeByOrder(shipmentSizeOrder) : ShipmentSize.getShipmentSizeByOrder(shipmentSizeOrder + 1);
            }
        }

        return ShipmentSize.getShipmentSizeByOrder(shipmentSizeOrderListInDescending.get(0));
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    private boolean isEmpty() {
        return products == null || products.isEmpty();
    }

    private Map<Integer, Integer> generateShipmentSizeAndCountsMapInBasket() {
        Map<Integer, Integer> shipmentSizeCountMap = new HashMap<>();
        products.forEach(e -> shipmentSizeCountMap.put(e.getSize().getOrder(), shipmentSizeCountMap.getOrDefault(e.getSize().getOrder(), 0) + 1));
        return shipmentSizeCountMap;
    }
}
