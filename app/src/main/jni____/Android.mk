LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LAME_CUS_DIR := cus

LOCAL_MODULE    := cus
LOCAL_SRC_FILES := \
$(LAME_CUS_DIR)/Test.cpp 

include $(BUILD_SHARED_LIBRARY)