package com.caring.dao.service;

import com.caring.dao.model.Member;
import com.caring.dao.model.query.Page;
import com.caring.dao.model.query.PageParam;
import com.caring.dao.model.query.filter.MemberFilter;
import com.caring.dao.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService extends BaseService {
    @Autowired
    private MemberRepository memberRepository;

    public Page<Member> findMembers(PageParam<MemberFilter> pageParam) {
        StringBuilder SQL = new StringBuilder("SELECT m FROM Member m");
        StringBuilder SQLcount = new StringBuilder("SELECT COUNT(1) FROM Member m");
        StringBuilder OrderBy = new StringBuilder("ORDER BY m.created DESC");
        return executePageQuery(pageParam, SQL, SQLcount, OrderBy);
    }

    public Member findMemberById(Long id) {
        return memberRepository.findOne(id);
    }

    public Member findMemberByMobile(String mobile) {
        return memberRepository.findByMobile(mobile);
    }

    public Member findMemberByUsername(String username) {
        return memberRepository.findByUsername(username);
    }
    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }

    public void deleteMember(Long id) {
        memberRepository.delete(id);
    }


}
