package com.example.androidtask.util

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.WindowManager
import com.example.androidtask.R
import com.ms_square.etsyblur.BlurDialogFragment

class BlurDialog() : BlurDialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog =  Dialog(requireActivity()).also{ it.setContentView(R.layout.progress_layout)}
      /*  val imageView  = dialog.findViewById<ImageView>(R.id.image)
        /*val options = RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round) */

        Glide.with(this).load(R.drawable.prescription_blur_image)/*.apply(options)*/.into(imageView)
        dialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));*/
       // val view = (context as Activity).layoutInflater.inflate(R.layout.progress_layout, null)
        dialog.setCancelable(false)
        //dialog.setContentView(view)
        dialog?.setCancelable(false)
        dialog.getWindow()!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.getWindow()!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        return dialog
    }
}