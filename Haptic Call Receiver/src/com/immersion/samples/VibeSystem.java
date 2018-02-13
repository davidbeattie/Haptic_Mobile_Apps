/*
** =========================================================================
** Copyright (c) 2010  Immersion Corporation.  All rights reserved.
**                     Immersion Corporation Confidential and Proprietary
**
** File:
**     VibeSystem.java
**
** Description: 
**     ImmVibe sample application for Android.
**
** =========================================================================
*/

package com.immersion.samples;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.immersion.Device;
import com.immersion.EffectHandle;
import com.immersion.IVTBuffer;
import com.immersion.ImmVibe;
import com.immersion.MagSweepEffectDefinition;
import com.immersion.PeriodicEffectDefinition;
import com.immersion.VibeTonz;

import android.app.Activity;
import android.content.Context;
import android.os.Vibrator;
import android.widget.Toast;

/**
 * This class provides a fallback mechanism for projects that
 * support handsets with advanced vibration support and will
 * still function in some capacity on handsets with other
 * vibration APIs. This class DOES NOT wrap app functions
 * of the various APIs. Instead this class abstracts out
 * what is useful for this particular set of examples across
 * various vibration APIs to support a wider set of phones.<p>
 * 
 * Add new vibration functionality directly to this class
 * rather than subclassing.<p>
 * 
 * There are similarly-named functions to the Android life cycle
 * functions onCreate, onDestroy, onPause and onResume that
 * should be called from every Activity that uses vibration.
 * It is very important that each of these be called at the
 * appropriate time to properly manage the vibration state.
 */
public class VibeSystem
{
    public static final int INVALID_EFFECT_HANDLE_VALUE = -1;
    
    // VibeTonz system support
    private VibeTonz m_vibetonz = null;
    private String m_license = "XKNS7KYGREPRY2MATZ7VRFLRSZK74DT2";
    private String m_licenseEmulator = "<available in SDK documentation>";
    private int m_hDeviceHandle = VibeTonz.VIBE_INVALID_DEVICE_HANDLE_VALUE;
    private int m_hEffectPeriodic = VibeTonz.VIBE_INVALID_EFFECT_HANDLE_VALUE;
    private int m_hEffectMagSweep = VibeTonz.VIBE_INVALID_EFFECT_HANDLE_VALUE;
    private byte[] m_ivt = null;
    
    // Google Android Vibrator API support
    private Vibrator m_vibrator = null;
    private long[][] m_vibratorPatterns = null;
    private String[] m_vibratorPatternNames = null;
    private int m_hVibratorPattern = -1;
    private long[] m_vibratorPeriodicPattern = {0, 0}; // off time, on time
    private long m_vibratorNextTime = Long.MAX_VALUE;
    
    /**
     * Sets the VibeTonz effect set to be used on handsets with the VibeTonz Player.
     * 
     * @param	ivt	a byte array containing the effect data (IVT format) exported from Immersion TouchSense Studio
     */
    public void setEffects(byte[] ivt)
    {
        if (m_vibetonz != null)
        {
            m_ivt = ivt;            
        }
    }
    
    /**
     * Sets the Android effect set to be used when the VibeTonz Player is not available.
     * The length of the arrays must be the same or both parameters must be null.
     * 
     * @param	patterns	an array of long arrays containing on/off pulse trains
     * @param	names		an array of Strings containing the names of the effects
     * @throws	IllegalArgumentException	The arrays are not the same length
     */
    public void setEffects(long[][] patterns, String[] names)
    {
        // verify size of buffers are the same size
        try
        {
            if (patterns.length != names.length)
                throw new IllegalArgumentException("Arrays must be same length");
        }
        catch (NullPointerException npe)
        {
            if (!(patterns == null && names == null))
                throw new IllegalArgumentException("Arrays must be same length");
        }
        
        m_vibratorPatterns = patterns;
        m_vibratorPatternNames = names;
    }
    
    /**
     * Call this function in your Activity's onCreate function to properly
     * set up the vibration system(s) during the Android lifecyle. This function
     * attempts to load the best available vibration rendering system and if
     * that is not available falls back to the next available system.
     * 
     *  @param	activity	Reference to the calling activity
     */
    public void onCreate(Activity activity)
    {
        // try VibeTonz (Behold 2 and older handsets)
        try
        {
            m_vibetonz = new VibeTonz();
            m_vibetonz.initialize();
            return;
        }
        catch (RuntimeException re) {}
        catch (UnsatisfiedLinkError ule) {}
        catch (NoClassDefFoundError ncdfe) {}
        catch (ExceptionInInitializerError eiie) {}
        
        m_vibetonz = null;

        // if all else fails, try Android Vibrator
        m_vibrator = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
    }
    
