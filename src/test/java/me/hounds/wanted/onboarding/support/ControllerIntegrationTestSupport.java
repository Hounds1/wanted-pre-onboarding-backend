package me.hounds.wanted.onboarding.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.hounds.wanted.onboarding.domain.auth.service.AuthService;
import me.hounds.wanted.onboarding.domain.board.service.BoardService;
import me.hounds.wanted.onboarding.domain.content.service.ContentReadService;
import me.hounds.wanted.onboarding.domain.content.service.ContentService;
import me.hounds.wanted.onboarding.domain.member.service.MemberReadService;
import me.hounds.wanted.onboarding.domain.member.service.MemberService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriPort = 9090)
@ExtendWith(RestDocumentationExtension.class)
@ActiveProfiles("test")
public abstract class ControllerIntegrationTestSupport {

    @MockBean
    protected MemberService memberService;

    @MockBean
    protected MemberReadService memberReadService;

    @MockBean
    protected AuthService authService;

    @MockBean
    protected BoardService boardService;

    @MockBean
    protected ContentService contentService;

    @MockBean
    protected ContentReadService contentReadService;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;
}
