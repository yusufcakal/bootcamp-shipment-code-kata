package com.trendyol.shipment;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DefaultShipmentSizeCalculator implements ShipmentSizeCalculator{
    private static final int SHIPMENT_SIZE_THRESHOLD = 3;

    @Override
    public ShipmentSize calculateShipmentSize(List<Product> products) {
        int productCount = products.size();

        if (productCount < SHIPMENT_SIZE_THRESHOLD) {
            return findBestShipmentSize(products);
        } else if (allProductsAreXLarge(products)) {
            return ShipmentSize.X_LARGE;
        } else {
            return findUpperShipmentSize(products);
        }
    }

    private ShipmentSize findBestShipmentSize(List<Product> products) {
        return products.stream()
                .map(Product::getSize)
                .max(ShipmentSize::compareTo)
                .orElse(ShipmentSize.SMALL);
    }

    private boolean allProductsAreXLarge(List<Product> products) {
        return products.stream().allMatch(product -> product.getSize() == ShipmentSize.X_LARGE);
    }

    private ShipmentSize findUpperShipmentSize(List<Product> products) {
        ShipmentSize mostCommonSize = findMostCommonShipmentSize(products);

        if (countShipmentSizeInBasket(mostCommonSize, products) >= SHIPMENT_SIZE_THRESHOLD) {
            return incrementShipmentSize(mostCommonSize);
        } else {
            return findBestShipmentSize(products);
        }
    }

    private ShipmentSize incrementShipmentSize(ShipmentSize size) {
        switch (size) {
            case SMALL:
                return ShipmentSize.MEDIUM;
            case MEDIUM:
                return ShipmentSize.LARGE;
            case LARGE:
            default:
                return ShipmentSize.X_LARGE;
        }
    }

    private ShipmentSize findMostCommonShipmentSize(List<Product> products) {
        Map<ShipmentSize, Long> sizeCount = products.stream()
                .map(Product::getSize)
                .collect(Collectors.groupingBy(size -> size, Collectors.counting()));

        return sizeCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(ShipmentSize.SMALL);
    }

    private long countShipmentSizeInBasket(ShipmentSize size, List<Product> products) {
        return products.stream()
                .filter(product -> product.getSize() == size)
                .count();
    }
}