    /**
     * Call this function in your Activity's onDestroy function to properly
     * shut down the vibration system(s) during the Android lifecyle.
     * 
     *  @param	activity	Reference to the calling activity
     */
    public void onDestroy(Activity activity)
    {
        if (m_vibetonz != null)
        {
            try { m_vibetonz.terminate(); }
            catch (RuntimeException re) {}
        }
        else if (m_vibrator != null)
        {
            m_vibrator = null;
        }
    }
    
    /**
     * Call this function in your Activity's onResume function to properly
     * acquire resources for the vibration system(s) during the Android lifecyle.
     * The VibeTonz system has a finite number of device handles so these are
     * opened and closed on onResume and onPause to minimize the number of
     * handles in use.
     * 
     *  @param	activity	Reference to the calling activity
     */
    public void onResume(Activity activity)
    {
        if (m_vibetonz != null)
        {
            // try the handset license key
            try { m_hDeviceHandle = m_vibetonz.openDevice(0, m_license); }
            catch (RuntimeException re)
            {
                // try the emulator license key
                try { m_hDeviceHandle = m_vibetonz.openDevice(0, m_licenseEmulator); }
                catch (RuntimeException re2)
                {
                    // debug message - failure to open a device frequently happens 
                    // during development if the application crashes often and is
                    // forcibly terminated by the debugger. If this happens around 16
                    // times on a VibeTonz device, it will no longer be possible to
                    // open a device handle until the handset is reset.
                    Toast.makeText(activity, "Failed VibeTonz openDevice (reset may be needed)", Toast.LENGTH_LONG).show();
                    
                    // stop using the VibeTonz system
                    m_vibetonz.terminate();
                    m_vibetonz = null;
                    
                    // use Android Vibrator service instead
                    m_vibrator = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
                }
            }
        }
        else if (m_vibrator != null)
        {
            /* FIXME: can cause crashes later if pattern[0] = 0 and pattern[1] = 1
            // resume vibration
            if ((m_hVibratorPattern == -2 || m_hVibratorPattern == -3) && m_vibratorNextTime != Long.MAX_VALUE) {
                m_vibratorPeriodicPattern[0] = m_vibratorNextTime - m_vibratorLastTime;
                m_vibrator.vibrate(m_vibratorPeriodicPattern, 0);
                m_vibratorLastTime = System.currentTimeMillis();
            }
            */
        }
    }
    
    /**
     * Call this function in your Activity's onPause function to properly
     * to do the right thing (like stop playing effects).
     * 
     *  @param	activity	Reference to the calling activity
     */
    public void onPause(Activity activity)
    {
        if (m_vibetonz != null)
        {
            try { m_vibetonz.stopAllPlayingEffects(m_hDeviceHandle); }
            catch (RuntimeException re) {}
            
            // close device because VibeTonz devices have finite
            // device handles shared amongst all applications
            try { m_vibetonz.closeDevice(m_hDeviceHandle); }
            catch (RuntimeException re) {}
        }
        else if (m_vibrator != null)
        {
            m_vibrator.cancel();
        }
    }
    
    /**
     * Get the names of all effects in the the effect data.
     * IVT data includes the effect names in the data. For Android,
     * the effect names are set as one of the parameters to setEffects.
     */
    public String[] getEffectNames()
    {
        int effectCount = 0;
        String[] effects = null;
        
        if (m_vibetonz != null)
        {
            if (m_ivt != null)
            {
                // get number of effects in IVT data
                try { effectCount = m_vibetonz.getIVTEffectCount(m_ivt); }
                catch (RuntimeException re) {}
                
                // allocate the buffer
                effects = new String[effectCount];
                
                // copy the names to the buffer
                for (int i = 0; i < effectCount; i++)
                {
                    try { effects[i] = m_vibetonz.getIVTEffectName(m_ivt, i); }
                    catch (RuntimeException re) {}
                }
            }
        }
        else if (m_vibrator != null)
        {
            if (m_vibratorPatternNames != null)
            {
                // get number of effect patterns
                effectCount = m_vibratorPatternNames.length;
                
                // allocate the buffer
                effects = new String[effectCount];
                
                // copy the names into the buffer
                for (int i = 0; i < effectCount; i++)
                {
                    effects[i] = m_vibratorPatternNames[i];
                }
            }
        }
        
        // return immutable result
        return effects;
    }
    
