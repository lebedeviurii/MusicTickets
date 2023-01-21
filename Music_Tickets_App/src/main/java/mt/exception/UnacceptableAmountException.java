package mt.exception;

public class UnacceptableAmountException extends RuntimeException{
    public UnacceptableAmountException(String message) {
        super(message);
    }
}
