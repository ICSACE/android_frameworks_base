/*
 * Copyright (C) 2008 The Android Open Source Project
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

package com.android.internal.policy.impl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
<<<<<<< HEAD
import static android.os.BatteryManager.BATTERY_STATUS_FULL;
import static android.os.BatteryManager.BATTERY_STATUS_UNKNOWN;
import static android.os.BatteryManager.BATTERY_HEALTH_UNKNOWN;
import static android.os.BatteryManager.EXTRA_STATUS;
import static android.os.BatteryManager.EXTRA_PLUGGED;
import static android.os.BatteryManager.EXTRA_LEVEL;
import static android.os.BatteryManager.EXTRA_HEALTH;
import android.media.AudioManager;
=======
import static android.os.BatteryManager.BATTERY_STATUS_CHARGING;
import static android.os.BatteryManager.BATTERY_STATUS_FULL;
import static android.os.BatteryManager.BATTERY_STATUS_UNKNOWN;
import android.media.AudioManager;
import android.media.IRemoteControlClient;
>>>>>>> e3fc4d0ba9f68910f3a9cbecf266073bd28e1f9e
import android.os.BatteryManager;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.provider.Telephony;
import static android.provider.Telephony.Intents.EXTRA_PLMN;
import static android.provider.Telephony.Intents.EXTRA_SHOW_PLMN;
import static android.provider.Telephony.Intents.EXTRA_SHOW_SPN;
import static android.provider.Telephony.Intents.EXTRA_SPN;
import static android.provider.Telephony.Intents.SPN_STRINGS_UPDATED_ACTION;

import com.android.internal.telephony.IccCard;
import com.android.internal.telephony.TelephonyIntents;

import android.telephony.TelephonyManager;
import android.util.Log;
import com.android.internal.R;
import com.google.android.collect.Lists;

import java.util.ArrayList;

/**
 * Watches for updates that may be interesting to the keyguard, and provides
 * the up to date information as well as a registration for callbacks that care
 * to be updated.
 *
 * Note: under time crunch, this has been extended to include some stuff that
 * doesn't really belong here.  see {@link #handleBatteryUpdate} where it shutdowns
 * the device, and {@link #getFailedAttempts()}, {@link #reportFailedAttempt()}
 * and {@link #clearFailedAttempts()}.  Maybe we should rename this 'KeyguardContext'...
 */
public class KeyguardUpdateMonitor {

    static private final String TAG = "KeyguardUpdateMonitor";
    static private final boolean DEBUG = false;

    /* package */ static final int LOW_BATTERY_THRESHOLD = 20;

    private final Context mContext;

    private IccCard.State mSimState = IccCard.State.READY;

    private boolean mKeyguardBypassEnabled;

    private boolean mDeviceProvisioned;

<<<<<<< HEAD
    private BatteryStatus mBatteryStatus;
=======
    private int mBatteryLevel;

    private int mBatteryStatus;
>>>>>>> e3fc4d0ba9f68910f3a9cbecf266073bd28e1f9e

    private CharSequence mTelephonyPlmn;
    private CharSequence mTelephonySpn;

    private int mFailedAttempts = 0;

    private boolean mClockVisible;

    private Handler mHandler;

    private ArrayList<InfoCallback> mInfoCallbacks = Lists.newArrayList();
    private ArrayList<SimStateCallback> mSimStateCallbacks = Lists.newArrayList();
    private ContentObserver mContentObserver;
    private int mRingMode;
    private int mPhoneState;

    // messages for the handler
    private static final int MSG_TIME_UPDATE = 301;
    private static final int MSG_BATTERY_UPDATE = 302;
    private static final int MSG_CARRIER_INFO_UPDATE = 303;
    private static final int MSG_SIM_STATE_CHANGE = 304;
    private static final int MSG_RINGER_MODE_CHANGED = 305;
    private static final int MSG_PHONE_STATE_CHANGED = 306;
    private static final int MSG_CLOCK_VISIBILITY_CHANGED = 307;
    private static final int MSG_DEVICE_PROVISIONED = 308;

    /**
     * When we receive a
     * {@link com.android.internal.telephony.TelephonyIntents#ACTION_SIM_STATE_CHANGED} broadcast,
     * and then pass a result via our handler to {@link KeyguardUpdateMonitor#handleSimStateChange},
     * we need a single object to pass to the handler.  This class helps decode
     * the intent and provide a {@link SimCard.State} result.
     */
    private static class SimArgs {
<<<<<<< HEAD
        public final IccCard.State simState;

