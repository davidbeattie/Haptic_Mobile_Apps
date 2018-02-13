/*
** =========================================================================
** Copyright (c) 2005-2009  Immersion Corporation.  All rights reserved.
**                          Immersion Corporation Confidential and Proprietary
**
** File:
**     VibeTonz.java
**
** Description: 
**     Java VibeTonz API wrapper class.
**
** =========================================================================
*/

package com.immersion;

public class VibeTonz
{
     /*
     ** Min/Max values, length
     */
     final public static int VIBE_MAX_MAGNITUDE                    = 10000; /*!< Maximum Force Magnitude */
     final public static int VIBE_MIN_MAGNITUDE                    = 0;     /*!< Minimum Force Magnitude */   
     final public static int VIBE_INVALID_INDEX                    = -1;    /*!< Invalid Index */
     final public static int VIBE_MAX_EFFECT_NAME_LENGTH           = 128;   /*!< Maximum effect name length */
     final public static int VIBE_MAX_DEVICE_NAME_LENGTH           = 64;    /*!< Maximum device name length */
     final public static int VIBE_MAX_CAPABILITY_STRING_LENGTH     = 64;    /*!< Maximum string length use by VibeGetDeviceCapabilityString/VibeSetDeviceCapabilityString */
     final public static int VIBE_MAX_PROPERTY_STRING_LENGTH       = 64;    /*!< Maximum string length use by VibeGetDevicePropertyString/VibeSetDevicePropertyString */
     final public static int VIBE_REPEAT_COUNT_INFINITE            = 255;   /*!< Infinite repeat count */
     final public static int VIBE_TIME_INFINITE                    = Integer.MAX_VALUE; /*!< Infinite time */

     /*
     ** Device property types
     */
     final public static int VIBE_DEVPROPTYPE_LICENSE_KEY          = 0;     /*!< Property type constant to set Application licence key */
     final public static int VIBE_DEVPROPTYPE_PRIORITY             = 1;     /*!< Property type constant to get/set device priority */
     final public static int VIBE_DEVPROPTYPE_DISABLE_EFFECTS      = 2;     /*!< Property type constant to enable/disable effects on a device */
     final public static int VIBE_DEVPROPTYPE_STRENGTH             = 3;     /*!< Property type constant to get/set the strength (volume) on a device */
     final public static int VIBE_DEVPROPTYPE_MASTERSTRENGTH       = 4;     /*!< Property type constant to get/set the strength (volume) on ALL devices */

     /* 
     ** Device type returned by VibeGetDeviceCapabilityInt32 in 'xxxx' field of
     ** 'yyyy' struct for 'deviceCapabilityType' equal to
     ** VIBE_DEVCAPTYPE_DEVICE_CATEGORY 
     */
     final public static int VIBE_DEVICECATEGORY_IFC               = 0;     /*!< Device category constant for IFC Devices */
     final public static int VIBE_DEVICECATEGORY_IMPULSE           = 1;     /*!< Device category constant for Impulse Devices */
     final public static int VIBE_DEVICECATEGORY_VIRTUAL           = 2;     /*!< Device category constant for Virtual Devices */
     final public static int VIBE_DEVICECATEGORY_EMBEDDED          = 3;     /*!< Device category constant for Embedded Devices */
     final public static int VIBE_DEVICECATEGORY_TETHERED          = 4;     /*!< Device category constant for Tethered Devices */
     final public static int VIBE_DEVICECATEGORY_IMMERSION_USB     = 5;     /*!< Device category constant for Immersion USB Devices */

     /*
     ** Effect type returned by VibeGetIVTEffectType 
     */
     final public static int VIBE_EFFECT_TYPE_PERIODIC             = 0;     /*!< Periodic Effect type constant */
     final public static int VIBE_EFFECT_TYPE_MAGSWEEP             = 1;     /*!< Magsweep Effect type constant */
     final public static int VIBE_EFFECT_TYPE_TIMELINE             = 2;     /*!< Timeline Effect type constant */
     final public static int VIBE_EFFECT_TYPE_STREAMING            = 3;     /*!< Streaming Effect type constant */

