const $profileMenuToggle = document.getElementById("profileMenuToggle");
const $profileMenu = document.getElementById("profileMenu");

if ($profileMenuToggle && $profileMenu) {
    const closeMenu = () => {
        $profileMenu.hidden = true;
        $profileMenuToggle.setAttribute("aria-expanded", "false");
    };

    const openMenu = () => {
        $profileMenu.hidden = false;
        $profileMenuToggle.setAttribute("aria-expanded", "true");
    };

    $profileMenuToggle.addEventListener("click", (event) => {
        event.stopPropagation();

        if ($profileMenu.hidden) {
            openMenu();
        } else {
            closeMenu();
        }
    });

    $profileMenu.addEventListener("click", (event) => {
        event.stopPropagation();
    });

    document.addEventListener("click", closeMenu);

    document.addEventListener("keydown", (event) => {
        if (event.key === "Escape") {
            closeMenu();
        }
    });
}