        SimArgs(IccCard.State state) {
            simState = state;
        }

        static SimArgs fromIntent(Intent intent) {
            IccCard.State state;
=======

        public final IccCard.State simState;

        private SimArgs(Intent intent) {
>>>>>>> e3fc4d0ba9f68910f3a9cbecf266073bd28e1f9e
            if (!TelephonyIntents.ACTION_SIM_STATE_CHANGED.equals(intent.getAction())) {
                throw new IllegalArgumentException("only handles intent ACTION_SIM_STATE_CHANGED");
            }
            String stateExtra = intent.getStringExtra(IccCard.INTENT_KEY_ICC_STATE);
            if (IccCard.INTENT_VALUE_ICC_ABSENT.equals(stateExtra)) {
                final String absentReason = intent
                    .getStringExtra(IccCard.INTENT_KEY_LOCKED_REASON);

                if (IccCard.INTENT_VALUE_ABSENT_ON_PERM_DISABLED.equals(
                        absentReason)) {
<<<<<<< HEAD
                    state = IccCard.State.PERM_DISABLED;
                } else {
                    state = IccCard.State.ABSENT;
                }
            } else if (IccCard.INTENT_VALUE_ICC_READY.equals(stateExtra)) {
                state = IccCard.State.READY;
=======
                    this.simState = IccCard.State.PERM_DISABLED;
                } else {
                    this.simState = IccCard.State.ABSENT;
                }
            } else if (IccCard.INTENT_VALUE_ICC_READY.equals(stateExtra)) {
                this.simState = IccCard.State.READY;
>>>>>>> e3fc4d0ba9f68910f3a9cbecf266073bd28e1f9e
            } else if (IccCard.INTENT_VALUE_ICC_LOCKED.equals(stateExtra)) {
                final String lockedReason = intent
                        .getStringExtra(IccCard.INTENT_KEY_LOCKED_REASON);
                if (IccCard.INTENT_VALUE_LOCKED_ON_PIN.equals(lockedReason)) {
<<<<<<< HEAD
                    state = IccCard.State.PIN_REQUIRED;
                } else if (IccCard.INTENT_VALUE_LOCKED_ON_PUK.equals(lockedReason)) {
                    state = IccCard.State.PUK_REQUIRED;
                } else {
                    state = IccCard.State.UNKNOWN;
                }
            } else if (IccCard.INTENT_VALUE_LOCKED_NETWORK.equals(stateExtra)) {
                state = IccCard.State.NETWORK_LOCKED;
            } else {
                state = IccCard.State.UNKNOWN;
            }
            return new SimArgs(state);
=======
                    this.simState = IccCard.State.PIN_REQUIRED;
                } else if (IccCard.INTENT_VALUE_LOCKED_ON_PUK.equals(lockedReason)) {
                    this.simState = IccCard.State.PUK_REQUIRED;
                } else {
                    this.simState = IccCard.State.UNKNOWN;
                }
            } else if (IccCard.INTENT_VALUE_LOCKED_NETWORK.equals(stateExtra)) {
                this.simState = IccCard.State.NETWORK_LOCKED;
            } else {
                this.simState = IccCard.State.UNKNOWN;
            }
>>>>>>> e3fc4d0ba9f68910f3a9cbecf266073bd28e1f9e
        }

        public String toString() {
            return simState.toString();
        }
    }

<<<<<<< HEAD
    private static class BatteryStatus {
        public final int status;
        public final int level;
        public final int plugged;
        public final int health;
        public BatteryStatus(int status, int level, int plugged, int health) {
            this.status = status;
            this.level = level;
            this.plugged = plugged;
            this.health = health;
        }

    }

