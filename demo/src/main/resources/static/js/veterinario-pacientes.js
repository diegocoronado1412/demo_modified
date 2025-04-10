document.addEventListener("DOMContentLoaded", function () {
    const pacientesContainer = document.getElementById("pacientes-container");
    const currentUser = JSON.parse(localStorage.getItem("currentUser"));

    if (!currentUser || currentUser.role !== "veterinario") {
        alert("Acceso denegado.");
        window.location.href = "../../index.html";
        return;
    }

    let pacientes = obtenerPacientesDeClientes();

    /** 🔹 Renderizar pacientes */
    function renderizarPacientes() {
        pacientesContainer.innerHTML = "";

        if (pacientes.length === 0) {
            pacientesContainer.innerHTML = "<p>No hay pacientes registrados.</p>";
            return;
        }

        pacientes.forEach((paciente) => {
            const div = document.createElement("div");
            div.classList.add("paciente-card");
            div.innerHTML = `
                <img src="${paciente.foto || '../../images/logo.png'}" alt="Foto de ${paciente.nombre}" class="paciente-photo">
                <h3>${paciente.nombre}</h3>
                <p><strong>Dueño:</strong> ${paciente.cliente}</p>
                <p><strong>Especie:</strong> ${paciente.especie}</p>
                <p><strong>Raza:</strong> ${paciente.raza || "No especificada"}</p>
                <p><strong>Edad:</strong> ${paciente.edad} años</p>
            `;
            pacientesContainer.appendChild(div);
        });
    }

    /** 🔹 Obtener pacientes de todos los clientes */
    function obtenerPacientesDeClientes() {
        let clientes = JSON.parse(localStorage.getItem("users")) || [];
        let pacientesTotales = [];

        clientes.forEach(cliente => {
            if (cliente.role === "cliente") {
                let mascotas = JSON.parse(localStorage.getItem(`mascotas_${cliente.cedula}`)) || [];
                mascotas.forEach(mascota => {
                    pacientesTotales.push({
                        ...mascota,
                        cliente: cliente.nombre
                    });
                });
            }
        });

        return pacientesTotales;
    }

    renderizarPacientes();
});
