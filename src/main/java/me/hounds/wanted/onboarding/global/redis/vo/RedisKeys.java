package me.hounds.wanted.onboarding.global.redis.vo;

import lombok.Getter;

@Getter
public enum RedisKeys {

    TOP_RECOMMENDATION_META("top-recommend-meta"),
    TOP_RECOMMENDATION_UNIT("top-recommend-"),
    CASHING_CONTENTS("cashing-contents-");


    private final String key;
    RedisKeys(final String key) {
        this.key = key;
    }
}
