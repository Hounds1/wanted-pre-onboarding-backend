package me.hounds.wanted.onboarding.domain.content.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.hounds.wanted.onboarding.domain.content.domain.persist.Content;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleContentResponse {

    private Long contentId;

    private String title;

    private String detail;

    private LocalDateTime createdTime;

    private String createdBy;

    private LocalDateTime lastModifiedTime;

    private String lastModifiedBy;

    private long likeCount;
    public static SimpleContentResponse of(final Content content, final long likeCount) {
        return new SimpleContentResponse(content.getId() ,content.getTitle(), content.getDetail(), content.getCreateTime(),
                content.getCreateBy(), content.getLastModifiedDate(), content.getLastModifiedBy(), likeCount);
    }
}
