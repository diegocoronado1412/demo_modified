document.addEventListener("DOMContentLoaded", () => {
  // Verificar elementos antes de cargar datos
  if (document.getElementById("listaMascotas")) {
    loadMascotasCliente();
  }
  if (document.getElementById("listaCitas")) {
    loadCitasCliente();
  }
  if (document.getElementById("citasCliente")) {
    contarCitas();
  }
  if (document.getElementById("recetasCliente")) {
    contarRecetas();
  }
  if (document.getElementById("carritoCliente")) {
    contarCarrito();
  }

  // Agregar evento al formulario de mascotas
  const form = document.getElementById("agregarMascotaForm");
  if (form) {
    form.addEventListener("submit", agregarMascotaCliente);
  }
});

// Cargar lista de mascotas
function loadMascotasCliente() {
  const lista = document.getElementById("listaMascotas");
  if (!lista) return;

  const mascotas = JSON.parse(localStorage.getItem("mascotas")) || [];
  lista.innerHTML = mascotas.map((m, i) => `
      <div class="mascota-card">
          <p><strong>${m.nombre}</strong> (${m.especie})</p>
          <p>Raza: ${m.raza} - Edad: ${m.edad}</p>
          <button onclick="eliminarMascotaCliente(${i})">Eliminar</button>
      </div>
  `).join("");
}

// Agregar mascota desde el formulario
function agregarMascotaCliente(event) {
  event.preventDefault();
  const nombre = document.getElementById("nombreMascota").value;
  const especie = document.getElementById("especieMascota").value;
  const raza = document.getElementById("razaMascota").value;
  const edad = document.getElementById("edadMascota").value;

  if (nombre && especie && raza && edad) {
    const mascotas = JSON.parse(localStorage.getItem("mascotas")) || [];
    mascotas.push({ nombre, especie, raza, edad });
    localStorage.setItem("mascotas", JSON.stringify(mascotas));
    loadMascotasCliente();
    alert("Mascota agregada exitosamente.");
    event.target.reset();
  } else {
    alert("Por favor completa todos los campos.");
  }
}

// Eliminar mascota
function eliminarMascotaCliente(index) {
  let mascotas = JSON.parse(localStorage.getItem("mascotas")) || [];
  mascotas.splice(index, 1);
  localStorage.setItem("mascotas", JSON.stringify(mascotas));
  loadMascotasCliente();
}

// Cargar citas del cliente
function loadCitasCliente() {
  const listaCitas = document.getElementById('listaCitas');
  if (!listaCitas) {
      console.error("Elemento listaCitas no encontrado en el DOM.");
      return;
  }

  const citas = JSON.parse(localStorage.getItem('citas')) || [];
  listaCitas.innerHTML = citas.map((cita, index) => `
      <div class="cita-card">
          <p><strong>${cita.fecha}</strong></p>
          <p>Hora: ${cita.hora}</p>
          <p>Veterinario: ${cita.veterinario}</p>
          <button onclick="cancelarCita(${index})">Cancelar</button>
      </div>
  `).join('');
}

// Cancelar cita
function cancelarCita(index) {
  let citas = JSON.parse(localStorage.getItem('citas')) || [];
  citas.splice(index, 1);
  localStorage.setItem('citas', JSON.stringify(citas));
  loadCitasCliente();
}

// Contar citas activas del cliente
function contarCitas() {
  const citasClienteElement = document.getElementById("citasCliente");
  if (!citasClienteElement) return;

  const citas = JSON.parse(localStorage.getItem("citas")) || [];
  citasClienteElement.textContent = citas.length;
}

// Contar recetas activas del cliente
function contarRecetas() {
  const recetasClienteElement = document.getElementById("recetasCliente");
  if (!recetasClienteElement) return;

  const recetas = JSON.parse(localStorage.getItem("recetas")) || [];
  recetasClienteElement.textContent = recetas.length;
}

// Contar productos en el carrito
function contarCarrito() {
  const carritoClienteElement = document.getElementById("carritoCliente");
  if (!carritoClienteElement) return;

  const carrito = JSON.parse(localStorage.getItem("carrito")) || [];
  carritoClienteElement.textContent = carrito.length;
}
