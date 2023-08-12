package me.hounds.wanted.onboarding.domain.board.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.hounds.wanted.onboarding.domain.board.domain.persist.Board;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleBoardResponse {

    private String boardName;

    public static SimpleBoardResponse of(final Board board) {
        return new SimpleBoardResponse(board.getBoardName());
    }
}
