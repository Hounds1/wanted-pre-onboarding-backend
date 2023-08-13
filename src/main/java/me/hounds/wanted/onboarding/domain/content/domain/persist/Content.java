package me.hounds.wanted.onboarding.domain.content.domain.persist;

import lombok.*;
import me.hounds.wanted.onboarding.domain.board.domain.persist.Board;
import me.hounds.wanted.onboarding.domain.content.domain.dto.UpdateContentRequest;
import me.hounds.wanted.onboarding.global.common.BaseEntity;
import me.hounds.wanted.onboarding.global.jwt.vo.AccessToken;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@DynamicUpdate
@DynamicInsert
@Where(clause = "activated = true")
@Builder
public class Content extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_id", updatable = false)
    private Long id;

    private String title;

    private String detail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    private boolean activated;

    public void initBoard(final Board board) {
        this.board = board;
    }

    public void update(final UpdateContentRequest request) {
        this.title = request.getTitle();
        this.detail = request.getDetail();
    }

    public void deactivated() {
        this.activated = false;
        recordDeleteTime();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        Content other = (Content) obj;
        return Objects.equals(id, other.getId());
    }
}
