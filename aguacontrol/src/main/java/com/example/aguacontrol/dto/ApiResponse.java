package com.example.aguacontrol.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiResponse {
    public String message;
    public Object data;
    public boolean success;

    public static ApiResponse ok(String message, Object data) {
        return new ApiResponse(message, data, true);
    }
    public static ApiResponse error(String message) {
        return new ApiResponse(message, null, false);
    }
}