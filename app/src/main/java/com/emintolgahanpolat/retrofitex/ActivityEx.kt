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
fun Context.toast(text: CharSequence?): Toast {
    return Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT)
}

fun Context.showToast(text: CharSequence?) {
    toast(text).show()
}



fun Activity.createLoadingDialog() : Dialog {
    val alertDialog = Dialog(this)
    alertDialog.setContentView(R.layout.loading_layout)
    return alertDialog
}




fun ImageView.base64(base64String: String) {
    val imageBytes = Base64.decode(base64String, Base64.DEFAULT)
    val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    this.setImageBitmap(decodedImage)
}