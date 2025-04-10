document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("formProducto");
    const productosContainer = document.getElementById("productos-container");
    let productos = JSON.parse(localStorage.getItem("productos")) || [];

    function renderizarProductos() {
        productosContainer.innerHTML = "";
        productos.forEach((producto, index) => {
            const div = document.createElement("div");
            div.classList.add("producto-card");
            div.innerHTML = `
                <img src="${producto.imagen}" alt="${producto.nombre}">
                <h3>${producto.nombre}</h3>
                <p><strong>Categoría:</strong> ${producto.categoria}</p>
                <p><strong>Precio:</strong> $${producto.precio}</p>
                <p><strong>Stock:</strong> ${producto.stock} unidades</p>
                <button class="btn btn-delete" onclick="eliminarProducto(${index})">Eliminar</button>
            `;
            productosContainer.appendChild(div);
        });
    }

    form.addEventListener("submit", function (event) {
        event.preventDefault();

        const nuevoProducto = {
            nombre: document.getElementById("nombre").value,
            categoria: document.getElementById("categoria").value,
            precio: document.getElementById("precio").value,
            stock: document.getElementById("stock").value,
            imagen: document.getElementById("imagen").value
        };

        productos.push(nuevoProducto);
        localStorage.setItem("productos", JSON.stringify(productos));

        form.reset();
        document.getElementById("formularioProducto").style.display = "none";
        renderizarProductos();
    });

    window.eliminarProducto = function (index) {
        productos.splice(index, 1);
        localStorage.setItem("productos", JSON.stringify(productos));
        renderizarProductos();
    };

    window.mostrarFormularioProducto = function () {
        document.getElementById("formularioProducto").style.display = "block";
    };

    renderizarProductos();
});
