package me.hounds.wanted.onboarding.support.content;

import me.hounds.wanted.onboarding.domain.content.domain.persist.Content;

public class GivenContent {

    public static final String GIVEN_TITLE = "TEST TITLE";
    public static final String GIVEN_DETAIL = "TEST DETAIL";
    public static final String GIVEN_UPDATE = "TEST UPDATE";
    public static final String GIVEN_UPDATE_DETAIL = "TEST UPDATE DETAIL";

    public static Content givenContent() {
        return Content.builder()
                .title(GIVEN_TITLE)
                .detail(GIVEN_DETAIL)
                .activated(true)
                .build();
    }

    public static Content givenContentWithCount() {
        return Content.builder()
                .id(1L)
                .title(GIVEN_TITLE)
                .detail(GIVEN_DETAIL)
                .activated(true)
                .build();
    }
}
