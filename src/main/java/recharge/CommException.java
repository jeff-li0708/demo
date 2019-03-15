package recharge;

public class CommException extends Exception{
    private int code;

    public CommException(int code, String message) {
        super(message);
        this.code = code;
    }

    public CommException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public int getCode(){
        return this.code;
    }

    public static CommException create(int code, String msg){
        return new CommException(code,msg);
    }
}
