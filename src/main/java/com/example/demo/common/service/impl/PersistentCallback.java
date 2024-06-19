package com.example.demo.common.service.impl;

import java.time.LocalDateTime;

import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.mongodb.core.mapping.event.ReactiveBeforeConvertCallback;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.reactivestreams.Publisher;
import org.springframework.data.mongodb.core.mapping.event.ReactiveBeforeConvertCallback;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import com.example.demo.common.service.PersistentService;
import com.example.demo.user.domain.Username;


public class PersistentCallback implements ReactiveBeforeConvertCallback<PersistentService>{
    @Override
    public Publisher<PersistentService> onBeforeConvert(@SuppressWarnings("null") PersistentService entity, @SuppressWarnings("null") String collection) {

        var user = ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .filter(it -> it != null && it.isAuthenticated())
                .map(Authentication::getPrincipal)
                .cast(UserDetails.class)
                .map(userDetails -> new Username(userDetails.getUsername()))
                .switchIfEmpty(Mono.empty());

        var currentTime = LocalDateTime.now();

        if (entity.getId() == null) {
            entity.setCreatedDate(currentTime);
        }
        entity.setLastModifiedDate(currentTime);

        return user.map(u -> {
            if (entity.getId() == null) {
                entity.setCreatedBy(u);
            }
            entity.setLastModifiedBy(u);

            return entity;
        }).defaultIfEmpty(entity);
    }
}