     /*
     ** Device capability types
     */
     final public static int VIBE_DEVCAPTYPE_DEVICE_CATEGORY       = 0;     /*!< Use VibeGetDeviceCapabilityInt32 */
     final public static int VIBE_DEVCAPTYPE_MAX_NESTED_REPEATS    = 1;     /*!< Use VibeGetDeviceCapabilityInt32 */
     final public static int VIBE_DEVCAPTYPE_NUM_ACTUATORS         = 2;     /*!< Use VibeGetDeviceCapabilityInt32 */
     final public static int VIBE_DEVCAPTYPE_ACTUATOR_TYPE         = 3;     /*!< Use VibeGetDeviceCapabilityInt32 */
     final public static int VIBE_DEVCAPTYPE_NUM_EFFECT_SLOTS      = 4;     /*!< Use VibeGetDeviceCapabilityInt32 */
     final public static int VIBE_DEVCAPTYPE_SUPPORTED_STYLES      = 5;     /*!< Use VibeGetDeviceCapabilityInt32 */
     final public static int VIBE_DEVCAPTYPE_MIN_PERIOD            = 6;     /*!< Use VibeGetDeviceCapabilityInt32 */
     final public static int VIBE_DEVCAPTYPE_MAX_PERIOD            = 7;     /*!< Use VibeGetDeviceCapabilityInt32 */
     final public static int VIBE_DEVCAPTYPE_MAX_EFFECT_DURATION   = 8;     /*!< Use VibeGetDeviceCapabilityInt32 */
     final public static int VIBE_DEVCAPTYPE_SUPPORTED_EFFECTS     = 9;     /*!< Use VibeGetDeviceCapabilityInt32 */
     final public static int VIBE_DEVCAPTYPE_DEVICE_NAME           = 10;    /*!< Use VibeGetDeviceCapabilityString */
     final public static int VIBE_DEVCAPTYPE_MAX_ENVELOPE_TIME     = 11;    /*!< Use VibeGetDeviceCapabilityInt32 */
     final public static int VIBE_DEVCAPTYPE_APIVERSIONNUMBER      = 12;    /*!< Use VibeGetDeviceCapabilityInt32 */
     final public static int VIBE_DEVCAPTYPE_MAX_IVT_SIZE_TETHERED = 13;    /*!< Use ImmVibeGetDeviceCapabilityInt32 */
     final public static int VIBE_DEVCAPTYPE_MAX_IVT_SIZE          = 14;    /*!< Use ImmVibeGetDeviceCapabilityInt32 */

     /* 
     ** handles 
     */
     final public static int VIBE_INVALID_EFFECT_HANDLE_VALUE      = -1;    /*!< Invalid Effect Handle */
     final public static int VIBE_INVALID_DEVICE_HANDLE_VALUE      = -1;    /*!< Invalid Device Handle */

     /* 
     ** Style
     */
     final public static int VIBE_STYLE_SMOOTH                     = 0;     /*!< "Smooth" Style */
     final public static int VIBE_STYLE_STRONG                     = 1;     /*!< "Strong" Style */
     final public static int VIBE_STYLE_SHARP                      = 2;     /*!< "Sharp" Style */
     final public static int VIBE_DEFAULT_STYLE                    = VIBE_STYLE_STRONG;

     /*
     ** Effect type support bit masks
     */
     final public static int VIBE_PERIODIC_EFFECT_SUPPORT           = (1 << VIBE_EFFECT_TYPE_PERIODIC);  /*!< Bitmask for Periodic effect support */
     final public static int VIBE_MAGSWEEP_EFFECT_SUPPORT           = (1 << VIBE_EFFECT_TYPE_MAGSWEEP);  /*!< Bitmask for Magsweepeffect support */
     final public static int VIBE_TIMELINE_EFFECT_SUPPORT           = (1 << VIBE_EFFECT_TYPE_TIMELINE);  /*!< Bitmask for Timeline effect support */
     final public static int VIBE_STREAMING_EFFECT_SUPPORT          = (1 << VIBE_EFFECT_TYPE_STREAMING); /*!< Bitmask for Streaming effect support */

