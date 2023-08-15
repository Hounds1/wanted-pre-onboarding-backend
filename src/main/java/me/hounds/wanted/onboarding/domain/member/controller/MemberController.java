package me.hounds.wanted.onboarding.domain.member.controller;

import lombok.RequiredArgsConstructor;
import me.hounds.wanted.onboarding.domain.member.domain.dto.JoinRequest;
import me.hounds.wanted.onboarding.domain.member.domain.dto.SimpleMemberResponse;
import me.hounds.wanted.onboarding.domain.member.domain.dto.UpdateMemberRequest;
import me.hounds.wanted.onboarding.domain.member.service.MemberReadService;
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
    private final MemberReadService memberReadService;

    @PostMapping("/public/members")
    public ResponseEntity<SimpleMemberResponse> join(@RequestBody @Valid JoinRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(memberService.join(request.toEntity()));
    }

    @PatchMapping("/members")
    public ResponseEntity<SimpleMemberResponse> update(@RequestBody @Valid UpdateMemberRequest request,
                                                       @AuthenticationPrincipal CustomUserDetails principal) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(memberService.update(request, principal.getId()));
    }

    @DeleteMapping("/members")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal CustomUserDetails principal) {
        memberService.delete(principal.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/public/members/{memberId}")
    public ResponseEntity<SimpleMemberResponse> findById(@PathVariable Long memberId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(memberReadService.findById(memberId));
    }

    @GetMapping("/members/me")
    public ResponseEntity<SimpleMemberResponse> whoAmI(@AuthenticationPrincipal CustomUserDetails principal) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(memberReadService.myInfo(principal.getId()));
    }


    /**
     * utils
     */
    @PatchMapping("/role/members")
    public ResponseEntity<SimpleMemberResponse> rankUp(@AuthenticationPrincipal CustomUserDetails principal) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(memberService.rankUp(principal.getId()));
    }
}
