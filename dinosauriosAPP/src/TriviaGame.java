import java.sql.*;
import java.util.*;

public class TriviaGame {
    private List<Question> questions;

    public TriviaGame(Connection connection) throws SQLException {
        questions = getRandomQuestions(connection);
    }

    public List<Question> getRandomQuestions(Connection connection) throws SQLException {
        List<Question> questions = new ArrayList<>();
        String query = "SELECT * FROM questions WHERE difficulty = ? ORDER BY RAND() LIMIT ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (String difficulty : new String[]{"low", "medium", "high"}) {
                stmt.setString(1, difficulty);
                stmt.setInt(2, 3);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Question question = new Question(
                                rs.getInt("id"),
                                rs.getString("question_text"),
                                rs.getString("correct_answer"),
                                rs.getString("wrong_answer_1"),
                                rs.getString("wrong_answer_2"),
                                rs.getString("wrong_answer_3")
                        );
                        questions.add(question);
                    }
                }
            }
        }
        return questions;
    }

    public List<Question> getQuestions() {
        return questions;
    }
}

