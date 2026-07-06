const showPassword = document.getElementById("eye-icon");
const password = document.getElementById("password");

if (showPassword && password) {
    showPassword.addEventListener("click", () => {
        const show = password.type === "password";
        password.type = show ? "text" : "password";
        showPassword.className = show ? "bi bi-eye-slash" : "bi bi-eye";
    });
}
