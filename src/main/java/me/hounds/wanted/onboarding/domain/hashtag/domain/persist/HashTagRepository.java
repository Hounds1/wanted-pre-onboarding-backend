package me.hounds.wanted.onboarding.domain.hashtag.domain.persist;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HashTagRepository extends JpaRepository<HashTag, Long> {

    boolean existsByTagName(final String tagName);

    Optional<HashTag> findByTagName(final String tagName);

}
