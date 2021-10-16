package com.app.musicalbums.helpers

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.util.Log
import com.app.musicalbums.contracts.IView
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject


class Authenticator @Inject constructor(private val view: IView) {
    private val signInLauncher = view.registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { result ->  onSignInResult(result) }

    private val getSignInProviders = listOf(
        AuthUI.IdpConfig.GoogleBuilder().build(),
    )

    private val createSignInIntent: Intent = AuthUI.getInstance()
        .createSignInIntentBuilder()
        .setAvailableProviders(getSignInProviders)
        .build()

    val launchSignIn:()->Unit = { signInLauncher.launch(createSignInIntent) }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            val user = FirebaseAuth.getInstance().currentUser
            Log.i("tag",user?.displayName ?: "")
            view.onResult()

        } else {
           Log.i("tag","not ok")
           Log.i("tag",response?.error?.message.toString())
        }
    }

    val getUser : () -> FirebaseUser? = {
        val firebase = FirebaseAuth.getInstance()
        firebase.currentUser
    }


}