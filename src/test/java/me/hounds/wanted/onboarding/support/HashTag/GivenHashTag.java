package me.hounds.wanted.onboarding.support.HashTag;

import me.hounds.wanted.onboarding.domain.hashtag.domain.dto.SimpleHashTagResponse;
import me.hounds.wanted.onboarding.domain.hashtag.domain.persist.HashTag;

import java.util.ArrayList;
import java.util.List;

public class GivenHashTag {

    public static List<SimpleHashTagResponse> getDummyHashTags() {
        List<SimpleHashTagResponse> result = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            HashTag dummy = HashTag.builder()
                    .tagName("default" + i)
                    .build();
            result.add(SimpleHashTagResponse.of(dummy));
        }

        return result;
    }

    public static List<String> getStringHashTags() {
        List<String> result = new ArrayList<>();

        for (int i = 0; i <= 3; i++) {
            result.add("default" + i);
        }

        return result;
    }
}
