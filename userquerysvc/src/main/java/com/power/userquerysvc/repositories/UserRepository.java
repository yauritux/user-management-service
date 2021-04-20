package com.power.userquerysvc.repositories;

import com.power.userquerysvc.projections.DefaultUserView;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRepository extends JpaRepository<DefaultUserView, String> {

    Optional<DefaultUserView> findByUsername(final String username);
    Optional<DefaultUserView> findByEmail(final String email);
}
