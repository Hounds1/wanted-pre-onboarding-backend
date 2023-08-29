package me.hounds.wanted.onboarding.global.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    /**
     * Common
     */
    BAD_REQUEST(400, "C001", "잘못된 요청입니다"),
    INFO_MISMATCH(403, "C002", "일치하지 않는 유저 정보."),

    /**
     * Member
     */
    DUPLICATE_EMAIL(400, "ME001", "이미 사용 중인 이메일입니다."),
    PASSWORD_LENGTH_REQUIRED(400, "ME002", "비밀번호는 8자 이상이어야 합니다."),
    MEMBER_NOT_FOUND(400, "ME003", "존재하지 않는 사용자입니다."),
    IS_NOT_ADMIN(403, "ME004", "관리자 계정이 아닙니다."),

    /**
     * Auth
     */
    TOKEN_NOT_FOUND(403, "AU001", "토큰 확인 중 이상이 감지되었습니다."),
    CAN_NOT_REISSUE(403, "AU002", "재인증 할 수 없는 상태입니다."),

    /**
     * Board
     */
    DUPLICATE_BOARD_NAME(400, "BO001", "이미 사용 중인 게시판 이름입니다."),
    BOARD_NOT_FOUND(400, "BO002", "존재하지 않는 게시판입니다."),

    /**
     * Content
     */
    CONTENT_NOT_FOUND(400, "CO001", "존재하지 않는 게시글입니다."),
    CAN_NOT_READ_CONTENTS(500, "CO002", "컨텐츠를 읽어올 수 없습니다. 잠시 후 시도해주십시오"),

    /**
     * Like
     */
    LIKE_HAS_PROBLEM(500, "LI001", "잠시 후 다시 시도해주세요."),

    /**
     * HashTag
     */
    CAN_NOT_USE_TARGET_TAG(400, "HA001", "해당 태그를 사용할 수 없는 상태입니다. 잠시 후 시도해주십시오.");
    private final int code;
    private final String status;
    private final String message;

    ErrorCode(int code, String status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }
}
