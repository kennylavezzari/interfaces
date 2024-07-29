public class Question {
    private int id;
    private String questionText;
    private String correctAnswer;
    private String[] wrongAnswers;

    public Question(int id, String questionText, String correctAnswer, String wrongAnswer1, String wrongAnswer2, String wrongAnswer3) {
        this.id = id;
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.wrongAnswers = new String[]{wrongAnswer1, wrongAnswer2, wrongAnswer3};
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pregunta: ").append(questionText).append("\n");
        sb.append("1. ").append(correctAnswer).append("\n");
        sb.append("2. ").append(wrongAnswers[0]).append("\n");
        sb.append("3. ").append(wrongAnswers[1]).append("\n");
        sb.append("4. ").append(wrongAnswers[2]).append("\n");
        return sb.toString();
    }
}
