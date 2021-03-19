package com.accountapp.rest.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Component
public class BaseEntityListener extends AuditingEntityListener {

    @PrePersist
    private void onPrePersist(BaseEntity baseEntity) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        baseEntity.setCreatedTime(LocalDateTime.now());
        baseEntity.setUpdatedTime(LocalDateTime.now());
        baseEntity.setCreatedBy(1L);
        baseEntity.setUpdatedBy(1L);
        if (authentication != null && !authentication.getName().equals("anonymousUser")) {
            long id = Long.parseLong(authentication.getName());
            baseEntity.setCreatedBy(id);
            baseEntity.setUpdatedBy(id);
        }
    }

    @PreUpdate
    private void onPreUpdate(BaseEntity baseEntity) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        baseEntity.setUpdatedTime(LocalDateTime.now());
        baseEntity.setUpdatedBy(1L);
        if (authentication != null && !authentication.getName().equals("anonymousUser")) {
            long id = Long.parseLong(authentication.getName());
            baseEntity.setUpdatedBy(id);
        }
    }
}
