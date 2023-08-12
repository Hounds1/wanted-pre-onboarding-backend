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

    private String title;

    private String detail;

    private LocalDateTime createdTime;

    private String CreatedBy;

    private LocalDateTime lastModifiedTime;

    private String lastModifiedBy;
    public static SimpleContentResponse of(final Content content) {
        return new SimpleContentResponse(content.getTitle(), content.getDetail(), content.getCreateTime(),
                content.getCreateBy(), content.getLastModifiedDate(), content.getLastModifiedBy());
    }
}
