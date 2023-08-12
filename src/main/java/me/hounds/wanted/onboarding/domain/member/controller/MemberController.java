package me.hounds.wanted.onboarding.domain.member.controller;

import lombok.RequiredArgsConstructor;
import me.hounds.wanted.onboarding.domain.member.domain.dto.JoinRequest;
import me.hounds.wanted.onboarding.domain.member.domain.dto.SimpleMemberResponse;
import me.hounds.wanted.onboarding.domain.member.service.MemberService;
import me.hounds.wanted.onboarding.global.security.principal.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/public/members")
    public ResponseEntity<SimpleMemberResponse> join(@RequestBody @Valid JoinRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(memberService.join(request.toEntity()));
    }

    @PatchMapping("/role/members")
    public ResponseEntity<SimpleMemberResponse> rankUp(@AuthenticationPrincipal CustomUserDetails principal) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(memberService.rankUp(principal.getId()));
    }
}
