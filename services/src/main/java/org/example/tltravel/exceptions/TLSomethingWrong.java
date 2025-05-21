package org.example.tltravel.exceptions;

public class TLSomethingWrong extends BaseTlTravelException{
    private  static String exp = "SOMETHING.IS.WRONG";

    public TLSomethingWrong() {
        super(exp);
    }

    public TLSomethingWrong(String message) {
        super(exp, message);
    }

    public TLSomethingWrong(String code, Throwable cause) {
        super(exp,code, cause);
    }

    public TLSomethingWrong(String code, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(exp,code, message, cause, enableSuppression, writableStackTrace);
    }
}
