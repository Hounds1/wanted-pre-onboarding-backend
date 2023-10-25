package me.hounds.wanted.onboarding.domain.content.service;

import me.hounds.wanted.onboarding.domain.board.domain.persist.Board;
import me.hounds.wanted.onboarding.domain.content.domain.dto.SimpleContentResponse;
import me.hounds.wanted.onboarding.domain.content.domain.dto.UpdateContentRequest;
import me.hounds.wanted.onboarding.domain.content.domain.persist.Content;
import me.hounds.wanted.onboarding.domain.member.domain.persist.Member;
import me.hounds.wanted.onboarding.global.common.error.MetaDataMismatchException;
import me.hounds.wanted.onboarding.support.HashTag.GivenHashTag;
import me.hounds.wanted.onboarding.support.IntegrationTestSupport;
import me.hounds.wanted.onboarding.support.board.GivenBoard;
import me.hounds.wanted.onboarding.support.content.GivenContent;
import me.hounds.wanted.onboarding.support.member.GivenMember;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ContentServiceTest extends IntegrationTestSupport {

    Member member;
    Board board;

    Content content;

    @BeforeEach
    void init() {
        member = memberRepository.save(GivenMember.givenMember());

        board = boardRepository.save(GivenBoard.givenBoardWithCount());

        Content givenContent = GivenContent.givenContentWithCount();
        givenContent.initBoard(board);
        givenContent.setCreateByForTest(member.getEmail());

        content = contentRepository.save(givenContent);
    }

    @Test
    @DisplayName("게시물을 작성 할 수 있다.")
    void create() {
        Content givenContent = GivenContent.givenContent();

        SimpleContentResponse response = contentService.create(givenContent, board.getId(), GivenHashTag.getStringHashTags());

        assertThat(response.getTitle()).isEqualTo(givenContent.getTitle());
        assertThat(response.getDetail()).isEqualTo(givenContent.getDetail());
    }

    @Test
    @DisplayName("작성자는 게시물을 업데이트 할 수 있다.")
    void update() {
        UpdateContentRequest request
                = new UpdateContentRequest(GivenContent.GIVEN_UPDATE, GivenContent.GIVEN_UPDATE_DETAIL);

        SimpleContentResponse response = contentService.update(request, content.getId(), member.getEmail());

        assertThat(response.getTitle()).isEqualTo(request.getTitle());
        assertThat(response.getDetail()).isEqualTo(request.getDetail());
    }

    @Test
    @DisplayName("작성자가 아니라면 업데이트를 거부한다.")
    void updateDenied() {
        UpdateContentRequest request
                = new UpdateContentRequest(GivenContent.GIVEN_UPDATE, GivenContent.GIVEN_UPDATE_DETAIL);

        assertThrows(MetaDataMismatchException.class,
                () -> contentService.update(request, content.getId(), "BlockingTest"));
    }

    @Test
    @DisplayName("작성자는 게시글을 삭제할 수 있다.")
    void delete() {
        contentService.delete(content.getId(), member.getEmail());

        List<Content> all = contentRepository.findAll();

        assertThat(all.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("작성자가 아니라면 삭제를 거부한다.")
    void deleteDenied() {
        assertThrows(MetaDataMismatchException.class, () -> contentService.delete(content.getId(), "DeniedTest"));
    }
}