    /**
     * Play the indexed effect using the best available vibration system
     * using fallbacks if not available. This function stops any previously-
     * playing effect. In most cases overlapping effects are not desirable
     * though the TouchSense APIs are capable of doing so.
     * 
     * @param	index	0-based index of the effect with the current effect set
     */
    public int playEffect(int index)
    {
        int hPlayingEffect = INVALID_EFFECT_HANDLE_VALUE;
        
        if (m_vibetonz != null)
        {
            // basis effects can be played simultaneously which may cause
            // undesirable results if the programmer is unaware of that.
            // stop all effects before playing another effect.
            try { m_vibetonz.stopAllPlayingEffects(m_hDeviceHandle); }
            catch (RuntimeException re) {}
            
            // play the indexed effect
            try { hPlayingEffect = m_vibetonz.playIVTEffect(m_hDeviceHandle, m_ivt, index); }
            catch (RuntimeException re) {}	
        }
        else if (m_vibrator != null)
        {
            // play the vibration pattern (stopping the previous pattern)
            if (m_vibratorPatterns != null && m_vibratorPatterns[index] != null)
            {
                m_vibrator.vibrate(m_vibratorPatterns[index], -1);
                
                m_vibratorNextTime = System.currentTimeMillis() + getDuration(index);
                
                m_hVibratorPattern = index;
                
                hPlayingEffect = index;
             }
        }
        
        return hPlayingEffect;
    }
    
    /**
     * Play a MagSweep effect type, falling back to a VERY crude representation
     * if the handset does not support that effect type. The fallback is very
     * crude and is only here for demo purposes.
     * 
     * @param   magnitude   [0,1] normalized magnitude. 0 = minimum magnitude, 1 = maximum magnitude
     * @param   duration    the length of time to play the effect or -1 to play continuously. This
     *                      parameter is not fully implemented for Android (any value other than -1 will
     *                      play the effect pattern only once.
     */
    public void playMagSweep(float magnitude, int duration)
    {
        // note - ImmVibe can do multi-actuator effects which is not used in this demo
        if (m_vibetonz != null)
        {
            if (m_hEffectMagSweep == VibeTonz.VIBE_INVALID_EFFECT_HANDLE_VALUE || duration != -1)
            {
                try { m_hEffectMagSweep = m_vibetonz.playMagSweepEffect(m_hDeviceHandle, (duration == -1 ? VibeTonz.VIBE_TIME_INFINITE : duration), (int) (VibeTonz.VIBE_MAX_MAGNITUDE * magnitude), VibeTonz.VIBE_STYLE_STRONG, 0, 0, 0, 0); }
                catch (RuntimeException re) {}
            }
            else
            {
                try { m_vibetonz.modifyPlayingMagSweepEffect(m_hDeviceHandle, m_hEffectMagSweep, (duration == -1 ? VibeTonz.VIBE_TIME_INFINITE : duration), (int) (VibeTonz.VIBE_MAX_MAGNITUDE * magnitude), VibeTonz.VIBE_STYLE_STRONG, 0, 0, 0, 0); }
                catch (RuntimeException re) {}
            }
        }
        else if (m_vibrator != null)
        {
            // no magnitude control so just play a constant buzz
            m_vibratorPeriodicPattern[0] = 0;
            m_vibratorPeriodicPattern[1] = (duration <= 0 ? 25 : duration);
            m_vibrator.vibrate(m_vibratorPeriodicPattern, (duration == -1 ? 0 : -1));
            m_hVibratorPattern = -3;
        }
    }
    
