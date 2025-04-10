document.addEventListener("DOMContentLoaded", function () {
    const formReceta = document.getElementById("formReceta");
    const pacienteSelect = document.getElementById("paciente");
    const recetasContainer = document.getElementById("recetas-container");
    const formularioReceta = document.getElementById("formularioReceta");

    const currentUser = JSON.parse(localStorage.getItem("currentUser"));
    if (!currentUser || currentUser.role !== "veterinario") {
        alert("Acceso denegado.");
        window.location.href = "../../index.html";
        return;
    }

    /** ✅ Función para mostrar el formulario de receta */
    window.mostrarFormularioReceta = function () {
        formularioReceta.style.display = formularioReceta.style.display === "none" ? "block" : "none";
    };

    /** 🔹 Cargar pacientes en el select */
    function cargarPacientes() {
        const usuarios = JSON.parse(localStorage.getItem("users")) || [];
        const pacientes = usuarios.filter(user => user.role === "cliente");

        pacienteSelect.innerHTML = "<option value=''>Seleccione un paciente</option>";
        pacientes.forEach(paciente => {
            const option = document.createElement("option");
            option.value = paciente.cedula;
            option.textContent = `${paciente.nombre} - ${paciente.email}`;
            pacienteSelect.appendChild(option);
        });
    }

    /** 🔹 Cargar recetas asignadas por el veterinario */
    function cargarRecetas() {
        recetasContainer.innerHTML = "";
        const recetas = JSON.parse(localStorage.getItem("recetas")) || [];
        const usuarios = JSON.parse(localStorage.getItem("users")) || [];

        // Filtrar recetas asignadas por el veterinario actual
        const recetasVeterinario = recetas.filter(receta => receta.veterinario === currentUser.cedula);

        if (recetasVeterinario.length === 0) {
            recetasContainer.innerHTML = "<p>No has asignado recetas aún.</p>";
            return;
        }

        recetasVeterinario.forEach(receta => {
            const cliente = usuarios.find(user => user.cedula === receta.paciente);
            const fotoCliente = localStorage.getItem(`foto_${cliente?.cedula}`) || "../../images/default-user.png";

            const div = document.createElement("div");
            div.classList.add("receta-card");
            div.innerHTML = `
                <img src="${fotoCliente}" alt="Foto de ${cliente?.nombre || "Paciente"}" class="receta-foto">
                <div class="receta-info">
                    <h3>${cliente?.nombre || "Paciente Desconocido"}</h3>
                    <p><strong>Fecha:</strong> ${receta.fecha}</p>
                    <p><strong>Medicamento:</strong> ${receta.medicamento}</p>
                    <p><strong>Instrucciones:</strong> ${receta.indicaciones}</p>
                </div>
            `;
            recetasContainer.appendChild(div);
        });
    }

    /** 🔹 Guardar nueva receta */
    formReceta.addEventListener("submit", function (event) {
        event.preventDefault();

        const pacienteCedula = pacienteSelect.value;
        const usuarios = JSON.parse(localStorage.getItem("users")) || [];
        const paciente = usuarios.find(p => p.cedula === pacienteCedula);

        if (!paciente) {
            alert("Error: No se encontró el paciente.");
            return;
        }

        const nuevaReceta = {
            paciente: pacienteCedula, // Cédula del paciente
            pacienteNombre: paciente.nombre,
            veterinario: currentUser.cedula,
            veterinarioNombre: currentUser.nombre,
            fecha: new Date().toISOString().split("T")[0], // Fecha actual
            medicamento: document.getElementById("medicamento").value,
            indicaciones: document.getElementById("indicaciones").value
        };

        const recetas = JSON.parse(localStorage.getItem("recetas")) || [];
        recetas.push(nuevaReceta);
        localStorage.setItem("recetas", JSON.stringify(recetas));

        alert("✅ Receta asignada correctamente.");
        formReceta.reset();
        cargarRecetas();
    });

    cargarPacientes();
    cargarRecetas();
});
