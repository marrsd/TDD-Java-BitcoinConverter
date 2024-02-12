package com.practicejava.tdd.bitcoin;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.util.Locale;

public class ConverterSvc
{
    private final String BITCOIN_CURRENTPRICE_URL = "https://api.coindesk.com/v1/bpi/currentprice.json";
    private final HttpGet httpGet = new HttpGet(BITCOIN_CURRENTPRICE_URL);

    private CloseableHttpClient httpClient;

    public ConverterSvc() {
        this.httpClient = HttpClients.createDefault();
    }

    public ConverterSvc(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public enum Currency {
        USD,
        GBP,
        EUR
    }

    public double getExchangeRate(Currency currency) {
        double rate = 0;

        try (CloseableHttpResponse response = this.httpClient.execute(httpGet)){

            InputStream inputStream = response.getEntity().getContent();
            var json = new BufferedReader(new InputStreamReader(inputStream));

            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            String n = jsonObject.get("bpi").getAsJsonObject().get(String.valueOf(currency)).getAsJsonObject().get("rate").getAsString();
            NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
            rate = numberFormat.parse(n).doubleValue();

        } catch (Exception e) {
            rate = -1;
        }
        return rate;
    }

    public double convertBitcoins(Currency currency, double coins) {
        double dollars = 0;

        if(coins < 0) throw new IllegalArgumentException("Number of coins must not be less than zero");

        var exchangeRate = getExchangeRate(currency);

        if (exchangeRate >= 0) {
            dollars = exchangeRate * coins;
        } else {
            dollars = -1;
        }
        return dollars;
    }
}