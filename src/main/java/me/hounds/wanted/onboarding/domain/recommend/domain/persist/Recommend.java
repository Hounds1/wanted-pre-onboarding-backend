package me.hounds.wanted.onboarding.domain.recommend.domain.persist;

import lombok.*;
import me.hounds.wanted.onboarding.global.common.BaseTimeEntity;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Where(clause = "activated = true")
@Table(name = "likes")
@Builder
public class Recommend extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id",updatable = false)
    private Long id;

    @Column(name = "content_id", updatable = false)
    private Long contentId;

    @Column(name = "member_id", updatable = false)
    private Long memberId;

    private boolean activated;

    private Recommend(Long contentId, Long memberId) {
        this.contentId = contentId;
        this.memberId = memberId;
    }

    public static Recommend of(final Long contentId, final Long memberId) {
        return new Recommend(contentId, memberId);
    }

    public void activated() {
        this.activated = true;
    }

    public void deactivated() {
        this.activated = false;
        recordDeleteTime();
    }
}
