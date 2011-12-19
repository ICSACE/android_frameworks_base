/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package android.speech.tts;

<<<<<<< HEAD
import android.speech.tts.TextToSpeechService.UtteranceProgressDispatcher;
=======
import android.speech.tts.TextToSpeechService.UtteranceCompletedDispatcher;
>>>>>>> e3fc4d0ba9f68910f3a9cbecf266073bd28e1f9e

abstract class MessageParams {
    static final int TYPE_SYNTHESIS = 1;
    static final int TYPE_AUDIO = 2;
    static final int TYPE_SILENCE = 3;

<<<<<<< HEAD
    private final UtteranceProgressDispatcher mDispatcher;
    private final String mCallingApp;

    MessageParams(UtteranceProgressDispatcher dispatcher, String callingApp) {
=======
    private final UtteranceCompletedDispatcher mDispatcher;
    private final String mCallingApp;

    MessageParams(UtteranceCompletedDispatcher dispatcher, String callingApp) {
>>>>>>> e3fc4d0ba9f68910f3a9cbecf266073bd28e1f9e
        mDispatcher = dispatcher;
        mCallingApp = callingApp;
    }

<<<<<<< HEAD
    UtteranceProgressDispatcher getDispatcher() {
=======
    UtteranceCompletedDispatcher getDispatcher() {
>>>>>>> e3fc4d0ba9f68910f3a9cbecf266073bd28e1f9e
        return mDispatcher;
    }

    String getCallingApp() {
        return mCallingApp;
    }

    @Override
    public String toString() {
        return "MessageParams[" + hashCode() + "]";
    }

    abstract int getType();
}
