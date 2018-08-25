package com.caring.wxrs.rest;

import static com.caring.dao.config.ModelConst.User.Who.USER;
import com.caring.dao.model.User;
import com.caring.dao.model.query.Page;
import com.caring.dao.model.query.PageParam;
import com.caring.dao.model.query.filter.UserFilter;
import com.caring.dao.service.SysUserService;
import static com.caring.service.CaringServiceConst.WECHAT_DOMAIN;
import com.caring.service.ServiceUtils;
import com.caring.wxrs.TongxinWxServiceException;
import com.caring.wxrs.rest.request.LoginRequest;
import com.caring.wxrs.rest.response.XTokenResponse;
import com.caring.wxrs.security.TokenHandler;
import static com.caring.wxrs.security.TokenHandler.AUTH_MG_COOKIE_NAME;
import static com.caring.wxrs.security.TokenHandler.AUTH_MG_HEADER_NAME;
import static com.caring.wxrs.security.TokenHandler.SET_COOKIE;
import com.caring.wxrs.security.XWxToken;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author james
 */
@RestController
@RequestMapping("/api/v1/user")
public class SysUserRestController extends BaseController {

    @Autowired
    private Environment env;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private TokenHandler authenticateTokenHandler;

    @RequestMapping(method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
                    consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Page<User> findUsers(@RequestBody(required = false) PageParam<UserFilter> pageParam) {
        Page<User> page = sysUserService.findUsers(pageParam);
        page.getContent().forEach(user -> user.setPassword(null));
        return page;
    }

    @RequestMapping(value = "/{userId}",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User findUserById(@PathVariable Long userId) {
        User user = sysUserService.findByUserId(userId);
        user.setPassword(null);
        return user;
    }

    @RequestMapping(method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User findUserByUsername(@RequestParam("username") String username) {
        User user = sysUserService.findByUsername(username);
        user.setPassword(null);
        return user;
    }

    @RequestMapping(method = RequestMethod.PUT,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
                    consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User saveUser(@RequestBody User user) {
        if (user != null && user.getPassword() != null) {
            user.setPassword(authenticateTokenHandler.crypt(user.getPassword()));
        }
        return sysUserService.saveUser(user);
    }

    @RequestMapping(value = "/{userId}",
                    method = RequestMethod.DELETE,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User deleteUser(@PathVariable long userId) {
        return sysUserService.deleteUser(userId);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
                    consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity loginUser(@RequestBody LoginRequest loginReq) {
        User savedUser = sysUserService.findByUsername(loginReq.getUsername());
        if (loginReq != null && savedUser != null) {
            if (authenticateTokenHandler.matches(loginReq.getPassword(), savedUser.getPassword())) {
                savedUser.setPassword(null);
                XWxToken xtoken = new XWxToken(USER, savedUser.getId().toString());
                return ResponseEntity.ok().header(SET_COOKIE, AUTH_MG_COOKIE_NAME + "=" + authenticateTokenHandler.createToken(xtoken))
                        .body(new XTokenResponse(env.getProperty(WECHAT_DOMAIN),
                                                 Maps.immutableEntry(AUTH_MG_HEADER_NAME, authenticateTokenHandler.createToken(xtoken)),
                                                 savedUser));
            }
            LOG.error("Error User: {}", ServiceUtils.toJson(loginReq));
            throw new TongxinWxServiceException("密码不正确");
        }
        LOG.error("Error User: {}", ServiceUtils.toJson(loginReq));
        throw new TongxinWxServiceException("用户名不正确");
    }
}
