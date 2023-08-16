package me.hounds.wanted.onboarding.domain.recommend.controller;

import lombok.RequiredArgsConstructor;
import me.hounds.wanted.onboarding.domain.recommend.service.RecommendService;
import me.hounds.wanted.onboarding.global.security.principal.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class RecommendController {

    private final RecommendService recommendService;

    @PostMapping("/{contentId}/likes")
    public ResponseEntity<Void> likeAndDislike(@PathVariable Long contentId, @AuthenticationPrincipal CustomUserDetails principal) {
        recommendService.likeAndDislike(contentId, principal.getId());

        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }

}
