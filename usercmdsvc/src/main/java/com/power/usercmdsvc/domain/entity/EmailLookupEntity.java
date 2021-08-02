package com.power.usercmdsvc.domain.entity;

import com.power.usercmdsvc.domain.repository.EmailLookupRepository;
import lombok.*;

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
