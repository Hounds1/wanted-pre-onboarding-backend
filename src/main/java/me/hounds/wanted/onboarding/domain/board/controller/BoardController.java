package me.hounds.wanted.onboarding.domain.board.controller;

import lombok.RequiredArgsConstructor;
import me.hounds.wanted.onboarding.domain.board.domain.dto.CreateBoardRequest;
import me.hounds.wanted.onboarding.domain.board.domain.dto.SimpleBoardResponse;
import me.hounds.wanted.onboarding.domain.board.domain.dto.UpdateBoardRequest;
import me.hounds.wanted.onboarding.domain.board.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/admin/boards")
    public ResponseEntity<SimpleBoardResponse> create(@RequestBody @Valid CreateBoardRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(boardService.create(request.toEntity()));
    }

    @PatchMapping("/admin/boards/{boardId}")
    public ResponseEntity<SimpleBoardResponse> update(@RequestBody @Valid UpdateBoardRequest request,
                                                      @PathVariable Long boardId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(boardService.update(request, boardId));
    }

    @DeleteMapping("/admin/boards/{boardId}")
    public ResponseEntity<Void> delete(@PathVariable Long boardId) {
        boardService.delete(boardId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
