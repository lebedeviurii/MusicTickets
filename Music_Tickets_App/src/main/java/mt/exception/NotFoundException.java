package mt.exception;

public class NotFoundException extends  RuntimeException{
    public  NotFoundException(String message){
        super(message);
    }
    public static NotFoundException createException(String name, Object id){
        return new NotFoundException( "// " + name + " identified by " + id + " not found.");
    }
}