=======
>>>>>>> e3fc4d0ba9f68910f3a9cbecf266073bd28e1f9e
    public KeyguardUpdateMonitor(Context context) {
        mContext = context;

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MSG_TIME_UPDATE:
                        handleTimeUpdate();
                        break;
                    case MSG_BATTERY_UPDATE:
<<<<<<< HEAD
                        handleBatteryUpdate((BatteryStatus) msg.obj);
=======
                        handleBatteryUpdate(msg.arg1,  msg.arg2);
>>>>>>> e3fc4d0ba9f68910f3a9cbecf266073bd28e1f9e
                        break;
                    case MSG_CARRIER_INFO_UPDATE:
                        handleCarrierInfoUpdate();
                        break;
                    case MSG_SIM_STATE_CHANGE:
                        handleSimStateChange((SimArgs) msg.obj);
                        break;
                    case MSG_RINGER_MODE_CHANGED:
                        handleRingerModeChange(msg.arg1);
                        break;
                    case MSG_PHONE_STATE_CHANGED:
                        handlePhoneStateChanged((String)msg.obj);
                        break;
                    case MSG_CLOCK_VISIBILITY_CHANGED:
                        handleClockVisibilityChanged();
                        break;
                    case MSG_DEVICE_PROVISIONED:
                        handleDeviceProvisioned();
                        break;
                }
            }
        };

        mKeyguardBypassEnabled = context.getResources().getBoolean(
                com.android.internal.R.bool.config_bypass_keyguard_if_slider_open);

        mDeviceProvisioned = Settings.Secure.getInt(
                mContext.getContentResolver(), Settings.Secure.DEVICE_PROVISIONED, 0) != 0;

        // Since device can't be un-provisioned, we only need to register a content observer
        // to update mDeviceProvisioned when we are...
        if (!mDeviceProvisioned) {
            mContentObserver = new ContentObserver(mHandler) {
                @Override
                public void onChange(boolean selfChange) {
                    super.onChange(selfChange);
                    mDeviceProvisioned = Settings.Secure.getInt(mContext.getContentResolver(),
                        Settings.Secure.DEVICE_PROVISIONED, 0) != 0;
                    if (mDeviceProvisioned) {
                        mHandler.sendMessage(mHandler.obtainMessage(MSG_DEVICE_PROVISIONED));
                    }
                    if (DEBUG) Log.d(TAG, "DEVICE_PROVISIONED state = " + mDeviceProvisioned);
                }
            };

            mContext.getContentResolver().registerContentObserver(
                    Settings.Secure.getUriFor(Settings.Secure.DEVICE_PROVISIONED),
                    false, mContentObserver);

            // prevent a race condition between where we check the flag and where we register the
            // observer by grabbing the value once again...
            boolean provisioned = Settings.Secure.getInt(mContext.getContentResolver(),
                Settings.Secure.DEVICE_PROVISIONED, 0) != 0;
            if (provisioned != mDeviceProvisioned) {
                mDeviceProvisioned = provisioned;
                if (mDeviceProvisioned) {
                    mHandler.sendMessage(mHandler.obtainMessage(MSG_DEVICE_PROVISIONED));
                }
            }
        }

        // take a guess to start
        mSimState = IccCard.State.READY;
<<<<<<< HEAD
        mBatteryStatus = new BatteryStatus(BATTERY_STATUS_UNKNOWN, 100, 0, 0);
=======
        mBatteryStatus = BATTERY_STATUS_UNKNOWN;
        mBatteryLevel = 100;
