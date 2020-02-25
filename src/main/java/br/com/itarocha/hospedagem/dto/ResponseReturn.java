package br.com.itarocha.hospedagem.dto;

public class ResponseReturn {

    private String message;

    public ResponseReturn(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
