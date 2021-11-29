package com.example.selltez.storage

import android.content.Context


class SharedPrefManager(val mCtx: Context) {

    val isLoggedIn: Boolean
        get() {
            val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getString("empid", null) != null
        }

    val isSignup: Boolean
        get() {
            val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getString("empid", null) != null
        }

//    val user: User?
//        get() {
//            val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
//            return sharedPreferences.getString("status", null)?.let {
//                User(
//                    sharedPreferences.getString("empid", null)!!,
//                    sharedPreferences.getString("name", null)!!,
//                    sharedPreferences.getString("adhar", null)!!,
//                    sharedPreferences.getString("proof", null)!!,
//                    sharedPreferences.getString("mobile", null)!!,
//                    sharedPreferences.getString("password", null)!!,

//                    it
//                )
//            }
//        }


    fun saveUser(user: List<User>) {

        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString("empid",user.get(0).empid)
        editor.putString("name", user.get(0).name)
        editor.putString("adhar", user.get(0).adhar)
        editor.putString("proof", user.get(0).proof)
        editor.putString("mobile", user.get(0).mobile)
        editor.putString("password", user.get(0).password)
        editor.putString("status", user.get(0).status)
        editor.apply()
    }

    fun userDetails(user: List<AccountDetails>) {

        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString("empid2",user.get(0).empid)
        editor.putString("name2", user.get(0).name)
        editor.putString("adhar2", user.get(0).adhar)
        editor.putString("proof2", user.get(0).proof)
        editor.putString("mobile2", user.get(0).mobile)
        editor.putString("password2", user.get(0).password)
        editor.putString("status2", user.get(0).status)
        editor.apply()
        editor.commit()

    }

    fun clear() {
        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    companion object {
         val SHARED_PREF_NAME = "my_shared_preff"
        private var mInstance: SharedPrefManager? = null
        @Synchronized
        fun getInstance(mCtx: Context): SharedPrefManager {
            if (mInstance == null) {
                mInstance = SharedPrefManager(mCtx)
            }
            return mInstance as SharedPrefManager
        }
    }

}