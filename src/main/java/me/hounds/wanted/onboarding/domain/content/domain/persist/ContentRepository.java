package me.hounds.wanted.onboarding.domain.content.domain.persist;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, Long> {

    Page<Content> findAllByBoardId(final Pageable pageable, final Long boardId);
}
