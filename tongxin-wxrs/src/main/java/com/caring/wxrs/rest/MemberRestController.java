package com.caring.wxrs.rest;

import com.caring.dao.model.Member;
import com.caring.dao.model.query.Page;
import com.caring.dao.model.query.PageParam;
import com.caring.dao.model.query.filter.MemberFilter;
import com.caring.dao.repository.MemberRepository;
import com.caring.dao.service.MemberService;
import com.caring.wxrs.TongxinWxServiceException;
import com.caring.wxrs.rest.request.LoginRequest;
import com.caring.wxrs.security.JwtToken;
import com.caring.wxrs.security.TokenHandler;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.rmi.ServerException;


@RestController
@RequestMapping("/api/v1/member")
public class MemberRestController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TokenHandler authenticateTokenHandler;

    @RequestMapping(method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Page<Member> findMembers(@RequestBody(required = false) PageParam<MemberFilter> pageParam) {
        return memberService.findMembers(pageParam);
    }

    @RequestMapping(method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Member saveMember(@RequestBody Member member) {
        return memberService.saveMember(member);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Member findMemberById(@PathVariable Long id) {
        return memberService.findMemberById(id);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void deleteMemberById(@PathVariable Long id) {
        memberService.deleteMember(id);
    }

    private void checkMemberMobileExisted(String mobile) {
        Member member = memberService.findMemberByMobile(mobile);
        if (member != null) {
            String error = String.format("客户手机号：%s， 已经注册，不能重复！", mobile);
//            LOG.error(error);
            throw new TongxinWxServiceException(error);
        }
    }

//    @RequestMapping(value = "/login", method = RequestMethod.POST,
//            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
//            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public ResponseEntity loginUser(@RequestBody LoginRequest loginReq) {
//        Member savedMember = memberService.findMemberByUsername(loginReq.getUsername());
//        if (loginReq != null && savedMember != null) {
//            if (authenticateTokenHandler.matches(loginReq.getPassword(), savedMember.getPassword())) {
//                savedMember.setPassword(null);
////                XWxToken xtoken = new XWxToken(MEMBER, savedMember.getId().toString());
//                return ResponseEntity.ok().header(SET_COOKIE, AUTH_MG_COOKIE_NAME + "=" + authenticateTokenHandler.createToken(xtoken))
//                        .body(new XTokenResponse(env.getProperty(WECHAT_DOMAIN),
//                                Maps.immutableEntry(AUTH_MG_HEADER_NAME, authenticateTokenHandler.createToken(xtoken)),
//                                savedMember));
//            }
//            throw new TongxinWxServiceException("密码不正确");
//        }
//        throw new TongxinWxServiceException("用户名不正确");
//    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Member memberRegister(@RequestBody Member member) throws ServerException {
        if (memberRepository.findByMobile(member.getMobile()) != null) {
            throw new ServerException("该手机已注册！");
        }
        if (memberRepository.findByUsername(member.getUsername()) != null) {
            throw new ServerException("该用户名已存在！");
        }
        member.setMobile(member.getMobile());
        member.setUsername(member.getUsername());
        member.setHeadImage(member.getHeadImage());
        final BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
        member.setPassword(pwEncoder.encode(member.getPassword()));
        return memberService.saveMember(member);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ImmutableMap<String, Object> menberLogin(@RequestBody LoginRequest loginReq) {
        Member member = memberService.findMemberByUsername(loginReq.getUsername());
        if (loginReq != null && member != null) {
            if (authenticateTokenHandler.matches(loginReq.getPassword(), member.getPassword())) {
//                member.setPassword(null);
//                return member ;
                return ImmutableMap.<String, Object>builder().put("token", authenticateTokenHandler.createToken(new JwtToken("user", member.getId() + ":" + member.getUsername())))
                        .put("user", member).build();
            }else {
                throw new TongxinWxServiceException("密码不正确");
            }
        }
        throw new TongxinWxServiceException("用户名不正确");
    }
}
