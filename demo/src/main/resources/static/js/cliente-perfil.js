document.addEventListener("DOMContentLoaded", function () {
    const fotoPerfil = document.getElementById("fotoPerfil");
    const previewFoto = document.getElementById("previewFoto");
    const nombreInput = document.getElementById("nombre");
    const cedulaInput = document.getElementById("cedula");
    const emailInput = document.getElementById("email");
    const telefonoInput = document.getElementById("telefono");
    const passwordInput = document.getElementById("password");
    const btnGuardar = document.getElementById("guardarPerfil");

    let usuario = JSON.parse(localStorage.getItem("currentUser"));

    if (!usuario) {
        alert("No hay usuario activo. Inicia sesión.");
        window.location.href = "../../index.html";
    } else {
        cargarDatos();
    }

    /** 🔹 Carga los datos del usuario */
    function cargarDatos() {
        nombreInput.value = usuario.nombre;
        cedulaInput.value = usuario.cedula;
        emailInput.value = usuario.email || "";
        telefonoInput.value = usuario.telefono || "";
        previewFoto.src = usuario.foto || "../../images/default-user.png";
    }

    /** 🔹 Guarda los cambios del usuario */
    btnGuardar.addEventListener("click", function () {
        usuario.nombre = nombreInput.value;
        usuario.email = emailInput.value;
        usuario.telefono = telefonoInput.value;

        if (passwordInput.value) {
            usuario.password = passwordInput.value;
        }

        localStorage.setItem("currentUser", JSON.stringify(usuario));

        let users = JSON.parse(localStorage.getItem("users")) || [];
        const index = users.findIndex(u => u.cedula === usuario.cedula);
        if (index !== -1) {
            users[index] = usuario;
            localStorage.setItem("users", JSON.stringify(users));
        }

        alert("✅ Perfil actualizado correctamente.");
        cargarDatos();
    });

    /** 🔹 Permite subir una imagen de perfil */
    fotoPerfil.addEventListener("change", function (event) {
        const file = event.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
                usuario.foto = e.target.result;
                previewFoto.src = usuario.foto;
                localStorage.setItem("currentUser", JSON.stringify(usuario));

                let users = JSON.parse(localStorage.getItem("users")) || [];
                const index = users.findIndex(u => u.cedula === usuario.cedula);
                if (index !== -1) {
                    users[index].foto = usuario.foto;
                    localStorage.setItem("users", JSON.stringify(users));
                }
            };
            reader.readAsDataURL(file);
        }
    });
});
