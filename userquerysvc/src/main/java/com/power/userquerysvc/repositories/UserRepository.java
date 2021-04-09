package com.power.userquerysvc.repositories;

import com.power.userquerysvc.projections.DefaultUserView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<DefaultUserView, String> {
}
