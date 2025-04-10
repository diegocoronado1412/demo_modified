document.addEventListener("DOMContentLoaded", function () {
    const tratamientosContainer = document.getElementById("tratamientos-container");
    let tratamientos = JSON.parse(localStorage.getItem("tratamientos")) || [];
    let carrito = JSON.parse(localStorage.getItem("carrito")) || [];

    /** 🔹 Muestra los tratamientos disponibles */
    function renderizarTratamientos() {
        tratamientosContainer.innerHTML = "";
        if (tratamientos.length === 0) {
            tratamientosContainer.innerHTML = "<p>No hay tratamientos disponibles.</p>";
            return;
        }

        tratamientos.forEach((tratamiento, index) => {
            const div = document.createElement("div");
            div.classList.add("tratamiento-card");
            div.innerHTML = `
                <img src="${tratamiento.imagen}" alt="${tratamiento.nombre}">
                <h3>${tratamiento.nombre}</h3>
                <p><strong>Descripción:</strong> ${tratamiento.descripcion}</p>
                <p><strong>Precio:</strong> $${tratamiento.precio}</p>
                <button class="btn" onclick="agregarAlCarrito(${index})">Agregar al Carrito</button>
            `;
            tratamientosContainer.appendChild(div);
        });
    }

    /** 🔹 Agrega tratamientos al carrito */
    window.agregarAlCarrito = function (index) {
        const tratamientoSeleccionado = tratamientos[index];

        // Verificar si ya está en el carrito
        const tratamientoEnCarrito = carrito.find(t => t.nombre === tratamientoSeleccionado.nombre);

        if (tratamientoEnCarrito) {
            tratamientoEnCarrito.cantidad++;
        } else {
            carrito.push({ ...tratamientoSeleccionado, cantidad: 1 });
        }

        localStorage.setItem("carrito", JSON.stringify(carrito));
        alert("✅ Tratamiento agregado al carrito.");
    };

    renderizarTratamientos();
});
