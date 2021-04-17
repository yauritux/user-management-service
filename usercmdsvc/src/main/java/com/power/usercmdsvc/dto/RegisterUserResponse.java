package com.power.usercmdsvc.dto;

import com.power.usercore.dto.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserResponse extends BaseResponse {
    private String username;

    public RegisterUserResponse(String id, String message) {
        super(message);
        this.username = id;
    }
}
