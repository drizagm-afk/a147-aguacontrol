package com.example.aguacontrol.dto;

public record ToastDTO(
        String type,
        String title,
        String message
) {
    public static ToastDTO success(String title, String message) {
        return new ToastDTO("success", title, message);
    }
    public static ToastDTO error(String title, String message) {
        return new ToastDTO("error", title, message);
    }
    public static ToastDTO warning(String title, String message) {
        return new ToastDTO("warning", title, message);
    }
    public static ToastDTO info(String title, String message) {
        return new ToastDTO("info", title, message);
    }
}