    /**
     * Play a Periodic effect type, falling back to a VERY crude representation
     * if the handset does not support that effect type. The fallback is very
     * crude and is only here for demo purposes.
     * 
     * @param	magnitude	[0,1] normalized magnitude. 0 = minimum magnitude, 1 = maximum magnitude
     * @param	period		milliseconds of the period of the pulsing waveform
     * @param	duration	the length of time to play the effect or -1 to play continuously. This
     * 						parameter is not fully implemented for Android (any value other than -1 will
     * 						play the effect pattern only once.
     * @return	a handle to the playing effect which can be passed to other functions
     * 			like isPlaying. 
     */
    public int playPeriodic(float magnitude, int period, int duration)
    {
        // note - ImmVibe can do multi-actuator effects which is not used in this demo
        if (m_vibetonz != null)
        {
            if (m_hEffectPeriodic == VibeTonz.VIBE_INVALID_EFFECT_HANDLE_VALUE)
            {
                try { m_hEffectPeriodic = m_vibetonz.playPeriodicEffect(m_hDeviceHandle, (duration == -1 ? VibeTonz.VIBE_TIME_INFINITE : duration), (int) (VibeTonz.VIBE_MAX_MAGNITUDE * magnitude), period, VibeTonz.VIBE_STYLE_SHARP, 0, 0, 0, 0); }
                catch (RuntimeException re) {}
            }
            else
            {
                try { m_vibetonz.modifyPlayingPeriodicEffect(m_hDeviceHandle, m_hEffectPeriodic, (duration == -1 ? VibeTonz.VIBE_TIME_INFINITE : duration), (int) (VibeTonz.VIBE_MAX_MAGNITUDE * magnitude), period, VibeTonz.VIBE_STYLE_SHARP, 0, 0, 0, 0); }
                catch (RuntimeException re) {}
            }
        }
        else if (m_vibrator != null)
        {
            // no magnitude control and duration is annoying to implement
            // so just use period and let caller stop the effect
            long now = System.currentTimeMillis();
            if (now > m_vibratorNextTime)
            {
                int half = (period < 2 ? 0 : period / 2);
                m_vibratorPeriodicPattern[0] = half;
                m_vibratorPeriodicPattern[1] = half;
                m_vibrator.vibrate(m_vibratorPeriodicPattern, (duration == -1 ? 0 : -1));
                m_vibratorNextTime = now + m_vibratorPeriodicPattern[0] + m_vibratorPeriodicPattern[1];
                m_hEffectPeriodic = -2;
                m_hVibratorPattern = -2;
            }
        }
        
        return m_hEffectPeriodic;
    }
    
    /**
     * Stop all playing effects
     */
    public void stopEffects()
    {
        if (m_vibetonz != null)
        {
            try { m_vibetonz.stopAllPlayingEffects(m_hDeviceHandle); }
            catch (RuntimeException re) {}
            
            m_hEffectPeriodic = VibeTonz.VIBE_INVALID_EFFECT_HANDLE_VALUE;
            m_hEffectMagSweep = VibeTonz.VIBE_INVALID_EFFECT_HANDLE_VALUE;
        }
        else if (m_vibrator != null)
        {
            m_vibrator.cancel();
            
            m_vibratorNextTime = Long.MAX_VALUE;          
            m_hVibratorPattern = -1;
        }
    }
    
    /**
     * Get the duration of the indexed effect in the effect set
     * 
     * @param index 0-based index of the effect within the current effect set
     * @return the duration of the indexed effect
     */
    public int getDuration(int index)
    {
        int duration = 0;
        
        if (m_vibetonz != null)
        {
            try { duration = m_vibetonz.getIVTEffectDuration(m_ivt, index); }
            catch (RuntimeException re) {}
        }
        else if (m_vibrator != null)
        {
            long[] pattern = m_vibratorPatterns[index];
            
            if (pattern != null)
            {
                for (int i = 0, count = pattern.length; i < count; i++)
                {
                    duration += (int) pattern[i];
                }
            }
        }
        
        return duration;
    }
    
    /**
     * Get the status of a previously-played effect
     * 
     * @param hPlayingEffect the handle returned by playEffect
     * @return true if the effect is currently playing
     */
    public boolean isPlaying(int hPlayingEffect)
    {
        if (m_vibetonz != null)
        {
            try { return VibeTonz.VIBE_EFFECT_STATE_PLAYING == m_vibetonz.getEffectState(m_hDeviceHandle, hPlayingEffect); }
            catch (RuntimeException re) {}
        }
        else if (m_vibrator != null)
        {
            return hPlayingEffect == m_hVibratorPattern && System.currentTimeMillis() < m_vibratorNextTime;
        }
        
        return false;
    }
}
