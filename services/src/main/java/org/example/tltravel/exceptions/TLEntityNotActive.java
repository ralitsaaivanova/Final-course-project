package org.example.tltravel.exceptions;

public class TLEntityNotActive extends BaseTlTravelException{
    private static String exp = "ENTITY.NOT.ACTIVE";

    @Override
    public int getStatus() {
        return 404;
    }


    public TLEntityNotActive(){
        super();
    }

    public TLEntityNotActive(String message) {
        super(exp,message);
    }

    public TLEntityNotActive(String exp,String message) {
        super(exp,message);
    }

    public TLEntityNotActive(String message, Throwable cause) {
        super(message, cause);
    }

    public TLEntityNotActive(Throwable cause) {
        super(exp,cause);
    }

    public TLEntityNotActive(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(exp, message, cause, enableSuppression, writableStackTrace);
    }
}
