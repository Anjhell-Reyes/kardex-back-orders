package com.kardex.infrastructure.exceptionHandler;

import com.kardex.domain.exception.*;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {

    private static final String MESSAGE = "Message";

    @ExceptionHandler(NotDataFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotDataFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.NOT_DATA_FOUND.getMessage()));
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleOrderNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.ORDER_NOT_FOUND.getMessage()));
    }

    @ExceptionHandler(OrderHistoryNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleOrderHistoryNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.ORDER_HISTORY_NOT_FOUND.getMessage()));
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCartNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.CART_NOT_FOUND.getMessage()));
    }

    @ExceptionHandler(CartEmptyException.class)
    public ResponseEntity<Map<String, String>> handleCartEmptyException() {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.CART_EMPTY.getMessage()));
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<Map<String, String>> handleMissingServletRequestPartException(
            MissingServletRequestPartException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE,
                        String.format(ExceptionResponse.MISSING_PARAMETER.getMessage(), exception.getRequestPartName())));
    }

    @ExceptionHandler(UserIdNotNullException.class)
    public ResponseEntity<Map<String, String>> handleNameNotNullException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.USER_ID_NULL.getMessage()));
    }

    @ExceptionHandler(ProviderIdNotNullException.class)
    public ResponseEntity<Map<String, String>> handleImageNotNullException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.PROVIDER_ID_NULL.getMessage()));
    }

    @ExceptionHandler(ProductIdNotNullException.class)
    public ResponseEntity<Map<String, String>> handleDescriptionNotNullException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.PRODUCT_ID_NULL.getMessage()));
    }

    @ExceptionHandler(QuantityNotNullException.class)
    public ResponseEntity<Map<String, String>> handleQuantityNotNullException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.QUANTITY_NULL.getMessage()));
    }

    @ExceptionHandler(CustomerEmailNotNullException.class)
    public ResponseEntity<Map<String, String>> handleCustomerEmailNotNullException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.CUSTOMER_EMAIL_NULL.getMessage()));
    }

    @ExceptionHandler(InvalidPageIndexException.class)
    public ResponseEntity<Map<String, String>> handleInvalidPageIndexException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.PAGE_INVALID.getMessage()));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleProductNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.PRODUCT_NOT_FOUND.getMessage()));
    }

    @ExceptionHandler(StatusNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleStatusNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.STATUS_NOT_FOUND.getMessage()));
    }
    @ExceptionHandler(FeignUnauthorizedException.class)
    public ResponseEntity<Map<String, String>> handleFeignUnAuthorizedException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.FEIGN_UNAUTHORIZED.getMessage()));
    }

    @ExceptionHandler(NotificationNotNullxception.class)
    public ResponseEntity<Map<String, String>> handleNotificationNotNullxception() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.NOTIFICATION_NOT_NULL.getMessage()));
    }

    @ExceptionHandler(FeignForbiddenException.class)
    public ResponseEntity<Map<String, String>> handleFeignForbiddenException() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.FEIGN_FORBIDDEN.getMessage()));
    }

    @ExceptionHandler(UserForbiddenException.class)
    public ResponseEntity<Map<String, String>> handleUserForbiddenException() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.USER_FORBIDDEN.getMessage()));
    }

    @ExceptionHandler(FeignServerErrorException.class)
    public ResponseEntity<Map<String, String>> handleFeignServerErrorException() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.FEIGN_INTERNAL_SERVER_ERROR.getMessage()));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Map<String, String>> handleExpiredJwtException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.EXPIRED_TIME.getMessage()));
    }

    @ExceptionHandler(EmailSendingException.class)
    public ResponseEntity<Map<String, String>> handleEmailException() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.EMAIL_EXCEPTION.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Map<String, String>>>  handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errorMessages = new HashMap<>();

        // Recorrer todos los errores de validación
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errorMessages.put(error.getField(), error.getDefaultMessage());
        }

        // Crear el objeto final con la clave "errors"
        Map<String, Map<String, String>> response = new HashMap<>();
        response.put(ExceptionResponse.ERROR_KEY.getMessage(), errorMessages);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
