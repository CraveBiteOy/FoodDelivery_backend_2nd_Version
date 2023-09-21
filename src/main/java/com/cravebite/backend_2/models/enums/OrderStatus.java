package com.cravebite.backend_2.models.enums;

public enum OrderStatus {

    ORDERED,
    REJECTED_BY_RESTAURANT,
    PREPARING,
    READY,
    ACCEPTED_BY_COURIER,
    REJECTED_BY_COURIER,
    PICKED_UP,
    DELIVERED,
    NO_COURIERS_AVAILABLE

}
