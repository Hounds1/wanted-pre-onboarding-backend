package me.hounds.wanted.onboarding.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.hounds.wanted.onboarding.domain.member.domain.persist.MemberRepository;
import me.hounds.wanted.onboarding.domain.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public abstract class IntegrationTestSupport {

    @Autowired
    protected MemberService memberService;

    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected ObjectMapper objectMapper;
}
