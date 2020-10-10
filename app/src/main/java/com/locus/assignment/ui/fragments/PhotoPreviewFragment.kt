package com.locus.assignment.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.locus.assignment.R
import com.locus.assignment.ui.bindings.ImageBindingAdapter


/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class PhotoPreviewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_photo_preview, container, false)
        val bundle = arguments
        var image: String?=null
        if (bundle != null) {
            image = bundle.getString("IMAGE")
        }
        ImageBindingAdapter.loadImage(v.findViewById<View>(R.id.iv_form_image) as ImageView, image)
        v.findViewById<View>(R.id.btn_close).setOnClickListener { activity?.onBackPressed() }
        return v
    }

}