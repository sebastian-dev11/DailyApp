document.addEventListener("DOMContentLoaded", cargarIngresos);

document.getElementById("formIngreso").addEventListener("submit", function(e) {
    e.preventDefault();

    const ingreso = {
        descripcion: document.getElementById("descripcion").value,
        monto: parseFloat(document.getElementById("monto").value),
        fecha: document.getElementById("fecha").value
    };

    fetch("/api/ingresos", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(ingreso)
    })
    .then(response => {
        if (response.ok) {
            alert("Ingreso guardado");
            cargarIngresos();
            e.target.reset();
        } else {
            alert("Error al guardar ingreso");
        }
    });
});

function cargarIngresos() {
    fetch("/api/ingresos")
        .then(response => response.json())
        .then(data => {
            const tbody = document.querySelector("#tablaIngresos tbody");
            tbody.innerHTML = "";

            data.forEach(ingreso => {
                const fila = document.createElement("tr");
                fila.innerHTML = `
                    <td>${ingreso.descripcion}</td>
                    <td>$${ingreso.monto.toFixed(2)}</td>
                    <td>${ingreso.fecha}</td>
                    <td><button onclick="eliminarIngreso(${ingreso.id})">Eliminar</button></td>
                `;
                tbody.appendChild(fila);
            });
        });
}

function eliminarIngreso(id) {
    fetch(`/api/ingresos/${id}`, {
        method: "DELETE"
    })
    .then(() => cargarIngresos());
}