     /*
     ** Effect Style support bit masks
     */
     final public static int VIBE_STYLE_SMOOTH_SUPPORT             = (1 << VIBE_STYLE_SMOOTH); /*!< Bitmask for "Smooth" style support */
     final public static int VIBE_STYLE_STRONG_SUPPORT             = (1 << VIBE_STYLE_STRONG); /*!< Bitmask for "Strong" style support */
     final public static int VIBE_STYLE_SHARP_SUPPORT              = (1 << VIBE_STYLE_SHARP);  /*!< Bitmask for "Sharp" style support */

     /*
     ** Device State constants
     */
     final public static int VIBE_DEVICESTATE_ATTACHED             = (1 << 0); /*!< Device is attached to the system */
     final public static int VIBE_DEVICESTATE_BUSY                 = (1 << 1); /*!< Device is busy (playing effects) */

     /*
     ** Device Actuator Type constants
     */
     final public static int VIBE_DEVACTUATORTYPE_ERM              = 0;
     final public static int VIBE_DEVACTUATORTYPE_BLDC             = 1;
     final public static int VIBE_DEVACTUATORTYPE_LRA              = 2;
     final public static int VIBE_DEVACTUATORTYPE_PIEZO            = 3;
     final public static int VIBE_DEVACTUATORTYPE_PIEZO_WAVE       = 4;

     /*
     ** Device priority values
     */
     final public static int VIBE_MIN_DEVICE_PRIORITY              = 0x0;   /*!< Minimum Device priority */
     final public static int VIBE_MAX_DEV_DEVICE_PRIORITY          = 0x7;   /*!< Maximum Device priority for 3rd-party applications */
     final public static int VIBE_MAX_OEM_DEVICE_PRIORITY          = 0xF;   /*!< Maximum Device priority for OEM applications */
     final public static int VIBE_MAX_DEVICE_PRIORITY              = VIBE_MAX_OEM_DEVICE_PRIORITY;   /*!< Maximum Device priority */
     final public static int VIBE_DEVPRIORITY_DEFAULT              = VIBE_MIN_DEVICE_PRIORITY;     /*!< Default Device priority */

     /*
     ** Streaming Sample
     */
     final public static int VIBE_MAX_STREAMING_SAMPLE_SIZE        = 255;           /*!< Maximum size for streaming sample */

     /*
     ** Effect States constants 
     */
     final public static int VIBE_EFFECT_STATE_NOT_PLAYING         = 0;     /*!< Not Playing and not paused */
     final public static int VIBE_EFFECT_STATE_PLAYING             = 1;     /*!< Playing */
     final public static int VIBE_EFFECT_STATE_PAUSED              = 2;     /*!< Paused */

