package com.practicejava.tdd.bitcoin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.practicejava.tdd.bitcoin.ConverterSvc;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ConverterSvcTest {

    private CloseableHttpClient client;
    private CloseableHttpResponse response;
    private StatusLine statusLine;
    private HttpEntity entity;
    private InputStream stream;

    @BeforeEach
    public void setUp() {
        client = mock(CloseableHttpClient.class);
        response = mock(CloseableHttpResponse.class);
        statusLine = mock(StatusLine.class);
        entity = mock(HttpEntity.class);

        stream = new ByteArrayInputStream("{\"time\": {\"updated\": \"Feb 8, 2024 19:38:40 UTC\", \"updatedISO\": \"2024-02-08T19:38:40+00:00\",\"updateduk\": \"Feb 8, 2024 at 19:38 GMT\"}, \"disclaimer\": \"This data was produced from the CoinDesk Bitcoin Price Index (USD). Non-USD currency data converted using hourly conversion rate from openexchangerates.org\", \"chartName\": \"Bitcoin\", \"bpi\": {\"USD\": {\"code\": \"USD\", \"symbol\": \"&#36;\", \"rate\": \"45,339.608\", \"description\": \"United States Dollar\", \"rate_float\": 45339.6082}, \"GBP\": {\"code\": \"GBP\", \"symbol\": \"&pound;\", \"rate\": \"35,928.647\", \"description\": \"British Pound Sterling\", \"rate_float\": 35928.6471}, \"EUR\": {\"code\": \"EUR\", \"symbol\": \"&euro;\", \"rate\": \"42,064.864\", \"description\": \"Euro\", \"rate_float\": 42064.8643}}}".getBytes());
    }


    @Test
    void getExchangeRate_USD_ReturnsUSDExchangeRate() throws IOException {
        // Arrange
        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(entity.getContent()).thenReturn(stream);
        when(response.getEntity()).thenReturn(entity);
        when(client.execute(any(HttpGet.class))).thenReturn(response);

        // Act
        ConverterSvc converterSvc = new ConverterSvc(client);
        var actual = converterSvc.getExchangeRate(ConverterSvc.Currency.USD);

        //Assert
        double expected = 45339.608;
        assertEquals(expected, actual);
    }

    @Test
    void getExchangeRate_GBP_ReturnGBP_ExchangeRate() throws IOException {
       //Arrange
       when(statusLine.getStatusCode()).thenReturn(200);
       when(entity.getContent()).thenReturn(stream);
       when(response.getEntity()).thenReturn(entity);
       when(response.getStatusLine()).thenReturn(statusLine);
       when(client.execute(any(HttpGet.class))).thenReturn(response);

       //Act
        ConverterSvc converterSvc = new ConverterSvc(client);
        var actual = converterSvc.getExchangeRate(ConverterSvc.Currency.GBP);

        //Assert
        double expected = 35928.647;
        assertEquals(expected, actual);
    }

    @Test
    public void getExchangeRate_EUR_ReturnsEURExchangeRate() throws IOException {
        //arrange
        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(response.getEntity()).thenReturn(entity);
        when(entity.getContent()).thenReturn(stream);
        when(client.execute(any(HttpGet.class))).thenReturn(response);

        //act
        ConverterSvc converterSvc = new ConverterSvc(client);
        var actual = converterSvc.getExchangeRate(ConverterSvc.Currency.EUR);

        //assert
        double expected = 42064.864;
        assertEquals(expected, actual);
    }

    @Test
    public void convertBitcoins_1BitCoinToUSD_ReturnsUSDDollars() throws IOException {
        //arrange
        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(response.getEntity()).thenReturn(entity);
        when(entity.getContent()).thenReturn(stream);
        when(client.execute(any(HttpGet.class))).thenReturn(response);

        //act
        ConverterSvc converterSvc = new ConverterSvc(client);
        var actual = converterSvc.convertBitcoins(ConverterSvc.Currency.USD, 1);

        //assert
        double expected = 45339.608;
        assertEquals(expected, actual);
    }

    @Test
    public void convertBitcoins_2BitCoinToUSD_ReturnsUSDDollars() throws IOException {
        //arrange
        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(response.getEntity()).thenReturn(entity);
        when(entity.getContent()).thenReturn(stream);
        when(client.execute(any(HttpGet.class))).thenReturn(response);

        //act
        ConverterSvc converterSvc = new ConverterSvc(client);
        var actual = converterSvc.convertBitcoins(ConverterSvc.Currency.USD, 2);

        //assert
        double expected = 90679.216;
        assertEquals(expected, actual);
    }

    @Test
    public void convertBitcoins_0BitCoinToUSD_ReturnsZeroUSDDollars() throws IOException {
        //arrange
        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(response.getEntity()).thenReturn(entity);
        when(entity.getContent()).thenReturn(stream);
        when(client.execute(any(HttpGet.class))).thenReturn(response);

        //act
        ConverterSvc converterSvc = new ConverterSvc(client);
        var actual = converterSvc.convertBitcoins(ConverterSvc.Currency.USD, 0);

        //assert
        double expected = 0;
        assertEquals(expected, actual);
    }

    @Test
    public void convertBitcoins_1BitCoinToGBP_ReturnsGBPDollars() throws IOException {
        //arrange
        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(response.getEntity()).thenReturn(entity);
        when(entity.getContent()).thenReturn(stream);
        when(client.execute(any(HttpGet.class))).thenReturn(response);

        //act
        ConverterSvc converterSvc = new ConverterSvc(client);
        var actual = converterSvc.convertBitcoins(ConverterSvc.Currency.GBP, 1);

        //assert
        double expected = 35928.647;
        assertEquals(expected, actual);
    }

    @Test
    public void convertBitcoins_2BitCoinToGBP_ReturnsGBPDollars() throws IOException {
        //arrange
        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(response.getEntity()).thenReturn(entity);
        when(entity.getContent()).thenReturn(stream);
        when(client.execute(any(HttpGet.class))).thenReturn(response);

        //act
        ConverterSvc converterSvc = new ConverterSvc(client);
        var actual = converterSvc.convertBitcoins(ConverterSvc.Currency.GBP, 2);

        //assert
        double expected = 71857.294;
        assertEquals(expected, actual);
    }

    @Test
    public void convertBitcoins_0BitCoinToGBP_ReturnsZeroGBPDollars() throws IOException {
        //arrange
        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(response.getEntity()).thenReturn(entity);
        when(entity.getContent()).thenReturn(stream);
        when(client.execute(any(HttpGet.class))).thenReturn(response);

        //act
        ConverterSvc converterSvc = new ConverterSvc(client);
        var actual = converterSvc.convertBitcoins(ConverterSvc.Currency.GBP, 0);

        //assert
        double expected = 0;
        assertEquals(expected, actual);
    }

    @Test
    public void convertBitcoins_1BitCoinToEUR_ReturnsEURDollars() throws IOException {
        //arrange
        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(response.getEntity()).thenReturn(entity);
        when(entity.getContent()).thenReturn(stream);
        when(client.execute(any(HttpGet.class))).thenReturn(response);

        //act
        ConverterSvc converterSvc = new ConverterSvc(client);
        var actual = converterSvc.convertBitcoins(ConverterSvc.Currency.EUR, 1);

        //assert
        double expected = 42064.864;
        assertEquals(expected, actual);
    }

    @Test
    public void convertBitcoins_2BitCoinToEUR_ReturnsEURDollars() throws IOException {
        //arrange
        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(response.getEntity()).thenReturn(entity);
        when(entity.getContent()).thenReturn(stream);
        when(client.execute(any(HttpGet.class))).thenReturn(response);

        //act
        ConverterSvc converterSvc = new ConverterSvc(client);
        var actual = converterSvc.convertBitcoins(ConverterSvc.Currency.EUR, 2);

        //assert
        double expected = 84129.728;
        assertEquals(expected, actual);
    }

    @Test
    public void convertBitcoins_0BitCoinToEUR_ReturnsZeroEURDollars() throws IOException {
        //arrange
        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(response.getEntity()).thenReturn(entity);
        when(entity.getContent()).thenReturn(stream);
        when(client.execute(any(HttpGet.class))).thenReturn(response);

        //act
        ConverterSvc converterSvc = new ConverterSvc(client);
        var actual = converterSvc.convertBitcoins(ConverterSvc.Currency.EUR, 0);

        //assert
        double expected = 0;
        assertEquals(expected, actual);
    }

    @Test
    public void convertBitcoins_NegativeBitcoinToUSDExchangeRate() throws IOException {
        //arrange
        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(response.getEntity()).thenReturn(entity);
        when(entity.getContent()).thenReturn(stream);
        when(client.execute(any(HttpGet.class))).thenReturn(response);

        //act
        ConverterSvc converterSvc = new ConverterSvc(client);

        //assert
        assertThrows(IllegalArgumentException.class, () -> converterSvc.convertBitcoins(ConverterSvc.Currency.USD, -1));
    }

    @Test
    public void convertBitcoins_503ServiceUnavailable_ReturnsCorrectErrorCode() throws IOException {
        //arrange
        when(statusLine.getStatusCode()).thenReturn(503);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(client.execute(any(HttpGet.class))).thenReturn(response);

        //act
        ConverterSvc converterSvc = new ConverterSvc(client);
        var actual = converterSvc.convertBitcoins(ConverterSvc.Currency.USD, 2);

        //assert
        double expected = -1;
        assertEquals(expected, actual);
    }
}
