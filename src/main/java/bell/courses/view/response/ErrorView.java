package bell.courses.view.response;

public class ErrorView {
    private String error;

    public ErrorView(String error) {
        this.error = error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}