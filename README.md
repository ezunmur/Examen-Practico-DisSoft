# Examen Práctico 2
Hecho por Ezequiel Zuñiga Murillo

Refactortizacion.
Aplicacion de Chain of Responsibility.
Enmendando CodeSmells.
Realización de Pruebas unitarias.

Nota aparte / Comentario del estudiante:
(Aprendiendo como usar el editor de texto para que el README quede chevere.
Lamentando la elección de la carrera.)


## 1. Aplicación del patrón de diseño

El código original resolvía el tipo de incidente con una larga cadena de
`if / else if` dentro de `GestorIncidentes.procesarIncidente`, repitiendo
en cada rama la misma secuencia de pasos (registrar, validar, asignar
especialista, calcular costo, notificar).

Se reemplazó esa estructura por Chain of Responsibility:

- `ManejadorIncidente` (clase abstracta, paquete `cadena`): define el
  método plantilla `manejar(Incidente)`, que pregunta a cada manejador si
  `puedeManejar(incidente)`; si no puede, delega automáticamente al
  siguiente eslabón de la cadena (`siguiente.manejar(...)`). También
  concentra la lógica común a todos los manejadores: validar la
  descripción y calcular el recargo por urgencia.
- `Manejadores concretos`: `ManejadorMotor`, `ManejadorElectrico`,
  `ManejadorCarroceria`, `ManejadorNeumaticos`. Cada uno solo conoce su
  propio tipo de incidente, su costo base y sus mensajes.
- `ManejadorGeneral`: manejador de respaldo (catch-all) que cierra la
  cadena y equivale a la rama `else` del código original (atención
  general para tipos no reconocidos).
- `GestorIncidentes`: ahora solo arma la cadena en su constructor
  (Motor → Eléctrico → Carrocería → Neumáticos → General) y delega el
  procesamiento a `cadenaManejadores.manejar(incidente)`.

Beneficio principal: para agregar un nuevo tipo de incidente ya no es
necesario tocar `GestorIncidentes` ni ningún manejador existente; basta con
crear una nueva clase que extienda `ManejadorIncidente` e insertarla en la
cadena (principio abierto/cerrado — Open/Closed Principle).

## 2. Code smells detectados

Los siguientes 5 code smells (se pedían mínimo 4) corresponden a
categorías del catálogo de Refactoring.Guru:

1. Long Method
   Clase Original`GestorIncidentes.procesarIncidente`.
   Por qué es un problema?
   El método tenía más de 10 líneas, concentrando la lógica completa de 4 tipos de incidente distintos:
   - Validación
   - Cálculo de costos
   - Logging
   - Construcción y envío de mensaje de resultado.


2. Duplicate Code
   Las cuatro ramas `if`/`else if` (`MOTOR`, `ELECTRICO`,
   `CARROCERIA`, `NEUMATICOS`) dentro de `procesarIncidente`.
   ¿Por qué es un problema?
   Cada rama repetía la validación de descripción vacía/nula, el cálculo del recargo por urgencia (`* 1.5`) y la estructura de todo `System.out.println`.
   
3. Switch Statements
   La secuencia `if (tipo.equals("MOTOR")) ... else if
   (tipo.equals("ELECTRICO")) ...` en `procesarIncidente`. Por qué es
   un problema: es el equivalente a un `switch` disfrazado de
   `if/else`.
   Agregar un nuevo tipo de incidente exigía modificar
   directamente este método, en lugar de poder extender el
   comportamiento sin tocar código existente (viola el principio OCP).

4. Primitive Obsession
   El atributo `tipo` de la clase `Incidente` (código
   original), representado como un `String` suelto ("MOTOR", `"ELECTRICO"`, etc.) y comparado con `equals` en cada rama de `procesarIncidente`.
   
   ¿Por qué es un problema?
   String no valida errores de tipeo

5. Shotgun Surgery
   El porcentaje de recargo por urgencia (`* 1.5`), repetido
   en las cuatro ramas de tipo conocido.

   ¿Por qué es un problema?
   Un solo cambio de negocio (por ejemplo, subir el recargo de urgencia
   de 50% a 40%) requería hacer pequeñas ediciones dispersas en varios
   puntos del mismo método, en lugar de cambiar un único lugar.

