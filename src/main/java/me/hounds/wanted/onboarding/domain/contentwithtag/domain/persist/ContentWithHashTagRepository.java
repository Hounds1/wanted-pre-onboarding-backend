package me.hounds.wanted.onboarding.domain.contentwithtag.domain.persist;

import me.hounds.wanted.onboarding.domain.content.domain.persist.Content;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContentWithHashTagRepository extends JpaRepository<ContentWithHashTag, Long> {

    List<ContentWithHashTag> findAllByContent(final Content content);
}
