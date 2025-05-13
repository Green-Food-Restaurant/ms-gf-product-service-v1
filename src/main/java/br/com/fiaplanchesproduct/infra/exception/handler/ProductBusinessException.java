package br.com.fiaplanchesproduct.infra.exception.handler;

public class ProductBusinessException extends RuntimeException{

        public ProductBusinessException(String message) {
            super(message);
        }
}
