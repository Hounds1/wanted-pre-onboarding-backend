package me.hounds.wanted.onboarding.support.board;

import me.hounds.wanted.onboarding.domain.board.domain.persist.Board;

public class GivenBoard {

    public static final String GIVEN_BOARD_NAME = "TEST BOARD";
    public static final String GIVEN_UPDATE_NAME = "TEST UPDATE";

    public static Board givenBoard() {
        return Board.builder()
                .boardName(GIVEN_BOARD_NAME)
                .activated(true)
                .build();
    }
}
