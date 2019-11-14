package com.byx.work.team.controller;

import com.byx.framework.core.web.RO;
import com.byx.work.team.config.JwtAuthConfig;
import com.byx.work.team.exception.BizException;
import com.byx.work.team.model.dto.LoginDTO;
import com.byx.work.team.model.dto.UserDTO;
import com.byx.work.team.service.UsersService;
import com.byx.work.team.utils.Md5Util;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liuxiaokun
 * @version 1.0-SNAPSHOT
 * @since 2019/11/14
 */
@RestController
public class LoginController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private JwtAuthConfig jwtAuthConfig;

    @PostMapping("/login")
    public Object login(@RequestBody LoginDTO loginDTO, HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> params = new HashMap<>(1);
        params.put("mobile", loginDTO.getMobile());
        UserDTO oneUsers = usersService.findOneUsers(params);

        if (null == oneUsers || null == oneUsers.getId()) {
            return RO.error("不存在的用户");
        } else {
            if (!Md5Util.md5(loginDTO.getPassword() + oneUsers.getSalt()).equals(oneUsers.getPassword())) {
                return RO.error("用户名密码不匹配");
            }
        }

        Instant now = Instant.now();
        String token = Jwts.builder()
                .setSubject(oneUsers.getId().toString())
                .claim("authorities", "")
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(jwtAuthConfig.getExpiration())))
                .signWith(SignatureAlgorithm.HS256, jwtAuthConfig.getSecret().getBytes())
                .compact();
        response.setHeader(jwtAuthConfig.getHeader(), jwtAuthConfig.getPrefix() + " " + token);
        response.setHeader("Access-Control-Expose-Headers", "*");
        return RO.success(oneUsers);
    }
}
