package com.kardex.infrastructure.exceptionHandler;
import com.kardex.domain.exception.*;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

public class FeignErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        HttpStatus status = HttpStatus.valueOf(response.status());

        return switch (status) {
            case NOT_FOUND ->  // 404
                    new ProductNotFoundException();
            case BAD_REQUEST ->  // 404
                    new NotificationNotNullxception();
            case FORBIDDEN -> // 403
                    new FeignForbiddenException();
            case UNAUTHORIZED -> // 401
                    new FeignUnauthorizedException();
            case INTERNAL_SERVER_ERROR -> // 500
                    new FeignServerErrorException();
            default -> defaultErrorDecoder.decode(methodKey, response);
        };
    }
}
