package me.hounds.wanted.onboarding.support;


public enum EndPoints {

    /**
     * Member
     */
    PUBLIC_MEMBER("/api/v1/public/members"),

    /**
     * Auth
     */
    PUBLIC_AUTH("/api/v1/public/auth"),

    /**
     * Board
     */
    ADMIN_BOARD("/api/v1/admin/boards"),
    ADMIN_BOARD_WITH_COUNT("/api/v1/admin/boards/1"),

    /**
     * Content
     */
    USER_CONTENT("/api/v1/1/contents"),
    PUBLIC_CONTENT_PAGING("/api/v1/public/1/contents"),
    PUBLIC_CONTENT_ID("/api/v1//public/contents?contentId=1");


    private final String url;
    EndPoints(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