     /*
     ** Error codes
     */
     final public static int  VIBE_S_SUCCESS                       = 0;     /*!< Success */
     final public static int  VIBE_S_FALSE                         = 0;     /*!< False */
     final public static int  VIBE_S_TRUE                          = 1;     /*!< True*/
     final public static int  VIBE_W_NOT_PLAYING                   = 1;     /*!< Effect is not playing */
     final public static int  VIBE_W_INSUFFICIENT_PRIORITY         = 2;     /*!< Effect doesn't have enough priority to play: higher priority effect is playing on the device */
     final public static int  VIBE_W_EFFECTS_DISABLED              = 3;     /*!< Effects are disabled on the device */
     final public static int  VIBE_W_NOT_PAUSED                    = 4;     /*!< The ImmVibeResumePausedEffect function cannot resume an effect that is not paused */
     final public static int  VIBE_E_ALREADY_INITIALIZED           = -1;    /*!< The API is already initialized (NOT USED) */
     final public static int  VIBE_E_NOT_INITIALIZED               = -2;    /*!< The API is already is not initialized */
     final public static int  VIBE_E_INVALID_ARGUMENT              = -3;    /*!< Invalid argument was used in a API function call */
     final public static int  VIBE_E_FAIL                          = -4;    /*!< Generic error */
     final public static int  VIBE_E_INCOMPATIBLE_EFFECT_TYPE      = -5;    /*!< Incompatible Effect type has been passed into  API function call */
     final public static int  VIBE_E_INCOMPATIBLE_CAPABILITY_TYPE  = -6;    /*!< Incompatible Capability type was used into one of the following functions */
                                                                                          /*
                                                                                          -VibeGetDeviceCapabilityString
                                                                                          -VibeSetDeviceCapabilityString
                                                                                          -VibeGetDeviceCapabilityBool
                                                                                          -VibeSetDeviceCapabilityBool
                                                                                          -VibeGetDeviceCapabilityInt32
                                                                                          -VibeSetDeviceCapabilityInt32
                                                                                          */
     final public static int  VIBE_E_INCOMPATIBLE_PROPERTY_TYPE    = -7;    /*!< Incompatible Property type was used into one of the following functions */
                                                                                          /*
                                                                                          -VibeSetDevicePropertyString 
                                                                                          -VibeSetDevicePropertyBool
                                                                                          -VibeSetDevicePropertyInt32 
                                                                                          */
     final public static int  VIBE_E_DEVICE_NEEDS_LICENSE          = -8;    /*!< The API function call is not available on Lite mode */
     final public static int  VIBE_E_NOT_ENOUGH_MEMORY             = -9;    /*!< The API function cannot allocate memory to complete the process */
     final public static int  VIBE_E_SERVICE_NOT_RUNNING           = -10;   /*!< ImmVibeService is not running */
     final public static int  VIBE_E_INSUFFICIENT_PRIORITY         = -11;   /*!< Not enough priority to achieve the request (insufficient license key priority) */
     final public static int  VIBE_E_SERVICE_BUSY                  = -12;   /*!< ImmVibeService is busy and failed to complete the request */

     /** Effect indexes in the API's internal IVT file */
     final public static int  VIBE_BUILTINEFFECT_SHORT_ON_SHORT_OFF     = 0;
     final public static int  VIBE_BUILTINEFFECT_SHORT_ON_MEDIUM_OFF    = 1;
     final public static int  VIBE_BUILTINEFFECT_SHORT_ON_LONG_OFF      = 2;
     final public static int  VIBE_BUILTINEFFECT_MEDIUM_ON_SHORT_OFF    = 3;
     final public static int  VIBE_BUILTINEFFECT_MEDIUM_ON_MEDIUM_OFF   = 4;
     final public static int  VIBE_BUILTINEFFECT_MEDIUM_ON_LONG_OFF     = 5;
     final public static int  VIBE_BUILTINEFFECT_LONG_ON_SHORT_OFF      = 6;
     final public static int  VIBE_BUILTINEFFECT_LONG_ON_MEDIUM_OFF     = 7;
     final public static int  VIBE_BUILTINEFFECT_LONG_ON_LONG_OFF       = 8;
     final public static int  VIBE_BUILTINEFFECT_SHORT                  = 9;
     final public static int  VIBE_BUILTINEFFECT_MEDIUM                 = 10;
     final public static int  VIBE_BUILTINEFFECT_LONG                   = 11;

