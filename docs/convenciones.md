# Convenciones del proyecto Killa

## Git

### Ramas
- `main`: estable, siempre compilable.
- `feature/<nombre>`: nueva funcionalidad (ej. `feature/login`).
- `fix/<nombre>`: corrección de bug (ej. `fix/polling-timeout`).
- `docs/<nombre>`: solo documentación.

### Commits (Conventional Commits)
Formato: `<tipo>(<alcance>): <descripción corta>`

Tipos:
- `feat`: nueva funcionalidad
- `fix`: corrección de bug
- `docs`: documentación
- `chore`: configuración, dependencias, build
- `refactor`: cambio interno sin alterar comportamiento
- `test`: pruebas
- `style`: formato (no lógica)

Ejemplos:
- `feat(login): implementar pantalla de login con validación de PIN`
- `chore(android): configurar Hilt y Retrofit`
- `docs(api): documentar contrato de /auth/login`

## Código Kotlin (Android)

- **Kotlin DSL** para Gradle (`build.gradle.kts`, `libs.versions.toml`).
- **Package base**: `com.killa.app`
- **Nombres**:
  - Clases: `PascalCase` (`LoginViewModel`)
  - Funciones y variables: `camelCase` (`iniciarSesion`)
  - Constantes: `UPPER_SNAKE_CASE` (`MAX_INTENTOS_PIN`)
  - Composables: `PascalCase` (`LoginScreen`)
- **Idioma**: identificadores en español para el dominio bancario (`cuenta`, `saldo`, `transferencia`), inglés para infraestructura técnica (`repository`, `viewModel`, `useCase`).
- **Corrutinas**: nunca `GlobalScope`. Usar `viewModeslScope` o `lifecycleScope`.
- **Estado**: `StateFlow` + `sealed interface` para estados de UI.

## Documentación

- Toda decisión arquitectónica relevante se registra como **ADR** en `docs/decisiones/`.
- Formato ADR: `NNNN-titulo.md` con secciones Contexto / Decisión / Consecuencias.
- El contrato de API se mantiene en `docs/contrato-api.md` y debe coincidir con el backend.

## Secretos

- Nunca commitear: `.env`, `local.properties`, keystores, tokens.
- Los archivos de ejemplo (`.env.example`) sí van al repo.