>>>>>>> e3fc4d0ba9f68910f3a9cbecf266073bd28e1f9e

        mTelephonyPlmn = getDefaultPlmn();

        // setup receiver
        final IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);
        filter.addAction(Intent.ACTION_TIME_CHANGED);
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        filter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
        filter.addAction(TelephonyIntents.ACTION_SIM_STATE_CHANGED);
        filter.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
        filter.addAction(SPN_STRINGS_UPDATED_ACTION);
        filter.addAction(AudioManager.RINGER_MODE_CHANGED_ACTION);
        context.registerReceiver(new BroadcastReceiver() {

            public void onReceive(Context context, Intent intent) {
                final String action = intent.getAction();
                if (DEBUG) Log.d(TAG, "received broadcast " + action);

                if (Intent.ACTION_TIME_TICK.equals(action)
                        || Intent.ACTION_TIME_CHANGED.equals(action)
                        || Intent.ACTION_TIMEZONE_CHANGED.equals(action)) {
                    mHandler.sendMessage(mHandler.obtainMessage(MSG_TIME_UPDATE));
                } else if (SPN_STRINGS_UPDATED_ACTION.equals(action)) {
                    mTelephonyPlmn = getTelephonyPlmnFrom(intent);
                    mTelephonySpn = getTelephonySpnFrom(intent);
                    mHandler.sendMessage(mHandler.obtainMessage(MSG_CARRIER_INFO_UPDATE));
                } else if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {
<<<<<<< HEAD
                    final int status = intent.getIntExtra(EXTRA_STATUS, BATTERY_STATUS_UNKNOWN);
                    final int plugged = intent.getIntExtra(EXTRA_PLUGGED, 0);
                    final int level = intent.getIntExtra(EXTRA_LEVEL, 0);
                    final int health = intent.getIntExtra(EXTRA_HEALTH, BATTERY_HEALTH_UNKNOWN);
                    final Message msg = mHandler.obtainMessage(
                            MSG_BATTERY_UPDATE, new BatteryStatus(status, level, plugged, health));
                    mHandler.sendMessage(msg);
                } else if (TelephonyIntents.ACTION_SIM_STATE_CHANGED.equals(action)) {
                    mHandler.sendMessage(mHandler.obtainMessage(
                            MSG_SIM_STATE_CHANGE, SimArgs.fromIntent(intent)));
=======
                    final int pluggedInStatus = intent
                            .getIntExtra("status", BATTERY_STATUS_UNKNOWN);
                    int batteryLevel = intent.getIntExtra("level", 0);
                    final Message msg = mHandler.obtainMessage(
                            MSG_BATTERY_UPDATE,
                            pluggedInStatus,
                            batteryLevel);
                    mHandler.sendMessage(msg);
                } else if (TelephonyIntents.ACTION_SIM_STATE_CHANGED.equals(action)) {
                    mHandler.sendMessage(mHandler.obtainMessage(
                            MSG_SIM_STATE_CHANGE,
                            new SimArgs(intent)));
>>>>>>> e3fc4d0ba9f68910f3a9cbecf266073bd28e1f9e
                } else if (AudioManager.RINGER_MODE_CHANGED_ACTION.equals(action)) {
                    mHandler.sendMessage(mHandler.obtainMessage(MSG_RINGER_MODE_CHANGED,
                            intent.getIntExtra(AudioManager.EXTRA_RINGER_MODE, -1), 0));
                } else if (TelephonyManager.ACTION_PHONE_STATE_CHANGED.equals(action)) {
                    String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
                    mHandler.sendMessage(mHandler.obtainMessage(MSG_PHONE_STATE_CHANGED, state));
                }
            }
        }, filter);
    }

    protected void handleDeviceProvisioned() {
        for (int i = 0; i < mInfoCallbacks.size(); i++) {
            mInfoCallbacks.get(i).onDeviceProvisioned();
        }
        if (mContentObserver != null) {
            // We don't need the observer anymore...
            mContext.getContentResolver().unregisterContentObserver(mContentObserver);
            mContentObserver = null;
        }
    }

    protected void handlePhoneStateChanged(String newState) {
        if (DEBUG) Log.d(TAG, "handlePhoneStateChanged(" + newState + ")");
        if (TelephonyManager.EXTRA_STATE_IDLE.equals(newState)) {
            mPhoneState = TelephonyManager.CALL_STATE_IDLE;
        } else if (TelephonyManager.EXTRA_STATE_OFFHOOK.equals(newState)) {
            mPhoneState = TelephonyManager.CALL_STATE_OFFHOOK;
        } else if (TelephonyManager.EXTRA_STATE_RINGING.equals(newState)) {
            mPhoneState = TelephonyManager.CALL_STATE_RINGING;
        }
        for (int i = 0; i < mInfoCallbacks.size(); i++) {
            mInfoCallbacks.get(i).onPhoneStateChanged(mPhoneState);
        }
    }

    protected void handleRingerModeChange(int mode) {
        if (DEBUG) Log.d(TAG, "handleRingerModeChange(" + mode + ")");
        mRingMode = mode;
        for (int i = 0; i < mInfoCallbacks.size(); i++) {
            mInfoCallbacks.get(i).onRingerModeChanged(mode);
        }
    }

    /**
     * Handle {@link #MSG_TIME_UPDATE}
     */
    private void handleTimeUpdate() {
        if (DEBUG) Log.d(TAG, "handleTimeUpdate");
        for (int i = 0; i < mInfoCallbacks.size(); i++) {
            mInfoCallbacks.get(i).onTimeChanged();
        }
    }

    /**
     * Handle {@link #MSG_BATTERY_UPDATE}
     */
