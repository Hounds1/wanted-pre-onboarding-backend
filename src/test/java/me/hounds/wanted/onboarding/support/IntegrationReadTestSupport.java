package me.hounds.wanted.onboarding.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.hounds.wanted.onboarding.domain.board.domain.persist.BoardRepository;
import me.hounds.wanted.onboarding.domain.content.domain.persist.ContentRepository;
import me.hounds.wanted.onboarding.domain.content.service.ContentReadService;
import me.hounds.wanted.onboarding.domain.content.service.ContentService;
import me.hounds.wanted.onboarding.domain.member.domain.persist.MemberRepository;
import me.hounds.wanted.onboarding.domain.member.service.MemberReadService;
import me.hounds.wanted.onboarding.domain.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional(readOnly = true)
public abstract class IntegrationReadTestSupport {

    @Autowired
    protected ContentReadService contentReadService;

    @Autowired
    protected ContentService contentService;

    @Autowired
    protected MemberReadService memberReadService;

    @Autowired
    protected MemberService memberService;

    @Autowired
    protected ContentRepository contentRepository;

    @Autowired
    protected BoardRepository boardRepository;

    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected ObjectMapper objectMapper;
}
