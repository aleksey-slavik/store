package com.globallogic.store.rest.validation;

import org.springframework.http.ResponseEntity;

public interface Executable {

    ResponseEntity<?> execute();
}
