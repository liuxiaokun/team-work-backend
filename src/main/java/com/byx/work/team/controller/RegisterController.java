package com.byx.work.team.controller;

import com.byx.framework.core.context.AppContext;
import com.byx.framework.core.web.RO;
import com.byx.work.team.exception.BizException;
import com.byx.work.team.model.dto.UserDTO;
import com.byx.work.team.model.dto.UserRegisterDTO;
import com.byx.work.team.model.entity.User;
import com.byx.work.team.service.UsersService;
import com.byx.work.team.utils.BeanUtil;
import com.byx.work.team.utils.Md5Util;
import com.byx.work.team.utils.SaltUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liuxiaokun
 * @version 1.0-SNAPSHOT
 * @since 2019/12/17
 */
@RestController
@Slf4j
public class RegisterController extends BaseController<User> {

    private final UsersService usersService;

    public RegisterController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("register")
    public Object register(@RequestBody UserRegisterDTO userRegisterDTO, HttpServletRequest request) {
        log.info("add users DTO:{}", userRegisterDTO);

        Map<String, Object> params = new HashMap<>();
        params.put("mobile", userRegisterDTO.getMobile());
        UserDTO oneUsers = usersService.findOneUsers(params);

        if(null != oneUsers && null != oneUsers.getId()) {
            return RO.fail("账号已存在");
        }

        User sourceUsers = new User();
        try {
            User user = BeanUtil.copyProperties(userRegisterDTO, sourceUsers);
            user.setId(AppContext.IdGen.nextId());
            user.setSalt(SaltUtil.genSalt(6));
            user.setState("active");
            user.setPassword(Md5Util.md5(userRegisterDTO.getPassword() + user.getSalt()));
            user.setStatus(1);
            user.setCreatedBy(0L);
            user.setCreatedDate(System.currentTimeMillis());
            user.setIp(getIp(request));
            usersService.saveUsers(user);
        } catch (BizException e) {
            log.error("add users failed,  usersDTO: {}, error message:{}", userRegisterDTO, e.getMessage());
            return RO.error(e.getMessage());
        }
        return RO.success();
    }
}
