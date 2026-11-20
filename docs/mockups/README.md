# Mockups de Killa

Diseño visual validado **antes** de la implementación en Compose.
Este archivo HTML es la fuente de verdad del diseño: colores, tipografía,
espaciados, flujos y estados de cada pantalla.

## Cómo visualizarlo

Abre `killa-mockups.html` en cualquier navegador (Chrome, Edge, Firefox).
No requiere servidor ni dependencias.

Arriba hay dos controles:
- **Selector de pantalla**: navega entre las 8 vistas
- **Toggle Light/Dark**: valida ambos temas

## Pantallas incluidas

| # | Pantalla | Estados |
|---|---|---|
| 1 | Splash | loading |
| 2 | Login | normal |
| 3 | Registro | 4 pasos + éxito |
| 4 | Home | normal |
| 5 | Historial | filtros (Todos/Ingresos/Egresos) |
| 6 | Transferir (unificado) | selector interno/externo → formulario → token → polling → éxito/error |
| 7 | Perfil | normal + diálogo logout |
| 8 | (Transferencia externa) | incluida dentro de "Transferir" con polling visual de 3 pasos |

## Variables de diseño (fuente de verdad para Compose)

Todas las variables están en `:root` del CSS. Al traducir a Compose:

| Variable CSS | Destino en Compose |
|---|---|
| `--killa-primary` | `Color.kt` → `KillaPrimary` |
| `--killa-primary-dark` | `Color.kt` → `KillaPrimaryDark` |
| `--killa-primary-soft` | `Color.kt` → `KillaPrimarySoft` |
| `--killa-secondary` | `Color.kt` → `KillaSecondary` |
| `--killa-error` | `Color.kt` → `KillaError` |
| `--killa-bg`, `--killa-surface`, etc. | `Color.kt` (light/dark schemes) |
| `--killa-radius-sm/md/lg` | `Dimens.kt` → `KillaRadius` |
| `--killa-space-xs/sm/md/lg/xl` | `Dimens.kt` → `KillaSpacing` |
| `--killa-font` | `Type.kt` (fontFamily default) |

## Decisión de diseño clave: token de seguridad

El flujo de transferencia simula una app bancaria real:

1. El usuario **nunca ve el token** (se envía por SMS/email en producción).
2. El campo se **auto-completa a los 2s** (simula detección automática).
3. El campo es **solo lectura** — no editable.
4. El botón "Confirmar" se **habilita solo** cuando llega el token.
5. El token **expira en 60s** y el botón se deshabilita.

En Compose, esto se implementará con `StateFlow<TokenState>` en el ViewModel,
donde `TokenState` es una `sealed interface` con `Esperando`, `Recibido`, `Expirado`.

## Notas para la implementación

- El mockup usa `setTimeout` para simular latencia. En Compose, se usará `delay()` en corrutinas dentro de `viewModelScope`.
- El polling de transferencia externa usa 3 pasos visuales de 2s cada uno. En Compose, se conectará a `GET /transfers/{id}` del Hub cada 3-5s.
- Los datos son estáticos (`FakeData`). En Compose, vendrán de `Repository` + `Result<T>`.
- El botón "Simular fallo" es solo para demo — no va a producción.