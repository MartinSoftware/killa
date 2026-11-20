package com.killa.app.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

// ============================================================
// Dimensiones Killa — espaciados, radios y elevaciones.
// Mapeo 1:1 de las variables CSS del mockup.
// ============================================================

object KillaSpacing {
    val xs = 4.dp
    val sm = 8.dp
    val md = 16.dp
    val lg = 24.dp
    val xl = 32.dp
}

object KillaRadius {
    val sm = 10.dp
    val md = 16.dp
    val lg = 24.dp
    val pill = 999.dp

    val smShape = RoundedCornerShape(sm)
    val mdShape = RoundedCornerShape(md)
    val lgShape = RoundedCornerShape(lg)
    val pillShape = RoundedCornerShape(pill)
}

object KillaElevation {
    val sm = 1.dp
    val md = 4.dp
    val lg = 8.dp
}

object KillaSizes {
    val buttonHeight = 52.dp
    val textFieldHeight = 52.dp
    val iconSmall = 18.dp
    val iconMedium = 24.dp
    val iconLarge = 32.dp
    val avatarSmall = 40.dp
    val avatarLarge = 96.dp
    val stepDot = 32.dp
    val logoMark = 72.dp
    val logoMarkSmall = 48.dp
    val logoMarkTiny = 36.dp
    val tokenBoxHeight = 72.dp
}