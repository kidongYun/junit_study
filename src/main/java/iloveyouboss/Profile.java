package iloveyouboss;

import java.util.HashMap;
import java.util.Map;

public class Profile {
    private Map<String, Answer> answers = new HashMap<>();
    private int score;
    private String name;

    public Profile(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void add(Answer answer) {
        answers.put(answer.getQuestionText(), answer);
    }

    public boolean matches(Criteria criteria) {
        score = 0;

        boolean kill = false;
        boolean anyMatches = false;

        // The code of inner for syntax is best important logic in this project..
        // So you should treat this part more considerable than the other.
        for(Criterion criterion: criteria) {
            boolean match = criterion.matches(answerMatching(criterion));

            if(!match && criterion.getWeight() == Weight.MustMatch) {
                kill = true;
            }

            if(match) {
                score += criterion.getWeight().getValue();
            }
            anyMatches |= match;
        }
        if(kill)
            return false;
        return anyMatches;
    }

    public int score() {
        return score;
    }

    private Answer answerMatching(Criterion criterion) {
        return answers.get(criterion.getAnswer().getQuestionText());
    }
}