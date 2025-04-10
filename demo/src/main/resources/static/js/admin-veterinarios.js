document.addEventListener("DOMContentLoaded", function () {
  const form = document.getElementById("formVeterinario");
  const veterinariosContainer = document.getElementById("veterinarios-container");

  // Obtener solo los usuarios con rol de "veterinario"
  function obtenerVeterinarios() {
      const usuarios = JSON.parse(localStorage.getItem("users")) || [];
      return usuarios.filter(usuario => usuario.role === "veterinario");
  }

  function renderizarVeterinarios() {
      veterinariosContainer.innerHTML = "";
      const veterinarios = obtenerVeterinarios();

      veterinarios.forEach((veterinario, index) => {
          const div = document.createElement("div");
          div.classList.add("veterinario-card");
          div.innerHTML = `
              <h3>${veterinario.nombre}</h3>
              <p><strong>Especialidad:</strong> ${veterinario.especialidad || "No especificada"}</p>
              <p><strong>Horario:</strong> ${veterinario.horario || "No asignado"}</p>
              <button class="btn btn-delete" onclick="eliminarVeterinario('${veterinario.cedula}')">Eliminar</button>
          `;
          veterinariosContainer.appendChild(div);
      });
  }

  form.addEventListener("submit", function (event) {
      event.preventDefault();

      let usuarios = JSON.parse(localStorage.getItem("users")) || [];

      const nuevoVeterinario = {
          id: usuarios.length + 1,
          cedula: document.getElementById("cedula").value,
          nombre: document.getElementById("nombre").value,
          especialidad: document.getElementById("especialidad").value,
          horario: document.getElementById("horario").value,
          role: "veterinario"
      };

      // Verificar si el veterinario ya existe
      if (usuarios.some(u => u.cedula === nuevoVeterinario.cedula)) {
          alert("Este veterinario ya existe.");
          return;
      }

      usuarios.push(nuevoVeterinario);
      localStorage.setItem("users", JSON.stringify(usuarios));

      form.reset();
      document.getElementById("formularioVeterinario").style.display = "none";
      renderizarVeterinarios();
  });

  window.eliminarVeterinario = function (cedula) {
      let usuarios = JSON.parse(localStorage.getItem("users")) || [];
      usuarios = usuarios.filter(usuario => usuario.cedula !== cedula);
      localStorage.setItem("users", JSON.stringify(usuarios));
      renderizarVeterinarios();
  };

  window.mostrarFormularioVeterinario = function () {
      document.getElementById("formularioVeterinario").style.display = "block";
  };

  renderizarVeterinarios();
});
