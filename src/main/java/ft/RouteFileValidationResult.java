package ft;

public class RouteFileValidationResult {

    private Object errorMessage = null;
    private boolean isValid = true;

    public boolean isValid() {
        return isValid;
    }

    public Object getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }
}
