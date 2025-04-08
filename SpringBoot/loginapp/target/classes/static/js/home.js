document.addEventListener("DOMContentLoaded", function () {
    const usuarioAutenticado = localStorage.getItem("usuario");

    if (usuarioAutenticado) {
        window.location.href = "dashboard.html"; // Redirige si el usuario ya está logueado
    }

    document.getElementById("btnLogin").addEventListener("click", function () {
        window.location.href = "http://localhost:8080/login.html";
    });

    document.getElementById("btnRegistro").addEventListener("click", function () {
        window.location.href = "http://localhost:8080/registro.html";
    });
});