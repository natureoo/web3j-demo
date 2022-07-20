//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.web3j.protocol.http;

import okhttp3.*;
import okhttp3.OkHttpClient.Builder;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import okio.Buffer;
import okio.BufferedSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.Service;
import org.web3j.protocol.exceptions.ClientConnectionException;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpService extends Service {
    private static final CipherSuite[] INFURA_CIPHER_SUITES;
    private static final ConnectionSpec INFURA_CIPHER_SUITE_SPEC;
    private static final List<ConnectionSpec> CONNECTION_SPEC_LIST;
    public static final MediaType JSON_MEDIA_TYPE;
    public static final String DEFAULT_URL = "http://localhost:8545/";
    private static final Logger log;
    private OkHttpClient httpClient;
    private final String url;
    private final boolean includeRawResponse;
    private HashMap<String, String> headers;

    public HttpService(String url, OkHttpClient httpClient, boolean includeRawResponses) {
        super(includeRawResponses);
        this.headers = new HashMap();
        this.url = url;
        this.httpClient = httpClient;
        this.includeRawResponse = includeRawResponses;
    }

    public HttpService(OkHttpClient httpClient, boolean includeRawResponses) {
        this("http://localhost:8545/", httpClient, includeRawResponses);
    }

    public HttpService(String url, OkHttpClient httpClient) {
        this(url, httpClient, false);
    }

    public HttpService(String url) {
        this(url, createOkHttpClient());
    }

    public HttpService(String url, boolean includeRawResponse) {
        this(url, createOkHttpClient(), includeRawResponse);
    }

    public HttpService(OkHttpClient httpClient) {
        this("http://localhost:8545/", httpClient);
    }

    public HttpService(boolean includeRawResponse) {
        this("http://localhost:8545/", includeRawResponse);
    }

    public HttpService() {
        this("http://localhost:8545/");
    }

    public static Builder getOkHttpClientBuilder() {
        Builder builder = (new Builder()).connectionSpecs(CONNECTION_SPEC_LIST);
        configureLogging(builder);
        return builder;
    }

    private static OkHttpClient createOkHttpClient() {
        return getOkHttpClientBuilder().build();
    }

    private static void configureLogging(Builder builder) {
        if (log.isDebugEnabled()) {
            Logger var10002 = log;
            var10002.getClass();
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor(var10002::debug);
            logging.setLevel(Level.BODY);
            builder.addInterceptor(logging);
        }

    }

    protected InputStream performIO(String request) throws IOException {
        RequestBody requestBody = RequestBody.create( JSON_MEDIA_TYPE, request);
        Headers headers = this.buildHeaders();
        Request httpRequest = (new okhttp3.Request.Builder()).url(this.url).headers(headers).post(requestBody).build();
        Response response = this.httpClient.newCall(httpRequest).execute();
        Throwable var6 = null;

        InputStream var8;
        try {
            this.processHeaders(response.headers());
            ResponseBody responseBody = response.body();
            if (!response.isSuccessful()) {
                int code = response.code();
                String text = responseBody == null ? "N/A" : responseBody.string();
                throw new ClientConnectionException("Invalid response received: " + code + "; " + text);
            }

            if (responseBody == null) {
                var8 = null;
                return var8;
            }

            var8 = this.buildInputStream(responseBody);
        } catch (Throwable var18) {
            var6 = var18;
            throw var18;
        } finally {
            if (response != null) {
                if (var6 != null) {
                    try {
                        response.close();
                    } catch (Throwable var17) {
                        var6.addSuppressed(var17);
                    }
                } else {
                    response.close();
                }
            }

        }

        return var8;
    }

    protected void processHeaders(Headers headers) {
    }

    private InputStream buildInputStream(ResponseBody responseBody) throws IOException {
        if (this.includeRawResponse) {
            BufferedSource source = responseBody.source();
            source.request(9223372036854775807L);
            Buffer buffer = source.buffer();
            long size = buffer.size();
            if (size > 2147483647L) {
                throw new UnsupportedOperationException("Non-integer input buffer size specified: " + size);
            } else {
                int bufferSize = (int)size;
                InputStream inputStream = responseBody.byteStream();
                BufferedInputStream bufferedinputStream = new BufferedInputStream(inputStream, bufferSize);
                bufferedinputStream.mark(inputStream.available());
                return bufferedinputStream;
            }
        } else {
            return new ByteArrayInputStream(responseBody.bytes());
        }
    }

    private Headers buildHeaders() {
        return Headers.of(this.headers);
    }

    public void addHeader(String key, String value) {
        this.headers.put(key, value);
    }

    public void addHeaders(Map<String, String> headersToAdd) {
        this.headers.putAll(headersToAdd);
    }

    public HashMap<String, String> getHeaders() {
        return this.headers;
    }

    public String getUrl() {
        return this.url;
    }

    public void close() throws IOException {
    }

    static {
        INFURA_CIPHER_SUITES = new CipherSuite[]{CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_RSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_RSA_WITH_3DES_EDE_CBC_SHA, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384, CipherSuite.TLS_RSA_WITH_AES_128_CBC_SHA256, CipherSuite.TLS_RSA_WITH_AES_256_CBC_SHA256};
        INFURA_CIPHER_SUITE_SPEC = (new okhttp3.ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)).cipherSuites(INFURA_CIPHER_SUITES).build();
        CONNECTION_SPEC_LIST = Arrays.asList(INFURA_CIPHER_SUITE_SPEC, ConnectionSpec.CLEARTEXT);
        JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
        log = LoggerFactory.getLogger(HttpService.class);
    }
}
