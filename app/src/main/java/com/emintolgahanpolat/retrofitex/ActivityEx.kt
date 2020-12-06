package com.emintolgahanpolat.retrofitex

import android.app.Activity
import android.app.Dialog
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog


fun Activity.startActivityIntent(cls: Class<*>?){
    this.startActivity(Intent(this,cls))
}
fun Activity.finishAndStartActivityIntent(cls: Class<*>?){
    this.startActivity(Intent(this,cls))
    this.finish()
}
fun Activity.toast(text: CharSequence?): Toast {
    return Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT)
}

fun Activity.showToast(text: CharSequence?) {
    toast(text).show()
}


inline fun Activity.alertDialog(body: AlertDialog.Builder.() -> AlertDialog.Builder): AlertDialog {
    return AlertDialog.Builder(this)
        .body()
        .show()
}


fun Activity.showOnUI(alertDialog: Dialog){
    runOnUiThread {
        alertDialog.show()
    }
}
fun Activity.dismissOnUI(alertDialog: Dialog){
    runOnUiThread {
        alertDialog.dismiss()
    }
}

fun Activity.createLoadingDialog() : Dialog {
    val alertDialog = Dialog(this)
    alertDialog.setContentView(R.layout.loading_layout)
    return alertDialog
}


object AppToast {
    private lateinit var context: Context
    fun init(context: Context) {
        this.context = context
    }

    fun show(text: CharSequence?) {
        Toast.makeText(context, "App Toast $text", Toast.LENGTH_SHORT).show()
    }


}


fun ImageView.base64(base64String: String) {
    val imageBytes = Base64.decode(base64String, Base64.DEFAULT)
    val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    this.setImageBitmap(decodedImage)
}