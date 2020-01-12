package util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.logging.Level;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static util.ContainsMatches.containsMatches;

public class SearchTest {
    private static final String A_TITLE = "1";
    private InputStream stream;

    @Before
    public void turnOffLogging() {
        Search.LOGGER.setLevel(Level.OFF);
    }

    @After
    public void closeResources() throws IOException {
        stream.close();
    }

    @Test
    public void returnsMatchesShowingContextWhenSearchStringInContent() throws IOException {
        /** Arrange */
        stream = streamOn("rest of text here"
                + "1234567890search term1234567890"
                + "more rest of text");
        Search search = new Search(stream, "search term", A_TITLE);
        search.setSurroundingCharacterCount(10);

        /** Act */
        search.execute();

        /** Assert */
        assertThat(search.getMatches(), containsMatches(new Match[]
                { new Match(A_TITLE,
                        "search term",
                        "1234567890search term1234567890") }));
    }

    @Test
    public void noMatchesReturnedWhenSearchStringNotInContent() throws MalformedURLException, IOException {
        /** Arrange */
        URLConnection connection = new URL("http://bit.ly/15sYPA7").openConnection();
        stream = connection.getInputStream();
        Search search = new Search(stream, "smelt", A_TITLE);

        /** Act */
        search.execute();

        /** Assert */
        assertTrue(search.getMatches().isEmpty());
    }

    private InputStream streamOn(String pageContent) {
        return new ByteArrayInputStream(pageContent.getBytes());
    }
}
