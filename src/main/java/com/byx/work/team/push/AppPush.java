package com.byx.work.team.push;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.style.Style0;
import lombok.extern.slf4j.Slf4j;
import rx.Single;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Slf4j
public class AppPush {
    private static final String appId = "jDOulCT7F96wA0bSeS6fj3";
    private static final String appKey = "zwVXcknZu58bukzUt4ocL4";
    private static final String masterSecret = "94FcJBu0tj64lzyNncABV5";
    private static final String url = "http://sdk.open.api.igexin.com/apiex.htm";

    public static void main(String[] args) throws IOException {

        //STEP1：获取应用基本信息
        IGtPush push = new IGtPush(url, appKey, masterSecret);

        Style0 style = new Style0();
        // STEP2：设置推送标题、推送内容
        style.setTitle("消息通知");
        style.setText("您有一条新的需求3");
        // 设置推送图标
        style.setLogo("push.png");
        // STEP3：设置响铃、震动等推送效果
        // 设置响铃
        style.setRing(true);
        // 设置震动
        style.setVibrate(true);

        // STEP4：选择通知模板
        NotificationTemplate template = new NotificationTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        template.setTransmissionType(1);
//        template.setTransmissionContent("/pages/function/detail/detail?id=997828679646052352");
        template.setTransmissionContent("/pages/function/function?id=992800712595341312");
        template.setStyle(style);

        // STEP5：定义"SingleMessage"类型消息对象,设置推送消息有效期等推送参数
        List<String> appIds = new ArrayList<String>();
        appIds.add(appId);
        SingleMessage singleMessage = new SingleMessage();
        singleMessage.setData(template);
        singleMessage.setOffline(true);
        singleMessage.setOfflineExpireTime(1000 * 600);
        singleMessage.setPushNetWorkType(0);

        Target target = new Target();
        target.setAppId(appId);
        //target.setAlias("18521599183");
        target.setClientId("");

        // STEP6：执行推送
        IPushResult ret = push.pushMessageToSingle(singleMessage, target);
        System.out.println(ret.getResponse().toString());
    }
}
