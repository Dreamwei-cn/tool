package com.ufc.channel.common.service;

import com.ufc.channel.common.bean.ResponseResult;
import okhttp3.Response;

public interface IBackService {

    public void onFailure(ResponseResult result);

    public void onSuccess(ResponseResult result, Response response);

}
