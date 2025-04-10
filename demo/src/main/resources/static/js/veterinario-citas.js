document.addEventListener("DOMContentLoaded", function () {
    const citasContainer = document.getElementById("citas-container");

    const currentUser = JSON.parse(localStorage.getItem("currentUser"));
    if (!currentUser || currentUser.role !== "veterinario") {
        alert("Acceso denegado.");
        window.location.href = "../../index.html";
        return;
    }

    function cargarCitas() {
        citasContainer.innerHTML = "";
        const citas = JSON.parse(localStorage.getItem("citas")) || [];
        const citasVeterinario = citas.filter(cita => cita.veterinario === currentUser.cedula);

        if (citasVeterinario.length === 0) {
            citasContainer.innerHTML = "<p>No tienes citas asignadas.</p>";
            return;
        }

        citasVeterinario.forEach((cita, index) => {
            const cliente = JSON.parse(localStorage.getItem("users")).find(user => user.cedula === cita.usuario);
            const fotoCliente = localStorage.getItem(`foto_${cliente.cedula}`) || "../../images/default-user.png";

            const div = document.createElement("div");
            div.classList.add("cita-card");
            div.innerHTML = `
                <img src="${fotoCliente}" alt="Foto de ${cliente.nombre}" class="cita-foto">
                <div class="cita-info">
                    <h3>${cliente.nombre}</h3>
                    <p><strong>Fecha:</strong> ${cita.fecha}</p>
                    <p><strong>Hora:</strong> ${cita.hora}</p>
                    <p><strong>Estado:</strong> <span class="estado estado-${cita.estado}">${cita.estado}</span></p>
                </div>
                <div class="acciones">
                    <button class="btn completar" onclick="actualizarEstado(${index}, 'completada')">✅ Completada</button>
                    <button class="btn cancelar" onclick="actualizarEstado(${index}, 'cancelada')">❌ Cancelada</button>
                    <button class="btn no-asistio" onclick="actualizarEstado(${index}, 'no-asistio')">🚫 No Asistió</button>
                </div>
            `;
            citasContainer.appendChild(div);
        });
    }

    window.actualizarEstado = function (index, nuevoEstado) {
        let citas = JSON.parse(localStorage.getItem("citas"));
        citas[index].estado = nuevoEstado;
        localStorage.setItem("citas", JSON.stringify(citas));
        cargarCitas();
        alert("✅ Estado de la cita actualizado.");
    };

    cargarCitas();
});
