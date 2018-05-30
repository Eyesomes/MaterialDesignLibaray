package com.exam.cn.framelibrary.http;

import android.content.Context;
import android.util.Log;

import com.exam.cn.baselibrary.http.HttpCallback;
import com.exam.cn.baselibrary.http.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Map;

/**
 * 与业务逻辑有关的 callback
 * Created by 杰 on 2017/10/16.
 */

public abstract class MyHttpCallback<T> implements HttpCallback{

    @Override
    public void onPreExecute(Context context, String url, Map<String, Object> params) {
    }

    @Override
    public void onSuccess(String result) {

        Log.i("HttpCallback", HttpUtils.analysisClazzInfo(this).toString()+"----:-----"+result);

        // 判断是否成功换回对象  根据业务写
        try {
            JsonObject returnData = new JsonParser().parse(result).getAsJsonObject();
            String resultKey = returnData.get("RESULT").getAsString();
            if (!resultKey.equals("ok")){
                onError(new Exception("服务器请求还回失败!!!"));
            }
            // 转成对象
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .disableHtmlEscaping()
                    .create();
            T objResult = (T) gson.fromJson(returnData.get("RESPONSE"), HttpUtils.analysisClazzInfo(this));
            onSuccess(objResult);
        }catch (Exception e){
            onError(e);
        }
    }
    public abstract void onSuccess(T result);
}
