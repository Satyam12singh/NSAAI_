//package com.example.nsaai.Authentication
//
//import android.content.Context
//import android.content.Intent
//import android.content.IntentSender
//import com.example.nsaai.R
//import com.google.android.gms.auth.api.identity.BeginSignInRequest
//import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
//import com.google.android.gms.auth.api.identity.SignInClient
//import com.google.firebase.Firebase
//import com.google.firebase.auth.GoogleAuthProvider
//import com.google.firebase.auth.auth
//import kotlinx.coroutines.tasks.await
//import java.util.concurrent.CancellationException
//
//class GoogleAuth (
//    private val context:Context,
//    private val oneTapClient: SignInClient,
//    private val signInRequest: BeginSignInRequest,
//    private val signUpRequest: BeginSignInRequest
//) {
//    private val auth = Firebase.auth
//
//    suspend fun signIn():IntentSender? {
//        val result = try {
//            oneTapClient.beginSignIn(
//                buildSignInRequest()
//            ).await()
//        } catch (e: Exception) {
//            e.printStackTrace()
//            if(e is CancellationException) throw e
//            null
//
//        }
//        return result?.pendingIntent?.intentSender
//
//    }
//
//    suspend fun SignInWithIntent(intent: Intent): SignInResult{
//        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
//        val googleIdToken = credential.googleIdToken
//        val googleCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
//
//        return try {
//
//            val user = auth.signInWithCredential(googleCredential).await().user
//            SignInResult(
//                data = user?.run {
//                    UserData(
//                        userId = uid,
//                        username = displayName.toString(),
//                        profilePictureUrl = photoUrl.toString()
//                    )
//                },
//                errorMessage = null
//            )
//
//        }catch (e : Exception){
//            e.printStackTrace()
//            if(e is CancellationException) throw e
//            SignInResult(
//                data = null,
//                errorMessage = e.message
//            )
//        }
//    }
//
//
//    suspend fun SignOut(){
//        try {
//            oneTapClient.signOut().await()
//            auth.signOut()
//
//        }catch (e : Exception){
//            e.printStackTrace()
//            if ( e is CancellationException) throw e
//        }
//    }
//
//    fun getSignedInUser(): UserData? = auth.currentUser?.run {
//        UserData(
//            userId = uid,
//            username = displayName.toString(),
//            profilePictureUrl = photoUrl.toString()
//        )
//    }
//
//    private fun buildSignInRequest():BeginSignInRequest{
//        return BeginSignInRequest.Builder()
//            .setGoogleIdTokenRequestOptions(
//                GoogleIdTokenRequestOptions.builder()
//                    .setSupported(true)
//                    .setFilterByAuthorizedAccounts(false)
//                    .setServerClientId(context.getString(R.string.default_web_client_id))
//                    .build()
//            )
//            .setAutoSelectEnabled(true)
//            .build()
//    }
//
//}

package com.example.nsaai.Authentication

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.example.nsaai.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException

class GoogleAuth(
    private val context: Context,
    private val oneTapClient: SignInClient,
    private val signInRequest: BeginSignInRequest
) {
    private val auth = FirebaseAuth.getInstance()

    suspend fun signIn(): IntentSender? {
        val result = try {
            oneTapClient.beginSignIn(signInRequest).await()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            null
        }
        return result?.pendingIntent?.intentSender
    }

    suspend fun signInWithIntent(intent: Intent): SignInResult {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredential = GoogleAuthProvider.getCredential(googleIdToken, null)

        return try {
            val user = auth.signInWithCredential(googleCredential).await().user
            SignInResult(
                data = user?.let {
                    UserData(
                        userId = it.uid,
                        username = it.displayName.toString(),
                        profilePictureUrl = it.photoUrl.toString()
                    )
                },
                errorMessage = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            SignInResult(data = null, errorMessage = e.message)
        }
    }

    suspend fun signOut() {
        try {
            oneTapClient.signOut().await()
            auth.signOut()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }

    fun getSignedInUser(): UserData? = auth.currentUser?.run {
        UserData(
            userId = uid,
            username = displayName.toString(),
            profilePictureUrl = photoUrl.toString()
        )
    }
}
