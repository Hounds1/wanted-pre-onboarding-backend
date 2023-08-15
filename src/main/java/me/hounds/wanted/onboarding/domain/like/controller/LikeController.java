package me.hounds.wanted.onboarding.domain.like.controller;

import lombok.RequiredArgsConstructor;
import me.hounds.wanted.onboarding.domain.like.service.LikeService;
import me.hounds.wanted.onboarding.global.security.principal.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{contentId}/likes")
    public ResponseEntity<Void> likeAndDislike(@PathVariable Long contentId, @AuthenticationPrincipal CustomUserDetails principal) {
        likeService.likeAndDislike(contentId, principal.getId());

        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }

}
