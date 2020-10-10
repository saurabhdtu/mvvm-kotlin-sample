package com.locus.assignment.ui.activities

import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.os.Environment
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.locus.assignment.R
import com.locus.assignment.databinding.ActivityMainBinding
import com.locus.assignment.models.constants.RequestCodes
import com.locus.assignment.models.entities.FormListItem
import com.locus.assignment.ui.adapters.AdapterFormList
import com.locus.assignment.ui.adapters.viewholders.formitems.PhotoVH
import com.locus.assignment.ui.fragments.PhotoPreviewFragment
import com.locus.assignment.ui.interfaces.ListItemInterface
import com.locus.assignment.ui.viewmodels.FormVM
import com.locus.assignment.utils.IntentHelper
import kotlinx.android.synthetic.main.activity_main.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions


class MainActivity : BaseActivity(), ListItemInterface {
    lateinit var formVm: FormVM
    var adapterForm: AdapterFormList? = null
    lateinit var point: Point
    var imagePath: String? = null
    var selectedFormItem: FormListItem? = null
    var selectedPosition: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        formVm = ViewModelProvider(this).get(FormVM::class.java)
        binding.formVM = formVm
        point = Point()
        windowManager.defaultDisplay.getSize(point)
        supportActionBar?.title = "Locus Form"

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RequestCodes.CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            selectedFormItem?.value = imagePath
            if (selectedFormItem != null)
                adapterForm?.updateItem(selectedPosition!!)

        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        rv_form_items.layoutManager = LinearLayoutManager(this)
        if (formVm.formList.value == null) {
            formVm.getFormData(getString(R.string.sample_input))
            formVm.formList.observe(this, {
                adapterForm =
                    formVm.formList.value?.let { it1 -> AdapterFormList(it1, this, point) }
                rv_form_items.adapter = adapterForm
            })
        } else {
            adapterForm = AdapterFormList(formVm.formList.value!!, this, point)
            rv_form_items.adapter = adapterForm
        }
        formVm.error.observe(this, {
            showToast(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_form, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.submit -> {
            formVm.submit()
            true
        }
        else -> {
            true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    @AfterPermissionGranted(RequestCodes.WRITE_PERMISSION_CODE)
    fun permissionCamera() {
        if (EasyPermissions.hasPermissions(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            imagePath = IntentHelper.launchCamera(
                this,
                getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!.absolutePath
            )
        } else {
            showToast("Permission missing")
        }
    }

    override fun listItemClicked(
        pos: Int,
        viewHolder: RecyclerView.ViewHolder,
        data: Any?,
        type: Int
    ) {
        when (type) {
            PhotoVH.FLAG_OPEN_CAMERA -> {
                selectedPosition = pos
                selectedFormItem = data as FormListItem
                if (EasyPermissions.hasPermissions(
                        this,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                ) {
                    imagePath = IntentHelper.launchCamera(
                        this,
                        getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!.absolutePath
                    )
                } else {
                    EasyPermissions.requestPermissions(
                        this,
                        getString(R.string.permission_storage),
                        RequestCodes.WRITE_PERMISSION_CODE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    )
                }
            }

            PhotoVH.FLAG_OPEN_IMAGE -> {
                val formItem = data as FormListItem
                val endFragment = PhotoPreviewFragment()
                val bundle = Bundle()
                bundle.putString("IMAGE", formItem.value)
                endFragment.arguments = bundle
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, endFragment)
                    .setCustomAnimations(
                        android.R.anim.fade_in,
                        android.R.anim.fade_out,
                        android.R.anim.fade_in,
                        android.R.anim.fade_out
                    )
                    .addToBackStack("preview")
                    .commit()
            }
        }
    }
}