package me.hounds.wanted.onboarding.support;


public enum EndPoints {

    /**
     * Member
     */
    PUBLIC_MEMBER("/api/v1/public/members"),
    MEMBER_WITH_AUTH("/api/v1/members"),
    PUBLIC_MEMBER_FIND("/api/v1/public/members/{memberId}"),
    MEMBER_WHO_AM_I("/api/v1/members/me"),

    /**
     * Auth
     */
    PUBLIC_AUTH("/api/v1/public/auth"),

    /**
     * Board
     */
    ADMIN_BOARD("/api/v1/admin/boards"),
    ADMIN_BOARD_WITH_COUNT("/api/v1/admin/boards/{boardId}"),

    /**
     * Content
     */
    USER_CONTENT("/api/v1/{boardId}/contents"),

    USER_CONTENT_UPDATE("/api/v1/{contentId}/contents"),
    USER_CONTENT_DELETE("/api/v1/contents/{contentId}"),
    PUBLIC_CONTENT_PAGING("/api/v1/public/{boardId}/contents"),
    PUBLIC_CONTENT_ID("/api/v1//public/contents"),

    /**
     *
     */

    REQUEST_LIKE("/api/v1/{contentId}/likes");

    private final String url;
    EndPoints(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
