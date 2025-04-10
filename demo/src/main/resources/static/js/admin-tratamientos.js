document.addEventListener("DOMContentLoaded", function () {
  const form = document.getElementById("formTratamiento");
  const tratamientosContainer = document.getElementById("tratamientos-container");
  let tratamientos = JSON.parse(localStorage.getItem("tratamientos")) || [];

  function renderizarTratamientos() {
      tratamientosContainer.innerHTML = "";
      tratamientos.forEach((tratamiento, index) => {
          const div = document.createElement("div");
          div.classList.add("tratamiento-card");
          div.innerHTML = `
              <h3>${tratamiento.nombre}</h3>
              <p><strong>Descripción:</strong> ${tratamiento.descripcion}</p>
              <p><strong>Precio:</strong> $${tratamiento.precio}</p>
              <button class="btn btn-delete" onclick="eliminarTratamiento(${index})">Eliminar</button>
          `;
          tratamientosContainer.appendChild(div);
      });
  }

  form.addEventListener("submit", function (event) {
      event.preventDefault();

      const nuevoTratamiento = {
          nombre: document.getElementById("nombre").value,
          descripcion: document.getElementById("descripcion").value,
          precio: document.getElementById("precio").value
      };

      tratamientos.push(nuevoTratamiento);
      localStorage.setItem("tratamientos", JSON.stringify(tratamientos));

      form.reset();
      document.getElementById("formularioTratamiento").style.display = "none";
      renderizarTratamientos();
  });

  window.eliminarTratamiento = function (index) {
      tratamientos.splice(index, 1);
      localStorage.setItem("tratamientos", JSON.stringify(tratamientos));
      renderizarTratamientos();
  };

  window.mostrarFormularioTratamiento = function () {
      document.getElementById("formularioTratamiento").style.display = "block";
  };

  renderizarTratamientos();
});
