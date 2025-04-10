document.addEventListener("DOMContentLoaded", function() {
    const loginForm = document.getElementById("loginForm");
    if (!loginForm) return;
  
    loginForm.addEventListener('submit', function (e) {
      e.preventDefault();
  
      const cedulaInput = document.getElementById("cedula");
      const passwordInput = document.getElementById("password");
  
      if (!cedulaInput || !passwordInput) {
        alert("Campos del formulario no encontrados");
        return;
      }
  
      const cedula = cedulaInput.value;
      const contraseña = passwordInput.value;
  
      fetch('/api/login', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({ cedula, contraseña })
      })
      .then(response => {
        if (!response.ok) {
          // Aquí se lanza un error si la respuesta no es 2xx
          throw new Error("Credenciales incorrectas");
        }
        return response.json(); // Aquí se espera un objeto { usuario, rol }
      })
      .then(data => {
        // data.usuario => objeto con la info del usuario
        // data.rol => "admin", "cliente" o "veterinario"
  
        // Guardar en sessionStorage
        sessionStorage.setItem("currentUser", JSON.stringify(data.usuario));
  
        // Normalizamos el rol a minúsculas
        const rolMinuscula = data.rol.toLowerCase();
        sessionStorage.setItem("rol", rolMinuscula);
  
        // Redirigir según el rol
        if (rolMinuscula === "admin") {
          // Asegúrate de tener un controlador que atienda "/admin/dashboard"
          window.location.href = "/admin/dashboard";
        } else if (rolMinuscula === "cliente") {
          // Panel de cliente
          window.location.href = "/cliente/panel";
        } else if (rolMinuscula === "veterinario") {
          // Panel de veterinario
          window.location.href = "/veterinario/panel";
        } else {
          throw new Error("Rol no reconocido");
        }
      })
      .catch(error => {
        alert(error);
        console.error(error);
      });
    });
  });
  