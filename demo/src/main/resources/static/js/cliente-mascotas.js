document.addEventListener("DOMContentLoaded", function () {
    const formMascota = document.getElementById("formMascota");
    const mascotasContainer = document.getElementById("mascotas-container");
    const formularioMascota = document.getElementById("formularioMascota");
    const btnAgregarMascota = document.getElementById("btnAgregarMascota");

    // Obtener cédula del cliente desde localStorage o sessionStorage
    const cedula = localStorage.getItem("cedulaCliente") || sessionStorage.getItem("cedulaCliente");

    if (!cedula) {
        console.error("No se encontró la cédula del cliente.");
    } else {
        iniciarSesion(cedula);
        cargarMascotas();
    }

    // Mostrar formulario al hacer clic en "Registrar Nueva Mascota"
    btnAgregarMascota.addEventListener("click", () => {
        formularioMascota.style.display = "block";
    });

    function iniciarSesion(clienteCedula) {
        localStorage.setItem("cedulaCliente", clienteCedula);
        console.log("Cédula guardada en localStorage:", clienteCedula);
    }

    async function cargarMascotas() {
        try {
            if (!cedula) return;

            const response = await fetch(`/cliente/obtener-mascotas/${cedula}`);
            if (!response.ok) throw new Error(`Error al obtener mascotas: ${response.status}`);

            const mascotas = await response.json();
            renderizarMascotas(mascotas);
        } catch (error) {
            console.error("Error al obtener mascotas", error);
        }
    }

    function renderizarMascotas(mascotas) {
        const tbody = document.getElementById("mascotas-container");
        tbody.innerHTML = ""; // Limpiar contenido previo

        if (mascotas.length === 0) {
            tbody.innerHTML = `<tr><td colspan="4">No hay mascotas registradas.</td></tr>`;
            return;
        }

        mascotas.forEach((mascota) => {
            const fila = document.createElement("tr");
            fila.innerHTML = `
                <td>${mascota.nombre}</td>
                <td>${mascota.especie}</td>
                <td>${mascota.raza}</td>
                <td>${mascota.edad} años</td>
                <td>${mascota.peso} kg</td>
                <td>${mascota.enfermedad}</td>
                <td>${mascota.foto}</td>
                <td>${mascota.cliente.nombre}</td>
            `;
            tbody.appendChild(fila);
        });
    }

    formMascota.addEventListener("submit", async function (event) {
        event.preventDefault();

        const nombreInput = document.getElementById("nombre");
        const especieInput = document.getElementById("especie");
        const razaInput = document.getElementById("raza");
        const edadInput = document.getElementById("edad");

        if (!nombreInput.value || !especieInput.value || !razaInput.value || !edadInput.value) {
            alert("Todos los campos son obligatorios.");
            return;
        }

        try {
            if (!cedula) {
                console.error("No se encontró la cédula del cliente.");
                return;
            }

            const response = await fetch("http://localhost:8090/mascota/guardar", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    nombre: nombreInput.value,
                    especie: especieInput.value,
                    raza: razaInput.value,
                    edad: parseInt(edadInput.value),
                    cliente: { cedula: cedula }  // Ahora se envía correctamente
                })
            });

            if (!response.ok) {
                throw new Error("Error al agregar mascota");
            }

            alert("Mascota agregada correctamente.");
            formMascota.reset();
            cargarMascotas();
        } catch (error) {
            console.error("Error al agregar mascota", error);
        }
    });
});
