package com.easyflow.network.rest

import android.widget.Toast
import com.easyflow.BuildConfig
import com.easyflow.activities.SignInActivity
import com.easyflow.cache.UserKey
import com.easyflow.cache.LastResopnse
import com.easyflow.models.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestApiManager {

    fun signIn(userInfo: User, signInActivity: SignInActivity){
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.signIn(userInfo).enqueue(
            object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if(response.code() == 200) {
                        UserKey.key = response.headers()["Authorization"]
                    }
                    else{
                        UserKey.key = null
                        Toast.makeText(signInActivity,
                            "invalid username or password ${if(BuildConfig.DEBUG) "error code: "+ response.code() else "."}",
                            Toast.LENGTH_LONG)
                            //.show()
                        LastResopnse.code = response.code()
                        LastResopnse.msg = response.body()

                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    UserKey.key = null
                    Toast.makeText(signInActivity,
                        "couldn't connect to the server.",
                        Toast.LENGTH_LONG)
                        .show()
                    //throw kotlin.Exception("exception1")
                }

            }
        )
       /* val response = retrofit.signIn(userInfo).execute()
        if(response.code() == 200)
            Toast.makeText(signInActivity, "key = ${response.headers()["Authorization"]}", Toast.LENGTH_LONG).show()
        else
            Toast.makeText(signInActivity, "error ${response.code()}", Toast.LENGTH_LONG).show()*/

    }
}