package com.ufc.channel.common.event;

import com.ufc.channel.common.bean.ResponseResult;
import org.springframework.context.ApplicationEvent;

public class OnFailureEvent  extends ApplicationEvent {


    private ResponseResult result;


    public OnFailureEvent(ResponseResult result) {
        super(result);
    }

    public ResponseResult getResult() {
        return result;
    }

    public void setResult(ResponseResult result) {
        this.result = result;
    }
}
