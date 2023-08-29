package me.hounds.wanted.onboarding.domain.hashtag.domain.persist;

import lombok.*;
import me.hounds.wanted.onboarding.global.common.BaseTimeEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class HashTag extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id")
    private Long id;

    private String tagName;

    @Builder
    HashTag (final String tagName) {
        this.tagName = tagName;
    }
}
