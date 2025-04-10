document.addEventListener("DOMContentLoaded", function () {
    const veterinariosContainer = document.getElementById("veterinarios-container");
    const clientesContainer = document.getElementById("clientes-container");

    function cargarUsuarios() {
        const usuarios = JSON.parse(localStorage.getItem("users")) || [];

        veterinariosContainer.innerHTML = "";
        clientesContainer.innerHTML = "";

        usuarios.forEach((usuario, index) => {
            const userCard = document.createElement("div");
            userCard.classList.add("user-card");

            userCard.innerHTML = `
                <img src="${usuario.foto || '../../images/default-user.png'}" alt="Foto de ${usuario.nombre}" class="user-photo">
                <div class="user-info">
                    <label>Nombre:</label>
                    <input type="text" value="${usuario.nombre}" id="nombre-${index}">
                    
                    <label>Correo:</label>
                    <input type="email" value="${usuario.email || ''}" id="correo-${index}">
                    
                    <label>Teléfono:</label>
                    <input type="text" value="${usuario.telefono || ''}" id="telefono-${index}">
                    
                    <label>Usuario (Cédula):</label>
                    <input type="text" value="${usuario.cedula}" id="usuario-${index}" disabled>
                    
                    <label>Contraseña:</label>
                    <input type="password" value="${usuario.password}" id="contrasena-${index}">
                    
                    <button onclick="guardarCambios(${index})" class="btn">Guardar</button>
                </div>
            `;

            if (usuario.role === "veterinario") {
                veterinariosContainer.appendChild(userCard);
            } else if (usuario.role === "cliente") {
                clientesContainer.appendChild(userCard);
            }
        });
    }

    window.guardarCambios = function (index) {
        const usuarios = JSON.parse(localStorage.getItem("users")) || [];

        usuarios[index].nombre = document.getElementById(`nombre-${index}`).value;
        usuarios[index].email = document.getElementById(`correo-${index}`).value;
        usuarios[index].telefono = document.getElementById(`telefono-${index}`).value;
        usuarios[index].password = document.getElementById(`contrasena-${index}`).value;

        localStorage.setItem("users", JSON.stringify(usuarios));
        alert("✅ Usuario actualizado correctamente.");
        cargarUsuarios();
    };

    cargarUsuarios();
});
