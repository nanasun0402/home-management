//package com.caring.service.sms.two35;
//
//import com.caring.dao.model.VerifyCode;
//import com.caring.service.CaringServiceException;
//import com.caring.service.HttpClientFactory;
//import com.caring.service.ServiceUtils;
//import com.caring.service.sms.BaseSmsService;
//import com.caring.service.sms.SmsReturn;
//import java.net.URI;
//import javax.annotation.PostConstruct;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.util.EntityUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//import org.springframework.web.util.UriComponentsBuilder;
//
///**
// *
// * @author james
// */
//@Service
//public class Sms235Service extends BaseSmsService {
//
//    @Autowired
//    private Sms235Config sms235Config;
//
//    @Autowired
//    private HttpClientFactory httpClientFactory;
//
//    @PostConstruct
//    public void init() {
//        super.expiredTime = sms235Config.getExpiredTime();
//    }
//
//    private SmsReturn sendSmsRequest(String mobile, String code) {
//        if (StringUtils.isEmpty(sms235Config.getApiURL())) {
//            LOG.warn("short message host is not configured, verfiy: " + code);
//            SmsReturn result = new SmsReturn();
//            result.setCode(-1);
//            return result;
//        }
//        try (CloseableHttpClient client = httpClientFactory.createHttpClient()) {
//            URI uri = UriComponentsBuilder.fromUriString(sms235Config.getApiURL())
//                    .queryParam("un", sms235Config.getApiUser())
//                    .queryParam("pw", sms235Config.getApiPwd())
//                    .queryParam("rd", 1)
//                    .queryParam("phone", mobile)
//                    .queryParam("msg", String.format("尊敬的用户，您本次的验证码为%s有效期10分钟。", code)).build().encode().toUri();
//            HttpPost post = new HttpPost(uri);
//            try (CloseableHttpResponse resp = client.execute(post)) {
//                String entity = EntityUtils.toString(resp.getEntity());
//                SmsReturn result = splitResponse(entity);
//                if (result.getCode() != 0) {
//                    LOG.error("发送手机验证码错误: {}", entity);
//                    throw new CaringServiceException("验证码服务错误,请确保手机有效");
//                }
//                return result;
//            }
//        } catch (CaringServiceException e) {
//            LOG.error(String.valueOf(e.fillInStackTrace()));
//            throw e;
//        } catch (Exception e) {
//            LOG.error(String.valueOf(e.fillInStackTrace()));
//            throw new CaringServiceException(e);
//        }
//    }
//
//    @Override
//    public String sendSmsCode(String mobile, boolean backdoor) {
//        String code = ServiceUtils.getRandomNumberByLength(4);
//        VerifyCode verifyCode = new VerifyCode();
//        verifyCode.setMobile(mobile);
//        verifyCode.setCode(code);
//        if (!backdoor) {
//            SmsReturn result = sendSmsRequest(mobile, code);
//            verifyCode.setSendStatus(result.getCode());
//        }
//        verifyCode.setGeneratedTime(System.currentTimeMillis());
//        verifyCodeRepository.save(verifyCode);
//        return code;
//    }
//
//    private SmsReturn splitResponse(String response) {
//        String[] lines = response.split("\n");
//        String timestamp = "0", code = "-1", messageid = "0";
//        if (lines != null && lines.length > 0) {
//            String[] line1Values = lines[0].split(",");
//            timestamp = line1Values[0].trim();
//            code = line1Values[1].trim();
//        }
//        if (lines != null && lines.length > 1) {
//            messageid = lines[1].trim();
//        }
//
//        SmsReturn result = new SmsReturn();
//        result.setTimestamp(Long.parseLong(timestamp));
//        result.setCode(Integer.parseInt(code));
//        result.setMessageId(Long.parseLong(messageid));
//        return result;
//    }
//}
