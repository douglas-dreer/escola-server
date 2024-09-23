package br.com.escola_server.exceptions;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.orm.jpa.JpaSystemException;

public class BusinessException extends RuntimeException {

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(HttpStatus httpStatus, String identificationNotEquals) {
    }

    public static PersistException fromPersistenceException(Exception e) {
        Throwable cause = e.getCause();

        if (cause instanceof ConstraintViolationException cve) {
            return new PersistException("Erro de restrição de integridade: " + cve.getConstraintName(), cve);
        } else if (cause instanceof DataIntegrityViolationException) {
            return new PersistException("Erro de integridade de dados.", cause);
        } else if (cause instanceof JpaSystemException) {
            return new PersistException("Erro ao persistir entidade.", e);
        } else {
            return new PersistException("Erro ao persistir entidade.", e);
        }
    }
}