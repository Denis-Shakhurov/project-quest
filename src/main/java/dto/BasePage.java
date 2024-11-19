package dto;

public class BasePage {
    private String flash;
    private String statusAnswer;
    private String question;

    public String getFlash() {
        return flash;
    }

    public void setFlash(String flash) {
        this.flash = flash;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getStatusAnswer() {
        return statusAnswer;
    }

    public void setStatusAnswer(String statusAnswer) {
        this.statusAnswer = statusAnswer;
    }
}
