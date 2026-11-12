# ArenaGT Lite - Lab 4 Polimorfismo

Aplicacion de consola en Java para gestionar equipos, encuentros de futbol sala y baloncesto, exhibiciones, calendario y tabla de posiciones.

## Compilar y ejecutar

Desde la carpeta `ArenaGT_Lite`:

```powershell
javac -encoding UTF-8 -d bin src\*.java
java -cp bin Principal
```

En Linux/macOS:

```bash
javac -encoding UTF-8 -d bin src/*.java
java -cp bin Principal
```

Requiere un JDK instalado. No necesita bibliotecas externas.

## Menu

1. Registrar equipo
2. Crear encuentro
3. Crear exhibicion
4. Programar o reprogramar evento
5. Registrar resultado
6. Tabla de posiciones
7. Calendario
8. Ver todos los eventos (incluidos los no programados)
0. Salir

Al seleccionar eventos, use el indice mostrado en la opcion 8 o en el listado previo a la operacion. La fecha se escribe como `AAAA-MM-DD` y la hora como un entero de 0 a 23.

## Diseño

- `Programable` y `Puntuable` son contratos separados.
- `Encuentro` es abstracta y aplica las reglas mediante metodos abstractos sobrescritos en `FutbolSala` y `Baloncesto`.
- `Equipo` implementa `Comparable<Equipo>` (puntos descendentes y nombre alfabetico).
- `Copa` implementa `Iterable<Programable>`; el criterio cronologico se define una sola vez.
- `ControladorCopa` coordina operaciones; `VistaCopa` administra la consola; `Principal` inicia el sistema.

### Nota de congruencia con el UML

Se conservaron las firmas publicas definidas en las tablas y UML. El controlador incorpora **un atributo privado auxiliar** `eventosRegistrados` para conservar el orden de seleccion de eventos, incluidos los no programados. Esto es necesario porque `Copa.iterator()` solo devuelve eventos programados, segun el contrato del calendario. **Antes de entregar el UML definitivo, agregue este atributo privado a `ControladorCopa`** si el profesor exige coincidencia literal de atributos. No se anadieron metodos publicos a las clases del diseno.

## Datos de inicio

Se cargan 4 equipos de 2 empresas, 4 encuentros de ambos deportes, 2 resultados (incluido empate de futbol sala), y 1 exhibicion programada. La tabla inicial permite comprobar el desempate alfabetico.

## Pruebas recomendadas

- Tabla inicial: Luna 2; Faro, Rio y Sol 1, en ese orden.
- Calendario inicial: 5 eventos, con exhibicion mezclada cronologicamente.
- Crear exhibicion, programarla y reprogramarla.
- Rechazar sede ocupada y encuentro de equipo ocupado a la misma fecha/hora.
- Rechazar empate de baloncesto, marcador negativo, resultado duplicado y resultado sin programar.
- Verificar que una operacion rechazada no cambia puntos ni programacion.
