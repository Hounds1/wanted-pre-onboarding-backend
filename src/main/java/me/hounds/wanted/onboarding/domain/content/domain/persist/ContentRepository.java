package me.hounds.wanted.onboarding.domain.content.domain.persist;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, Long> {
}
