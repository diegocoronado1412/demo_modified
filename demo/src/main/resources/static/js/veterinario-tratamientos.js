document.addEventListener("DOMContentLoaded", function () {
    const tratamientosContainer = document.getElementById("tratamientos-container");

    const currentUser = JSON.parse(localStorage.getItem("currentUser"));

    if (!currentUser || currentUser.role !== "veterinario") {
        alert("Acceso denegado.");
        window.location.href = "../../index.html";
        return;
    }

    let tratamientos = JSON.parse(localStorage.getItem("tratamientosPendientes")) || [];

    /** 🔹 Renderizar tratamientos */
    function renderizarTratamientos() {
        tratamientosContainer.innerHTML = "";

        if (tratamientos.length === 0) {
            tratamientosContainer.innerHTML = "<p>No hay tratamientos pendientes de aprobación.</p>";
            return;
        }

        tratamientos.forEach((tratamiento, index) => {
            const div = document.createElement("div");
            div.classList.add("tratamiento-card");
            div.innerHTML = `
                <h3>${tratamiento.nombre}</h3>
                <p><strong>Paciente:</strong> ${tratamiento.paciente}</p>
                <p><strong>Dueño:</strong> ${tratamiento.dueño}</p>
                <p><strong>Descripción:</strong> ${tratamiento.descripcion}</p>
                <p><strong>Precio:</strong> $${tratamiento.precio}</p>
                <button class="btn" onclick="aprobarTratamiento(${index})">✅ Aprobar</button>
                <button class="btn btn-delete" onclick="rechazarTratamiento(${index})">❌ Rechazar</button>
            `;
            tratamientosContainer.appendChild(div);
        });
    }

    /** 🔹 Aprobar tratamiento */
    window.aprobarTratamiento = function (index) {
        const tratamientoAprobado = tratamientos.splice(index, 1)[0];
        let tratamientosAprobados = JSON.parse(localStorage.getItem("tratamientosAprobados")) || [];

        tratamientosAprobados.push(tratamientoAprobado);
        localStorage.setItem("tratamientosAprobados", JSON.stringify(tratamientosAprobados));
        localStorage.setItem("tratamientosPendientes", JSON.stringify(tratamientos));

        alert("✅ Tratamiento aprobado correctamente.");
        renderizarTratamientos();
    };

    /** 🔹 Rechazar tratamiento */
    window.rechazarTratamiento = function (index) {
        if (!confirm("¿Seguro que quieres rechazar este tratamiento?")) return;

        tratamientos.splice(index, 1);
        localStorage.setItem("tratamientosPendientes", JSON.stringify(tratamientos));

        alert("❌ Tratamiento rechazado.");
        renderizarTratamientos();
    };

    renderizarTratamientos();
});
