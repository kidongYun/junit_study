package iloveyouboss.controller;

import iloveyouboss.domain.Question;
import org.junit.Before;
import org.junit.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class QuestionControllerTest {
    QuestionController controller;

    @Before
    public void setUp() {}
    @Test
    public void questionAnswersDateAdded() {
        Instant now = new Date().toInstant();
        controller.setClock(Clock.fixed(now, ZoneId.of("America/Denver")));
        int id = controller.addBooleanQuestion("text");

        Question question = controller.find(id);
        assertThat(question.getCreateTimestamp(), equalTo(now));
    }
}
