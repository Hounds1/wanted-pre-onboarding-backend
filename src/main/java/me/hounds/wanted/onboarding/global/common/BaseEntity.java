package me.hounds.wanted.onboarding.global.common;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@MappedSuperclass
public class BaseEntity {

    @CreatedBy
    @Column(updatable = false)
    private Long createBy;

    @LastModifiedBy
    private Long lastModifiedBy;
}
