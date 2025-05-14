// Cargar usuario autenticado
fetch("http://localhost:8080/auth/usuario", {
    method: "GET",
    credentials: "include"
})
.then(response => {
    if (!response.ok) {
        // No autenticado → redirigir al login
        window.location.href = "login.html";
        return null;
    }
    return response.text();
})
.then(nombre => {
    if (nombre) {
        document.getElementById("usuarioNombre").textContent = `Hola, ${nombre}`;
    }
});

// Logout
const logoutBtn = document.getElementById("logoutBtn");
logoutBtn.addEventListener("click", () => {
    sessionStorage.removeItem("nombreUsuario");

    fetch("http://localhost:8080/auth/logout", {
        method: "POST",
        credentials: "include"
    }).finally(() => {
        window.location.href = "login.html";
    });
});

// Cargar gastos al cargar la página
window.addEventListener("DOMContentLoaded", () => {
    cargarGastos();
});

function mostrarMensajeGasto(texto, tipo = "exito") {
    const mensajeDiv = document.getElementById("mensajeGasto");
    if (!mensajeDiv) return;
    mensajeDiv.textContent = texto;
    mensajeDiv.style.color = tipo === "exito" ? "green" : "red";

    setTimeout(() => {
        mensajeDiv.textContent = "";
    }, 3000);
}

function cargarGastos() {
    fetch("http://localhost:8080/api/gastos", {
        credentials: "include"
    })
    .then(response => {
        if (!response.ok) throw new Error("Error al cargar gastos");
        return response.json();
    })
    .then(gastos => {
        const container = document.getElementById("gastosContainer");
        if (!container) return;

        if (gastos.length === 0) {
            container.innerHTML = "<p>No tienes gastos registrados.</p>";
            return;
        }

        const tabla = document.createElement("table");
        tabla.style.width = "100%";
        tabla.style.borderCollapse = "collapse";
        tabla.innerHTML = `
            <thead>
                <tr style="background-color: #f8bbd0; color: #880e4f;">
                    <th style="padding: 10px; border: 1px solid #ddd;">Categoría</th>
                    <th style="padding: 10px; border: 1px solid #ddd;">Descripción</th>
                    <th style="padding: 10px; border: 1px solid #ddd;">Monto</th>
                    <th style="padding: 10px; border: 1px solid #ddd;">Fecha</th>
                    <th style="padding: 10px; border: 1px solid #ddd;">Acciones</th>
                </tr>
            </thead>
            <tbody>
                ${gastos.map(gasto => `
                    <tr>
                        <td style="padding: 10px; border: 1px solid #ddd;">${gasto.categoria}</td>
                        <td style="padding: 10px; border: 1px solid #ddd;">${gasto.descripcion}</td>
                        <td style="padding: 10px; border: 1px solid #ddd;">$${gasto.monto.toFixed(2)}</td>
                        <td style="padding: 10px; border: 1px solid #ddd;">${gasto.fecha.split('T')[0]}</td>
                        <td style="padding: 10px; border: 1px solid #ddd; text-align: center;">
                            <button data-id="${gasto.id}" onclick="editarGasto(${gasto.id})">✏️</button>
                        </td>
                    </tr>
                `).join("")}
            </tbody>
        `;

        container.innerHTML = "<div id='mensajeGasto' style='margin-bottom: 10px; font-weight: bold;'></div>";
        container.appendChild(tabla);
    })
    .catch(error => {
        console.error("Error:", error);
        const container = document.getElementById("gastosContainer");
        if (container) container.innerText = "No se pudieron cargar los gastos.";
    });
}

function editarGasto(id) {
    const fila = document.querySelector(`button[data-id="${id}"]`).closest("tr");

    // Obtener valores actuales
    const categoria = fila.children[0].textContent;
    const monto = parseFloat(fila.children[1].textContent.replace("$", ""));
    const descripcion = fila.children[2].textContent;
    const fecha = fila.children[3].textContent;

    // Reemplazar celdas por inputs
    fila.innerHTML = `
        <td><input type="text" value="${categoria}" /></td>
    <td><input type="text" value="${descripcion}" /></td>
    <td><input type="number" step="0.01" value="${monto}" /></td>
    <td><input type="date" value="${formatearFechaInput(fecha)}" /></td>
    <td>
        <button class="guardar-btn">💾</button>
        <button class="cancelar-btn">❌</button>
    </td>
    `;

    // Botón guardar
    fila.querySelector(".guardar-btn").addEventListener("click", () => {
        const nuevosDatos = {
            categoria: fila.children[0].querySelector("input").value,
            descripcion: fila.children[1].querySelector("input").value,
            monto: parseFloat(fila.children[2].querySelector("input").value),
            fecha: fila.children[3].querySelector("input").value
        };

        fetch(`http://localhost:8080/api/gastos/${id}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            credentials: "include",
            body: JSON.stringify(nuevosDatos)
        })
        .then(response => {
            if (!response.ok) throw new Error("Error al actualizar gasto");
            mostrarToast("Gasto actualizado correctamente.", "success");
            cargarGastos(); // Recargar tabla
        })
        .catch(error => {
            console.error("Error:", error);
            mostrarToast("Error al actualizar gasto", "error");
        });
    });

    // Botón cancelar
    fila.querySelector(".cancelar-btn").addEventListener("click", () => {
        cargarGastos(); // Recarga original
    });
}


function formatearFechaInput(fechaStr) {
    return fechaStr;
}
function eliminarGasto(id) {
    if (!confirm("¿Estás seguro de que deseas eliminar este gasto?")) return;

    fetch(`http://localhost:8080/api/gastos/${id}`, {
        method: "DELETE",
        credentials: "include"
    })
    .then(response => {
        if (!response.ok) throw new Error("Error al eliminar gasto");
        mostrarToast("Gasto eliminado correctamente.", "success");
        cargarGastos(); // Recargar tabla actualizada
    })
    .catch(error => {
        console.error("Error:", error);
        mostrarToast("No se pudo eliminar el gasto", "error");
    });
}

function mostrarToast(message, tipo = 'success') {
    const toast = document.getElementById('toastMessage');
    
    // Cambiar el color del fondo según el tipo de mensaje
    if (tipo === 'success') {
        toast.style.backgroundColor = '#4CAF50'; // Verde para éxito
    } else if (tipo === 'error') {
        toast.style.backgroundColor = '#f44336'; // Rojo para error
    }

    toast.innerHTML = message;
    toast.style.display = 'block';

    // Ocultar el toast después de 4 segundos
    setTimeout(() => {
        toast.style.display = 'none';
    }, 4000);
}

function ocultarToast() {
    const toast = document.getElementById('toastMessage');
    toast.style.display = 'none';
}
