//package com.caring.service.sms;
//
//import com.caring.dao.model.VerifyCode;
//import com.caring.dao.repository.VerifyCodeRepository;
//import com.caring.service.CaringServiceException;
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
///**
// *
// * @author james
// */
//public abstract class BaseSmsService implements SmsService {
//
//    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());
//
//    protected long expiredTime;
//
//    protected VerifyCodeRepository verifyCodeRepository;
//
//    @Autowired
//    public void setVerifyCodeRepository(VerifyCodeRepository verifyCodeRepository) {
//        this.verifyCodeRepository = verifyCodeRepository;
//    }
//
//    @Override
//    public void checkSmsCode(String mobile, String code) {
//        if (StringUtils.isEmpty(code)) {
//            throw new CaringServiceException("验证码不能为空");
//        }
//        VerifyCode verifyCode = verifyCodeRepository.findOne(mobile);
//        if (verifyCode == null) {
//            throw new CaringServiceException("无效的验证码或手机号码");
//        }
//        if (!verifyCode.getCode().equals(code)) {
//            throw new CaringServiceException("验证码无效");
//        }
//        if ((verifyCode.getGeneratedTime() + expiredTime) < System.currentTimeMillis()) {
//            throw new CaringServiceException("验证码过期");
//        }
//    }
//
//}
