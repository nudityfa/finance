package com.nudity.util;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.adminUtil.exception.MyException;
import com.alibaba.fastjson.JSONObject;
import com.nudity.timer.ScheduledBug;

/**
 * Copyright: Copyright (C) 2022 XXX, Inc. All rights reserved. <p>
 * Company: 霉运有限公司<p>
 *
 * @author luojinhua
 * @since 2022/5/23 21:41
 */
public class DanJuanJiJinUtil {

    public static String getDanJuanResponseItems(String url,int i) throws MyException{
        HttpResponse httpResponse = HttpUtil.createGet(url + i).addHeaders(ScheduledBug.headerMap).execute();
        JSONObject jsonObject = JSONObject.parseObject(httpResponse.body());
        Object resultCode = jsonObject.get("result_code");
        if (resultCode != null && resultCode.toString().equals("0")){
            JSONObject data = jsonObject.getJSONObject("data");
            if (data != null){
                Object ob = data.get("items");
                if(ob != null){
                    return ob.toString();
                }
            }
        }
        throw new MyException("请求蛋卷基金异常,异常地址为：" + url);
    }
}
