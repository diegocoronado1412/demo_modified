document.addEventListener("DOMContentLoaded", function () {
    const carritoContainer = document.getElementById("carrito-items");
    const totalContainer = document.getElementById("total");
    const btnPagar = document.getElementById("btnPagar");

    let carrito = JSON.parse(localStorage.getItem("carrito")) || [];

    /** 🔹 Renderiza los productos en el carrito */
    function renderizarCarrito() {
        carritoContainer.innerHTML = "";
        let total = 0;

        if (carrito.length === 0) {
            carritoContainer.innerHTML = "<p>El carrito está vacío.</p>";
            totalContainer.textContent = "Total: $0";
            return;
        }

        carrito.forEach((producto, index) => {
            total += producto.precio * producto.cantidad;

            const div = document.createElement("div");
            div.classList.add("carrito-item");
            div.innerHTML = `
                <img src="${producto.imagen}" alt="${producto.nombre}">
                <div class="info">
                    <h3>${producto.nombre}</h3>
                    <p>Precio: $${producto.precio}</p>
                    <p>Cantidad: 
                        <button onclick="cambiarCantidad(${index}, -1)">-</button>
                        ${producto.cantidad}
                        <button onclick="cambiarCantidad(${index}, 1)">+</button>
                    </p>
                    <p>Subtotal: $${producto.precio * producto.cantidad}</p>
                    <button class="btn btn-delete" onclick="eliminarDelCarrito(${index})">Eliminar</button>
                </div>
            `;
            carritoContainer.appendChild(div);
        });

        totalContainer.textContent = `Total: $${total}`;
    }

    /** 🔹 Cambia la cantidad de un producto */
    window.cambiarCantidad = function (index, cambio) {
        if (carrito[index].cantidad + cambio > 0) {
            carrito[index].cantidad += cambio;
            localStorage.setItem("carrito", JSON.stringify(carrito));
            renderizarCarrito();
        }
    };

    /** 🔹 Elimina un producto del carrito */
    window.eliminarDelCarrito = function (index) {
        carrito.splice(index, 1);
        localStorage.setItem("carrito", JSON.stringify(carrito));
        renderizarCarrito();
    };

    /** 🔹 Finaliza la compra */
    btnPagar.addEventListener("click", function () {
        if (carrito.length === 0) {
            alert("El carrito está vacío.");
            return;
        }

        alert("✅ Compra realizada con éxito. Gracias por confiar en Vibra Animal.");
        carrito = [];
        localStorage.removeItem("carrito");
        renderizarCarrito();
    });

    document.addEventListener("DOMContentLoaded", function () {
        const carritoContainer = document.getElementById("carrito-container");
        const totalContainer = document.getElementById("total-container");
        let carrito = JSON.parse(localStorage.getItem("carrito")) || [];
    
        /** 🔹 Renderizar elementos del carrito */
        function renderizarCarrito() {
            carritoContainer.innerHTML = "";
            let total = 0;
    
            if (carrito.length === 0) {
                carritoContainer.innerHTML = "<p>El carrito está vacío.</p>";
                totalContainer.textContent = "Total: $0";
                return;
            }
    
            carrito.forEach((item, index) => {
                total += item.precio * item.cantidad;
    
                const div = document.createElement("div");
                div.classList.add("carrito-item");
                div.innerHTML = `
                    <h4>${item.nombre}</h4>
                    <p>Precio: $${item.precio} x ${item.cantidad}</p>
                    <button class="btn btn-delete" onclick="eliminarDelCarrito(${index})">Eliminar</button>
                `;
                carritoContainer.appendChild(div);
            });
    
            totalContainer.textContent = `Total: $${total}`;
        }
    
        /** 🔹 Eliminar del carrito */
        window.eliminarDelCarrito = function (index) {
            carrito.splice(index, 1);
            localStorage.setItem("carrito", JSON.stringify(carrito));
            renderizarCarrito();
        };
    
        renderizarCarrito();
    });
    

    // Ejecutar funciones iniciales
    renderizarCarrito();
});
