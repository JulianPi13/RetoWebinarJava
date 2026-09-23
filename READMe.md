# Reto Evaluativo: Sistema de Inscripción a Webinar Corporativo

## Descripción
Aplicación de consola desarrollada en Java para gestionar las inscripciones a un webinar corporativo. Implementa JDBC, PreparedStatement, transacciones (commit/rollback) y arquitectura MVC (Modelo-Vista-Controlador).

## Configuración de Base de Datos
- **Entorno:** MySQL Server + Workbench
- **Base de datos:** my_db
- **URL de conexión:** jdbc:mysql://localhost:3306/my_db
- **Usuario:** root
- **Contraseña:** 123456

## Script SQL de la tabla participantes

```sql
USE my_db;

CREATE TABLE IF NOT EXISTS participantes(
    idparticipante INT AUTO_INCREMENT PRIMARY KEY, 
    nombre VARCHAR(100) NOT NULL, 
    correo VARCHAR(120) NOT NULL UNIQUE, 
    empresa VARCHAR(100) NOT NULL 
);

# Pruebas de la Aplicación

## Evidencias de las pruebas

A continuación se presentan las evidencias de las pruebas realizadas durante la ejecución de la aplicación.

![Prueba de inscripción de participantes](images/imagen_1.png)

![Prueba de inscripción de participantes](images/imagen_2.png)

![Prueba de inscripción de participantes](images/imagen_3.png)

---

## Prueba 1: Inscripción de participantes

Se inscriben 3 participantes con correos distintos de al menos 2 empresas diferentes:

* Juan — EmpresaA
* Maria — EmpresaB
* Pedro — EmpresaA

La inscripción debe realizarse correctamente para los tres participantes.

---

## Prueba 2: Validación de correo repetido

Se intenta inscribir a un nuevo participante:

* **Nombre:** Luis
* **Correo:** `juan@empresaA.com`

El correo ya se encuentra registrado.

El sistema debe:

* Rechazar la inscripción.
* Informar claramente el error al usuario.
* No detener el programa.
* No insertar el registro duplicado.
* Ejecutar `rollback()` para revertir la transacción.

![Prueba 2 - Validación de correo repetido](images/imagen_4.png)

---

## Prueba 3: Listar todos los participantes

Se listan todos los participantes inscritos exitosamente.

Deben aparecer los 3 registros con:

* ID
* Nombre
* Correo
* Empresa

Ejemplo:

| ID | Nombre | Correo                                          | Empresa  |
| -: | ------ | ----------------------------------------------- | -------- |
|  1 | Juan   | [juan@empresaA.com](mailto:juan@empresaA.com)   | EmpresaA |
|  2 | Maria  | [maria@empresaB.com](mailto:maria@empresaB.com) | EmpresaB |
|  3 | Pedro  | [pedro@empresaA.com](mailto:pedro@empresaA.com) | EmpresaA |

![Prueba 3 - Listar participantes](images/imagen_5.png)

---

## Prueba 4: Buscar participantes por empresa

Se realiza una búsqueda utilizando la empresa:

```text
EmpresaA
```

El sistema debe retornar únicamente los participantes que trabajan en dicha empresa:

* Juan
* Pedro

El participante de `EmpresaB` debe quedar excluido de los resultados.

![Prueba 4 - Buscar participantes por empresa](images/imagen_6.png)

---

## Prueba 5: Contar total de participantes

Se cuenta el total de participantes registrados en el sistema.

El resultado esperado es:

```text
Total de participantes: 3
```

El número debe coincidir con la cantidad de inscripciones realizadas exitosamente.

![Prueba 5 - Contar participantes](images/imagen_7.png)

---

## Prueba 6: Eliminar participante por ID

Se elimina el participante con:

```text
ID: 2
```

Este ID corresponde a **Maria**.

Posteriormente, se vuelven a listar los participantes para verificar que únicamente quedan:

* Juan
* Pedro

Finalmente, se realiza nuevamente el conteo.

Resultado esperado:

```text
Total de participantes: 2
```

El total debe disminuir en 1.

![Prueba 6 - Eliminar participante por ID](images/imagen_8.png)

---

## Prueba 7: Eliminar ID inexistente

Se intenta eliminar un participante con un ID que no existe en la base de datos:

```text
ID: 999
```

El sistema debe:

* Informar claramente que no se encontró ningún participante con ese ID.
* No generar errores no controlados.
* No cerrar el programa.
* Permitir continuar utilizando el menú.

![Prueba 7 - Eliminar ID inexistente](images/imagen_9.png)

---

# Características Técnicas Implementadas

## 1. Transacciones (Commit/Rollback)

La inscripción de participantes maneja transacciones completas.

El proceso es el siguiente:

1. Se valida si el correo ya existe mediante `SELECT COUNT(*)`.
2. Si el correo ya existe:

   * Se ejecuta `rollback()`.
   * Se informa al usuario que el correo ya está registrado.
3. Si el correo no existe:

   * Se ejecuta el `INSERT`.
   * Se ejecuta `commit()`.
4. En caso de cualquier error SQL:

   * Se ejecuta `rollback()`.
   * Se mantiene la integridad de la base de datos.

---

## 2. PreparedStatement

Todas las consultas SQL utilizan `PreparedStatement` con parámetros:

```text
?
```

No se utiliza `Statement` con concatenación de strings.

Esto permite:

* Evitar la concatenación directa de datos en las consultas.
* Reducir el riesgo de **SQL Injection**.
* Mantener las consultas SQL parametrizadas.

---

# 3. Arquitectura MVC

El proyecto utiliza una arquitectura basada en **MVC (Modelo - Vista - Controlador)**.

### Modelo

#### `Modelo.Clases`

Contiene la clase:

```text
Participante
```

Incluye:

* Atributos.
* Constructores.
* Getters y setters.
* Método `toString()`.

#### `Modelo.Persistencia`

Contiene las clases encargadas de la persistencia de datos:

```text
ConexionBD
Operaciones
```

**`ConexionBD`**

Se encarga de gestionar la conexión con la base de datos.

**`Operaciones`**

Contiene las operaciones SQL necesarias para:

* Insertar participantes.
* Listar participantes.
* Buscar por empresa.
* Contar participantes.
* Eliminar participantes.

---

### Controlador

Clase:

```text
ControladorParticipante
```

Se encarga de orquestar las operaciones de la aplicación.

El controlador no contiene consultas SQL directamente.

---

### Vista

Clase:

```text
Main
```

Se encarga del menú de consola y de la interacción con el usuario.

La vista recibe las opciones del usuario y llama al controlador correspondiente.

---

# 4. Validaciones y Manejo de Errores

La aplicación implementa diferentes validaciones para mantener la integridad de los datos.

### Campos obligatorios

Se valida que los siguientes campos no estén vacíos:

* Nombre.
* Correo.
* Empresa.

### Correo único

Antes de insertar un participante se verifica que el correo no se encuentre registrado previamente.

### IDs inexistentes

Al intentar eliminar un participante mediante un ID que no existe, el sistema informa al usuario sin generar errores no controlados.

### Cierre de recursos

Los recursos utilizados en las operaciones de base de datos son cerrados correctamente:

* `ResultSet`
* `PreparedStatement`
* `Connection`

El cierre se realiza mediante bloques `finally`.

---

# Cómo Ejecutar el Proyecto

## Requisitos Previos

Antes de ejecutar el proyecto se necesita tener instalado:

* **Java JDK 11 o superior**
* **Maven**
* **MySQL Server**
* **NetBeans**, **IntelliJ IDEA** o **Eclipse**

---

## Pasos de Instalación

### 1. Clonar el repositorio

```bash
git clone <URL_DEL_REPOSITORIO>
```

Ingresar a la carpeta del proyecto:

```bash
cd RetoWebinar1
```

---

### 2. Crear la base de datos y tabla

Abrir **MySQL Workbench** y ejecutar el script SQL incluido en el proyecto.

Este script se encarga de crear la estructura necesaria para almacenar los participantes.

---

### 3. Configurar la conexión

Abrir el archivo:

```text
src/main/java/Modelo/Persistencia/ConexionBD.java
```

Verificar que los datos de conexión correspondan al entorno local de MySQL:

* URL de conexión.
* Usuario.
* Contraseña.
* Puerto, si aplica.

---

### 4. Compilar y ejecutar

Abrir el proyecto en el IDE preferido.

Se recomienda utilizar **NetBeans**.

Ejecutar la clase:

```text
src/main/java/Vista/Main.java
```

También es posible compilar el proyecto desde la terminal utilizando Maven:

```bash
mvn clean install
```

Posteriormente:

```bash
mvn exec:java
```

> Los comandos pueden variar dependiendo de la configuración del `pom.xml`.

---

## 5. Usar el menú

Una vez ejecutada la aplicación, seguir las opciones disponibles en el menú de consola para:

* Inscribir participantes.
* Listar participantes.
* Buscar participantes por empresa.
* Contar participantes.
* Eliminar participantes.

---

# Autor

Desarrollado por **Julian** como parte del **Reto Evaluativo** de la asignatura de **Programación Orientada a Objetos / Bases de Datos**.

---

# Licencia

Proyecto educativo desarrollado con fines académicos.

> **Nota:** Este repositorio es público para fines de evaluación.
