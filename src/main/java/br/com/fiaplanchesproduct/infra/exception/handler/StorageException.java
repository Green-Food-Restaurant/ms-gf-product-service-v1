package br.com.fiaplanchesproduct.infra.exception.handler;

public class StorageException extends RuntimeException{

    public StorageException(String message, Exception e) {
        super(message);
    }
}
