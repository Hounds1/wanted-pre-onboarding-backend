package me.hounds.wanted.onboarding.domain.board.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBoardRequest {

    @NotBlank(message = "게시판 이름은 비어있을 수 없습니다.")
    private String boardName;
}
