package com.byx.work.team.config;

import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author liuxiaokun
 * @version 0.0.1
 * @since 2018/11/14
 */
@Getter
@ToString
@Configuration
public class JwtAuthConfig {

    @Value("${jwt.url:/login}")
    private String url;

    @Value("${jwt.header:Authorization}")
    private String header;

    @Value("${jwt.prefix:Bearer}")
    private String prefix;

    @Value("${jwt.expiration:#{30*24*60*60}}")
    private int expiration;

    @Value("${jwt.secret:team-work}")
    private String secret;
}
