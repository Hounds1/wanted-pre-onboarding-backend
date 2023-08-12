package me.hounds.wanted.onboarding.domain.member.domain.persist;

import lombok.*;
import me.hounds.wanted.onboarding.domain.member.domain.vo.RoleType;
import me.hounds.wanted.onboarding.global.common.BaseTimeEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@DynamicUpdate
@DynamicInsert
@Where(clause = "activated = true")
@Builder
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id",updatable = false)
    private Long id;

    @Column(updatable = false)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    private boolean activated;

    public void initPassword(final String encodedPassword) {
        this.password = encodedPassword;
    }

    public void activated() {
        this.activated = true;
    }

    public void deactivated() {
        this.activated = false;
    }

    public void changeRole(final RoleType role) {
        this.role = role;
    }

    /**
     * 회원가입 시 기본 설정
     */
    public void forJoin() {
        activated();
        this.role = RoleType.USER;
    }
}
