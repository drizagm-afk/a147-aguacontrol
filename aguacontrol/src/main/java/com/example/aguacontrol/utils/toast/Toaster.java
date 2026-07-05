package com.example.aguacontrol.utils.toast;

import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

public class Toaster {
    final List<Toast> toasts = new ArrayList<>();

    public Toaster success(String message) {
        return success("", message);
    }
    public Toaster success(String title, String message) {
        toasts.add(Toast.success(title, message));
        return this;
    }

    public Toaster error(String message) {
        return error("", message);
    }
    public Toaster error(String title, String message) {
        toasts.add(Toast.error(title, message));
        return this;
    }

    public Toaster warning(String message) {
        return warning("", message);
    }
    public Toaster warning(String title, String message) {
        toasts.add(Toast.warning(title, message));
        return this;
    }

    public Toaster info(String message) {
        return info("", message);
    }
    public Toaster info(String title, String message) {
        toasts.add(Toast.info(title, message));
        return this;
    }

    public void cook(Model model) {
        model.addAttribute("toasts", toasts);
    }
    public void cook(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("toasts", toasts);
    }
}
