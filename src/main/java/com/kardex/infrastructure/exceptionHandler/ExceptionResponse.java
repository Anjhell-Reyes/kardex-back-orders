package com.kardex.infrastructure.exceptionHandler;

import lombok.Getter;

@Getter
public enum ExceptionResponse {
    ORDER_NOT_FOUND("Order not found"),
    PRODUCT_NOT_FOUND("Product not found"),
    STATUS_NOT_FOUND("Status not found"),
    CART_NOT_FOUND("Cart not found"),
    ORDER_HISTORY_NOT_FOUND("Order history not found"),
    NOT_DATA_FOUND("No data found"),
    CART_EMPTY("Cart is empty"),
    ORDER_ALREADY_EXISTS("Order already exists"),
    PRODUCT_ID_NULL("Product id must not be null"),
    PROVIDER_ID_NULL("Provider id must not be null"),
    USER_ID_NULL("User id must not be null"),
    QUANTITY_NULL("Quantity must not be null"),
    CUSTOMER_EMAIL_NULL("Customer email must not be null"),
    PAGE_INVALID("Page index must not be less than zero"),
    ERROR_KEY("errors"),
    MISSING_PARAMETER("The required parameter '%s' is missing."),
    EXPIRED_TIME("Your session has expired. Please log in again."),
    FEIGN_UNAUTHORIZED("Not authenticated"),
    FEIGN_FORBIDDEN("Not authorized"),
    FEIGN_INTERNAL_SERVER_ERROR("Internal server error"),
    USER_FORBIDDEN("The user does not have permission to perform this action"),
    EMAIL_EXCEPTION("Error al enviar el correo"),
    NOTIFICATION_NOT_NULL("Product name or Status name must not be null");

    private final String message;

    ExceptionResponse(String message) {
        this.message = message;
    }
}
