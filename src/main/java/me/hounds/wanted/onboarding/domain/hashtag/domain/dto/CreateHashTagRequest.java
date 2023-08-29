package me.hounds.wanted.onboarding.domain.hashtag.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.hounds.wanted.onboarding.domain.hashtag.domain.persist.HashTag;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateHashTagRequest {

    private String tagName;

    public static CreateHashTagRequest from(final String tagName) {
        return new CreateHashTagRequest(tagName);
    }

    public HashTag toEntity() {
        return HashTag.builder()
                .tagName(tagName)
                .build();
    }
}
