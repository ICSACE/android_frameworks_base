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

#ifndef ANDROID_HWUI_SKIA_COLOR_FILTER_H
#define ANDROID_HWUI_SKIA_COLOR_FILTER_H

#include <GLES2/gl2.h>
#include <SkColorFilter.h>

<<<<<<< HEAD
#include <cutils/compiler.h>

=======
>>>>>>> e3fc4d0ba9f68910f3a9cbecf266073bd28e1f9e
#include "ProgramCache.h"
#include "Extensions.h"

namespace android {
namespace uirenderer {

///////////////////////////////////////////////////////////////////////////////
// Base color filter
///////////////////////////////////////////////////////////////////////////////

/**
 * Represents a Skia color filter. A color filter modifies a ProgramDescription
 * and sets uniforms on the resulting shaders.
 */
struct SkiaColorFilter {
    /**
     * Type of Skia color filter in use.
     */
    enum Type {
        kNone,
        kColorMatrix,
        kLighting,
        kBlend,
    };

<<<<<<< HEAD
    ANDROID_API SkiaColorFilter(SkColorFilter *skFilter, Type type, bool blend);
=======
    SkiaColorFilter(SkColorFilter *skFilter, Type type, bool blend);
>>>>>>> e3fc4d0ba9f68910f3a9cbecf266073bd28e1f9e
    virtual ~SkiaColorFilter();

    virtual void describe(ProgramDescription& description, const Extensions& extensions) = 0;
    virtual void setupProgram(Program* program) = 0;

    inline bool blend() const {
        return mBlend;
    }

    Type type() const {
        return mType;
    }

    SkColorFilter* getSkColorFilter() {
        return mSkFilter;
    }

protected:
    Type mType;
    bool mBlend;

private:
    SkColorFilter *mSkFilter;
}; // struct SkiaColorFilter

///////////////////////////////////////////////////////////////////////////////
// Implementations
///////////////////////////////////////////////////////////////////////////////

/**
 * A color filter that multiplies the source color with a matrix and adds a vector.
 */
struct SkiaColorMatrixFilter: public SkiaColorFilter {
<<<<<<< HEAD
    ANDROID_API SkiaColorMatrixFilter(SkColorFilter *skFilter, float* matrix, float* vector);
=======
    SkiaColorMatrixFilter(SkColorFilter *skFilter, float* matrix, float* vector);
>>>>>>> e3fc4d0ba9f68910f3a9cbecf266073bd28e1f9e
    ~SkiaColorMatrixFilter();

    void describe(ProgramDescription& description, const Extensions& extensions);
    void setupProgram(Program* program);

private:
    float* mMatrix;
    float* mVector;
}; // struct SkiaColorMatrixFilter

/**
 * A color filters that multiplies the source color with a fixed value and adds
 * another fixed value. Ignores the alpha channel of both arguments.
 */
struct SkiaLightingFilter: public SkiaColorFilter {
<<<<<<< HEAD
    ANDROID_API SkiaLightingFilter(SkColorFilter *skFilter, int multiply, int add);
=======
    SkiaLightingFilter(SkColorFilter *skFilter, int multiply, int add);
>>>>>>> e3fc4d0ba9f68910f3a9cbecf266073bd28e1f9e

    void describe(ProgramDescription& description, const Extensions& extensions);
    void setupProgram(Program* program);

private:
    GLfloat mMulR, mMulG, mMulB;
    GLfloat mAddR, mAddG, mAddB;
}; // struct SkiaLightingFilter

/**
 * A color filters that blends the source color with a specified destination color
 * and PorterDuff blending mode.
 */
struct SkiaBlendFilter: public SkiaColorFilter {
<<<<<<< HEAD
    ANDROID_API SkiaBlendFilter(SkColorFilter *skFilter, int color, SkXfermode::Mode mode);
=======
    SkiaBlendFilter(SkColorFilter *skFilter, int color, SkXfermode::Mode mode);
>>>>>>> e3fc4d0ba9f68910f3a9cbecf266073bd28e1f9e

    void describe(ProgramDescription& description, const Extensions& extensions);
    void setupProgram(Program* program);

private:
    SkXfermode::Mode mMode;
    GLfloat mR, mG, mB, mA;
}; // struct SkiaBlendFilter

}; // namespace uirenderer
}; // namespace android

#endif // ANDROID_HWUI_SKIA_COLOR_FILTER_H
