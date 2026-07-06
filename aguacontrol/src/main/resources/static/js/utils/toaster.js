import * as Html from "/js/utils/html.js";

let $toasts = document.getElementById("toasts");

if (!$toasts) {
    $toasts = document.createElement("div");
    $toasts.id = "toasts";
    $toasts.className = "toast-container position-fixed top-0 end-0 p-3";
    $toasts.style.zIndex = "2000";

    document.body.appendChild($toasts);
}

//RENDER
function show(type, title, message) {
    const $toast = cook(
        type, title, message, {
            success: "text-bg-success",
            error: "text-bg-danger",
            warning: "text-bg-warning",
            info: "text-bg-info"
        }[type]
    );
    $toasts.appendChild($toast);

    if (!window.bootstrap || !bootstrap.Toast)
        return;
    const $bs_toast = new bootstrap.Toast($toast, {
        delay: 3500,
        autohide: true
    });
    $bs_toast.show();

    $toast.addEventListener("hidden.bs.toast", () => $toast.remove());
}
function cook(type, title, message, bootstrapClass) {
    const $toast = document.createElement("div");
    $toast.className = `toast align-items-center border-0 ${bootstrapClass}`;
    $toast.setAttribute("role", "alert");
    $toast.setAttribute("aria-live", "assertive");
    $toast.setAttribute("aria-atomic", "true");
    const icons = {
        success: "bi bi-bell-fill",
        error: "bi bi-x-circle-fill",
        warning: "bi bi-exclamation-triangle-fill",
        info: "bi bi-info-circle-fill"
    };
    $toast.innerHTML = `
        <div class="d-flex align-items-start gap-3">
            <i class="${icons[type]}"></i>
            <div class="toast-body">
                <strong class="d-block">${Html.text(title)}</strong>
                <span>${Html.text(message)}</span>
            </div>
            <button type="button"
                    class="btn-close btn-close-white ms-auto"
                    data-bs-dismiss="toast"
                    aria-label="Cerrar"></button>
        </div>
    `;

    return $toast;
}

document.addEventListener("DOMContentLoaded", () => {
    const $serverToasts = document.getElementById("server-toasts");
    if (!$serverToasts)
        return;

    $serverToasts.querySelectorAll("*").forEach($ => {
        const type = $.dataset.type;
        const title = $.dataset.title;
        const message = $.dataset.message;

        show(type, title, message);
    });
    $serverToasts.remove();
});

//UTILS
export function success(title, message) {
    show("success", title, message);
}

export function error(title, message) {
    show("error", title, message);
}

export function warning(title, message) {
    show("warning", title, message);
}

export function info(title, message) {
    show("info", title, message);
}