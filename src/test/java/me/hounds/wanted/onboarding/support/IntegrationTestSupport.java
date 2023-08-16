package me.hounds.wanted.onboarding.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.hounds.wanted.onboarding.domain.auth.service.AuthService;
import me.hounds.wanted.onboarding.domain.board.domain.persist.BoardRepository;
import me.hounds.wanted.onboarding.domain.board.service.BoardService;
import me.hounds.wanted.onboarding.domain.content.domain.persist.ContentRepository;
import me.hounds.wanted.onboarding.domain.content.service.ContentService;
import me.hounds.wanted.onboarding.domain.recommend.domain.persist.RecommendRepository;
import me.hounds.wanted.onboarding.domain.recommend.service.RecommendService;
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
    protected AuthService authService;

    @Autowired
    protected BoardService boardService;

    @Autowired
    protected ContentService contentService;

    @Autowired
    protected RecommendService recommendService;

    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected BoardRepository boardRepository;

    @Autowired
    protected ContentRepository contentRepository;

    @Autowired
    protected RecommendRepository recommendRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected ObjectMapper objectMapper;
}
