document.addEventListener("DOMContentLoaded", function () {
    const formPerfil = document.getElementById("veterinarioPerfilForm");
    const fechaDisponible = document.getElementById("fechaDisponible");
    const horasDisponibles = document.getElementById("horasDisponibles");
    const btnGuardarDisponibilidad = document.getElementById("guardarDisponibilidad");
    const fotoInput = document.getElementById("fotoUpload");
    const fotoPreview = document.getElementById("veterinarioFoto");

    // Obtener usuario autenticado
    const currentUser = JSON.parse(localStorage.getItem("currentUser"));
    if (!currentUser || currentUser.role !== "veterinario") {
        alert("Acceso denegado.");
        window.location.href = "../../index.html";
        return;
    }

    // Cargar datos del veterinario en el formulario
    function cargarPerfil() {
        document.getElementById("vetNombre").value = currentUser.nombre || "";
        document.getElementById("vetEmail").value = currentUser.email || "";
        document.getElementById("vetTelefono").value = currentUser.telefono || "";
        
        // Cargar foto de perfil si existe
        const fotoGuardada = localStorage.getItem(`foto_${currentUser.cedula}`);
        fotoPreview.src = fotoGuardada ? fotoGuardada : "../../images/default-vet.png";
    }

    // Guardar nueva foto de perfil
    fotoInput.addEventListener("change", function (event) {
        const reader = new FileReader();
        reader.onload = function () {
            const fotoBase64 = reader.result;
            fotoPreview.src = fotoBase64;

            // Guardar la imagen en localStorage
            localStorage.setItem(`foto_${currentUser.cedula}`, fotoBase64);
            alert("✅ Foto de perfil actualizada.");
        };
        reader.readAsDataURL(event.target.files[0]);
    });

    // Generar checkboxes de horas
    function generarHoras() {
        horasDisponibles.innerHTML = "";
        const horas = [
            "07:00 AM", "08:00 AM", "09:00 AM", "10:00 AM", "11:00 AM",
            "12:00 PM", "01:00 PM", "02:00 PM", "03:00 PM", "04:00 PM", 
            "05:00 PM", "06:00 PM", "07:00 PM", "08:00 PM"
        ];

        horas.forEach(hora => {
            const label = document.createElement("label");
            label.classList.add("hora-checkbox");
            label.innerHTML = `<input type="checkbox" value="${hora}"> ${hora}`;
            horasDisponibles.appendChild(label);
        });
    }

    // Cargar disponibilidad según la fecha seleccionada
    function cargarDisponibilidad() {
        if (!fechaDisponible.value) return;
        
        const disponibilidadKey = `disponibilidad_${currentUser.cedula}_${fechaDisponible.value}`;
        const horasGuardadas = JSON.parse(localStorage.getItem(disponibilidadKey)) || [];

        // Marcar checkboxes según disponibilidad guardada
        document.querySelectorAll("#horasDisponibles input").forEach(input => {
            input.checked = horasGuardadas.includes(input.value);
        });
    }

    // Guardar disponibilidad del veterinario
    btnGuardarDisponibilidad.addEventListener("click", function () {
        if (!fechaDisponible.value) {
            alert("Selecciona una fecha primero.");
            return;
        }

        const horasSeleccionadas = [...document.querySelectorAll("#horasDisponibles input:checked")]
            .map(input => input.value);

        if (horasSeleccionadas.length === 0) {
            alert("Selecciona al menos una hora disponible.");
            return;
        }

        // Guardar en localStorage con la clave basada en la cédula y fecha
        const disponibilidadKey = `disponibilidad_${currentUser.cedula}_${fechaDisponible.value}`;
        localStorage.setItem(disponibilidadKey, JSON.stringify(horasSeleccionadas));

        alert("✅ Disponibilidad guardada correctamente.");
    });

    // Actualizar las horas disponibles cuando el veterinario cambia la fecha
    fechaDisponible.addEventListener("change", cargarDisponibilidad);

    cargarPerfil();
    generarHoras();
});
