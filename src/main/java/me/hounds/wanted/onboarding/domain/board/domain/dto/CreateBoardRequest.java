package me.hounds.wanted.onboarding.domain.board.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.hounds.wanted.onboarding.domain.board.domain.persist.Board;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateBoardRequest {

    @NotBlank(message = "게시판 이름은 비어있을 수 없습니다.")
    private String boardName;

    public Board toEntity() {
        return Board.builder()
                .boardName(boardName)
                .contents(new ArrayList<>())
                .build();
    }
}
