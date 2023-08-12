package me.hounds.wanted.onboarding.domain.member.domain.persist;

import lombok.*;
import me.hounds.wanted.onboarding.domain.member.domain.vo.RoleType;
import me.hounds.wanted.onboarding.global.common.BaseTimeEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
    private Long id;

    private String email;

    private String password;

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

    public void forJoin() {
        activated();
        this.role = RoleType.USER;
    }
}
