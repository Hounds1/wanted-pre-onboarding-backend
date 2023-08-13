package me.hounds.wanted.onboarding.domain.content.controller;

import lombok.RequiredArgsConstructor;
import me.hounds.wanted.onboarding.domain.content.domain.dto.CreateContentRequest;
import me.hounds.wanted.onboarding.domain.content.domain.dto.SimpleContentResponse;
import me.hounds.wanted.onboarding.domain.content.domain.dto.UpdateContentRequest;
import me.hounds.wanted.onboarding.domain.content.service.ContentReadService;
import me.hounds.wanted.onboarding.domain.content.service.ContentService;
import me.hounds.wanted.onboarding.global.common.CustomPageResponse;
import me.hounds.wanted.onboarding.global.security.principal.CustomUserDetails;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ContentController {

    private final ContentService contentService;
    private final ContentReadService contentReadService;

    @PostMapping("/{boardId}/contents")
    public ResponseEntity<SimpleContentResponse> create(@RequestBody @Valid CreateContentRequest request,
                                                        @PathVariable Long boardId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(contentService.create(request.toEntity(), boardId));
    }

    @PutMapping("/{contentId}/contents")
    public ResponseEntity<SimpleContentResponse> update(@RequestBody @Valid UpdateContentRequest request,
                                                        @PathVariable Long contentId,
                                                        @AuthenticationPrincipal CustomUserDetails details) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(contentService.update(request, contentId, details.getUsername()));
    }

    @DeleteMapping("/{contentId}/contents")
    public ResponseEntity<Void> delete(@PathVariable Long contentId, @AuthenticationPrincipal CustomUserDetails details) {
        contentService.delete(contentId, details.getUsername());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    /**
     * 요청 시 쿼리스트링 page 필수
     * ex) ~/1/contents?page=2
     */
    @GetMapping("/public/{boardId}/contents")
    public ResponseEntity<CustomPageResponse<SimpleContentResponse>> findContentsByPaging(@PathVariable Long boardId,
                                                                                          @PageableDefault(size = 20, sort = "title", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(contentReadService.findWithPaging(boardId, pageable));
    }

    @GetMapping("/public/contents")
    public ResponseEntity<SimpleContentResponse> findById(@RequestParam(name = "contentId") Long contentId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(contentReadService.findById(contentId));
    }
}
