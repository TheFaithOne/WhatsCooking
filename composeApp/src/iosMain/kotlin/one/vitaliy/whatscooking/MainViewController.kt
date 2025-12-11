package one.vitaliy.whatscooking

import androidx.compose.ui.window.ComposeUIViewController
import one.vitaliy.whatscooking.di.initKoin

@Suppress("unused")
fun MainViewController() = ComposeUIViewController {
    initKoin()
    App()
}
