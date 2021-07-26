package com.power.userquerysvc.domain.repositories;

import com.power.userquerysvc.domain.projections.DefaultUserView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<DefaultUserView, String> {

    Optional<DefaultUserView> findByUsername(final String username);
    Optional<DefaultUserView> findByEmail(final String email);
}
