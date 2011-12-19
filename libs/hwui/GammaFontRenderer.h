/*
 * Copyright (C) 2010 The Android Open Source Project
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

#ifndef ANDROID_HWUI_GAMMA_FONT_RENDERER_H
#define ANDROID_HWUI_GAMMA_FONT_RENDERER_H

#include <SkPaint.h>

#include "FontRenderer.h"

namespace android {
namespace uirenderer {

struct GammaFontRenderer {
    GammaFontRenderer();
<<<<<<< HEAD
    ~GammaFontRenderer();

    enum Gamma {
        kGammaDefault = 0,
        kGammaBlack = 1,
        kGammaWhite = 2,
        kGammaCount = 3
    };

    void clear();
    void flush();
=======
>>>>>>> e3fc4d0ba9f68910f3a9cbecf266073bd28e1f9e

    FontRenderer& getFontRenderer(const SkPaint* paint);

    uint32_t getFontRendererCount() const {
<<<<<<< HEAD
        return kGammaCount;
    }

    uint32_t getFontRendererSize(uint32_t fontRenderer) const {
        if (fontRenderer >= kGammaCount) return 0;

        FontRenderer* renderer = mRenderers[fontRenderer];
        if (!renderer) return 0;

        return renderer->getCacheHeight() * renderer->getCacheWidth();
    }

private:
    FontRenderer* getRenderer(Gamma gamma);

    uint32_t mRenderersUsageCount[kGammaCount];
    FontRenderer* mRenderers[kGammaCount];
=======
        return 3;
    }

    uint32_t getFontRendererSize(uint32_t fontRenderer) const {
        switch (fontRenderer) {
            case 0:
                return mDefaultRenderer.getCacheHeight() * mDefaultRenderer.getCacheWidth();
            case 1:
                return mBlackGammaRenderer.getCacheHeight() * mBlackGammaRenderer.getCacheWidth();
            case 2:
                return mWhiteGammaRenderer.getCacheHeight() * mWhiteGammaRenderer.getCacheWidth();
        }
        return 0;
    }

private:
    FontRenderer mDefaultRenderer;
    FontRenderer mBlackGammaRenderer;
    FontRenderer mWhiteGammaRenderer;
>>>>>>> e3fc4d0ba9f68910f3a9cbecf266073bd28e1f9e

    int mBlackThreshold;
    int mWhiteThreshold;

<<<<<<< HEAD
    uint8_t mGammaTable[256 * kGammaCount];
=======
    uint8_t mDefault[256];
    uint8_t mBlackGamma[256];
    uint8_t mWhiteGamma[256];
>>>>>>> e3fc4d0ba9f68910f3a9cbecf266073bd28e1f9e
};

}; // namespace uirenderer
}; // namespace android

#endif // ANDROID_HWUI_GAMMA_FONT_RENDERER_H
