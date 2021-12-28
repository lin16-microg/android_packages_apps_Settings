/*
 * Copyright (C) 2019 The Android Open Source Project
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

package com.android.settings.accessibility;

import android.content.Context;
import android.media.AudioSystem;
import android.os.UserHandle;
import android.provider.Settings;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceViewHolder;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.android.settings.R;
import com.android.settings.widget.SeekBarPreference;

/** A slider preference that directly controls audio balance **/
public class BalanceSeekBarPreference extends SeekBarPreference {
    private static final String TAG = "BalanceSeekBarPreference";
    private final Context mContext;
    private BalanceSeekBar mSeekBar;
    private ImageView mIconView;

    public BalanceSeekBarPreference(Context context, AttributeSet attrs) {
        super(context, attrs, TypedArrayUtils.getAttr(context,
                R.attr.preferenceStyle,
                android.R.attr.preferenceStyle));
        mContext = context;
        setLayoutResource(R.layout.preference_balance_slider);
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder view) {
        super.onBindViewHolder(view);
        mSeekBar = (BalanceSeekBar) view.findViewById(com.android.internal.R.id.seekbar);
        mIconView = (ImageView) view.findViewById(com.android.internal.R.id.icon);
        init();
    }

    private void init() {
        if (mSeekBar == null) {
            return;
        }
        final float balance = Settings.System.getFloatForUser(
                mContext.getContentResolver(), Settings.System.MASTER_BALANCE,
                0.f /* default */, UserHandle.USER_CURRENT);
        // Rescale balance to range 0-200 centered at 100.
        mSeekBar.setMax(200);
        mSeekBar.setProgress((int)(balance * 100.f) + 100);
        mSeekBar.setEnabled(isEnabled());
    }
}
