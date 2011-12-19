# Build the unit tests.
LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := EGL_test

LOCAL_MODULE_TAGS := tests

LOCAL_SRC_FILES := \
<<<<<<< HEAD
    egl_cache_test.cpp \
=======
>>>>>>> e3fc4d0ba9f68910f3a9cbecf266073bd28e1f9e
    EGL_test.cpp \

LOCAL_SHARED_LIBRARIES := \
	libEGL \
	libcutils \
	libstlport \
	libutils \

LOCAL_STATIC_LIBRARIES := \
	libgtest \
	libgtest_main \

LOCAL_C_INCLUDES := \
    bionic \
<<<<<<< HEAD
    bionic/libc/private \
    bionic/libstdc++/include \
    external/gtest/include \
    external/stlport/stlport \
    frameworks/base/opengl/libs \
    frameworks/base/opengl/libs/EGL \
=======
    bionic/libstdc++/include \
    external/gtest/include \
    external/stlport/stlport \
>>>>>>> e3fc4d0ba9f68910f3a9cbecf266073bd28e1f9e

include $(BUILD_EXECUTABLE)

# Include subdirectory makefiles
# ============================================================

# If we're building with ONE_SHOT_MAKEFILE (mm, mmm), then what the framework
# team really wants is to build the stuff defined by this makefile.
ifeq (,$(ONE_SHOT_MAKEFILE))
include $(call first-makefiles-under,$(LOCAL_PATH))
endif
