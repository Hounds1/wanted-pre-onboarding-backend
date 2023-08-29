package me.hounds.wanted.onboarding.domain.contentwithtag.domain.persist;

import lombok.*;
import me.hounds.wanted.onboarding.domain.content.domain.persist.Content;
import me.hounds.wanted.onboarding.domain.hashtag.domain.persist.HashTag;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ContentWithHashTag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_with_tag_id", updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "content_id")
    private Content content;

    @ManyToOne
    @JoinColumn(name = "hashtag_id")
    private HashTag hashTag;

    public static ContentWithHashTag from(final Content content, final HashTag hashTag) {
        return ContentWithHashTag.builder()
                .content(content)
                .hashTag(hashTag)
                .build();
    }
}
