package pl.zankowski.iextrading4j.client.rest.request.stocks;

import org.junit.Test;
import pl.zankowski.iextrading4j.api.stocks.Quote;
import pl.zankowski.iextrading4j.client.rest.manager.MethodType;
import pl.zankowski.iextrading4j.client.rest.manager.RestRequest;

import javax.ws.rs.core.GenericType;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

public class CollectionRequestBuilderTest {

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionWhenCollectionTypeIsNull() {
        new CollectionRequestBuilder()
                .withCollectionName("name")
                .build();
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionWhenCollectionNameIsNull() {
        new CollectionRequestBuilder()
                .withCollectionType(CollectionType.SECTOR)
                .build();
    }

    @Test
    public void shouldSuccessfullyCreateRequest() {
        final CollectionType collectionType = CollectionType.SECTOR;
        final String collectionName = "name";

        final RestRequest<List<Quote>> request = new CollectionRequestBuilder()
                .withCollectionType(collectionType)
                .withCollectionName(collectionName)
                .build();

        assertThat(request.getMethodType()).isEqualTo(MethodType.GET);
        assertThat(request.getPath()).isEqualTo("/stock/market/collection/{type}");
        assertThat(request.getResponseType()).isEqualTo(new GenericType<List<Quote>>() {});
        assertThat(request.getQueryParams()).contains(entry("collectionName", collectionName));
        assertThat(request.getPathParams()).contains(entry("type", collectionType.name().toLowerCase()));
    }

}
