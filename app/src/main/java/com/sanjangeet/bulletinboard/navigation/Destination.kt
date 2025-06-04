package com.sanjangeet.bulletinboard.navigation

sealed class Destination {
    object Login : Destination()
    object Signup : Destination()
    object SignupVerifyOTP : Destination()
    object Main : Destination()
    object Search : Destination()
    object Settings : Destination()
    object Groups : Destination()
    object GroupPosts : Destination()
    object EditGroup : Destination()
}