<<<<<<< HEAD
    private void handleBatteryUpdate(BatteryStatus batteryStatus) {
        if (DEBUG) Log.d(TAG, "handleBatteryUpdate");
        final boolean batteryUpdateInteresting =
                isBatteryUpdateInteresting(mBatteryStatus, batteryStatus);
        mBatteryStatus = batteryStatus;
        if (batteryUpdateInteresting) {
            for (int i = 0; i < mInfoCallbacks.size(); i++) {
                // TODO: pass BatteryStatus object to onRefreshBatteryInfo() instead...
                mInfoCallbacks.get(i).onRefreshBatteryInfo(
                    shouldShowBatteryInfo(),isPluggedIn(batteryStatus), batteryStatus.level);
=======
    private void handleBatteryUpdate(int batteryStatus, int batteryLevel) {
        if (DEBUG) Log.d(TAG, "handleBatteryUpdate");
        if (isBatteryUpdateInteresting(batteryStatus, batteryLevel)) {
            mBatteryStatus = batteryStatus;
            mBatteryLevel = batteryLevel;
            final boolean pluggedIn = isPluggedIn(batteryStatus);;
            for (int i = 0; i < mInfoCallbacks.size(); i++) {
                mInfoCallbacks.get(i).onRefreshBatteryInfo(
                        shouldShowBatteryInfo(), pluggedIn, batteryLevel);
>>>>>>> e3fc4d0ba9f68910f3a9cbecf266073bd28e1f9e
            }
        }
    }

    /**
     * Handle {@link #MSG_CARRIER_INFO_UPDATE}
     */
    private void handleCarrierInfoUpdate() {
        if (DEBUG) Log.d(TAG, "handleCarrierInfoUpdate: plmn = " + mTelephonyPlmn
            + ", spn = " + mTelephonySpn);

        for (int i = 0; i < mInfoCallbacks.size(); i++) {
            mInfoCallbacks.get(i).onRefreshCarrierInfo(mTelephonyPlmn, mTelephonySpn);
        }
    }

    /**
     * Handle {@link #MSG_SIM_STATE_CHANGE}
     */
    private void handleSimStateChange(SimArgs simArgs) {
        final IccCard.State state = simArgs.simState;

        if (DEBUG) {
            Log.d(TAG, "handleSimStateChange: intentValue = " + simArgs + " "
                    + "state resolved to " + state.toString());
        }

        if (state != IccCard.State.UNKNOWN && state != mSimState) {
            mSimState = state;
            for (int i = 0; i < mSimStateCallbacks.size(); i++) {
                mSimStateCallbacks.get(i).onSimStateChanged(state);
            }
        }
    }

    private void handleClockVisibilityChanged() {
        if (DEBUG) Log.d(TAG, "handleClockVisibilityChanged()");
        for (int i = 0; i < mInfoCallbacks.size(); i++) {
            mInfoCallbacks.get(i).onClockVisibilityChanged();
        }
    }

    /**
<<<<<<< HEAD
     * @param pluggedIn state from {@link android.os.BatteryManager#EXTRA_PLUGGED}
     * @return Whether the device is considered "plugged in."
     */
    private static boolean isPluggedIn(BatteryStatus status) {
        return status.plugged == BatteryManager.BATTERY_PLUGGED_AC
                || status.plugged == BatteryManager.BATTERY_PLUGGED_USB;
    }

    private static boolean isBatteryUpdateInteresting(BatteryStatus old, BatteryStatus current) {
        final boolean nowPluggedIn = isPluggedIn(current);
        final boolean wasPluggedIn = isPluggedIn(old);
        final boolean stateChangedWhilePluggedIn =
            wasPluggedIn == true && nowPluggedIn == true
            && (old.status != current.status);

        // change in plug state is always interesting
        if (wasPluggedIn != nowPluggedIn || stateChangedWhilePluggedIn) {
=======
     * @param status One of the statuses of {@link android.os.BatteryManager}
     * @return Whether the status maps to a status for being plugged in.
     */
    private boolean isPluggedIn(int status) {
        return status == BATTERY_STATUS_CHARGING || status == BATTERY_STATUS_FULL;
    }

    private boolean isBatteryUpdateInteresting(int batteryStatus, int batteryLevel) {
        // change in plug is always interesting
        final boolean isPluggedIn = isPluggedIn(batteryStatus);
        final boolean wasPluggedIn = isPluggedIn(mBatteryStatus);
        final boolean stateChangedWhilePluggedIn =
            wasPluggedIn == true && isPluggedIn == true && (mBatteryStatus != batteryStatus);
        if (wasPluggedIn != isPluggedIn || stateChangedWhilePluggedIn) {
>>>>>>> e3fc4d0ba9f68910f3a9cbecf266073bd28e1f9e
            return true;
        }

        // change in battery level while plugged in
<<<<<<< HEAD
        if (nowPluggedIn && old.level != current.level) {
            return true;
        }

        // change where battery needs charging
        if (!nowPluggedIn && isBatteryLow(current) && current.level != old.level) {
            return true;
=======
        if (isPluggedIn && mBatteryLevel != batteryLevel) {
            return true;
        }

        if (!isPluggedIn) {
            // not plugged in and below threshold
            if (isBatteryLow(batteryLevel) && batteryLevel != mBatteryLevel) {
                return true;
            }
>>>>>>> e3fc4d0ba9f68910f3a9cbecf266073bd28e1f9e
        }
        return false;
    }

<<<<<<< HEAD
    private static boolean isBatteryLow(BatteryStatus status) {
        return status.level < LOW_BATTERY_THRESHOLD;
=======
    private boolean isBatteryLow(int batteryLevel) {
        return batteryLevel < LOW_BATTERY_THRESHOLD;
>>>>>>> e3fc4d0ba9f68910f3a9cbecf266073bd28e1f9e
    }

    /**
     * @param intent The intent with action {@link Telephony.Intents#SPN_STRINGS_UPDATED_ACTION}
     * @return The string to use for the plmn, or null if it should not be shown.
     */
    private CharSequence getTelephonyPlmnFrom(Intent intent) {
        if (intent.getBooleanExtra(EXTRA_SHOW_PLMN, false)) {
            final String plmn = intent.getStringExtra(EXTRA_PLMN);
            if (plmn != null) {
                return plmn;
            } else {
                return getDefaultPlmn();
            }
        }
        return null;
    }

    /**
     * @return The default plmn (no service)
     */
    private CharSequence getDefaultPlmn() {
        return mContext.getResources().getText(
                        R.string.lockscreen_carrier_default);
    }

    /**
     * @param intent The intent with action {@link Telephony.Intents#SPN_STRINGS_UPDATED_ACTION}
     * @return The string to use for the plmn, or null if it should not be shown.
     */
    private CharSequence getTelephonySpnFrom(Intent intent) {
        if (intent.getBooleanExtra(EXTRA_SHOW_SPN, false)) {
            final String spn = intent.getStringExtra(EXTRA_SPN);
            if (spn != null) {
                return spn;
            }
        }
        return null;
    }

    /**
     * Remove the given observer from being registered from any of the kinds
     * of callbacks.
     * @param observer The observer to remove (an instance of {@link ConfigurationChangeCallback},
     *   {@link InfoCallback} or {@link SimStateCallback}
     */
    public void removeCallback(Object observer) {
        mInfoCallbacks.remove(observer);
        mSimStateCallbacks.remove(observer);
    }

    /**
     * Callback for general information relevant to lock screen.
     */
    interface InfoCallback {
        void onRefreshBatteryInfo(boolean showBatteryInfo, boolean pluggedIn, int batteryLevel);
        void onTimeChanged();

        /**
         * @param plmn The operator name of the registered network.  May be null if it shouldn't
         *   be displayed.
         * @param spn The service provider name.  May be null if it shouldn't be displayed.
         */
        void onRefreshCarrierInfo(CharSequence plmn, CharSequence spn);

        /**
         * Called when the ringer mode changes.
         * @param state the current ringer state, as defined in
         * {@link AudioManager#RINGER_MODE_CHANGED_ACTION}
         */
        void onRingerModeChanged(int state);

        /**
         * Called when the phone state changes. String will be one of:
         * {@link TelephonyManager#EXTRA_STATE_IDLE}
         * {@link TelephonyManager@EXTRA_STATE_RINGING}
         * {@link TelephonyManager#EXTRA_STATE_OFFHOOK
         */
        void onPhoneStateChanged(int phoneState);

        /**
         * Called when visibility of lockscreen clock changes, such as when
         * obscured by a widget.
         */
        void onClockVisibilityChanged();

        /**
         * Called when the device becomes provisioned
         */
        void onDeviceProvisioned();
    }

    /**
     * Callback to notify of sim state change.
     */
    interface SimStateCallback {
        void onSimStateChanged(IccCard.State simState);
    }

    /**
     * Register to receive notifications about general keyguard information
     * (see {@link InfoCallback}.
     * @param callback The callback.
     */
    public void registerInfoCallback(InfoCallback callback) {
        if (!mInfoCallbacks.contains(callback)) {
            mInfoCallbacks.add(callback);
            // Notify listener of the current state
<<<<<<< HEAD
            callback.onRefreshBatteryInfo(shouldShowBatteryInfo(),isPluggedIn(mBatteryStatus),
                    mBatteryStatus.level);
=======
            callback.onRefreshBatteryInfo(shouldShowBatteryInfo(), isPluggedIn(mBatteryStatus),
                    mBatteryLevel);
>>>>>>> e3fc4d0ba9f68910f3a9cbecf266073bd28e1f9e
            callback.onTimeChanged();
            callback.onRingerModeChanged(mRingMode);
            callback.onPhoneStateChanged(mPhoneState);
            callback.onRefreshCarrierInfo(mTelephonyPlmn, mTelephonySpn);
            callback.onClockVisibilityChanged();
        } else {
            if (DEBUG) Log.e(TAG, "Object tried to add another INFO callback",
                    new Exception("Whoops"));
        }
    }

    /**
     * Register to be notified of sim state changes.
     * @param callback The callback.
     */
    public void registerSimStateCallback(SimStateCallback callback) {
        if (!mSimStateCallbacks.contains(callback)) {
            mSimStateCallbacks.add(callback);
            // Notify listener of the current state
            callback.onSimStateChanged(mSimState);
        } else {
            if (DEBUG) Log.e(TAG, "Object tried to add another SIM callback",
                    new Exception("Whoops"));
        }
    }

    public void reportClockVisible(boolean visible) {
        mClockVisible = visible;
        mHandler.obtainMessage(MSG_CLOCK_VISIBILITY_CHANGED).sendToTarget();
    }

    public IccCard.State getSimState() {
        return mSimState;
    }

    /**
<<<<<<< HEAD
     * Report that the user successfully entered the SIM PIN or PUK/SIM PIN so we
     * have the information earlier than waiting for the intent
     * broadcast from the telephony code.
     *
     * NOTE: Because handleSimStateChange() invokes callbacks immediately without going
     * through mHandler, this *must* be called from the UI thread.
     */
    public void reportSimUnlocked() {
        mSimState = IccCard.State.READY;
        handleSimStateChange(new SimArgs(mSimState));
=======
     * Report that the user succesfully entered the sim pin or puk so we
     * have the information earlier than waiting for the intent
     * broadcast from the telephony code.
     */
    public void reportSimUnlocked() {
        mSimState = IccCard.State.READY;
>>>>>>> e3fc4d0ba9f68910f3a9cbecf266073bd28e1f9e
    }

    public boolean isKeyguardBypassEnabled() {
        return mKeyguardBypassEnabled;
    }

    public boolean isDevicePluggedIn() {
        return isPluggedIn(mBatteryStatus);
    }

    public boolean isDeviceCharged() {
<<<<<<< HEAD
        return mBatteryStatus.status == BATTERY_STATUS_FULL
                || mBatteryStatus.level >= 100; // in case particular device doesn't flag it
    }

    public int getBatteryLevel() {
        return mBatteryStatus.level;
    }

    public boolean shouldShowBatteryInfo() {
        return isPluggedIn(mBatteryStatus) || isBatteryLow(mBatteryStatus);
=======
        return mBatteryStatus == BatteryManager.BATTERY_STATUS_FULL
                || mBatteryLevel >= 100; // in case a particular device doesn't flag it
    }

    public int getBatteryLevel() {
        return mBatteryLevel;
    }

    public boolean shouldShowBatteryInfo() {
        return isPluggedIn(mBatteryStatus) || isBatteryLow(mBatteryLevel);
>>>>>>> e3fc4d0ba9f68910f3a9cbecf266073bd28e1f9e
    }

    public CharSequence getTelephonyPlmn() {
        return mTelephonyPlmn;
    }

    public CharSequence getTelephonySpn() {
        return mTelephonySpn;
    }

    /**
     * @return Whether the device is provisioned (whether they have gone through
     *   the setup wizard)
     */
    public boolean isDeviceProvisioned() {
        return mDeviceProvisioned;
    }

    public int getFailedAttempts() {
        return mFailedAttempts;
    }

    public void clearFailedAttempts() {
        mFailedAttempts = 0;
    }

    public void reportFailedAttempt() {
        mFailedAttempts++;
    }

    public boolean isClockVisible() {
        return mClockVisible;
    }

    public int getPhoneState() {
        return mPhoneState;
    }
}
