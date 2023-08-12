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
    ADMIN_BOARD_WITH_COUNT("/api/v1/admin/boards/1");




    private final String url;
    EndPoints(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
