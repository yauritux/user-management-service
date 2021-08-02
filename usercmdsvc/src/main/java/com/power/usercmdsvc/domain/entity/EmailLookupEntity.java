package com.power.usercmdsvc.domain.entities;

import com.power.usercmdsvc.domain.repositories.EmailLookupRepository;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailLookupEntity {

    @Id
    private String email;
    private String username;

    public boolean isEmailExist(final String email, final EmailLookupRepository repository) {
        return repository.existsById(email);
    }
}
