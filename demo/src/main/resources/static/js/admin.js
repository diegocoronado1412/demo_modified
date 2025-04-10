document.addEventListener("DOMContentLoaded", () => {
  // Funciones internas para sessionStorage
  const getCurrentUser = () => {
    const userJSON = sessionStorage.getItem("currentUser");
    return userJSON ? JSON.parse(userJSON) : null;
  };
  const getRol = () => sessionStorage.getItem("rol") || null;

  // Verificar si el usuario es admin
  const currentUser = getCurrentUser();
  const rol = getRol();
  if (!currentUser || rol !== "admin") {
    alert("Acceso denegado. Debes ser administrador para ingresar aquí.");
    window.location.href = "/login";
    return;
  }

  // Mostrar nombre del admin en la interfaz
  const adminNameElem = document.getElementById("adminName");
  if (adminNameElem) {
    adminNameElem.textContent = currentUser.nombre || "Administrador";
  }

  // Cargar estadísticas
  cargarEstadisticas();
  cargarUltimosTratamientos();
  cargarMedicamentosBajos();

  // Manejar cierre de sesión
  const logoutBtn = document.getElementById("logoutBtn");
  if (logoutBtn) {
    logoutBtn.addEventListener("click", (e) => {
      e.preventDefault();
      sessionStorage.removeItem("currentUser");
      sessionStorage.removeItem("rol");
      window.location.href = "/login";
    });
  }

  // ----------------- Funciones Internas -----------------

  function cargarEstadisticas() {
    // Aquí podrías hacer fetch a tu backend para estadísticas reales.
    // O usar datos en sessionStorage si simulas una base local.

    // Ejemplo simulado:
    const mascotasActivas = 10;  // Número simulado
    const vetsActivos = 5;       // Número simulado
    const tratamientosMes = 12;  // Número simulado
    const ingresosMes = 500000;  // Número simulado

    // Actualizar UI
    document.getElementById("mascotasTratamiento").textContent = mascotasActivas;
    document.getElementById("veterinariosActivos").textContent = vetsActivos;
    document.getElementById("tratamientosMes").textContent = tratamientosMes;
    document.getElementById("ingresosMes").textContent = `$${ingresosMes.toLocaleString("es-CO")}`;
  }

  function cargarUltimosTratamientos() {
    const tabla = document.getElementById("ultimosTratamientos");
    if (!tabla) return;
    const tbody = tabla.querySelector("tbody");
    if (!tbody) return;

    // Ejemplo simulado:
    const tratamientos = [
      { fecha: "2025-03-01", mascota: "Firulais", vet: "Dr. Gómez", medicamento: "Antipulgas" },
      { fecha: "2025-03-02", mascota: "Rex", vet: "Dr. García", medicamento: "Desparasitante" },
      // ... etc
    ];

    // Ordenar por fecha desc
    tratamientos.sort((a, b) => new Date(b.fecha) - new Date(a.fecha));
    // Tomar solo 5
    const ultimos = tratamientos.slice(0, 5);

    tbody.innerHTML = "";
    ultimos.forEach((t) => {
      const row = tbody.insertRow();
      row.innerHTML = `
        <td>${t.mascota}</td>
        <td>${t.vet}</td>
        <td>${t.medicamento}</td>
        <td>${new Date(t.fecha).toLocaleDateString()}</td>
      `;
    });
  }

  function cargarMedicamentosBajos() {
    const tabla = document.getElementById("medicamentosBajos");
    if (!tabla) return;
    const tbody = tabla.querySelector("tbody");
    if (!tbody) return;

    // Ejemplo simulado:
    const medicamentos = [
      { nombre: "Antipulgas", unidadesDisponibles: 8 },
      { nombre: "Desparasitante", unidadesDisponibles: 2 },
      { nombre: "Analgésico", unidadesDisponibles: 12 },
    ];
    const STOCK_MINIMO = 10;
    const medsBajos = medicamentos.filter((m) => m.unidadesDisponibles <= STOCK_MINIMO);

    tbody.innerHTML = "";
    medsBajos.forEach((m) => {
      const row = tbody.insertRow();
      row.innerHTML = `
        <td>${m.nombre}</td>
        <td>${m.unidadesDisponibles}</td>
        <td>${STOCK_MINIMO}</td>
        <td>
          <span class="status ${m.unidadesDisponibles === 0 ? "status-low" : "status-normal"}">
            ${m.unidadesDisponibles === 0 ? "Sin Stock" : "Bajo"}
          </span>
        </td>
      `;
    });
  }
});
