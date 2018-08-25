package com.caring.wxrs.security;

import com.caring.dao.service.SysUserService;
import static com.caring.wxrs.security.TokenHandler.AUTH_MG_COOKIE_NAME;
import static com.caring.wxrs.security.TokenHandler.AUTH_WX_COOKIE_NAME;
import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class JwtFilter extends GenericFilterBean {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private final String[] excludedUrlList;
    private final ApplicationContext context;

    public JwtFilter(ApplicationContext context, String... excludedUrlList) {
        this.context = context;
        this.excludedUrlList = excludedUrlList;
    }

    @Override
    public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
            throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        LOG.debug("RequestURI: {}, Method: {}", request.getRequestURI(), request.getMethod());
        if (needAuthentication(request.getRequestURI())) {
            final HttpServletResponse response = (HttpServletResponse) res;
            final String cookieAuthWxToken = getCookie(AUTH_WX_COOKIE_NAME, request.getCookies());
            final String cookieAuthMgToken = getCookie(AUTH_MG_COOKIE_NAME, request.getCookies());
            String authWxHeaderToken = request.getHeader(TokenHandler.AUTH_WX_HEADER_NAME);
            String authMgHeaderToken = request.getHeader(TokenHandler.AUTH_MG_HEADER_NAME);
            if (StringUtils.isEmpty(authWxHeaderToken)) {
                authWxHeaderToken = cookieAuthWxToken;
            }
            if (StringUtils.isEmpty(authMgHeaderToken)) {
                authMgHeaderToken = cookieAuthMgToken;
            }
            if ("OPTIONS".equals(request.getMethod())) {
                response.setStatus(HttpServletResponse.SC_OK);
                chain.doFilter(req, res);
            } else {
                    LOG.info("OK");
                    chain.doFilter(req, res);
//                if (StringUtils.isEmpty(authMgHeaderToken)) {
//                    throw new ServletException("无效的认证信息");
//                }
//                if (StringUtils.isNotEmpty(authWxHeaderToken)) {
//                    if (checkWechatToken(authWxHeaderToken)) {
//                        LOG.info("OK");
//                        chain.doFilter(req, res);
//                    }
//                } else if (StringUtils.isNotEmpty(authMgHeaderToken)) {
//                    if (checkMgToken(authMgHeaderToken)) {
//                        LOG.info("OK");
//                        chain.doFilter(req, res);
//                    }
//                }
            }
        } else {
            LOG.info("OK");
            chain.doFilter(req, res);
        }
    }

    private String getCookie(String name, Cookie[] cookies) {
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equalsIgnoreCase(name)) {
                return cookie.getValue();
            }
        }
        return null;
    }

    private boolean needAuthentication(String contextPath) {
        if (this.excludedUrlList == null) {
            return true;
        }
        LOG.debug("Exclude list: {}", Arrays.toString(excludedUrlList));
        for (String item : this.excludedUrlList) {
            if (contextPath.contains(item)) {
                return false;
            }
        }
        return true;
    }

//    private boolean checkWechatToken(String authWxHeaderToken) throws ServletException {
//        try {
//            TokenHandler wechatTokenHandler = (TokenHandler) context.getBean(AuthenticateTokenHandler.class);
//            WechatService wechatServicie = context.getBean(WechatService.class);
//            XWxToken xtoken = (XWxToken) wechatTokenHandler.parseToken(authWxHeaderToken);
//            String openId = xtoken.getId();
//            if (wechatServicie.findWechatByOpenId(openId) == null) {
//                LOG.error("Error: {}", xtoken);
//                throw new ServletException("无效的认证信息");
//            }
//            LOG.debug("Wx Token checking: {}", xtoken);
//        } catch (Exception e) {
//            LOG.error("Error: {}", e.fillInStackTrace());
//            throw new ServletException("无效的认证信息");
//        }
//        return true;
//    }

    private boolean checkMgToken(String authMgHeaderToken) throws ServletException {
        try {
            TokenHandler managementTokenHandler = (TokenHandler) context.getBean(AuthenticateTokenHandler.class);
            SysUserService sysUserService = context.getBean(SysUserService.class);
            XWxToken xtoken = (XWxToken) managementTokenHandler.parseToken(authMgHeaderToken);
            if (null == sysUserService.findByUserId(Long.parseLong(xtoken.getId()))) {
                throw new ServletException("无效的认证信息");
            }
            LOG.debug("Mg Token checking: {}", xtoken.getId());
        } catch (Exception e) {
            LOG.error("Error: {}", e.fillInStackTrace());
            throw new ServletException("无效的认证信息");
        }
        return true;
    }
}
