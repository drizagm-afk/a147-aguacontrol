const $surfaceProfileMenuToggle = document.getElementById("surfaceProfileMenuToggle");
const $surfaceProfileMenu = document.getElementById("surfaceProfileMenu");

if ($surfaceProfileMenuToggle && $surfaceProfileMenu) {
    const closeMenu = () => {
        $surfaceProfileMenu.hidden = true;
        $surfaceProfileMenuToggle.setAttribute("aria-expanded", "false");
    };

    const openMenu = () => {
        $surfaceProfileMenu.hidden = false;
        $surfaceProfileMenuToggle.setAttribute("aria-expanded", "true");
    };

    $surfaceProfileMenuToggle.addEventListener("click", (event) => {
        event.stopPropagation();

        if ($surfaceProfileMenu.hidden) {
            openMenu();
        } else {
            closeMenu();
        }
    });

    $surfaceProfileMenu.addEventListener("click", (event) => {
        event.stopPropagation();
    });

    document.addEventListener("click", closeMenu);

    document.addEventListener("keydown", (event) => {
        if (event.key === "Escape") {
            closeMenu();
        }
    });
}
