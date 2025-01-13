//package com.example.nsaai.Authentication
//
//import android.app.Activity
//
//
//
//import android.content.Context
//import android.content.Intent
//import com.facebook.AccessToken
//import com.facebook.CallbackManager
//import com.facebook.FacebookException
//import com.facebook.GraphRequest
//import com.facebook.login.LoginManager
//import com.facebook.login.LoginResult
//import com.google.firebase.auth.FacebookAuthProvider
//import com.google.firebase.auth.FirebaseAuth
//import kotlinx.coroutines.tasks.await
//
//class FacebookAuth(
//    private val context: Context,
//    private val callbackManager: CallbackManager,
//) {
//    private val auth = FirebaseAuth.getInstance()
//
//    fun signIn(onSuccess: (SignInResult) -> Unit, onError: (String?) -> Unit) {
//        LoginManager.getInstance().logInWithReadPermissions(
//            (context as Activity),
//            listOf("email", "public_profile")
//        )
//
//        LoginManager.getInstance().registerCallback(callbackManager, object : com.facebook.FacebookCallback<LoginResult> {
//            override fun onSuccess(result: LoginResult) {
//                val token = result.accessToken
//                val credential = FacebookAuthProvider.getCredential(token.token)
//
//                auth.signInWithCredential(credential)
//                    .addOnCompleteListener { task ->
//                        if (task.isSuccessful) {
//                            val user = auth.currentUser
//                            val userData = user?.let {
//                                UserData(
//                                    userId = it.uid,
//                                    username = it.displayName.toString(),
//                                    profilePictureUrl = it.photoUrl.toString()
//                                )
//                            }
//                            onSuccess(SignInResult(data = userData, errorMessage = null))
//                        } else {
//                            onError(task.exception?.message)
//                        }
//                    }
//            }
//
//            override fun onCancel() {
//                onError("Facebook sign-in canceled")
//            }
//
//            override fun onError(error: FacebookException?) {
//                onError(error?.message)
//            }
//        })
//    }
//
//    fun getSignedInUser(): UserData? {
//        return auth.currentUser?.run {
//            UserData(
//                userId = uid,
//                username = displayName.toString(),
//                profilePictureUrl = photoUrl.toString()
//            )
//        }
//    }
//
//    fun logOut() {
//        auth.signOut()
//        LoginManager.getInstance().logOut()
//    }
//}

package com.example.nsaai.Authentication

import android.app.Activity
import android.content.Context
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth

class FacebookAuth(
    private val context: Context,
    private val callbackManager: CallbackManager
) {
    private val auth = FirebaseAuth.getInstance()

    fun signIn(onSuccess: (SignInResult) -> Unit, onError: (String?) -> Unit) {
        LoginManager.getInstance().logInWithReadPermissions(
            (context as Activity),
            listOf("email", "public_profile")
        )

//        LoginManager.getInstance().registerCallback(callbackManager, object : com.facebook.FacebookCallback<LoginResult> {
//            override fun onSuccess(result: LoginResult) {
//                val token = result.accessToken
//                val credential = FacebookAuthProvider.getCredential(token.token)
//
//                auth.signInWithCredential(credential)
//                    .addOnCompleteListener { task ->
//                        if (task.isSuccessful) {
//                            val user = auth.currentUser
//                            val userData = user?.let {
//                                UserData(
//                                    userId = it.uid,
//                                    username = it.displayName.toString(),
//                                    profilePictureUrl = it.photoUrl.toString()
//                                )
//                            }
//                            onSuccess(SignInResult(data = userData, errorMessage = null))
//                        } else {
//                            onError(task.exception?.message)
//                        }
//                    }
//            }
//
//            override fun onCancel() {
//                onError("Facebook sign-in canceled")
//            }
//
//            override fun onError(error: FacebookException?) {
//                onError(error?.message)
//            }
//        })
    }

    fun getSignedInUser(): UserData? {
        return auth.currentUser?.run {
            UserData(
                userId = uid,
                username = displayName.toString(),
                profilePictureUrl = photoUrl.toString()
            )
        }
    }

    fun logOut() {
        auth.signOut()
        LoginManager.getInstance().logOut()
    }
}

