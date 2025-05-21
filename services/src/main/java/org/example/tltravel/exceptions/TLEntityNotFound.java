package org.example.tltravel.exceptions;

public class TLEntityNotFound extends BaseTlTravelException{
    private static String exp = "ENTITY.NOT.FOUND";

    @Override
    public int getStatus() {
        return 404;
    }

    public TLEntityNotFound(){
        super();
    }

    public TLEntityNotFound(String message) {
        super(exp,message);
    }

    public TLEntityNotFound(String exp,String message) {
        super(exp,message);
    }

    public TLEntityNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public TLEntityNotFound(Throwable cause) {
        super(exp,cause);
    }

    public TLEntityNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(exp, message, cause, enableSuppression, writableStackTrace);
    }
}
