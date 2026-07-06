const companyCheckbox = document.getElementById("chk-empresa");
const companyFields = document.getElementById("form-empresa");
const passwordInput = document.getElementById("password");
const passwordToggle = document.getElementById("signup-eye-toggle");
const passwordToggleIcon = document.getElementById("signup-eye-icon");

function syncCompanyFields() {
    if (!companyCheckbox || !companyFields) {
        return;
    }

    companyFields.classList.toggle("hide", !companyCheckbox.checked);
}

if (companyCheckbox && companyFields) {
    companyCheckbox.addEventListener("change", syncCompanyFields);
    document.addEventListener("DOMContentLoaded", syncCompanyFields);
    syncCompanyFields();
}

if (passwordInput && passwordToggle && passwordToggleIcon) {
    passwordToggle.addEventListener("click", () => {
        const show = passwordInput.type === "password";
        passwordInput.type = show ? "text" : "password";
        passwordToggleIcon.className = show ? "bi bi-eye-slash" : "bi bi-eye";
        passwordToggle.setAttribute("aria-label", show ? "Ocultar contraseña" : "Mostrar contraseña");
    });
}
