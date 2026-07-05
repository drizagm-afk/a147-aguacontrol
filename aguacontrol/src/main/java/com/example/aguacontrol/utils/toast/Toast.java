package com.example.aguacontrol.utils.toast;

public record Toast(
        String type,
        String title,
        String message
) {
    public static Toast success(String title, String message) {
        return new Toast("success", title, message);
    }
    public static Toast error(String title, String message) {
        return new Toast("error", title, message);
    }
    public static Toast warning(String title, String message) {
        return new Toast("warning", title, message);
    }
    public static Toast info(String title, String message) {
        return new Toast("info", title, message);
    }
}
