package bell.courses.view;

public class ErrorView implements ResponseView {

    private String error;

    public ErrorView(String error){
        this.error = error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
