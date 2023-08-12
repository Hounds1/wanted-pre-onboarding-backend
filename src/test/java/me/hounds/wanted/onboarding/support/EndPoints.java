package me.hounds.wanted.onboarding.support;


public enum EndPoints {

    /**
     * Member
     */
    PUBLIC_MEMBER("/api/v1/public/members"),
    PUBLIC_AUTH("/api/v1/public/auth");


    private final String url;
    EndPoints(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
