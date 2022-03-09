package com.liberty.poker;

import com.liberty.poker.userstory.UserStoryVoteException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = ObjectDomainNotFoundException.class)
    public ResponseEntity<String> objectDomainNotFoundExceptionHandler(final ObjectDomainNotFoundException ex){

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());

    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = EmptyResultDataAccessException.class)
    public ResponseEntity<String> emptyResultDataAccessException(final EmptyResultDataAccessException ex){

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());

    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = UserStoryVoteException.class)
    public ResponseEntity<String> emptyResultDataAccessException(final UserStoryVoteException ex){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());

    }
}
