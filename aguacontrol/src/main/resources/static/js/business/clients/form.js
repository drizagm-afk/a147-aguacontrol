import * as Expr from '/js/utils/expr.js';
import * as Ctrl from '/js/business/main.js';

//INIT
let telefonos = [];
let direcciones = [];

const $telefonoInit = document.getElementById("telefonoInit");
const $direccionInit = document.getElementById("direccionInit");

function text(value) {
    return String(value ?? "")
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll('"', "&quot;")
        .replaceAll("'", "&#039;");
}

function init() {
    $telefonoInit.querySelectorAll("*").forEach(elem => {
        telefonos.push({
            numero: elem.dataset.value ?? "",
            error: elem.dataset.error || null
        });
    });
    $direccionInit.querySelectorAll("*").forEach(elem => {
        direcciones.push({
            referencia: elem.dataset.value ?? "",
            error: elem.dataset.error || null
        });
    });

    renderTelefonoView();
    renderDireccionView();
}

//>>>> RENDER
const $form = document.getElementById("form");
const $telefonoModal = document.getElementById("telefonoModal");
const $direccionModal = document.getElementById("direccionModal");

//TELEFONOS RENDER
const $telefonoView = document.getElementById("telefonoView");

function renderTelefonoView() {
    $telefonoView.value = telefonos[telefonos.length - 1]?.numero ?? "";
}

document.getElementById("telefonoOpen").addEventListener("click", () => {
    $telefonoModal.classList.add("open");
});
document.getElementById("telefonoClose").addEventListener("click", () => {
    $telefonoModal.classList.remove("open");
});

const $telefonos = document.getElementById("telefonos");
const $telefonoFields = document.getElementById("telefonoFields");

function renderTelefonos() {
    $telefonos.innerHTML = "";
    telefonos.forEach((t, i) => {
        $telefonos.innerHTML += `
            <tr>
                <td>
                    <div class="modal-input-row">
                    <input class="modal-field" data-id="${i}" placeholder="Telefono" value="${text(t.numero)}"
                        ${Ctrl.ifView("readonly")}>
                    </div>
                    <div class="modal-field-error">${text(t.error)}</div>
                </td>
                <td>${Ctrl.ifNotView(`<button type="button" class="telefonoDel btn-delete-row" data-id="${i}">Eliminar</button>`)}</td>
            </tr>
        `;
    })
    $telefonos.querySelectorAll("input").forEach($ => {
        $.addEventListener("input", e => {
            const lastId = telefonos.length - 1;
            const id = Number($.dataset.id);
            telefonos[Number($.dataset.id)] = {
                numero: e.target.value,
                error: null
            }

            if (id === lastId)
                renderTelefonoView();
        });
    });
    $telefonos.querySelectorAll(".telefonoDel").forEach($ => {
        $.addEventListener("click", () => {
            const lastId = telefonos.length - 1;
            const id = Number($.dataset.id);
            telefonos.splice(id, 1);
            renderTelefonos();

            if (id === lastId)
                renderTelefonoView();
        });
    });
}

Expr.With(document.getElementById("telefonoAdd"), btn => {
    btn.addEventListener("click", () => {
        telefonos.push({
            numero: ""
        });
        renderTelefonos();
        renderTelefonoView();
    });
})

function syncTelefonoFields() {
    $telefonoFields.innerHTML = "";
    telefonos.forEach((t, i) => {
        $telefonoFields.innerHTML += `
            <input type="hidden" name="telefonos[${i}].numero" value="${text(t.numero)}">
        `;
    });
}

//DIRECCIONES RENDER
const $direccionView = document.getElementById("direccionView");

function renderDireccionView() {
    $direccionView.value = direcciones[direcciones.length - 1]?.referencia ?? "";
}

document.getElementById("direccionOpen").addEventListener("click", () => {
    $direccionModal.classList.add("open");
});
document.getElementById("direccionClose").addEventListener("click", () => {
    $direccionModal.classList.remove("open");
});

const $direcciones = document.getElementById("direcciones");
const $direccionFields = document.getElementById("direccionFields");

function renderDirecciones() {
    $direcciones.innerHTML = "";
    direcciones.forEach((d, i) => {
        $direcciones.innerHTML += `
            <tr>
                <td>
                    <div class="modal-input-row">
                    <input class="modal-field" data-id="${i}" placeholder="Direccion" value="${text(d.referencia)}"
                        ${Ctrl.ifView("readonly")}>
                    </div>
                    <div class="modal-field-error">${text(d.error)}</div>
                </td>
                <td>${Ctrl.ifNotView(`<button type="button" class="direccionDel btn-delete-row" data-id="${i}">Eliminar</button>`)}</td>
            </tr>
        `;
    })
    $direcciones.querySelectorAll("input").forEach($ => {
        $.addEventListener("input", e => {
            const lastId = direcciones.length - 1;
            const id = Number($.dataset.id);
            direcciones[Number($.dataset.id)] = {
                referencia: e.target.value,
                error: null
            }

            if (id === lastId)
                renderDireccionView();
        });
    });
    $direcciones.querySelectorAll(".direccionDel").forEach($ => {
        $.addEventListener("click", () => {
            const lastId = direcciones.length - 1;
            const id = Number($.dataset.id);
            direcciones.splice(id, 1);
            renderDirecciones();

            if (id === lastId)
                renderDireccionView();
        });
    });
}

Expr.With(document.getElementById("direccionAdd"), btn => {
    btn.addEventListener("click", () => {
        direcciones.push({
            referencia: ""
        });
        renderDirecciones();
        renderDireccionView();
    });
})

function syncDireccionFields() {
    $direccionFields.innerHTML = "";
    direcciones.forEach((d, i) => {
        $direccionFields.innerHTML += `
            <input type="hidden" name="direcciones[${i}].referencia" value="${text(d.referencia)}">
        `;
    });
}

//DOCUMENT
document.addEventListener("DOMContentLoaded", () => {
    init();

    renderTelefonos();
    renderDirecciones();

    $form.addEventListener("submit", () => {
        syncTelefonoFields();
        syncDireccionFields();
    })
});

$telefonoModal.addEventListener("click", e => {
    if (e.target === $telefonoModal)
        $telefonoModal.classList.remove("open");
});

$direccionModal.addEventListener("click", e => {
    if (e.target === $direccionModal)
        $direccionModal.classList.remove("open");
});
