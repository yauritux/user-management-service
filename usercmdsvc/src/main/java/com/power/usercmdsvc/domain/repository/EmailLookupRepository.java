package com.power.usercmdsvc.domain.repositories;

import com.power.usercmdsvc.domain.entities.EmailLookupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailLookupRepository extends JpaRepository<EmailLookupEntity, String> {

    Optional<EmailLookupEntity> findByUsername(final String username);
    Optional<EmailLookupEntity> findByEmail(final String email);
}
