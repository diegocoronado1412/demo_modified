document.addEventListener("DOMContentLoaded", function () {
  // Depuración: Mostrar en consola lo almacenado en sessionStorage
  const currentUserJSON = sessionStorage.getItem("currentUser");
  const currentRol = sessionStorage.getItem("rol");
  console.log("sessionStorage currentUser:", currentUserJSON);
  console.log("sessionStorage rol:", currentRol);

  const currentUser = currentUserJSON ? JSON.parse(currentUserJSON) : null;

  if (!currentUser || currentRol !== "veterinario") {
      alert("Acceso denegado. No tienes permisos para acceder aquí.");
      window.location.href = "../../index.html";
      return;
  }

  // Mostrar el nombre del veterinario en el elemento con id "veterinarioNombre"
  document.getElementById("veterinarioNombre").textContent = currentUser.nombre;

  // Cargar datos de pacientes, citas y tratamientos
  cargarDatosVeterinario();
});

function cargarDatosVeterinario() {
  const pacientes = JSON.parse(sessionStorage.getItem("pacientes")) || [];
  const citas = JSON.parse(sessionStorage.getItem("citas")) || [];
  const tratamientos = JSON.parse(sessionStorage.getItem("tratamientos")) || [];

  document.getElementById("pacientesVeterinario").textContent = pacientes.length;
  document.getElementById("citasVeterinario").textContent = citas.length;
  document.getElementById("tratamientosVeterinario").textContent = tratamientos.length;
}
