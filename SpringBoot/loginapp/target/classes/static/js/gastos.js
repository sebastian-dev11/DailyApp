document.getElementById("formGasto").addEventListener("submit", function(event) {
    event.preventDefault();

    const categoria = document.getElementById("categoria").value;
    const monto = document.getElementById("monto").value;
    const descripcion = document.getElementById("descripcion").value;
    const fecha = document.getElementById("fecha").value;

    fetch("http://localhost:8080/api/gastos", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Accept": "application/json"
        },
        credentials: "include", // para enviar la cookie de sesión
        body: JSON.stringify({
            categoria: categoria,
            monto: parseFloat(monto),
            descripcion: descripcion,
            fecha: fecha
        })
    })
    .then(response => {
        if (!response.ok) throw new Error("Error al guardar gasto");
        return response.text();
    })
    .then(data => {
        document.getElementById("mensaje").innerText = data;
    })
    .catch(error => {
        console.error("Error:", error);
        document.getElementById("mensaje").innerText = "Hubo un error al registrar el gasto";
    });
});