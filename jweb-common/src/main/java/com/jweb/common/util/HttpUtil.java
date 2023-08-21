package com.jweb.common.util;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Slf4j
public class HttpUtil {
	private static volatile OkHttpClient okHttpClient = null;
    private static volatile Semaphore semaphore = null;
    private Map<String, String> headerMap;
    private Map<String, String> paramMap;
    private String paramJson;
    private long connectTimeout = 30;
    private long writeTimeout = 30;
    private long readTimeout = 30;
    private String url;
    private Request.Builder request;

    /**
     * 初始化okHttpClient，并且允许https访问
     */
    private HttpUtil() {
        if (okHttpClient == null) {
            synchronized (HttpUtil.class) {
                if (okHttpClient == null) {
                    TrustManager[] trustManagers = buildTrustManagers();
                    okHttpClient = new OkHttpClient.Builder()
                            .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                            .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                            .readTimeout(readTimeout, TimeUnit.SECONDS)
                            .sslSocketFactory(createSSLSocketFactory(trustManagers), (X509TrustManager) trustManagers[0])
                            .hostnameVerifier((hostName, session) -> true)
                            .retryOnConnectionFailure(true)
                            .build();
                    addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
                }
            }
        }
    }

    /**
     * 用于异步请求时，控制访问线程数，返回结果
     *
     * @return
     */
    private static Semaphore getSemaphoreInstance() {
        //只能1个线程同时访问
        synchronized (HttpUtil.class) {
            if (semaphore == null) {
                semaphore = new Semaphore(0);
            }
        }
        return semaphore;
    }

    /**
     * 创建OkHttpUtils
     *
     * @return
     */
    public static HttpUtil builder() {
        return new HttpUtil();
    }
    
    /**
     * 设置超时时间
     * @param connectTimeout 连接超时时间，大于0，默认30，单位秒
     * @param writeTimeout 写超时时间，大于0，默认30，单位秒
     * @param readTimeout 读超时时间，大于0，默认30，单位秒
     * @return
     */
    public HttpUtil setTimeout(long connectTimeout, long writeTimeout, long readTimeout) {
    	if(connectTimeout > 0) {
    		this.connectTimeout = connectTimeout;
    	}
    	if(writeTimeout > 0) {
    		this.writeTimeout = writeTimeout;
    	}
    	if(readTimeout > 0) {
    		this.readTimeout = readTimeout;
    	}
    	return this;
    }

    /**
     * 添加url
     *
     * @param url
     * @return
     */
    public HttpUtil url(String url) {
        this.url = url;
        return this;
    }

    /**
     * 添加参数
     * 
     * @param key   参数名
     * @param value 参数值
     * @return
     */
    public HttpUtil addParam(String key, String value) {
        if (paramMap == null) {
            paramMap = new LinkedHashMap<>(16);
        }
        paramMap.put(key, value);
        return this;
    }
    /**
     * 添加参数
     * @param json json参数
     * @return
     */
    public HttpUtil addParam(String json) {
    	this.paramJson = json;
        return this;
    }

    /**
     * 添加请求头
     *
     * @param key   参数名
     * @param value 参数值
     * @return
     */
    public HttpUtil addHeader(String key, String value) {
        if (headerMap == null) {
            headerMap = new LinkedHashMap<>(16);
        }
        headerMap.put(key, value);
        return this;
    }
    
