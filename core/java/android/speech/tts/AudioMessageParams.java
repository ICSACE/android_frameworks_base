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

class AudioMessageParams extends MessageParams {
    private final BlockingMediaPlayer mPlayer;

<<<<<<< HEAD
    AudioMessageParams(UtteranceProgressDispatcher dispatcher,
=======
    AudioMessageParams(UtteranceCompletedDispatcher dispatcher,
>>>>>>> e3fc4d0ba9f68910f3a9cbecf266073bd28e1f9e
            String callingApp, BlockingMediaPlayer player) {
        super(dispatcher, callingApp);
        mPlayer = player;
    }

    BlockingMediaPlayer getPlayer() {
        return mPlayer;
    }

    @Override
    int getType() {
        return TYPE_AUDIO;
    }

}
