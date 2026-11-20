# Killa — Banco Digital

Proyecto universitario Full Stack: banco digital con **app Android nativa**, **backend propio** e **integración con Hub Interbancario** para transferencias externas.

## Estado del proyecto

| Componente | Estado |
| --- | --- |
| Repositorio y estructura | ✅ Inicializado |
| App Android (Kotlin + Compose) | 🚧 En desarrollo |
| Backend Killa (Ktor + PostgreSQL) | ⏳ Pendiente |
| Integración Hub Interbancario | ⏳ Pendiente |

## Arquitectura general

```
┌─────────────────┐       HTTPS/JSON        ┌──────────────────┐
│   App Android   │ ──────────────────────► │  Backend Killa   │
│ (Kotlin+Compose)│ ◄────────────────────── │ (Ktor + Postgres)│
└─────────────────┘                         └────────┬─────────┘
                                                     │ OAuth2 Client Credentials
                                                     │ + firma digital
                                                     ▼
                                            ┌──────────────────┐
                                            │ Hub Interbancario│
                                            │ (FastAPI, externo)│
                                            └────────┬─────────┘
                                                     │
                                                     ▼
                                            ┌──────────────────┐
                                            │     DinoBit      │
                                            │   (otro banco)   │
                                            └──────────────────┘
```

## Estructura del monorepo

```
killa/
├── android/          # App móvil (Kotlin + Jetpack Compose)
├── backend/          # API REST (Kotlin + Ktor + PostgreSQL)
├── docs/
│   ├── arquitectura.md
│   ├── contrato-api.md
│   ├── convenciones.md
│   ├── diagramas/
│   └── decisiones/   # ADRs
└── README.md
```

## Requisitos

* **Android**: Android Studio Ladybug o superior, JDK 17, SDK API 26+
* **Backend** (futuro): JDK 17, PostgreSQL 15+

## Cómo ejecutar la app Android

1. Abre la carpeta `android/` en Android Studio (no la raíz del monorepo).
2. Crea `android/local.properties` (ignorado por git) con:

```properties
sdk.dir=/ruta/a/tu/Android/Sdk
killa.api.baseUrl=http://10.0.2.2:8080/
```

> **Nota:** `10.0.2.2` es la IP del host vista desde el emulador Android.

3. Ejecuta el módulo `app` en un emulador o dispositivo físico.

## Documentación

* Arquitectura
* Contrato de API
* Convenciones de código y git
* Decisiones de arquitectura (ADRs)
* Mockups de diseño (docs/mockups/)

## Autor

* **Martín Sebastian Martinez Palacios**
* Estudiante de Ingeniería de Software
* 2026