    /**
     * 添加请求头
     *
     * @param key   参数名
     * @param value 参数值
     * @return
     */
    public HttpUtil addHeader(Map<String, String> headers) {
        if (headerMap == null) {
            headerMap = new LinkedHashMap<>(16);
        }
        if(headers == null || headers.isEmpty()) {
        	return this;
        }
        headerMap.putAll(headers);
        return this;
    }
    /**
     * 初始化get方法
     *
     * @return
     */
    public HttpUtil get() {
        request = new Request.Builder().get();
        StringBuilder urlBuilder = new StringBuilder(url);
        if (paramMap != null) {
            urlBuilder.append("?");
            try {
                for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                    urlBuilder.append(URLEncoder.encode(entry.getKey(), "utf-8")).
                            append("=").
                            append(URLEncoder.encode(entry.getValue(), "utf-8")).
                            append("&");
                }
            } catch (Exception e) {
                log.error("拼接GET请求参数出错：{}", url, e);
            }
            urlBuilder.deleteCharAt(urlBuilder.length() - 1);
        }
        request.url(urlBuilder.toString());
        return this;
    }

    /**
     * 初始化post方法
     *
     * @param isJsonPost true等于json的方式提交数据，类似postman里post方法的raw
     *                   false等于普通的表单提交
     * @return
     */
    public HttpUtil post(boolean isJsonPost) {
        RequestBody requestBody;
        if (isJsonPost) {
            if (paramMap != null) {
            	paramJson = JSON.toJSONString(paramMap);
            }
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), paramJson);
        } else {
            FormBody.Builder formBody = new FormBody.Builder();
            if (paramMap != null) {
                paramMap.forEach(formBody::add);
            }
            requestBody = formBody.build();
        }
        request = new Request.Builder().post(requestBody).url(url);
        return this;
    }
    /**
     * 初始化文件流post方法
     * @param file
     * @return
     */
    public HttpUtil multipartPost(MultipartFile file) {
    	try {
			MultipartBody.Builder builder = new MultipartBody.Builder()
					.setType(MultipartBody.FORM)
					.addFormDataPart("file", file.getOriginalFilename(), RequestBody.create(MediaType.parse("application/octet-stream"), file.getBytes()));
			if (paramMap != null) {
				for (Map.Entry<String, String> entry : paramMap.entrySet()) {
					builder.addFormDataPart(entry.getKey(), entry.getValue());
				}
			}
			RequestBody requestBody = builder.build();
			request = new Request.Builder().post(requestBody).url(url);
		    return this;
		} catch (IOException e) {
			log.error("构建Multipart请求出错", e);
			return this;
		}
    }

    /**
     * 同步请求
     *
     * @return
     */
    public HttpResult sync() {
        setHeader(request);
        try {
            Response response = okHttpClient.newCall(request.build()).execute();
            assert response.body() != null;
            String responseInfo = response.body().string();
            log.info("request {} response：{}", url, responseInfo);
            return HttpResult.ok(responseInfo);
        } catch (IOException e) {
        	log.error("request error：{}", url, e);
            return HttpResult.fail("request error：" + e.getMessage());
        }
    }

    /**
     * 异步请求，有返回值
     */
    public HttpResult async() {
        StringBuilder buffer = new StringBuilder("");
        setHeader(request);
        okHttpClient.newCall(request.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            	log.error("request error：{}", url, e);
                buffer.append("request error：").append(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                assert response.body() != null;
                String responseInfo = response.body().string();
                log.info("request {} response：{}", url, responseInfo);
                buffer.append(responseInfo);
                getSemaphoreInstance().release();
            }
        });
        try {
            getSemaphoreInstance().acquire();
        } catch (InterruptedException e) {
        	log.error("request error：{}", url, e);
        }
        return HttpResult.ok(buffer.toString());
    }

    /**
     * 异步请求，带有接口回调
     *
     * @param callBack
     */
    public void async(HttpCallBack callBack) {
        setHeader(request);
        okHttpClient.newCall(request.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onFailure(call, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                assert response.body() != null;
                callBack.onSuccessful(call, response.body().string());
            }
        });
    }

    /**
     * 为request添加请求头
     *
     * @param request
     */
    private void setHeader(Request.Builder request) {
        if (headerMap != null) {
            try {
                for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                    request.addHeader(entry.getKey(), entry.getValue());
                }
            } catch (Exception e) {
            	log.error("添加HEADER出错：{}", url, e);
            }
        }
    }


    /**
     * 生成安全套接字工厂，用于https请求的证书跳过
     *
     * @return
     */
    private SSLSocketFactory createSSLSocketFactory(TrustManager[] trustAllCerts) {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        	log.error("创建SSLSocket出错：{}", url, e);
        }
        return ssfFactory;
    }

    private TrustManager[] buildTrustManagers() {
        return new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[]{};
                    }
                }
        };
    }
    
    @Data
    public static class HttpResult{
    	private boolean isOk;
    	private String response;
    	
    	public static HttpResult ok(String response) {
    		HttpResult result = new HttpResult();
    		result.setOk(true);
    		result.setResponse(response);
    		return result;
    	}
    	public static HttpResult fail(String response) {
    		HttpResult result = new HttpResult();
    		result.setOk(false);
    		result.setResponse(response);
    		return result;
    	}
    }

    /**
     * 自定义一个接口回调
     */
    public interface HttpCallBack {

        void onSuccessful(Call call, String data);

        void onFailure(Call call, String errorMsg);

    }
}
