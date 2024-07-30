package com.example.myapplication.core.util.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.googlefonts.Font
import com.example.myapplication.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val poppinsFontFamily = FontFamily(
    Font(
        googleFont = GoogleFont("Poppins"),
        fontProvider = provider,
    )
)

val avenirFontFamily = FontFamily(
    androidx.compose.ui.text.font.Font(R.font.avenir_black, FontWeight.Black),
    androidx.compose.ui.text.font.Font(R.font.avenir_heavy, FontWeight.ExtraBold),
)

val AppTypography = Typography()

