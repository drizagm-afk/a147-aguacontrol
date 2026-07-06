import * as Ctrl from '/js/business/main.js';

const $input = document.getElementById("browse");
const $content = document.getElementById("content");

$input.addEventListener('input', e => browse(e.target.value));

function text(value) {
    const div = document.createElement("div");
    div.textContent = value ?? "";
    return div.innerHTML;
}

function renderEmptyState() {
    $content.innerHTML = `
        <tr>
            <td class="empty-state-cell" colspan="5">
                <i class="bi bi-search"></i>
                <span class="empty-state-title">Sin resultados</span>
                <span class="empty-state-subtitle">No se encontraron clientes que coincidan con la busqueda.</span>
            </td>
        </tr>
    `;
}

function browse(keyword = "") {
    const params = new URLSearchParams({keyword: keyword}).toString();
    fetch(`/business/clients/browse?${params}`)
        .then(r => r.json())
        .then(r => {
            $content.innerHTML = "";
            if (!r.data || r.data.length === 0) {
                renderEmptyState();
                return;
            }
            r.data.forEach(i => $content.innerHTML += `
                <tr>
                    <td class="code-cell">${text(i.codigo)}</td>
                    <td class="alias-cell">${text(i.nombre)}</td>
                    <td>${text(i.telefono ?? "")}</td>
                    <td>${text(i.direccion ?? "")}</td>
                    <td class="actions-cell">
                        <div class="actions-wrapper">
                            <a class="action-icon-btn" href="/business/clients/form/view/${i.id}" title="Ver detalle">
                                <i class="bi bi-eye"></i>
                            </a>
                            <a class="action-icon-btn" href="/business/clients/form/update/${i.id}" title="Editar">
                                <i class="bi bi-pencil"></i>
                            </a>
                        </div>
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
