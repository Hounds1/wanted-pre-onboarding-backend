package me.hounds.wanted.onboarding.domain.board.domain.persist;

import lombok.*;
import me.hounds.wanted.onboarding.domain.board.domain.dto.UpdateBoardRequest;
import me.hounds.wanted.onboarding.global.common.BaseEntity;
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
public class Board extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id", updatable = false)
    private Long id;

    private String boardName;

    private boolean activated;

    public void forCreate() {
        this.activated = true;
    }

    public void update(final UpdateBoardRequest request) {
        this.boardName = request.getBoardName();
    }

    public void deactivated() {
        this.activated = false;
        recordDeleteTime();
    }
}