## 3. Técnicas de refactorización aplicadas

1. Replace Conditional with Polymorphism responde al CodeSmell de Switch
   Statements

   La cadena de if/else if se sustituyó por una jerarquía
   de clases (ManejadorIncidente y sus subclases como ManejadorElectrico o ManejadorMotor) donde cada una decide
   por sí misma si puede procesar el incidente (puedeManejar).


2. Extract Class responde al CodeSmell Long Method.

   Cada rama del método original se extrajo a su propia clase (`ManejadorMotor`,
   `ManejadorElectrico`, `ManejadorCarroceria`, `ManejadorNeumaticos`,
   `ManejadorGeneral`), cada una con una única responsabilidad y fácil de
   probar de forma aislada.

3. Extract Method + Consolidate Duplicate Conditional Fragments responde al CodeSmell Duplicate Code y a Shotgun Surgery. 

   La validación de (`validarDescripcion`) y el cálculo del costo con recargo por urgencia (`calcularCostoConRecargo`) se extrajeron como métodos protegidos en la
   clase base `ManejadorIncidente`, los cuales son reutilizados por las subclases
   en lugar de repetirse en cada una. Esta misma extracción resuelve de
   paso el smell Shotgun Surgery: el porcentaje de recargo ahora vive
   en un único lugar (`RECARGO_URGENCIA`), asi evitamos realizar cambios en varias clases.

4. Replace Type Code with Enum responde al smell *Primitive
   Obsession.
   Se creó el enum `TipoIncidente` (`MOTOR`, `ELECTRICO`, `CARROCERIA`, `NEUMATICOS`, `DESCONOCIDO`) y `Incidente.getTipo()` lo
   devuelve en lugar de un `String`. Cada manejador compara con `==`
   contra una constante del enum (`incidente.getTipo() ==
   TipoIncidente.MOTOR`) en vez de `String.equals(...)`. El compilador
   ahora impide construir un incidente con un tipo inválido.

5. Introduce Template Method — responde al smell Long Method ySwitch Statements.
   El método final `manejar(Incidente)` en `ManejadorIncidente` define el flujo común de la cadena (verificar si puede manejar entonces procesar, o delegar al siguiente), mientras que cada subclase concreta solo implementa `puedeManejar` y `procesar`.

## 4. Pruebas unitarias

Se agregaron/ampliaron dos clases de prueba, con un total de 22 pruebas
(11 por clase, superando el mínimo de 8 solicitado) y usando 7 tipos
distintos de comprobaciones (`assertEquals`, `assertTrue`, `assertFalse`,
`assertNotNull`, `assertNull`, `assertSame`, `assertThrows`,
`assertDoesNotThrow`, `assertAll`):

- `GestorIncidentesTest`
   (pruebas de integración de la cadena completa): 
   Procesamiento correcto de cada tipo de incidente (urgente y no urgente), derivación a atención general para `TipoIncidente.DESCONOCIDO`,
   lanzamiento de excepción ante descripción vacía/nula en tipos
   conocidos, ausencia de excepción para tipo desconocido sin descripción,
   y verificación agrupada (`assertAll`) de que el resultado de cada tipo
   contenga el nombre correcto.

- `ManejadorIncidenteChainTest` 
   (pruebas unitarias del patrón):
   verifica que cada manejador reconozca (`puedeManejar`) únicamente su
   propio `TipoIncidente`, que la delegación al siguiente manejador
   funcione (`setSiguiente` / `manejar`), que el manejador general sea
   siempre catch-all, que la cadena devuelva un mensaje de respaldo si
   nadie la maneja, y que el cálculo de costos y validaciones funcione a
   nivel de cada manejador individual.

Todas las pruebas originales como `procesaIncidenteDeMotorNoUrgente`,
`procesaIncidenteDeMotorUrgente`, `procesaIncidenteElectricoNoUrgente`,
`procesaIncidenteDeCarroceriaUrgente`) fueron adaptadas para utilizar `TipoIncidente.MOTOR` en vez de la cadena `"MOTOR"`, consecuencia de la corrección de Primitive Obsession

La refactorización mantuvo el comportamiento del sistema.

## Se ejecuta de la siguiente forma

```bash
mvn test
```
