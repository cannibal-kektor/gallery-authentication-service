package kektor.innowise.gallery.authentication.exception.handler;

import kektor.innowise.gallery.authentication.exception.InvalidAccessTokenException;
import kektor.innowise.gallery.authentication.exception.InvalidPasswordException;
import kektor.innowise.gallery.authentication.exception.InvalidRefreshTokenException;
import kektor.innowise.gallery.authentication.exception.TokenExpiredException;
import kektor.innowise.gallery.authentication.exception.TooManyActiveSessionsException;
import kektor.innowise.gallery.authentication.exception.UsernameNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Optional;


@Slf4j
@RestControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({InvalidPasswordException.class, UsernameNotFoundException.class})
    public ErrorResponse handleLoginException(Exception ex) {
        return ErrorResponse.create(ex, HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler({InvalidRefreshTokenException.class, TokenExpiredException.class})
    public ErrorResponse handleRefreshException(Exception ex) {
        if (ex instanceof TokenExpiredException expiredEx) {
            return ErrorResponse.builder(ex, HttpStatus.BAD_REQUEST, ex.getMessage())
                    .property("expiredAt", expiredEx.getExpiredAt())
                    .build();
        }
        return ErrorResponse.create(ex, HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(InvalidAccessTokenException.class)
    public ErrorResponse handleInvalidAccessTokenException(InvalidAccessTokenException ex) {
        return ErrorResponse.create(ex, HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(TooManyActiveSessionsException.class)
    public ErrorResponse handleTooManyActiveSessionsException(TooManyActiveSessionsException ex) {
        return ErrorResponse.create(ex, HttpStatus.TOO_MANY_REQUESTS, ex.getMessage());
    }

    @ExceptionHandler(RestClientResponseException.class)
    ResponseEntity<ProblemDetail> handleRestClientResponseException(RestClientResponseException ex) {
        ProblemDetail detail = ex.getResponseBodyAs(ProblemDetail.class);
        return ResponseEntity.status(ex.getStatusCode()).body(detail);
    }

    @ExceptionHandler({DataAccessException.class, DbActionExecutionException.class})
    public ErrorResponse handleInvalidDatabaseRequestException(Exception ex) {
        return ErrorResponse.create(ex, HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    ErrorResponse handleAll(Exception ex) {
        return ErrorResponse.create(ex, HttpStatus.INTERNAL_SERVER_ERROR, Optional.ofNullable(ex.getMessage())
                .orElse("Internal server error"));
    }

}
