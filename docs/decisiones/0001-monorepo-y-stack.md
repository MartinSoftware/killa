# ADR 0001 — Monorepo y elección de stack

- **Fecha**: 2026-11-08
- **Estado**: Aceptado

## Contexto

Proyecto universitario Full Stack con dos componentes propios (app Android + backend Killa) y un componente externo ya existente (Hub Interbancario). Se necesita una organización que facilite la entrega, la documentación y el mantenimiento del contrato de API entre app y backend.

## Decisión

1. **Monorepo** con estructura `android/`, `backend/`, `docs/`.
2. **Backend**: Kotlin + Ktor + PostgreSQL.
3. **App**: Kotlin + Jetpack Compose + Hilt + Retrofit + Kotlinx Serialization + DataStore + Navigation Compose.
4. **Package base Android**: `com.killa.app`.
5. **Convenciones**: Conventional Commits, Kotlin DSL, ADRs para decisiones.

## Consecuencias

**Positivas**
- Un solo `git clone` para revisión.
- Contrato de API versionado junto a ambos consumidores.
- Reutilización de Kotlin y corrutinas entre app y backend (menor curva de aprendizaje).

**Negativas**
- El repo crece más rápido.
- El CI (si se añade) debe configurarse por carpeta (`android/` y `backend/` por separado).

## Alternativas consideradas

- **Multi-repo**: descartado por fricción para la entrega universitaria.
- **NestJS / Go / FastAPI** para backend: descartados por curva de aprendizaje o inconsistencia con el stack Android (ver conversación de diseño).