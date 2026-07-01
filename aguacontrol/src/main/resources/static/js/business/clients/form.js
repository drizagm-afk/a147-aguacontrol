import * as Expr from '/js/utils/expr.js';
import * as Ctrl from '/js/business/main.js';

//INIT
let telefonos = [];
let direcciones = [];

const $telefonoInit = document.getElementById("telefonoInit");
const $direccionInit = document.getElementById("direccionInit");

function init() {
    $telefonoInit.querySelectorAll("*").forEach(elem => {
        telefonos.push({
            numero: elem.dataset.value,
            error: elem.dataset.error
        });
    });
    $direccionInit.querySelectorAll("*").forEach(elem => {
        direcciones.push({
            referencia: elem.dataset.value,
            error: elem.dataset.error
        });
    });

    renderTelefonoView();
    renderDireccionView();
}

//>>>> RENDER
const $form = document.getElementById("form");

//TELEFONOS RENDER
const $telefonoView = document.getElementById("telefonoView");

function renderTelefonoView() {
    $telefonoView.value = telefonos[telefonos.length - 1]?.numero ?? "";
}

document.getElementById("telefonoOpen").addEventListener("click", () => {

});
document.getElementById("telefonoClose").addEventListener("click", () => {

});

const $telefonos = document.getElementById("telefonos");
const $telefonoFields = document.getElementById("telefonoFields");

function renderTelefonos() {
    $telefonos.innerHTML = "";
    telefonos.forEach((t, i) => {
        $telefonos.innerHTML += `
            <tr>
                <td>
                    <input data-id="${i}" placeholder="Numero" value="${t.numero}"
                        ${Ctrl.ifView("readonly")}>
                    <div>${t.error ?? ""}</div>
                </td>
                <td>${Ctrl.ifNotView(`<button class="telefonoDel" data-id="${i}">Eliminar</button>`)}</td>
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
            <input type="hidden" name="telefonos[${i}].numero" value="${t.numero}">
        `;
    });
}

//DIRECCIONES RENDER
const $direccionView = document.getElementById("direccionView");

function renderDireccionView() {
    $direccionView.value = direcciones[direcciones.length - 1]?.referencia ?? "";
}

document.getElementById("direccionOpen").addEventListener("click", () => {

});
document.getElementById("direccionClose").addEventListener("click", () => {

});

const $direcciones = document.getElementById("direcciones");
const $direccionFields = document.getElementById("direccionFields");

function renderDirecciones() {
    $direcciones.innerHTML = "";
    direcciones.forEach((d, i) => {
        $direcciones.innerHTML += `
            <tr>
                <td>
                    <input data-id="${i}" placeholder="Numero" value="${d.referencia}"
                        ${Ctrl.ifView("readonly")}>
                    <div>${d.error ?? ""}</div>
                </td>
                <td>${Ctrl.ifNotView(`<button class="direccionDel" data-id="${i}">Eliminar</button>`)}</td>
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
            <input type="hidden" name="direcciones[${i}].referencia" value="${d.referencia}">
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