package com.atguigu.gmall.dw.dwlogger.controller;


/**
 * Created by WangYong
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.GmallConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController //Controller+responsebody
public class LoggerController {

    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    @PostMapping("log")
    public String log(@RequestParam("logString") String logString ){

        // 0 补充时间戳
        JSONObject jsonObject = JSON.parseObject(logString);
        jsonObject.put("ts",System.currentTimeMillis());
        // 1 落盘成文件
        String jsonString = jsonObject.toJSONString();
        log.info(jsonString);


        // 2 推送到kafka
        if( "startup".equals( jsonObject.getString("type"))){
            kafkaTemplate.send(GmallConstants.KAFKA_TOPIC_STARTUP,jsonString);
        }else{
            kafkaTemplate.send(GmallConstants.KAFKA_TOPIC_EVENT,jsonString);
        }



        return "success";
    }


}
