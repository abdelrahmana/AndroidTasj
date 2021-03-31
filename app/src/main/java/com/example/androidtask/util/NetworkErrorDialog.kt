package com.imperialcreation.remcoowner.utils

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.example.androidtask.R
import com.example.androidtask.util.NameUtil
import com.example.androidtask.util.UtilKotlin
import kotlinx.android.synthetic.main.dialog_network.*

//this class make error and animation style

class NetworkErrorDialog : DialogFragment() {


    var dialogNoNetwork: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //createObserverViewModel()
        //setGlobalScope()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    // this is really called when you call show and this is called when we call show on activity it self
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialogNoNetwork = Dialog(ContextThemeWrapper(activity, R.style.DialogSlideAnim))
        dialogNoNetwork?.getWindow()?.requestFeature(Window.FEATURE_NO_TITLE)
        dialogNoNetwork!!.setContentView(R.layout.dialog_network)

        dialogNoNetwork!!.getWindow()!!.getAttributes().windowAnimations = R.style.DialogSlideAnim
        if (dialogNoNetwork!!.isShowing)
            dialogNoNetwork!!.dismiss()
        dialogNoNetwork!!.show()
        dialogNoNetwork!!.window!!.setGravity(Gravity.CENTER)
        dialogNoNetwork?.setCancelable(false)
        dialogNoNetwork!!.getWindow()!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialogNoNetwork?.getWindow()!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialogNoNetwork!!.window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.MATCH_PARENT
        lp.gravity = Gravity.CENTER
        lp.windowAnimations = R.style.DialogAnimation
        dialogNoNetwork!!.window!!.attributes = lp

        //dialogToast?.window?.setBackgroundDrawableResource(R.drawable.dialog_background)
// dialogToast!!.getWindow()!!.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        dialogNoNetwork!!.show()
        initViews(dialogNoNetwork)
        return dialogNoNetwork!!
    }

    private fun initViews(binder: Dialog?) {
        var model = UtilKotlin.declarViewModel(requireActivity())

        binder?.tryAgainNetwork?.setOnClickListener{
            model?.setNotifyItemSelected(NameUtil.isNetworkError)
            dismiss()
        }
    }
    companion object {

    }
}
