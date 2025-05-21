package org.example.tltravel.exceptions;

public class BaseTlTravelException extends Exception{

    protected String code="BASE.EXCEPTION.MESSAGE";
    private int status = 500;

    public BaseTlTravelException(String exp) {
    }

    public BaseTlTravelException(String exp, String code, Throwable cause) {
    }

    public BaseTlTravelException(String exp, String code, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    }

    public int getStatus() {
        return status;
    }

    public BaseTlTravelException(){}


    public BaseTlTravelException(String code,String message ) {
        super(message);
        this.code = code;
    }


    public BaseTlTravelException( String code,Throwable cause) {
        super(cause);
        this.code = code;
    }

    public BaseTlTravelException( String code,String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
