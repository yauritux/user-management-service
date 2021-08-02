package com.power.usercore.domain.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdatedEvent extends BaseEvent {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
}
