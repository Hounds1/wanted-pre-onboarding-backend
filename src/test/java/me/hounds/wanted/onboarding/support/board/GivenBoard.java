package me.hounds.wanted.onboarding.support.board;

import me.hounds.wanted.onboarding.domain.board.domain.persist.Board;

import java.util.ArrayList;

public class GivenBoard {

    public static final String GIVEN_BOARD_NAME = "TEST BOARD";
    public static final String GIVEN_BOARD_NAME_V2 = "TEST V2 BOARD";
    public static final String GIVEN_UPDATE_NAME = "TEST UPDATE";

    public static Board givenBoard() {
        return Board.builder()
                .boardName(GIVEN_BOARD_NAME)
                .contents(new ArrayList<>())
                .activated(true)
                .build();
    }

    public static Board givenBoardWithCount() {
        return Board.builder()
                .id(1L)
                .boardName(GIVEN_BOARD_NAME_V2)
                .contents(new ArrayList<>())
                .activated(true)
                .build();
    }
}
