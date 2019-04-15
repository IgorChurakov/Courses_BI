package bell.courses.view;

public class ResultView implements ResponseView {

    private String result;

    public ResultView(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
