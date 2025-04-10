document.addEventListener("DOMContentLoaded", function () {
    const productosContainer = document.getElementById("productos-container");
    const carritoContainer = document.getElementById("carrito-container");
    const totalContainer = document.getElementById("total-container");
    const btnPagar = document.getElementById("btnPagar");

    let productos = JSON.parse(localStorage.getItem("productos")) || [];
    let carrito = JSON.parse(localStorage.getItem("carrito")) || [];

    /** 🔹 Muestra los productos disponibles en la tienda */
    function renderizarProductos() {
        if (!productosContainer) return;

        productosContainer.innerHTML = "";
        if (productos.length === 0) {
            productosContainer.innerHTML = "<p>No hay productos disponibles.</p>";
            return;
        }

        productos.forEach((producto, index) => {
            const div = document.createElement("div");
            div.classList.add("producto-card");
            div.innerHTML = `
                <img src="${producto.imagen}" alt="${producto.nombre}">
                <h3>${producto.nombre}</h3>
                <p><strong>Categoría:</strong> ${producto.categoria}</p>
                <p><strong>Precio:</strong> $${producto.precio}</p>
                <p><strong>Stock:</strong> ${producto.stock} unidades</p>
                <button class="btn" onclick="agregarAlCarrito(${index})">Agregar al Carrito</button>
            `;
            productosContainer.appendChild(div);
        });
    }

    /** 🔹 Agrega productos al carrito */
    window.agregarAlCarrito = function (index) {
        const productoSeleccionado = productos[index];

        if (!productoSeleccionado) {
            alert("Error: Producto no encontrado.");
            return;
        }

        // Verificar si hay stock disponible
        if (productoSeleccionado.stock > 0) {
            const productoEnCarrito = carrito.find(p => p.nombre === productoSeleccionado.nombre);

            if (productoEnCarrito) {
                if (productoEnCarrito.cantidad < productoSeleccionado.stock) {
                    productoEnCarrito.cantidad++;
                } else {
                    alert("No hay más stock disponible.");
                    return;
                }
            } else {
                carrito.push({ ...productoSeleccionado, cantidad: 1 });
            }

            localStorage.setItem("carrito", JSON.stringify(carrito));
            renderizarCarrito();
        } else {
            alert("Producto sin stock disponible.");
        }
    };

    /** 🔹 Muestra los productos en el carrito */
    function renderizarCarrito() {
        if (!carritoContainer || !totalContainer) return;

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
                <h4>${producto.nombre}</h4>
                <p>Precio: $${producto.precio} x ${producto.cantidad}</p>
                <button class="btn btn-delete" onclick="eliminarDelCarrito(${index})">Eliminar</button>
            `;
            carritoContainer.appendChild(div);
        });

        totalContainer.textContent = `Total: $${total}`;
    }

    /** 🔹 Elimina un producto del carrito */
    window.eliminarDelCarrito = function (index) {
        if (index < 0 || index >= carrito.length) return;
        carrito.splice(index, 1);
        localStorage.setItem("carrito", JSON.stringify(carrito));
        renderizarCarrito();
    };

    /** 🔹 Finaliza la compra */
    if (btnPagar) {
        btnPagar.addEventListener("click", function () {
            if (carrito.length === 0) {
                alert("El carrito está vacío.");
                return;
            }

            alert("Compra realizada con éxito.");
            carrito = [];
            localStorage.removeItem("carrito");
            renderizarCarrito();
        });
    }

    // Ejecutar funciones iniciales
    renderizarProductos();
    renderizarCarrito();
});
