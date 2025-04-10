document.addEventListener("DOMContentLoaded", () => {
  const contactForm = document.getElementById("contactForm")

  contactForm.addEventListener("submit", (e) => {
    e.preventDefault()

    const nombre = document.getElementById("nombre").value
    const email = document.getElementById("email").value
    const telefono = document.getElementById("telefono").value
    const mensaje = document.getElementById("mensaje").value

    // Aquí normalmente enviarías los datos a un servidor
    // Para este ejemplo, solo mostraremos un mensaje de éxito
    console.log("Formulario enviado:", { nombre, email, telefono, mensaje })

    alert("Gracias por tu mensaje. Nos pondremos en contacto contigo pronto.")
    contactForm.reset()
  })
})

