/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings.deviceinfo.firmwareversion;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.android.settings.R;
import com.android.settingslib.DeviceInfoUtils;
import com.android.settingslib.wrapper.PackageManagerWrapper;

public class CustomPatchLevelDialogController implements View.OnClickListener {

    private static final String TAG = "CustomPatchCtrl";

    @VisibleForTesting
    static final int CUSTOM_PATCH_VALUE_ID = R.id.custom_patch_level_value;
    @VisibleForTesting
    static final int CUSTOM_PATCH_LABEL_ID = R.id.custom_patch_level_label;

    private final FirmwareVersionDialogFragment mDialog;
    private final Context mContext;
    private final PackageManagerWrapper mPackageManager;
    private final String mCustomPatch;

    public CustomPatchLevelDialogController(FirmwareVersionDialogFragment dialog) {
        mDialog = dialog;
        mContext = dialog.getContext();
        mPackageManager = new PackageManagerWrapper(mContext.getPackageManager());
        mCustomPatch = DeviceInfoUtils.getCustomPatch();
    }

    @Override
    public void onClick(View v) {
       new AlertDialog.Builder(mContext)
            .setTitle(R.string.custom_patch)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setMessage(R.string.custom_patch_info)
            .setNegativeButton(R.string.cancel, null)
            .create().show();
    }

    /**
     * Populates the security patch level field in the dialog and registers click listeners.
     */
    public void initialize() {
        if (TextUtils.isEmpty(mCustomPatch)) {
            mDialog.removeSettingFromScreen(CUSTOM_PATCH_LABEL_ID);
            mDialog.removeSettingFromScreen(CUSTOM_PATCH_VALUE_ID);
            return;
        }
        registerListeners();
        mDialog.setText(CUSTOM_PATCH_VALUE_ID, mCustomPatch);
    }

    private void registerListeners() {
        mDialog.registerClickListener(CUSTOM_PATCH_LABEL_ID, this /* listener */);
        mDialog.registerClickListener(CUSTOM_PATCH_VALUE_ID, this /* listener */);
    }
}
