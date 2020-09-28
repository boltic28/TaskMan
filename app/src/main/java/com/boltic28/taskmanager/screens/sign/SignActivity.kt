package com.boltic28.taskmanager.screens.sign

import android.app.Activity


class SignActivity: Activity() {


}

//companion object{
//    const val RC_SIGN_IN = 8008
//    const val TAG = "SignInActivity"
//}
//
//private var mFirebaseAuth: FirebaseAuth? = null
//private var mFirebaseUser: FirebaseUser? = null
//private var mUsername: String? = null
//private var mPhotoUrl: String? = null
//
//private var mSignInButton: SignInButton? = null
//private var mSignInClient: GoogleSignInClient? = null
//
//override fun onCreate(savedInstanceState: Bundle?) {
//    super.onCreate(savedInstanceState)
//    setContentView(R.layout.activity_sign)
//
//    mFirebaseAuth = FirebaseAuth.getInstance()
//    mFirebaseUser = mFirebaseAuth?.currentUser
//
//    sign_in_button.setOnClickListener {
//        signIn()
//    }
//}
//
//private fun signIn(){
//    val signInIntent = mSignInClient?.signInIntent
//    startActivityForResult(signInIntent, RC_SIGN_IN)
//}
//
//override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//    super.onActivityResult(requestCode, resultCode, data)
//
//    if (requestCode == RC_SIGN_IN) {
//        val task: Task<GoogleSignInAccount> =
//            GoogleSignIn.getSignedInAccountFromIntent(data)
//        try {
//            val account =
//                task.getResult(ApiException::class.java)
//            firebaseAuthWithGoogle(account!!)
//        } catch (e: ApiException) {
//            Log.w(TAG,"Google sign in failed", e)
//        }
//    }
//}
//
//private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
//    Log.d(TAG, "firebaseAuthWithGoogle:" + acct.id)
//    val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
//    mFirebaseAuth!!.signInWithCredential(credential)
//        .addOnCompleteListener(this) { task ->
//            Log.d(
//                TAG,
//                "signInWithCredential:onComplete:" + task.isSuccessful
//            )
//
//            if (!task.isSuccessful) {
//                Log.w(TAG, "signInWithCredential", task.exception)
//                Toast.makeText(
//                    this@SignActivity, "Authentication failed.",
//                    Toast.LENGTH_SHORT
//                ).show()
//            } else {
//                startActivity(Intent(this@SignActivity, MainActivity::class.java))
//                finish()
//            }
//        }
//}