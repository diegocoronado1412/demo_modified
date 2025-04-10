document.addEventListener("DOMContentLoaded", function () {
  const mascotasContainer = document.getElementById("mascotas-container");

  /** 🔹 Obtener todas las mascotas de todos los usuarios */
  function getAllMascotas() {
      let allMascotas = [];
      for (let i = 0; i < localStorage.length; i++) {
          let key = localStorage.key(i);
          if (key.startsWith("mascotas_")) {
              let mascotasUsuario = JSON.parse(localStorage.getItem(key)) || [];
              allMascotas = allMascotas.concat(mascotasUsuario);
          }
      }
      return allMascotas;
  }

  /** 🔹 Renderizar todas las mascotas */
  function renderizarMascotas() {
      mascotasContainer.innerHTML = "";
      const mascotas = getAllMascotas();

      if (mascotas.length === 0) {
          mascotasContainer.innerHTML = "<p>No hay mascotas registradas.</p>";
          return;
      }

      mascotas.forEach((mascota, index) => {
          const div = document.createElement("div");
          div.classList.add("mascota-card");
          div.innerHTML = `
              <img src="${mascota.foto || '../../images/default-pet.png'}" alt="Foto de ${mascota.nombre}" class="mascota-photo">
              <div class="mascota-info">
                  <h3>${mascota.nombre}</h3>
                  <p><strong>Especie:</strong> ${mascota.especie}</p>
                  <p><strong>Raza:</strong> ${mascota.raza || "No especificada"}</p>
                  <p><strong>Edad:</strong> ${mascota.edad} años</p>
              </div>
          `;
          mascotasContainer.appendChild(div);
      });
  }

  renderizarMascotas();
});
