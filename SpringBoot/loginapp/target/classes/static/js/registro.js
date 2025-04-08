document.getElementById("registroForm").addEventListener("submit", function(event) {
    event.preventDefault();

    const nombre = document.getElementById("nombre").value;
    const correo = document.getElementById("correo").value;
    const contrasena = document.getElementById("contrasena").value;

    fetch("http://localhost:8080/auth/registro", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ nombre, correo, contrasena })
    })
    .then(response => response.text())
    .then(data => {
        document.getElementById("mensaje").textContent = data;
    })
    .catch(error => {
        document.getElementById("mensaje").textContent = "Error al registrar";
        console.error("Error:", error);
    });
});
