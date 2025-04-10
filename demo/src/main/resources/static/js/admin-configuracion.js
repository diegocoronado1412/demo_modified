document.addEventListener("DOMContentLoaded", function () {
    // Referencia al formulario
    const form = document.getElementById("formConfiguracion");
  
    // 1. Cargar la configuración previa desde sessionStorage (o usar valores por defecto)
    function cargarConfiguracion() {
      // Intentamos leer la clave "configuracion" en sessionStorage
      const configuracionSession = sessionStorage.getItem("configuracion");
  
      // Si existe, la parseamos; si no, creamos un objeto por defecto
      const configuracion = configuracionSession
        ? JSON.parse(configuracionSession)
        : {
            nombreClinica: "Vibra Animal",
            horarioAtencion: "Lunes a Viernes de 8 AM a 6 PM",
            contacto: "info@vibraanimal.com",
            tituloInicio: "Bienvenidos a Vibra Animal",
            subtituloInicio: "Cuidado especializado para tus mascotas",
            nuevosServicios: ["Hospitalización", "Cirugías", "Monitoreo"],
            mensajeContacto:
              "¡Estamos aquí para ayudarte! Contáctanos para más información."
          };
  
      // Rellenamos el formulario con estos valores
      document.getElementById("nombreClinica").value = configuracion.nombreClinica;
      document.getElementById("horarioAtencion").value = configuracion.horarioAtencion;
      document.getElementById("contacto").value = configuracion.contacto;
      document.getElementById("tituloInicio").value = configuracion.tituloInicio;
      document.getElementById("subtituloInicio").value = configuracion.subtituloInicio;
      // Convertimos array en string separado por comas
      document.getElementById("nuevosServicios").value = configuracion.nuevosServicios.join(", ");
      document.getElementById("mensajeContacto").value = configuracion.mensajeContacto;
    }
  
    // 2. Escuchamos el submit del formulario para guardar los datos en sessionStorage
    form.addEventListener("submit", function (event) {
      // Evitar que el formulario se envíe al servidor
      event.preventDefault();
  
      // Obtenemos los valores actualizados del formulario
      const configuracionActualizada = {
        nombreClinica: document.getElementById("nombreClinica").value,
        horarioAtencion: document.getElementById("horarioAtencion").value,
        contacto: document.getElementById("contacto").value,
        tituloInicio: document.getElementById("tituloInicio").value,
        subtituloInicio: document.getElementById("subtituloInicio").value,
        nuevosServicios: document
          .getElementById("nuevosServicios")
          .value.split(",")
          .map(s => s.trim()),
        mensajeContacto: document.getElementById("mensajeContacto").value
      };
  
      // Guardamos en sessionStorage como JSON
      sessionStorage.setItem("configuracion", JSON.stringify(configuracionActualizada));
  
      alert("Configuración guardada exitosamente en sessionStorage.");
    });
  
    // Llamamos a la función para cargar los datos al iniciar
    cargarConfiguracion();
  });
  