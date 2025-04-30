# Conversor de Moneda MXN

Este proyecto es una aplicación de escritorio en Java (Swing) que convierte pesos mexicanos (MXN) a dólares (USD), euros (EUR) y yenes japoneses (JPY), utilizando tasas de cambio **en tiempo real** obtenidas desde la API pública de [AwesomeAPI](https://economia.awesomeapi.com.br/).

## Características

- Conversión dinámica al escribir el monto o cambiar la moneda.
- Tasas actualizadas desde Internet (no valores fijos).
- Interfaz simple y moderna usando Java Swing.
- Cálculo especial para conversión MXN → JPY vía USD.

## Captura

![Captura de pantalla](captura.png) <!-- Puedes agregar la imagen real cuando la tengas -->

## Dependencias

- Java 8 o superior
- [org.json (JSON-java)](https://github.com/stleary/JSON-java)
- Apache Ant

## Estructura del proyecto

```
ConversorMoneda/
├── src/
│   └── ConversorMoneda.java
├── lib/
│   └── json-20240303.jar
├── build/
├── build.xml
└── README.md
```

## Compilación y ejecución

### 1. Clona el repositorio
```bash
git clone https://github.com/tu_usuario/conversor-mxn.git
cd conversor-mxn
```

### 2. Coloca la librería `json-20240303.jar` en la carpeta `lib/`
Descárgala desde:  
[https://repo1.maven.org/maven2/org/json/json/20240303/json-20240303.jar](https://repo1.maven.org/maven2/org/json/json/20240303/json-20240303.jar)

### 3. Compila y ejecuta con Ant
```bash
ant run
```

> Si estás en Windows, asegúrate de tener correctamente configuradas las variables de entorno `JAVA_HOME` y `ANT_HOME`.

## Licencia

Este proyecto está disponible bajo la licencia MIT. Consulta el archivo `LICENSE` para más información.

---

Desarrollado por [Tu Nombre o Usuario].
