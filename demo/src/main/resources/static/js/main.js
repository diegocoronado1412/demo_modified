document.addEventListener("DOMContentLoaded", () => {
  // Función para manejar el scroll suave
  document.querySelectorAll('a[href^="#"]').forEach((anchor) => {
    anchor.addEventListener("click", function (e) {
      e.preventDefault()

      document.querySelector(this.getAttribute("href")).scrollIntoView({
        behavior: "smooth",
      })
    })
  })

  // Función para manejar el menú responsive
  const navToggle = document.createElement("button")
  navToggle.classList.add("nav-toggle")
  navToggle.innerHTML = '<i class="fas fa-bars"></i>'
  document.querySelector("nav").appendChild(navToggle)

  navToggle.addEventListener("click", () => {
    const navUl = document.querySelector("nav ul")
    navUl.classList.toggle("show")
  })

  // Función para cambiar el estilo del header al hacer scroll
  window.addEventListener("scroll", () => {
    const header = document.querySelector("header")
    header.classList.toggle("scrolled", window.scrollY > 0)
  })
})

document.addEventListener("DOMContentLoaded", () => {
  const logoutBtn = document.getElementById("logoutBtn");
  if (logoutBtn) {
    logoutBtn.addEventListener("click", (e) => {
      e.preventDefault();
      logout();
      window.location.href = "/index.html";
    });
  }

  // Otras funcionalidades...
});
