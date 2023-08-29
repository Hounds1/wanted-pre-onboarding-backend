package me.hounds.wanted.onboarding.global.common;

import lombok.Getter;
import lombok.Setter;
import me.hounds.wanted.onboarding.global.security.principal.CustomUserDetails;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@MappedSuperclass
public class BaseEntity extends BaseTimeEntity{

    @CreatedBy
    @Column(updatable = false)
    private String createBy;

    @LastModifiedBy
    private String lastModifiedBy;

    private String deleteBy;

    public void setCreateByForTest(final String email) {
        this.createBy = email;
    }

    public void recordDeleteBy() {
        CustomUserDetails principal
                = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        this.deleteBy = principal.getUsername();
    }
}
