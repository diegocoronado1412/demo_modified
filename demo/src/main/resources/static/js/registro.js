document.addEventListener("DOMContentLoaded", () => {
  const registroForm = document.getElementById("registroForm");
  if (!registroForm) {
      console.error("No se encontró el formulario de registro con ID 'registroForm'.");
      return;
  }

  registroForm.addEventListener("submit", async (e) => {
    e.preventDefault();

    // Obtener elementos del formulario
    const nombreElem = document.getElementById("nombre");
    const cedulaElem = document.getElementById("cedula");
    const correoElem = document.getElementById("correo");
    const telefonoElem = document.getElementById("celular");
    const passwordElem = document.getElementById("contraseña");
    const confirmPasswordElem = document.getElementById("confirmPassword");
    const roleElem = document.getElementById("role");

    // Comprobar que todos los elementos existen
    if (!nombreElem || !cedulaElem || !correoElem || !telefonoElem || !passwordElem || !confirmPasswordElem || !roleElem) {
      alert("⚠ Algunos campos del formulario no se encontraron. Verifica los IDs en el HTML.");
      return;
    }

    // Obtener los valores
    const nombre = nombreElem.value;
    const cedula = cedulaElem.value;
    const correo = correoElem.value;
    const telefono = telefonoElem.value;
    const password = passwordElem.value;
    const confirmPassword = confirmPasswordElem.value;
    const role = roleElem.value;

    // Verificar contraseñas
    if (password !== confirmPassword) {
      alert("⚠ Las contraseñas no coinciden");
      return;
    }

    // Crear objeto usuario con la propiedad 'contraseña' en lugar de 'password'
    const usuario = {
      cedula,
      nombre,
      correo,
      celular: telefono,
      contraseña: password  // Uso consistente de "contraseña" con tilde
    };

    // Seleccionar URL según el rol elegido
    let url = role === "cliente" 
          ? "/api/cliente/registrar"
          : "/api/veterinario/registrar";

    try {
      const response = await fetch(url, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(usuario)
      });

      if (response.ok) {
        alert("✅ Registro exitoso. Por favor, inicia sesión.");
        window.location.href = "/login"; 
      } else {
        console.log(await response.text());
        alert("⚠ Error en el registro. Intenta nuevamente.");
      }
    } catch (error) {
      console.error("Error en el registro:", error);
      alert("⚠ Error en el servidor.");
    }
  });
});
