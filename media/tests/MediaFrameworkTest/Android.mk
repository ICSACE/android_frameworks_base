LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := tests

LOCAL_SRC_FILES := $(call all-subdir-java-files)

LOCAL_JAVA_LIBRARIES := android.test.runner

<<<<<<< HEAD
LOCAL_STATIC_JAVA_LIBRARIES := easymocklib

=======
>>>>>>> e3fc4d0ba9f68910f3a9cbecf266073bd28e1f9e
LOCAL_PACKAGE_NAME := mediaframeworktest

include $(BUILD_PACKAGE)
