package com.trendyol.shipment;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

public enum ShipmentSize {

    SMALL(1),
    MEDIUM(2),
    LARGE(3),
    X_LARGE(4);

    private final int order;

    ShipmentSize(int order) {
        this.order = order;
    }

    public int getOrder() {
        return this.order;
    }

    public static int getMaxOrder() {
        Optional<ShipmentSize> maxShipmentSize = Arrays.stream(ShipmentSize.values()).max(Comparator.comparing(ShipmentSize::getOrder));
        return maxShipmentSize.map(ShipmentSize::getOrder).orElse(0);
    }

    public static ShipmentSize getShipmentSizeByOrder(int order) {
        for (ShipmentSize shipmentSize : ShipmentSize.values()) {
            if (shipmentSize.getOrder() == order) {
                return shipmentSize;
            }
        }
        return null;
    }
}
