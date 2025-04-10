document.addEventListener("DOMContentLoaded", function () {
    const recetasContainer = document.getElementById("recetas-container");

    function cargarRecetas() {
        const currentUser = JSON.parse(localStorage.getItem("currentUser"));
        if (!currentUser) return;

        const recetas = JSON.parse(localStorage.getItem("recetas")) || [];

        // Filtrar solo las recetas del cliente autenticado
        const recetasUsuario = recetas.filter(receta => receta.paciente === currentUser.cedula);

        recetasContainer.innerHTML = "";

        if (recetasUsuario.length === 0) {
            recetasContainer.innerHTML = "<p>No tienes recetas asignadas.</p>";
            return;
        }

        recetasUsuario.forEach(receta => {
            const recetaCard = document.createElement("div");
            recetaCard.classList.add("receta-card");

            recetaCard.innerHTML = `
                <h3>Receta Médica</h3>
                <p><strong>Fecha:</strong> ${receta.fecha}</p>
                <p><strong>Veterinario:</strong> ${receta.veterinarioNombre}</p>
                <p><strong>Medicamento:</strong> ${receta.medicamento}</p>
                <p><strong>Instrucciones:</strong> ${receta.indicaciones}</p>
                <a href="cliente-tienda.html" class="btn-comprar">Comprar Medicamentos</a>
            `;

            recetasContainer.appendChild(recetaCard);
        });
    }

    cargarRecetas();
});
