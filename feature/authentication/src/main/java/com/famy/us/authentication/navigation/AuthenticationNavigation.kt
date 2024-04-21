package com.famy.us.authentication.navigation

import com.famy.us.core.utils.navigation.Destination

/**
 * Object representing all destination possibles by the Authentication Flow.
 */
object AuthenticationNavigation {

    /**
     * THe route for the authentication navigation.
     */
    object ROUTE : Destination("authentication")

    /**
     * Destination for pre login screen.
     */
    object PreLogin : Destination("pre_login")

    /**
     * Destination for login screen.
     */
    object Login : Destination("login")

    /**
     * Destination for the create account flow.
     */
    object CreateAccount : Destination("create_account")

    /**
     * Destination for the forgot password flow.
     */
    object ForgotPassword : Destination("forgot_password")

    /**
     * Destination to insert the email to receive a code to recover the forgotten password.
     */
    class InsertEmailToRecover(
        email: String = "{email}",
    ) : Destination("insert_email_to_recover", email)

    object ReadQrCode : Destination("read_qr_code")
}
