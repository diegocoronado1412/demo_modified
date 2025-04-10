export async function login(cedula, password) {
    try {
      const response = await fetch("/api/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ cedula, contraseña: password })
      });
  
      if (!response.ok) {
        throw new Error("Credenciales incorrectas");
      }
  
      // El backend devuelve { usuario, rol }
      const { usuario, rol } = await response.json();
  
      const rolMinuscula = rol.toLowerCase();
      if (!["admin", "cliente", "veterinario"].includes(rolMinuscula)) {
        throw new Error("Rol no reconocido");
      }
  
      // Guardar en sessionStorage
      sessionStorage.setItem("currentUser", JSON.stringify(usuario));
      sessionStorage.setItem("rol", rolMinuscula);
  
      // Redirigir
      if (rolMinuscula === "admin") {
        window.location.href = "/admin/panel";
      } else if (rolMinuscula === "cliente") {
        window.location.href = "/cliente/panel";
      } else if (rolMinuscula === "veterinario") {
        window.location.href = "/veterinario/panel";
      }
  
      return { usuario, rol: rolMinuscula };
    } catch (error) {
      alert(error.message);
      console.error("Error en login:", error);
      return null;
    }
  }
  
  // Obtener usuario actual desde el backend
  export async function getCurrentUser() {
    try {
      const response = await fetch("/api/usuario-actual");
      if (!response.ok) {
        sessionStorage.removeItem("currentUser");
        sessionStorage.removeItem("rol");
        window.location.href = "/login";
        return null;
      }
      const { usuario, rol } = await response.json();
      const rolMinuscula = rol.toLowerCase();
      sessionStorage.setItem("currentUser", JSON.stringify(usuario));
      sessionStorage.setItem("rol", rolMinuscula);
      return { usuario, rol: rolMinuscula };
    } catch (error) {
      console.error("Error obteniendo usuario actual:", error);
      return null;
    }
  }
  
  // Cerrar sesión
  export async function logout() {
    try {
      await fetch("/logout", { method: "GET" });
    } catch (error) {
      console.error("Error cerrando sesión:", error);
    }
    sessionStorage.removeItem("currentUser");
    sessionStorage.removeItem("rol");
    window.location.href = "/login";
  }
  