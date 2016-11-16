package com.companyname.framework;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.Future;

public class RestClient {
    private static final Logger logger = LoggerFactory.getLogger(RestClient.class);

    static final String ENCODING = "UTF-8";
    static final String CONTENT_TYPE = "application/json";


    public final static HttpResponse get(String url, List<NameValuePair> parameters) throws Throwable {
        CloseableHttpAsyncClient client = HttpAsyncClients.createDefault();

        StringBuffer sb = new StringBuffer("?");
        for (NameValuePair pair : parameters) {
            sb.append(pair.getName());
            sb.append("=");
            sb.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
            sb.append("&");
        }

        client.start();
        final HttpGet httpGet = new HttpGet(url + sb.toString());
        httpGet.removeHeaders("X-FORWARDED-FOR");
        httpGet.setHeader("X-FORWARDED-FOR", Environment.LOCAL_IP_ADDR);

        logger.debug("-> GET " + (url + sb.toString()));

        Future<HttpResponse> future = client.execute(httpGet, null);
        HttpResponse resp = future.get();

        return resp;
    }

    public final static HttpResponse post(String url, List<NameValuePair> parameters) throws Throwable {
        CloseableHttpAsyncClient client = HttpAsyncClients.createDefault();

        client.start();
        final HttpPost httpPost = new HttpPost(url);
        httpPost.removeHeaders("X-FORWARDED-FOR");
        httpPost.setHeader("X-FORWARDED-FOR", Environment.LOCAL_IP_ADDR);
        UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(parameters, "UTF-8");
        httpPost.setEntity(encodedFormEntity);

        logger.debug("-> POST " + url + " Parameters " + JsonUtil.toString(parameters));

        Future<HttpResponse> future = client.execute(httpPost, null);
        HttpResponse resp = future.get();

        return resp;
    }

    public final static HttpResponse post(String url, Object model) throws Throwable {
        CloseableHttpAsyncClient client = HttpAsyncClients.createDefault();

        client.start();
        final HttpPost httpPost = new HttpPost(url);
        httpPost.removeHeaders("X-FORWARDED-FOR");
        httpPost.setHeader("X-FORWARDED-FOR", Environment.LOCAL_IP_ADDR);
        StringEntity stringEntity = new StringEntity(JsonUtil.toString(model), ENCODING);
        stringEntity.setContentType(CONTENT_TYPE);
        httpPost.setEntity(stringEntity);

        logger.debug("-> POST " + url + " Parameters " + JsonUtil.toString(model));

        Future<HttpResponse> future = client.execute(httpPost, null);
        HttpResponse resp = future.get();

        return resp;
    }

    public final static HttpResponse post(String url, Object model, HttpEntity httpEntity) throws Throwable {
        CloseableHttpAsyncClient client = HttpAsyncClients.createDefault();

        client.start();
        final HttpPost httpPost = new HttpPost(url);
        httpPost.removeHeaders("X-FORWARDED-FOR");
        httpPost.setHeader("X-FORWARDED-FOR", Environment.LOCAL_IP_ADDR);
        httpPost.setEntity(httpEntity);

        logger.debug("-> POST " + url + " httpEntity  Parameters " + JsonUtil.toString(model));

        Future<HttpResponse> future = client.execute(httpPost, null);
        HttpResponse resp = future.get();

        return resp;
    }
}