     /*
     ** Functions
     */
     public int getDeviceCount()
     {
    	 return GetDeviceCount();
     }
     public int getDeviceState(int nDeviceIndex)
     {
          return GetDeviceState(nDeviceIndex);
     }
     public boolean getDeviceCapabilityBool(int nDeviceIndex, int nDevCapType)
     {
          return GetDeviceCapabilityBool(nDeviceIndex, nDevCapType);
     }
     public int getDeviceCapabilityInt32(int nDeviceIndex, int nDevCapType)
     {
          return GetDeviceCapabilityInt32(nDeviceIndex, nDevCapType);
     }
     public String getDeviceCapabilityString(int nDeviceIndex, int nDevCapType)
     {
          return GetDeviceCapabilityString(nDeviceIndex, nDevCapType);
     }
     public int openDevice(int nDeviceIndex, String strLicenseKey)
     {
          return OpenDevice(nDeviceIndex, strLicenseKey);
     }
     public void closeDevice(int hDeviceHandle)
     {
          CloseDevice(hDeviceHandle);
     }
     public boolean getDevicePropertyBool(int hDeviceHandle, int nDevPropType)
     {
          return GetDevicePropertyBool(hDeviceHandle, nDevPropType);
     }
     public void setDevicePropertyBool(int hDeviceHandle, int nDevPropType, boolean bDevPropValue)
     {
          SetDevicePropertyBool(hDeviceHandle, nDevPropType, bDevPropValue);
     }
     public int getDevicePropertyInt32(int hDeviceHandle, int nDevPropType)
     {
          return GetDevicePropertyInt32(hDeviceHandle, nDevPropType);
     }
     public void setDevicePropertyInt32(int hDeviceHandle, int nDevPropType, int nDevPropValue)
     {
          SetDevicePropertyInt32(hDeviceHandle, nDevPropType, nDevPropValue);
     }
     public String getDevicePropertyString(int hDeviceHandle, int nDevPropType)
     {
          return GetDevicePropertyString(hDeviceHandle, nDevPropType);
     }
     public void setDevicePropertyString(int hDeviceHandle, int nDevPropType, String strDevPropValue)
     {
          SetDevicePropertyString(hDeviceHandle, nDevPropType, strDevPropValue);
     }
     public int getIVTEffectCount(byte pIVT[])        // Effect count is the returned value
     {
          return GetIVTEffectCount(pIVT);
     }
     public String getIVTEffectName(byte pIVT[], int nEffectIndex)
     {
          return GetIVTEffectName(pIVT, nEffectIndex);
     }
     public int getIVTEffectIndexFromName(byte pIVT[], String strEffectName)
     {
          return GetIVTEffectIndexFromName(pIVT, strEffectName);
     }
     public int getIVTEffectType(byte pIVT[], int nEffectIndex)
     {
          return GetIVTEffectType(pIVT, nEffectIndex);
     }
     public int playIVTEffect(int hDeviceHandle, byte pIVT[], int nEffectIndex)
     {
          return PlayIVTEffect(hDeviceHandle, pIVT, nEffectIndex);
     }
     public int playIVTEffectRepeat(int hDeviceHandle, byte pIVT[], int nEffectIndex, byte nRepeat)
     {
          return PlayIVTEffectRepeat(hDeviceHandle, pIVT, nEffectIndex, nRepeat);
     }
     public int playBuiltInEffect(int hDeviceHandle, int nEffectIndex)
     {
          return PlayBuiltInEffectRepeat(hDeviceHandle, nEffectIndex, (byte)0);
     }
     public int playBuiltInEffectRepeat(int hDeviceHandle, int nEffectIndex, byte nRepeat)
     {
          return PlayBuiltInEffectRepeat(hDeviceHandle, nEffectIndex, nRepeat);
     }
     public void getIVTMagSweepEffectDefinition(byte pIVT[], int nEffectIndex, int pnDuration[], int pnMagnitude[], int pnControlModeType[], int pnAttackTime[], int pnAttackLevel[], int pnFadeTime[], int nFadeLevel[])
     {
          GetIVTMagSweepEffectDefinition(pIVT, nEffectIndex, pnDuration, pnMagnitude, pnControlModeType, pnAttackTime, pnAttackLevel, pnFadeTime, nFadeLevel);
     }
     public void getIVTPeriodicEffectDefinition(byte pIVT[], int nEffectIndex, int pnDuration[], int pnMagnitude[], int pnPeriod[], int pnControlModeType[], int pnAttackTime[], int pnAttackLevel[], int pnFadeTime[], int pnFadeLevel[])
     {
          GetIVTPeriodicEffectDefinition(pIVT, nEffectIndex, pnDuration, pnMagnitude, pnPeriod, pnControlModeType, pnAttackTime, pnAttackLevel, pnFadeTime, pnFadeLevel);
     }
     public int playMagSweepEffect(int hDeviceHandle, int nDuration, int nMagnitude, int nControlModeType, int nAttackTime, int nAttackLevel, int nFadeTime, int nFadeLevel)
     {
          return PlayMagSweepEffect(hDeviceHandle, nDuration, nMagnitude, nControlModeType, nAttackTime, nAttackLevel, nFadeTime, nFadeLevel);
     }
     public int playPeriodicEffect(int hDeviceHandle, int nDuration, int nMagnitude, int nPeriod, int nControlModeType, int nAttackTime, int nAttackLevel, int nFadeTime, int nFadeLevel)
     {
          return PlayPeriodicEffect(hDeviceHandle, nDuration, nMagnitude, nPeriod, nControlModeType, nAttackTime, nAttackLevel, nFadeTime, nFadeLevel);
     }
     public void modifyPlayingMagSweepEffect(int hDeviceHandle, int hEffectHandle, int nDuration, int nMagnitude, int nControlModeType, int nAttackTime, int nAttackLevel, int nFadeTime, int nFadeLevel)
     {
          ModifyPlayingMagSweepEffect(hDeviceHandle, hEffectHandle, nDuration, nMagnitude, nControlModeType, nAttackTime, nAttackLevel, nFadeTime, nFadeLevel);
     }
     public void modifyPlayingPeriodicEffect(int hDeviceHandle, int hEffectHandle, int nDuration, int nMagnitude, int nPeriod, int nControlModeType, int nAttackTime, int nAttackLevel, int nFadeTime, int nFadeLevel)
     {
          ModifyPlayingPeriodicEffect(hDeviceHandle, hEffectHandle, nDuration, nMagnitude, nPeriod, nControlModeType, nAttackTime, nAttackLevel, nFadeTime, nFadeLevel);
     }
     public void stopPlayingEffect(int hDeviceHandle, int hEffect)
     {
          StopPlayingEffect(hDeviceHandle, hEffect);
     }
     public void stopAllPlayingEffects(int hDeviceHandle)
     {
          StopAllPlayingEffects(hDeviceHandle);
     }
     public void saveIVTFile(byte pIVT[], String strPathName)
     {
          SaveIVTFile(pIVT, strPathName);
     }
     public void deleteIVTFile(String strPathName)
     {
          DeleteIVTFile(strPathName);
     }
     public int createStreamingEffect(int hDeviceHandle)
     {
          return CreateStreamingEffect(hDeviceHandle);
     }
     public void destroyStreamingEffect(int hDeviceHandle, int hEffectHandle)
     {
          DestroyStreamingEffect(hDeviceHandle, hEffectHandle);
     }
     public void playStreamingSample(int hDeviceHandle, int hEffectHandle, byte pStreamingSample[], int nSize)
     {
          PlayStreamingSample(hDeviceHandle, hEffectHandle, pStreamingSample, nSize);
     }
     public void playStreamingSampleWithOffset(int hDeviceHandle, int hEffectHandle, byte pStreamingSample[], int nSize, int nOffsetTime)
     {
          PlayStreamingSampleWithOffset(hDeviceHandle, hEffectHandle, pStreamingSample, nSize, nOffsetTime);
     }
     public void pausePlayingEffect(int hDeviceHandle, int hEffectHandle)
     {
          PausePlayingEffect(hDeviceHandle, hEffectHandle);
     }
     public void resumePausedEffect(int hDeviceHandle, int hEffectHandle)
     {
          ResumePausedEffect(hDeviceHandle, hEffectHandle);
     }
     public int getEffectState(int hDeviceHandle, int hEffectHandle)
     {
          return GetEffectState(hDeviceHandle, hEffectHandle);
     }
     public int getIVTEffectDuration(byte pIVT[], int nEffectIndex)
     {
          return GetIVTEffectDuration(pIVT, nEffectIndex);
     }
     public void initialize()
     {
          Initialize();
     }
     public void terminate()
     {
          Terminate();
     }

