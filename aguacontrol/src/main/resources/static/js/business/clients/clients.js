import * as Ctrl from '/js/business/main.js';

const $input = document.getElementById("browse");
const $content = document.getElementById("content");

$input.addEventListener('input', e => browse(e.target.value));

function browse(keyword = "") {
    const params = new URLSearchParams({keyword: keyword}).toString();
    fetch(`/business/clients/browse?${params}`)
        .then(r => r.json())
        .then(r => {
            $content.innerHTML = "";
            r.data.forEach(i => $content.innerHTML += `
                <tr>
                    <td>${i.codigo}</td>
                    <td>${i.nombre}</td>
                    <td>${i.telefonos[i.telefonos.length - 1]?.numero ?? ""}</td>
                    <td>${i.direcciones[i.direcciones.length - 1]?.referencia ?? ""}</td>
                    <td>
                        <a href="/business/clients/form/view/${i.id}">Ver</a>
                        <a href="/business/clients/form/update/${i.id}">Editar</a>
                        <a class="delete-btn" data-id="${i.id}">Eliminar</a>
                    </td>
                </tr>
            `);
        })
        .catch(e => console.error("Error al listar clientes:", e))
}

$content.addEventListener('click', e => {
    if (e.target.classList.contains('delete-btn')) {
        const id = e.target.getAttribute('data-id');
        del(id);
    }
});
function del(id) {
    if (confirm("¿Desea eliminar este cliente?")) {
        Ctrl.fetchDelete("/business/clients/delete/" + id)
            .then(() => browse(""))
            .catch(e => console.error("Error al eliminar cliente:", e));
    }
}

document.addEventListener("DOMContentLoaded", () => browse());