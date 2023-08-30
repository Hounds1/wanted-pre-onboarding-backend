package me.hounds.wanted.onboarding.domain.hashtag.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.hounds.wanted.onboarding.domain.hashtag.domain.persist.HashTag;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class SimpleHashTagResponse implements Serializable {

    private Long HashTagId;
    private String tagName;

    public static SimpleHashTagResponse of(final HashTag hashTag) {
        return new SimpleHashTagResponse(hashTag.getId(), hashTag.getTagName());
    }
}
