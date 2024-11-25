package com.eyepetizer.android.ui

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.lifecycle.lifecycleScope
import com.eyepetizer.android.R
import com.eyepetizer.android.databinding.ActivitySplashBinding
import com.eyepetizer.android.ui.common.ui.BaseActivity
import com.eyepetizer.android.util.DataStoreUtils
import com.eyepetizer.android.util.GlobalUtil
import com.permissionx.guolindev.PermissionX
import kotlinx.coroutines.*


/**
 * 闪屏页面，应用程序首次启动入口。
 */
class SplashActivity : BaseActivity() {

    private var _binding: ActivitySplashBinding? = null

    private val binding: ActivitySplashBinding
        get() = _binding!!

    private val splashDuration = 3 * 1000L

    private val alphaAnimation by lazy {
        AlphaAnimation(0.5f, 1.0f).apply {
            duration = splashDuration
            fillAfter = true
        }
    }

    private val scaleAnimation by lazy {
        ScaleAnimation(1f, 1.05f, 1f, 1.05f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f).apply {
            duration = splashDuration
            fillAfter = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(TAG, "onCreate")
        requestWriteExternalStoragePermission()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun setupViews() {
        super.setupViews()
        binding.ivSlogan.startAnimation(alphaAnimation)
        binding.ivSplashPicture.startAnimation(scaleAnimation)
        lifecycleScope.launch {
            delay(splashDuration)
            MainActivity.start(this@SplashActivity)
            finish()
        }
        isFirstEntryApp = false
    }

    private fun requestWriteExternalStoragePermission() {
        Log.e(TAG, "requestWriteExternalStoragePermission: ")
        PermissionX.init(this@SplashActivity).permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .onExplainRequestReason { scope, deniedList ->
                val message = GlobalUtil.getString(R.string.request_permission_picture_processing)
                scope.showRequestReasonDialog(deniedList, message, GlobalUtil.getString(R.string.ok), GlobalUtil.getString(R.string.cancel))
            }
            .onForwardToSettings { scope, deniedList ->
                val message = GlobalUtil.getString(R.string.request_permission_picture_processing)
                scope.showForwardToSettingsDialog(deniedList, message, GlobalUtil.getString(R.string.settings), GlobalUtil.getString(R.string.cancel))
            }
            .request { allGranted, grantedList, deniedList ->
                requestReadPhoneStatePermission()
            }
    }

    private fun requestReadPhoneStatePermission() {
        Log.e(TAG, "requestReadPhoneStatePermission: ")
        PermissionX.init(this@SplashActivity).permissions(Manifest.permission.READ_PHONE_STATE)
            .onExplainRequestReason { scope, deniedList ->
                val message = GlobalUtil.getString(R.string.request_permission_access_phone_info)
                scope.showRequestReasonDialog(deniedList, message, GlobalUtil.getString(R.string.ok), GlobalUtil.getString(R.string.cancel))
            }
            .onForwardToSettings { scope, deniedList ->
                val message = GlobalUtil.getString(R.string.request_permission_access_phone_info)
                scope.showForwardToSettingsDialog(deniedList, message, GlobalUtil.getString(R.string.settings), GlobalUtil.getString(R.string.cancel))
            }
            .request { allGranted, grantedList, deniedList ->
                _binding = ActivitySplashBinding.inflate(layoutInflater)
                setContentView(binding.root)
            }
    }

    companion object {

        /**
         * 是否首次进入APP应用
         */
        var isFirstEntryApp: Boolean
            get() = DataStoreUtils.readBooleanData("is_first_entry_app", true)
            set(value) {
                CoroutineScope(Dispatchers.IO).launch { DataStoreUtils.saveBooleanData("is_first_entry_app", value) }
            }
    }
}
