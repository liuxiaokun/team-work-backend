package com.byx.work.team;

import com.byx.framework.core.context.AppContext;
import com.byx.framework.core.utils.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.Properties;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCaching
@Slf4j
public class WorkTeamApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(WorkTeamApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Properties properties = new Properties();
        String dcid = RandomUtil.random(1, 10) + "";
        String wkid = RandomUtil.random(1, 10) + "";
        properties.put("snowflake.datacenter", dcid);
        properties.put("snowflake.worker", wkid);
        log.info("snowflake properties:{}", properties);
        AppContext.initialize(properties);
    }
}
