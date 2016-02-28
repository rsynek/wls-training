package org.jboss.training.wascargo;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.junit.Assert;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

public class DataSourceEndpointIT {

    private static final String ENDPOINT_URL = "http://localhost:7001/wls-cargo/ds";
    private static final String DS_JNDI = "jdbc/testds";
    private static final String TABLE_NAME = "test";

    private static final String EXPECTED_RESULT = "thanks for querying me!";

    private String getUrl() {
        return ENDPOINT_URL + "/" + encode(DS_JNDI) + "/" + encode(TABLE_NAME);
    }

    private String encode(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }

    }

    @Test
    public void testTableContent() throws Exception {

        HttpClient httpClient = HttpClients.createDefault();
        HttpUriRequest request = RequestBuilder.get().setUri(getUrl()).build();
        HttpResponse response = httpClient.execute(request);
        InputStream is = response.getEntity().getContent();
        final String result = IOUtils.toString(is);

        Assert.assertEquals(EXPECTED_RESULT, result);
    }
}
