package com.example.demo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.io.File;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SeleniumControllerTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private final String baseUrl = "http://localhost:4200";

    @BeforeAll
    public void setupClass() {
        WebDriverManager.chromedriver()
            .clearDriverCache()
            .setup();
    }

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    /** Click vía JS para evitar interceptaciones */
    private void jsClick(WebElement el) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
    }

    @Test
    @DisplayName("Sprint10 – Caso de uso 1 completo con navegación extra")
    public void testCasoDeUso1_FlujoCompleto() {
        //
        // 1) Veterinario: login fallido y exitoso
        //
        driver.get(baseUrl + "/login");
        driver.findElement(By.id("cedula")).sendKeys("2001");
        driver.findElement(By.id("password")).sendKeys("wrongpass");
        jsClick(driver.findElement(By.cssSelector("button[type='submit']")));
        assertTrue(driver.getCurrentUrl().endsWith("/login"));

        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("clavevet1");
        jsClick(driver.findElement(By.cssSelector("button[type='submit']")));
        wait.until(d -> !d.findElements(By.cssSelector(".fa-sign-out-alt")).isEmpty());

        //
        // 2) Crear cliente con validaciones y alertas
        //
        driver.get(baseUrl + "/cliente/crear");
        WebElement nombre = driver.findElement(By.id("nombre"));
        WebElement cedula = driver.findElement(By.id("cedula"));
        WebElement btnSubmit = driver.findElement(By.cssSelector("button[type='submit']"));

        // Validaciones
        nombre.sendKeys("M");              assertFalse(btnSubmit.isEnabled());
        cedula.sendKeys("123");            assertFalse(btnSubmit.isEnabled());
        cedula.clear(); cedula.sendKeys("1234567"); assertFalse(btnSubmit.isEnabled());

        // Completar formulario
        nombre.clear(); nombre.sendKeys("Manuel Torres");
        cedula.clear(); cedula.sendKeys("99999999");
        driver.findElement(By.id("correo")).sendKeys("manuel.torres@mail.com");
        driver.findElement(By.id("celular")).sendKeys("3001234567");
        driver.findElement(By.id("contraseña")).sendKeys("clave123");

        wait.until(d -> btnSubmit.isEnabled());
        jsClick(btnSubmit);

        // Aceptar alerta
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        assertEquals("Cliente creado correctamente", alert.getText());
        alert.accept();

        // Volver al formulario
        jsClick(driver.findElement(By.cssSelector("button.secondary")));
        wait.until(d -> d.getCurrentUrl().contains("/cliente/crear"));

        //
        // 2.2) Navegar a Listar Clientes y verificar el cliente creado
        //
        driver.get(baseUrl + "/cliente/listar");
        wait.until(d -> d.getCurrentUrl().contains("/cliente/listar"));
        List<WebElement> filasClientes = driver.findElements(By.xpath("//td[text()='99999999']"));
        assertFalse(filasClientes.isEmpty(), "El cliente 99999999 no aparece en la lista");

        // Volver al formulario de nuevo
        driver.get(baseUrl + "/cliente/crear");
        wait.until(d -> d.getCurrentUrl().contains("/cliente/crear"));

        //
        // 3) Sección de mascotas del veterinario
        //
        driver.get(baseUrl + "/cliente/veterinario/seleccionar-mascotas");
        wait.until(d -> d.getCurrentUrl().contains("/cliente/veterinario/seleccionar-mascotas"));

        // Esperar que la opción esté poblada
        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//select[@name='clienteCedula']/option[text()='99999999 - Manuel Torres']")));
        new Select(driver.findElement(By.name("clienteCedula")))
            .selectByVisibleText("99999999 - Manuel Torres");
        jsClick(driver.findElement(By.cssSelector("button[type='submit']")));
        wait.until(d -> d.getPageSource().contains("Mascotas Registradas"));

        // Volver al selector
        driver.get(baseUrl + "/cliente/veterinario/seleccionar-mascotas");
        wait.until(d -> d.getCurrentUrl().contains("/cliente/veterinario/seleccionar-mascotas"));

        // Recargar la lista de mascotas
        driver.get(baseUrl + "/cliente/veterinario/seleccionar-mascotas?reload=true");
        wait.until(d -> d.getPageSource().contains("Mascotas Registradas"));

        //
        // 3.3) Crear nueva mascota con foto
        //
        driver.get(baseUrl + "/mascota/crear");
        wait.until(d -> d.getCurrentUrl().contains("/mascota/crear"));

        driver.findElement(By.id("nombre")).sendKeys("Rocky");
        driver.findElement(By.id("especie")).sendKeys("Perro");
        driver.findElement(By.id("raza")).sendKeys("Boxer");
        driver.findElement(By.id("edad")).sendKeys("4");
        File foto = new File("src/test/resources/rocky.jpg");
        driver.findElement(By.id("archivo")).sendKeys(foto.getAbsolutePath());
        jsClick(driver.findElement(By.cssSelector("button[type='submit']")));

        // Tras crear, verificar en Listar Mascotas
        driver.get(baseUrl + "/mascota/listar");
        wait.until(d -> d.getCurrentUrl().contains("/mascota/listar"));
        List<WebElement> filasMascotasVet = driver.findElements(By.xpath("//td[text()='Rocky']"));
        assertFalse(filasMascotasVet.isEmpty(), "Rocky no aparece en listado de mascotas (veterinario)");

        //
        // 4) Login como cliente y verificar "Mis Mascotas"
        //
        driver.get(baseUrl + "/login");
        driver.findElement(By.id("cedula")).sendKeys("99999999");
        driver.findElement(By.id("password")).sendKeys("clave123");
        jsClick(driver.findElement(By.cssSelector("button[type='submit']")));
        wait.until(d -> !d.findElements(By.cssSelector(".fa-sign-out-alt")).isEmpty());

        driver.get(baseUrl + "/cliente/mascotas");
        wait.until(d -> d.getCurrentUrl().contains("/cliente/mascotas"));
        List<WebElement> filasMascotasCli = driver.findElements(By.xpath("//td[text()='Rocky']"));
        assertFalse(filasMascotasCli.isEmpty(), "Rocky no aparece en Mis Mascotas del cliente");
    }
}
