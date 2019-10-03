package com.hua.gentlemanx2.net;

import com.hua.gentlemanx2.net.callback.IError;
import com.hua.gentlemanx2.net.callback.IFailure;
import com.hua.gentlemanx2.net.callback.IRequest;
import com.hua.gentlemanx2.net.callback.ISuccess;
import com.hua.gentlemanx2.net.callback.RequestCallbacks;

import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/*
* Created by hua
* 2019/10/1 14:53
*/
public class RestClient {

    private final String URL;
    private static final WeakHashMap<String,Object> PAMAMS = RestCreator.getParams();
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final RequestBody BODY;

    public RestClient(String url,
                      Map<String, Object> pamams,
                      IRequest request,
                      ISuccess success,
                      IFailure failure,
                      IError error,
                      RequestBody body) {
        this.URL = url;
        PAMAMS.putAll(pamams);
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.BODY = body;
    }

    public static RestClientBuilder builder(){
        return new RestClientBuilder();
    }

    private void request(HttpMethod method){
        final RestService service = RestCreator.getRestService();
        Call<String> call = null;
        if (REQUEST != null){
            REQUEST.onRequestStart();
        }
        switch (method){
            case GET:
                call = service.get(URL,PAMAMS);
                break;
            case POST:
                call = service.post(URL,PAMAMS);
                break;
            case PUT:
                call = service.put(URL,PAMAMS);
                break;
            case DELETE:
                call = service.delete(URL,PAMAMS);
                break;
        }

        if (call != null){
            call.enqueue(getRequestCallback());
        }
    }

    private Callback<String> getRequestCallback(){
        return new RequestCallbacks(
             REQUEST,
             SUCCESS,
             FAILURE,
                ERROR
        );
    }

    public final void get(){
        request(HttpMethod.GET);
    }

    public final void post(){
        request(HttpMethod.POST);
    }

    public final void put(){
        request(HttpMethod.PUT);
    }

    public final void delete(){
        request(HttpMethod.DELETE);
    }




}
