/*
 * Copyright (C) 2011 The Android Open Source Project
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

#ifndef ANDROID_LAYER_SCREENSHOT_H
#define ANDROID_LAYER_SCREENSHOT_H

#include <stdint.h>
#include <sys/types.h>

#include <EGL/egl.h>
#include <EGL/eglext.h>

#include "LayerBase.h"

// ---------------------------------------------------------------------------

namespace android {

class LayerScreenshot : public LayerBaseClient
{
    GLuint mTextureName;
    GLfloat mTexCoords[8];
    sp<SurfaceFlinger> mFlinger;
public:    
            LayerScreenshot(SurfaceFlinger* flinger, DisplayID display,
                        const sp<Client>& client);
        virtual ~LayerScreenshot();

        status_t capture();

<<<<<<< HEAD
    virtual void initStates(uint32_t w, uint32_t h, uint32_t flags);
    virtual uint32_t doTransaction(uint32_t flags);
=======
>>>>>>> e3fc4d0ba9f68910f3a9cbecf266073bd28e1f9e
    virtual void onDraw(const Region& clip) const;
    virtual bool isOpaque() const         { return false; }
    virtual bool isSecure() const         { return false; }
    virtual bool isProtectedByApp() const { return false; }
    virtual bool isProtectedByDRM() const { return false; }
    virtual const char* getTypeId() const { return "LayerScreenshot"; }
<<<<<<< HEAD

private:
    status_t captureLocked();
    void initTexture(GLfloat u, GLfloat v);
=======
>>>>>>> e3fc4d0ba9f68910f3a9cbecf266073bd28e1f9e
};

// ---------------------------------------------------------------------------

}; // namespace android

#endif // ANDROID_LAYER_SCREENSHOT_H
