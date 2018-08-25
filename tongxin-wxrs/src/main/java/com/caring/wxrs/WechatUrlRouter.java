//package com.caring.wxrs;
//
//import com.caring.service.ServiceUtils;
//import com.caring.service.wx.config.WechatMpProperties;
//import java.io.IOException;
//import java.util.Map;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import me.chanjar.weixin.mp.api.WxMpService;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// *
// * @author james
// */
//@WebServlet(urlPatterns = "/route", description = "Wechat view route")
//public class WechatUrlRouter extends HttpServlet {
//
//    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
//
//    @Autowired
//    private WechatMpProperties wxProperties;
//    @Autowired
//    private WxMpService wxService;
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        try {
//            LOG.info("Weixin: {}", ServiceUtils.toJson(req.getParameterMap()));
//            String key = req.getParameter("key");
//            LOG.debug("key:{}", key);
//            String redirectUrl = wxProperties.getRedirectMapping().get(key);
//            for (Map.Entry<String, String[]> entry : req.getParameterMap().entrySet()) {
//                for (String value : entry.getValue()) {
//                    redirectUrl += "&" + entry.getKey() + "=" + value;
//                }
//            }
//            if (StringUtils.isNotEmpty(redirectUrl)) {
//                resp.sendRedirect(redirectUrl);
//            }
//            LOG.info("redirect to: " + redirectUrl);
//        } catch (Exception e) {
//            throw new ServletException(e);
//        }
//    }
//
//}
