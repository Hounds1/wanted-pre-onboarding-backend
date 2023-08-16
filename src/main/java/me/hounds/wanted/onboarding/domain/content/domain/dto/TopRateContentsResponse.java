package me.hounds.wanted.onboarding.domain.content.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TopRateContentsResponse {

    private Long contentId;

    private String title;

    private long likeCount;

    public static TopRateContentsResponse of(final SimpleContentResponse response) {
        return new TopRateContentsResponse(response.getContentId(), response.getTitle(), response.getLikeCount());
    }
}
