document.getElementById("loginForm").addEventListener("submit", function(event) {
    event.preventDefault();

    const correo = document.getElementById("correo").value;
    const contrasena = document.getElementById("contrasena").value;

    fetch("http://localhost:8080/auth/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Accept": "application/json"
        },
        credentials: "include", // ✅ Para mantener la sesión
        body: JSON.stringify({ correo, contrasena })
    })
    .then(response => response.text())
    .then(data => {
        document.getElementById("mensaje").innerText = data;

        if (data.trim().toLowerCase() === "inicio de sesión exitoso") {
            // ✅ Si el login es exitoso, obtener el nombre del usuario desde el backend
            fetch("http://localhost:8080/auth/usuario", {
                method: "GET",
                credentials: "include"
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error("No se pudo obtener el usuario");
                }
                return response.text();
            })
            .then(nombre => {
                // ✅ Guardamos el nombre en sessionStorage
                sessionStorage.setItem("nombreUsuario", nombre);
                alert("Bienvenido " + nombre);
                window.location.href = "dashboard.html";
            })
            .catch(error => {
                console.error("Error al obtener usuario:", error);
                window.location.href = "dashboard.html"; // Igual redirige
            });
        }
    })
    .catch(error => {
        console.error("Error:", error);
        document.getElementById("mensaje").innerText = "Error al iniciar sesión";
    });
});

