package com.sj.basemodule;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.SerializationService;

import java.lang.reflect.Type;

/**
 * author: shijun
 * created on: 2020/3/12
 * description:
 */
@Route(path = "/service/json")
//因为实现了ARouter的SerializationService接口，我们自定义的对象即可不用写代码序列化而直接使用
public class JsonServiceImpl implements SerializationService {
    @Override
    public <T> T json2Object(String input, Class<T> clazz) {
        return null;
    }

    @Override
    public String object2Json(Object instance) {
        return null;
    }

    @Override
    public <T> T parseObject(String input, Type clazz) {
        return null;
    }

    @Override
    public void init(Context context) {

    }
}