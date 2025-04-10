document.addEventListener("DOMContentLoaded", function () {
    const formCita = document.getElementById("formCita");
    const veterinarioSelect = document.getElementById("veterinario");
    const fechaInput = document.getElementById("fecha");
    const horaSelect = document.getElementById("hora");
    const citasContainer = document.getElementById("citas-container");
    const horarioDisponible = document.getElementById("horarioDisponible");

    const formReagendar = document.getElementById("formReagendar");
    const reagendarFecha = document.getElementById("reagendarFecha");
    const reagendarHora = document.getElementById("reagendarHora");
    const reagendarSection = document.getElementById("reagendar-section");

    let citaReagendarIndex = null;

    /** 🔹 Cargar veterinarios con sus horarios disponibles */
    function cargarVeterinarios() {
        const veterinarios = JSON.parse(localStorage.getItem("users")) || [];
        const vetsDisponibles = veterinarios.filter(user => user.role === "veterinario");

        veterinarioSelect.innerHTML = "<option value=''>Seleccione un veterinario</option>";
        vetsDisponibles.forEach(vet => {
            const option = document.createElement("option");
            option.value = vet.cedula;
            option.textContent = `${vet.nombre} - ${vet.email}`;
            veterinarioSelect.appendChild(option);
        });
    }

    /** 🔹 Cargar horarios del veterinario seleccionado */
    function cargarHorarios() {
        const veterinarioId = veterinarioSelect.value;
        const fechaSeleccionada = fechaInput.value;

        if (!veterinarioId || !fechaSeleccionada) {
            horarioDisponible.textContent = "Horario disponible: No definido";
            horaSelect.innerHTML = "<option value=''>Seleccione una fecha primero</option>";
            formCita.querySelector("button[type='submit']").disabled = true;
            return;
        }

        // Buscar la disponibilidad del veterinario para la fecha seleccionada
        const disponibilidadKey = `disponibilidad_${veterinarioId}_${fechaSeleccionada}`;
        const horasDisponibles = JSON.parse(localStorage.getItem(disponibilidadKey)) || [];

        if (horasDisponibles.length > 0) {
            horarioDisponible.textContent = `Horas disponibles: ${horasDisponibles.join(", ")}`;
            horaSelect.innerHTML = horasDisponibles.map(hora => `<option value="${hora}">${hora}</option>`).join("");
            formCita.querySelector("button[type='submit']").disabled = false;
        } else {
            horarioDisponible.textContent = "No hay horarios disponibles en esta fecha.";
            horaSelect.innerHTML = "<option value=''>No disponible</option>";
            formCita.querySelector("button[type='submit']").disabled = true;
        }
    }

    /** 🔹 Cargar citas del usuario */
    function cargarCitas() {
        const usuario = JSON.parse(localStorage.getItem("currentUser"));
        if (!usuario) return;

        const citas = JSON.parse(localStorage.getItem("citas")) || [];
        const citasUsuario = citas.filter(cita => cita.usuario === usuario.cedula);

        citasContainer.innerHTML = citasUsuario.map((cita, index) => `
            <div class="card">
                <h3>${cita.veterinario}</h3>
                <p><strong>Fecha:</strong> ${cita.fecha}</p>
                <p><strong>Hora:</strong> ${cita.hora}</p>
                <p><strong>Estado:</strong> <span class="estado estado-${cita.estado}">${cita.estado}</span></p>
                ${cita.estado === "pendiente" ? `
                    <button class="btn confirmar" onclick="confirmarCita(${index})">Confirmar</button>
                    <button class="btn reagendar" onclick="mostrarReagendar(${index})">Reagendar</button>
                    <button class="btn cancelar" onclick="cancelarCita(${index})">Cancelar</button>
                ` : ""}
            </div>
        `).join("");
    }

    /** 🔹 Mostrar formulario de reagendar */
    function mostrarReagendar(index) {
        citaReagendarIndex = index;
        const citas = JSON.parse(localStorage.getItem("citas"));
        const cita = citas[index];

        reagendarFecha.value = cita.fecha;

        // Buscar los horarios disponibles del veterinario en la fecha seleccionada
        const disponibilidadKey = `disponibilidad_${cita.veterinario}_${cita.fecha}`;
        const horasDisponibles = JSON.parse(localStorage.getItem(disponibilidadKey)) || [];

        reagendarHora.innerHTML = horasDisponibles.length > 0 
            ? horasDisponibles.map(hora => `<option value="${hora}">${hora}</option>`).join("")
            : `<option value="">No disponible</option>`;

        reagendarSection.style.display = "block";
    }

    /** 🔹 Cancelar reagendar */
    function cancelarReagendar() {
        reagendarSection.style.display = "none";
        citaReagendarIndex = null;
    }

    /** 🔹 Reagendar cita */
    formReagendar.addEventListener("submit", function (event) {
        event.preventDefault();
        if (citaReagendarIndex === null) return;

        const citas = JSON.parse(localStorage.getItem("citas"));
        citas[citaReagendarIndex].fecha = reagendarFecha.value;
        citas[citaReagendarIndex].hora = reagendarHora.value;
        localStorage.setItem("citas", JSON.stringify(citas));

        alert("✅ Cita reagendada correctamente.");
        reagendarSection.style.display = "none";
        cargarCitas();
    });

    /** 🔹 Agendar nueva cita */
    formCita.addEventListener("submit", function (event) {
        event.preventDefault();
        
        const usuario = JSON.parse(localStorage.getItem("currentUser"));
        if (!usuario) {
            alert("Debes iniciar sesión.");
            return;
        }

        const nuevaCita = {
            usuario: usuario.cedula,
            veterinario: veterinarioSelect.value,
            fecha: fechaInput.value,
            hora: horaSelect.value,
            estado: "pendiente"
        };

        const citas = JSON.parse(localStorage.getItem("citas")) || [];
        citas.push(nuevaCita);
        localStorage.setItem("citas", JSON.stringify(citas));

        alert("✅ Cita agendada correctamente.");
        formCita.reset();
        cargarCitas();
    });

    /** 🔹 Confirmar una cita */
    window.confirmarCita = function (index) {
        const citas = JSON.parse(localStorage.getItem("citas"));
        citas[index].estado = "confirmada";
        localStorage.setItem("citas", JSON.stringify(citas));
        alert("✅ Cita confirmada.");
        cargarCitas();
    };

    /** 🔹 Cancelar una cita */
    window.cancelarCita = function (index) {
        const citas = JSON.parse(localStorage.getItem("citas"));
        if (confirm("¿Estás seguro de cancelar esta cita?")) {
            citas.splice(index, 1);
            localStorage.setItem("citas", JSON.stringify(citas));
            alert("🚫 Cita cancelada.");
            cargarCitas();
        }
    };

    // Event Listeners para actualizar horarios según veterinario y fecha seleccionados
    veterinarioSelect.addEventListener("change", cargarHorarios);
    fechaInput.addEventListener("change", cargarHorarios);

    cargarVeterinarios();
    cargarCitas();
});
