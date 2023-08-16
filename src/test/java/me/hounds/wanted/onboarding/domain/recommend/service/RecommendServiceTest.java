package me.hounds.wanted.onboarding.domain.recommend.service;

import me.hounds.wanted.onboarding.domain.content.domain.persist.Content;
import me.hounds.wanted.onboarding.domain.recommend.domain.persist.Recommend;
import me.hounds.wanted.onboarding.domain.member.domain.persist.Member;
import me.hounds.wanted.onboarding.support.IntegrationTestSupport;
import me.hounds.wanted.onboarding.support.content.GivenContent;
import me.hounds.wanted.onboarding.support.member.GivenMember;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class RecommendServiceTest extends IntegrationTestSupport {

    Member member;

    Content content;

    @BeforeEach
    void init() {
        member = memberRepository.save(GivenMember.givenMember());
        content = contentRepository.save(GivenContent.givenContentWithCount());
    }

    @Test
    @DisplayName("좋아요가 생성된다.")
    void like() {
        recommendService.likeAndDislike(content.getId(), member.getId());

        Optional<Recommend> findLike = recommendRepository.findByContentIdAndMemberId(content.getId(), member.getId());

        if (findLike.isPresent()) {
            Recommend recommend = findLike.get();

            assertThat(recommend.getContentId()).isEqualTo(content.getId());
            assertThat(recommend.getMemberId()).isEqualTo(member.getId());;
            assertThat(recommend.isActivated()).isTrue();
        }

        List<Recommend> all = recommendRepository.findAll();

        assertThat(all.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("존재하는 좋아요가 요청되면 비활성화된다.")
    void dislike() {
        recommendService.likeAndDislike(content.getId(), member.getId());
        recommendService.likeAndDislike(content.getId(), member.getId());

        List<Recommend> all = recommendRepository.findAll();

        assertThat(all.size()).isEqualTo(0);
    }


}