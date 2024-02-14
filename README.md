# Java JDK17 TDD Bitcoin Converter

El siguiente repositorio contiene código desarrollado usando prácticas TDD(Test Driven Development). El proyecto implementa una librería Java(JDK 17) el cual interactúa con la api del [índice de precios del Bitcoin - Coindesk](https://www.coindesk.com/coindesk-api)

# Objetivos

- Configurar un proyecto inicial con el primer set de pruebas unitarias usando JUnit5 y la anotación ```@Test``` utilizando una implementación falsa de la API del índice de precios del Bitcoin.

- Refactorizar las pruebas unitarias y el código base para hacer request a la API de Coindesk usando Apache HttpClient.

- Usar Mockito para refactorizar las pruebas unitarias simulando las llamadas Http de Apache HttpClient a la API de Coindesk, usar la anotacion ```@BeforeEach```.

- Agregar pruebas unitarias con condiciones de error.

- Agregar un workflow en GitHub Action para ejecutar automatizaciones de compilaciones y testeos con eventos push, el cual genera un artefacto jar.

- Refactorizar el workflow para agregar una acción de release.

- Crear un proyecto cliente que use el artefacto jar generado por el GitHub action

## Requisitos previos

JDK17 y Maven3 se requieren para este proyecto.

Para saber las versiones, ejecutar:

* JDK17

```
java -version

java version "17.0.9" 2023-10-17 LTS
...
```

* Maven3

```
mvn --version

Apache Maven 3.9.5 (57804ffe001d7215b5e7bcb531cf83df38f93546)
...
```

# Herramientas y Frameworks

Las siguientes herramientas y frameworks han sido usados para el desarrollo TDD:

* Maven: se utiliza para automatizar y gestionar proyectos de construcción y pruebas.
* JUnit5: Marco de pruebas unitarias
* Mockito: Librería usada para crear mocks(simulaciones) de dependencias externas.
* GitHub Actions: Usada para proveer características CICD para automatizar builds y tests

# Bitcoin Converter Library

Este proyecto tiene los siguientes dos métodos:

El método ```getExchangeRate``` devuelve el valor del Bitcoin para las divisas(USD, GBP, EUR):

```
public double getExchangeRate(Currency currency)
```

El método ```convertBitcoins``` devuelve el valor del dólar de la moneda dada(USD, GBP, EUR) y el número de Bitcoins.

```
public double convertBitcoins(Currency currency, double coins)
```

Para construir la librería ejecutar el comando:

```
mvn clean compile test package
```

# Bitcoin Converter Client

Este proyecto hace uso de una aplicación basado en consola para importar y usar la librería Bitcoin Converter library. Ver más en [Client-TDD-Java-BitcoinConverter](https://github.com/marrsd/Client-TDD-Java-BitcoinConverter)


# GitHub Action - CICD

GitHub Action Workflow usado:

### build.yml

```
name: Java CI
on:
  push:
    tags:
      - '*'

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
      - uses: actions/checkout@v4

      - name: JDK 17 Install
        uses: actions/setup-java@v1
        with:
          java-version: 17

      - name: Maven Build
        run: |
          mvn versions:set -DremoveSnapshot
          mvn -B clean package test
          mkdir release
          cp target/*.jar release

      - name: Artifact Upload
        uses: actions/upload-artifact@v2
        with:
          name: Package
          path: release
        
      - name: Make Release
        uses: softprops/action-gh-release@v1
        with:
          files:
            target/*.jar
        env:
          GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
```

