package com.emintolgahanpolat.retrofitex

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.emintolgahanpolat.retrofitex.connection.ApiResult
import com.emintolgahanpolat.retrofitex.connection.RetrofitBuilder
import com.emintolgahanpolat.retrofitex.connection.enqueue
import com.emintolgahanpolat.retrofitex.data.AppPreferences
import com.emintolgahanpolat.retrofitex.data.getTokens
import com.emintolgahanpolat.retrofitex.model.User
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private lateinit var appPermission: AppPermission
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appPermission = AppPermission(this)

        loginDataLbl.text = AppPreferences.getTokens()

        refreshTokenBtn.setOnClickListener {
            refreshToken()
        }
        permissionBtn.setOnClickListener {
            appPermission.setPermission(AppPermission.Permission.CAMERA,AppPermission.Permission.READ_CONTACTS,AppPermission.Permission.WRITE_CONTACTS).check(listener = object :AppPermission.PermissionListener{
                override fun onPermissionGranted() {
                    showToast("İzin Verildi")
                }

                override fun onPermissionDenied(deniedPermissions: List<AppPermission.Permission>) {

                    if (deniedPermissions.find { x ->  x == AppPermission.Permission.CAMERA } != null){
                        alertDialog {
                            setTitle("Uyarı")
                            setMessage("Uygulamayı kullanabilmek için kamerra izni verilmesi gerrekiyor.")
                            setCancelable(false)
                            setNegativeButton("Hayır"){_,_ ->
                                exitProcess(0)
                            }
                            setPositiveButton("İzin Ver"){_,_->
                                appPermission.openSettings()
                            }
                        }
                    }

                }

            })
        }

        testServiceBtn.setOnClickListener {
            serviceDataLbl.text = ""

            fetchUserDetail{
                serviceDataLbl.text = "\n${it}\n"
            }


        }
        removeTokenBtn.setOnClickListener {
            AppPreferences.token = null
            showToast("Removed Token")
        }

        removeRefreshTokenBtn.setOnClickListener {
            AppPreferences.token = null
            AppPreferences.refreshToken = null
            showToast("Removed Refresh Token")
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        appPermission.onRequestPermissionsResult(requestCode,permissions,grantResults)
    }
    private fun fetchUserDetail(callback: (User?) -> Unit) {
        AppPreferences.user?.let(callback)
        RetrofitBuilder.instance.user().enqueue(this){
            AppPreferences.user = it.response
            it.response?.let(callback)
        }
    }

    private fun refreshToken() {
        RetrofitBuilder.instance.refreshToken("Bearer ${AppPreferences.refreshToken}")
                .enqueue(this) {
                    if (it.response != null) {
                        AppPreferences.refreshToken = it.response?.refreshToken
                        AppPreferences.token = it.response?.token
                        loginDataLbl.text =
                                "Token :\n ${it.response?.token}\n\nRefresh Token :\n${it.response?.refreshToken}"

                    } else {
                        it.error?.message?.let {
                            showToast(it)
                        }
                    }
                }
    }

}