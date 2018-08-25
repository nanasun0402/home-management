//package com.caring.service.sms.sohu;
//
//import com.caring.dao.model.VerifyCode;
//import com.caring.service.CaringServiceException;
//import com.caring.service.ServiceUtils;
//import com.caring.service.sms.BaseSmsService;
//import com.caring.service.sms.sohu.sendcloud.sdk.core.SendCloud;
//import com.caring.service.sms.sohu.sendcloud.sdk.exception.SmsException;
//import com.caring.service.sms.sohu.sendcloud.sdk.model.SendCloudSms;
//import com.caring.service.sms.sohu.sendcloud.sdk.util.ResponseData;
//import java.io.IOException;
//import javax.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
///**
// *
// * @author james
// */
//@Service
//public class SmsSohuService extends BaseSmsService {
//
//    @Autowired
//    private SmsSohuConfig sohuConfig;
//
//    @PostConstruct
//    public void init() {
//        super.expiredTime = sohuConfig.getSms_expired();
//    }
//
//    private SendCloud createSendCloud() {
//        SendCloud sc = new SendCloud();
//        sc.setServer(sohuConfig.getServer());
//        sc.setMailAPI(sohuConfig.getSend_api());
//        sc.setTemplateAPI(sohuConfig.getSend_template_api());
//        sc.setSmsAPI(sohuConfig.getSend_sms_api());
//        sc.setVoiceAPI(sohuConfig.getSend_voice_api());
//        return sc;
//    }
//
//    public void sendSmsSlient(SendCloudSms sms) {
//        try {
//            sendSms(sms);
//        } catch (CaringServiceException e) {
//            LOG.error("Error: {}", e.fillInStackTrace());
//        }
//    }
//
//    public void sendSms(SendCloudSms sms) {
//        try {
//            SendCloud sc = createSendCloud();
//            ResponseData res = sc.sendSms(sms);
//            LOG.debug("Res: {}", ServiceUtils.toJson(res));
//        } catch (SmsException e) {
//            LOG.error("Error: {}", e.fillInStackTrace());
//            throw new CaringServiceException(e.getMessage());
//        } catch (IOException e) {
//            LOG.error("Error: {}", e.fillInStackTrace());
//            throw new CaringServiceException("网络错误", e);
//        }
//    }
//
//    @Override
//    public String sendSmsCode(String mobile, boolean backdoor) {
//        try {
//            String code = ServiceUtils.getRandomNumberByLength(4);
//            VerifyCode verifyCode = new VerifyCode();
//            verifyCode.setMobile(mobile);
//            verifyCode.setCode(code);
//            LOG.debug("sms code: {}", code);
//            if (!backdoor) {
//                SendCloudSms sms = new SendCloudSms();
//                sms.setMsgType(0);
//                sms.setTemplateId(sohuConfig.getTemplate_id());
//                sms.addPhone(mobile);
//                sms.addVars("code", code);
//                SendCloud sc = createSendCloud();
//                ResponseData res = sc.sendSms(sms);
//                verifyCode.setSendStatus(res.getStatusCode());
//                LOG.info(ServiceUtils.toJson(res));
//            }
//            verifyCode.setGeneratedTime(System.currentTimeMillis());
//            verifyCodeRepository.save(verifyCode);
//            return code;
//        } catch (SmsException e) {
//            LOG.error("Error: {}", e.fillInStackTrace());
//            throw new CaringServiceException(e.getMessage());
//        } catch (IOException e) {
//            LOG.error("Error: {}", e.fillInStackTrace());
//            throw new CaringServiceException("网络错误", e);
//        }
//    }
//
//}
