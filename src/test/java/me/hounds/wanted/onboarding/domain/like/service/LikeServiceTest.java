package me.hounds.wanted.onboarding.domain.like.service;

import me.hounds.wanted.onboarding.domain.content.domain.persist.Content;
import me.hounds.wanted.onboarding.domain.like.domain.persist.Like;
import me.hounds.wanted.onboarding.domain.member.domain.persist.Member;
import me.hounds.wanted.onboarding.support.IntegrationTestSupport;
import me.hounds.wanted.onboarding.support.content.GivenContent;
import me.hounds.wanted.onboarding.support.member.GivenMember;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class LikeServiceTest extends IntegrationTestSupport {

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
        likeService.likeAndDislike(content.getId(), member.getId());

        Optional<Like> findLike = likeRepository.findByContentIdAndMemberId(content.getId(), member.getId());

        if (findLike.isPresent()) {
            Like like = findLike.get();

            assertThat(like.getContentId()).isEqualTo(content.getId());
            assertThat(like.getMemberId()).isEqualTo(member.getId());;
            assertThat(like.isActivated()).isTrue();
        }

        List<Like> all = likeRepository.findAll();

        assertThat(all.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("존재하는 좋아요가 요청되면 비활성화된다.")
    void dislike() {
        likeService.likeAndDislike(content.getId(), member.getId());
        likeService.likeAndDislike(content.getId(), member.getId());

        List<Like> all = likeRepository.findAll();

        assertThat(all.size()).isEqualTo(0);
    }


}