package com.sanjangeet.bulletinboard.navigation

sealed class Destination {
    object Login : Destination()
    object Signup : Destination()
    object ResetPassword : Destination()
    object Main : Destination()
}
