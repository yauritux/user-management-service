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
public class UpdateUserResponse extends BaseResponse {

    private String username;

    public UpdateUserResponse(String id, String message) {
        super(message);
        this.username = id;
    }
}
