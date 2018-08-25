package com.caring.dao.repository;

import com.caring.dao.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    public Member findByMobile(String mobile);
    public Member findByUsername(String username);
    public Member findByPassword(String password);
}


