package com.power.usercore.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisteredEvent extends BaseEvent {
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
}
