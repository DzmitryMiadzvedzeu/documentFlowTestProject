package org.doc.com.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DocumentExistsException extends RuntimeException{

    public DocumentExistsException(String message) {
        super(message);
    }
}
