package com.cravebite.backend_2.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CraveBiteGlobalExceptionHandler extends RuntimeException {

    private HttpStatus status;

    public CraveBiteGlobalExceptionHandler(String message) {
        super(message);
    }

    public CraveBiteGlobalExceptionHandler(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