     public VibeTonz()
     {
     }

     static 
     {
          System.loadLibrary("ImmVibeJ");
     }

     private native int GetDeviceCount();
     private native int GetDeviceState(int nDeviceIndex);
     private native boolean GetDeviceCapabilityBool(int nDeviceIndex, int nDevCapType);
     private native int GetDeviceCapabilityInt32(int nDeviceIndex, int nDevCapType);
     private native String GetDeviceCapabilityString(int nDeviceIndex, int nDevCapType);
     private native int OpenDevice(int nDeviceIndex, String strLicenseKey);
     private native void CloseDevice(int hDeviceHandle);
     private native boolean GetDevicePropertyBool(int hDeviceHandle, int nDevPropType);
     private native void SetDevicePropertyBool(int hDeviceHandle, int nDevPropType, boolean bDevPropValue);
     private native int GetDevicePropertyInt32(int hDeviceHandle, int nDevPropType);
     private native void SetDevicePropertyInt32(int hDeviceHandle, int nDevPropType, int nDevPropValue);
     private native String GetDevicePropertyString(int hDeviceHandle, int nDevPropType);
     private native void SetDevicePropertyString(int hDeviceHandle, int nDevPropType, String strDevPropValue);
     private native int GetIVTEffectCount(byte pIVT[]);
     private native String GetIVTEffectName(byte pIVT[], int nEffectIndex);
     private native int GetIVTEffectIndexFromName(byte pIVT[], String strEffectName);
     private native int GetIVTEffectType(byte pIVT[], int nEffectIndex);
     private native int PlayIVTEffect(int hDeviceHandle, byte pIVT[], int nEffectIndex);
     private native int PlayIVTEffectRepeat(int hDeviceHandle, byte pIVT[], int nEffectIndex, byte nRepeat);
     private native int PlayBuiltInEffectRepeat(int hDeviceHandle, int nEffectIndex, byte nRepeat);
     private native void GetIVTMagSweepEffectDefinition(byte pIVT[], int nEffectIndex, int pnDuration[], int pnMagnitude[], int pnControlModeType[], int pnAttackTime[], int pnAttackLevel[], int pnFadeTime[], int nFadeLevel[]);
     private native void GetIVTPeriodicEffectDefinition(byte pIVT[], int nEffectIndex, int pnDuration[], int pnMagnitude[], int pnPeriod[], int pnControlModeType[], int pnAttackTime[], int pnAttackLevel[], int pnFadeTime[], int pnFadeLevel[]);
     private native int PlayMagSweepEffect(int hDeviceHandle, int nDuration, int nMagnitude, int nControlModeType, int nAttackTime, int nAttackLevel, int nFadeTime, int nFadeLevel);
     private native int PlayPeriodicEffect(int hDeviceHandle, int nDuration, int nMagnitude, int nPeriod, int nControlModeType, int nAttackTime, int nAttackLevel, int nFadeTime, int nFadeLevel);
     private native void ModifyPlayingMagSweepEffect(int hDeviceHandle, int hEffectHandle, int nDuration, int nMagnitude, int nControlModeType, int nAttackTime, int nAttackLevel, int nFadeTime, int nFadeLevel);
     private native void ModifyPlayingPeriodicEffect(int hDeviceHandle, int hEffectHandle, int nDuration, int nMagnitude, int nPeriod, int nControlModeType, int nAttackTime, int nAttackLevel, int nFadeTime, int nFadeLevel);
     private native void StopPlayingEffect(int hDeviceHandle, int hEffect);
     private native void StopAllPlayingEffects(int hDeviceHandle);
     private native void SaveIVTFile(byte pIVT[], String strPathName);
     private native void DeleteIVTFile(String strPathName);
     private native int CreateStreamingEffect(int hDeviceHandle);
     private native void DestroyStreamingEffect(int hDeviceHandle, int hEffectHandle);
     private native void PlayStreamingSample(int hDeviceHandle, int hEffectHandle, byte pStreamingSample[], int nSize);
     private native void PlayStreamingSampleWithOffset(int hDeviceHandle, int hEffectHandle, byte pStreamingSample[], int nSize, int nOffsetTime);
     private native void PausePlayingEffect(int hDeviceHandle, int hEffectHandle);
     private native void ResumePausedEffect(int hDeviceHandle, int hEffectHandle);
     private native int GetEffectState(int hDeviceHandle, int hEffectHandle);
     private native int GetIVTEffectDuration(byte pIVT[], int nEffectIndex);

     private native void Initialize();
     private native void Terminate();

     private int m_nPriority = VIBE_MIN_DEVICE_PRIORITY;
     private int m_nStrength = VIBE_MAX_MAGNITUDE;
     private boolean m_bInitialized = false;
     private int m_hDevice = VIBE_INVALID_DEVICE_HANDLE_VALUE;
     private boolean m_bDisabled = false;
}
