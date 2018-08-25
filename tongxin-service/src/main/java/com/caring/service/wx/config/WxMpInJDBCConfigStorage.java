//package com.caring.service.wx.config;
//
//import com.caring.dao.model.OfficalWechat;
//import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
//import com.caring.dao.repository.OfficalWechatRepository;
//
///**
// *
// * @author james
// */
//public class WxMpInJDBCConfigStorage extends WxMpInMemoryConfigStorage {
//
//    private final OfficalWechatRepository officalAccountRepository;
//
//    public WxMpInJDBCConfigStorage(OfficalWechatRepository officalAccountRepository) {
//        this.officalAccountRepository = officalAccountRepository;
//    }
//
//    @Override
//    public boolean isJsapiTicketExpired() {
//        if (super.isJsapiTicketExpired()) {
//            OfficalWechat oAccount = officalAccountRepository.findOne(super.getAppId());
//            if (oAccount != null) {
//                super.setJsapiTicketExpiresTime(oAccount.getJsapiTicketExpiredTime());
//                super.setJsapiTicket(oAccount.getJsapiTicket());
//            }
//        }
//        return super.isJsapiTicketExpired();
//    }
//
//    @Override
//    public synchronized void updateJsapiTicket(String jsapiTicket, int expiresInSeconds) {
//        OfficalWechat oAccount = officalAccountRepository.findOne(super.getAppId());
//        if (oAccount == null) {
//            oAccount = new OfficalWechat();
//            oAccount.setAppId(super.getAppId());
//        }
//        super.updateJsapiTicket(jsapiTicket, expiresInSeconds);
//        oAccount.setJsapiTicket(jsapiTicket);
//        oAccount.setJsapiTicketExpiredTime(super.getJsapiTicketExpiresTime());
//        officalAccountRepository.save(oAccount);
//    }
//
//}
