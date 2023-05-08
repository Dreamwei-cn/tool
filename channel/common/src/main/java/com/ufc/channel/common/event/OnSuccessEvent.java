package com.ufc.channel.common.event;

import com.ufc.channel.common.bean.ResponseResult;
import okhttp3.Response;
import org.springframework.context.ApplicationEvent;

public class OnSuccessEvent extends ApplicationEvent {

    private ResponseResult result;

    private Response response;

    public OnSuccessEvent(ResponseResult result, Response response) {
        super(result);
    }

    public ResponseResult getResult() {
        return result;
    }

    public void setResult(ResponseResult result) {
        this.result = result;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
