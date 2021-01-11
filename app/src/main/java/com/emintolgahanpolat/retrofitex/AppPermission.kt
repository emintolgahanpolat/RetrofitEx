package com.emintolgahanpolat.retrofitex

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

/**
<uses-permission android:name="android.permission.INTERNET" />

<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>

<uses-permission android:name="android.permission.CAMERA"/>

<uses-permission android:name="android.permission.READ_CALENDAR"/>
<uses-permission android:name="android.permission.WRITE_CALENDAR"/>

<uses-permission android:name="android.permission.READ_CONTACTS"/>
<uses-permission android:name="android.permission.WRITE_CONTACTS"/>
<uses-permission android:name="android.permission.GET_ACCOUNTS"/>

<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>

<uses-permission android:name="android.permission.RECORD_AUDIO"/>

<uses-permission android:name="android.permission.READ_PHONE_STATE"/>
<uses-permission android:name="android.permission.READ_PHONE_NUMBERS"/>
<uses-permission android:name="android.permission.CALL_PHONE"/>
<uses-permission android:name="android.permission.ANSWER_PHONE_CALLS"/>
<uses-permission android:name="android.permission.READ_CALL_LOG"/>
<uses-permission android:name="android.permission.WRITE_CALL_LOG"/>
<uses-permission android:name="android.permission.ADD_VOICEMAIL"/>
<uses-permission android:name="android.permission.USE_SIP"/>

<uses-permission android:name="android.permission.BODY_SENSORS"/>

<uses-permission android:name="android.permission.SEND_SMS"/>
<uses-permission android:name="android.permission.RECEIVE_SMS"/>
<uses-permission android:name="android.permission.READ_SMS"/>
<uses-permission android:name="android.permission.RECEIVE_WAP_PUSH"/>
<uses-permission android:name="android.permission.RECEIVE_MMS"/>
*/
class AppPermission (private val activity: Activity) {

    enum class Permission(val value : String){
        READ_EXTERNAL_STORAGE ( Manifest.permission.READ_EXTERNAL_STORAGE),
        WRITE_EXTERNAL_STORAGE ( Manifest.permission.WRITE_EXTERNAL_STORAGE),
        CAMERA ( Manifest.permission.CAMERA),
        READ_CALENDAR ( Manifest.permission.READ_CALENDAR),
        WRITE_CALENDAR ( Manifest.permission.WRITE_CALENDAR),
        READ_CONTACTS ( Manifest.permission.READ_CONTACTS),
        WRITE_CONTACTS ( Manifest.permission.WRITE_CONTACTS),
        GET_ACCOUNTS ( Manifest.permission.GET_ACCOUNTS),
        ACCESS_FINE_LOCATION ( Manifest.permission.ACCESS_FINE_LOCATION),
        ACCESS_COARSE_LOCATION ( Manifest.permission.ACCESS_COARSE_LOCATION),
        RECORD_AUDIO ( Manifest.permission.RECORD_AUDIO),
        READ_PHONE_STATE ( Manifest.permission.READ_PHONE_STATE),
        READ_PHONE_NUMBERS ( Manifest.permission.READ_PHONE_NUMBERS),
        CALL_PHONE ( Manifest.permission.CALL_PHONE),
        ANSWER_PHONE_CALLS ( Manifest.permission.ANSWER_PHONE_CALLS),
        READ_CALL_LOG ( Manifest.permission.READ_CALL_LOG),
        WRITE_CALL_LOG ( Manifest.permission.WRITE_CALL_LOG),
        ADD_VOICEMAIL ( Manifest.permission.ADD_VOICEMAIL),
        USE_SIP ( Manifest.permission.USE_SIP),
        BODY_SENSORS ( Manifest.permission.BODY_SENSORS),
        SEND_SMS ( Manifest.permission.SEND_SMS),
        RECEIVE_SMS ( Manifest.permission.RECEIVE_SMS),
        READ_SMS ( Manifest.permission.READ_SMS),
        RECEIVE_WAP_PUSH ( Manifest.permission.RECEIVE_WAP_PUSH),
        RECEIVE_MMS ( Manifest.permission.RECEIVE_MMS)
    }

    private var REQUESTCODE = -1
    private var mPermissions = mutableListOf<String>()
    private val needPermissionList = mutableListOf<String>()



    fun setPermission(vararg permissions: Permission) = apply {
        mPermissions = permissions.map { x -> x.value }.toMutableList()
    }


    fun check(requestCode: Int = -1 , listener: PermissionListener? = null) {
        REQUESTCODE = requestCode
        mPermissionListener = listener
        if (REQUESTCODE == -1) {
            REQUESTCODE = (1..100).random()
        }
        askPermission()
    }


    private fun askPermission() {
        for (item in mPermissions) {
            if (ContextCompat.checkSelfPermission(
                    activity,
                    item
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                needPermissionList.add(item)
            }
        }

        if (needPermissionList.isEmpty()) {
            mPermissionListener?.onPermissionGranted()
        } else {

            ActivityCompat.requestPermissions(
                activity,
                mPermissions.map { it }.toTypedArray(),
                REQUESTCODE
            )
        }
    }

    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUESTCODE) {
            if (grantResults.filter { it == -1 }.count() > 0) {
                val deniedList = mutableListOf<Permission>()
                grantResults.forEachIndexed { index, i ->
                    if (i == -1) {
                        Permission.values().find { it.value == permissions[index] }?.let {
                            deniedList.add(it)
                        }
                    }
                }
                mPermissionListener?.onPermissionDenied(deniedList)
            } else {
                mPermissionListener?.onPermissionGranted()
            }

        }

    }

    private var mPermissionListener: PermissionListener? = null

    interface PermissionListener {
        fun onPermissionGranted()
        fun onPermissionDenied(deniedPermissions: List<Permission>)
    }

    fun openSettings(){
        val intent = Intent()
        intent.action = ACTION_APPLICATION_DETAILS_SETTINGS
        val uri: Uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
        intent.data = uri
        activity.startActivity(intent)
    }

}