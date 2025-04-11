window.addEventListener("DOMContentLoaded", () => {
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
        // Limpia sessionStorage (por si acaso)
        sessionStorage.removeItem("nombreUsuario");

        // Llama al backend para cerrar sesión
        fetch("http://localhost:8080/auth/logout", {
            method: "POST",
            credentials: "include"
        }).finally(() => {
            window.location.href = "login.html";
        });
    });
});
