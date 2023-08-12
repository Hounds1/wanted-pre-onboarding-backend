package me.hounds.wanted.onboarding.domain.content.controller;

import lombok.RequiredArgsConstructor;
import me.hounds.wanted.onboarding.domain.content.domain.dto.CreateContentRequest;
import me.hounds.wanted.onboarding.domain.content.domain.dto.SimpleContentResponse;
import me.hounds.wanted.onboarding.domain.content.service.ContentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ContentController {

    private final ContentService contentService;

    @PostMapping("/{boardId}/contents")
    public ResponseEntity<SimpleContentResponse> create(@RequestBody @Valid CreateContentRequest request,
                                                        @PathVariable Long boardId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(contentService.create(request.toEntity(), boardId));
    }

    // TODO: 2023-08-12 CRUD 전체 생성 및 테스트 
}
