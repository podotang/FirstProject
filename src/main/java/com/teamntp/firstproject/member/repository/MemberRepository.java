package com.teamntp.firstproject.member.repository;

import com.teamntp.firstproject.member.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // @EntityGraph 을 이용해서 memberRole 이 left join 되도록 처리한다.
    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select m from Member m where m.path =:path and m.loginId =:loginId ")
    Optional<Member> findByLoginIdWithPath(String loginId, String path);

    @Query("select m from Member m where m.email=:email ")
    Optional<Member> findByEmail(String email);

    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select m from Member m where m.loginId =:loginId")
    Optional<Member> findByLoginId(String loginId);
}
