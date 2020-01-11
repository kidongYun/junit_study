package iloveyouboss;

import org.json.simple.parser.ParseException;
import org.junit.Test;
import util.Http;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AddressRetrieverTest {

    @Test
    public void answersAppropriateAddressForValidCoordinates() throws IOException, ParseException {
        /** Arrange */
        Http http = mock(Http.class);
        when(http.get(contains("lat=38.000000&lon=-104.000000"))).thenReturn(
                "{\"address\":{"
                        + "\"house_number\":\"324\","
                        // ...
                        + "\"road\":\"North Tejon Street\","
                        + "\"city\":\"Colorado Springs\","
                        + "\"state\":\"Colorado\","
                        + "\"postcode\":\"80903\","
                        + "\"country_code\":\"us\"}"
                        + "}");

        System.out.println(1 + "");

        /** Act */
        AddressRetriever retriever = new AddressRetriever(http);
        Address address = retriever.retrieve(37.0, -104.0);

        System.out.println(2 + "");

        /** Assert */
        assertThat(address.houseNumber, equalTo("324"));
        assertThat(address.road, equalTo("North Tejon Street"));
        assertThat(address.city, equalTo("Colorado Springs"));
        assertThat(address.state, equalTo("Colorado"));
        assertThat(address.zip, equalTo("80903"));
    }
}
