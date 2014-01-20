package sps.display.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import sps.core.Logger;
import sps.core.SpsConfig;

//Modified from: https://github.com/mattdesl/lwjgl-basics/wiki/LibGDX-Brightness-&-Contrast
public class ShaderBatch extends SpriteBatch {

    private static final int GLSL_VALUE_NOT_PRESENT = -1;
    private static boolean __shaderCapabilitiesDisplayed = false;
    private static final String __brightnessId = "u_brightness";
    private static final String __contrastId = "u_contrast";
    private static final String __saturationId = "u_saturation";

    private float _brightness = 0f;
    private float _contrast = 1f;
    private float _saturation = 0f;

    private boolean _brightnessControlsSupported;
    private boolean _contrastControlsSupported;
    private boolean _saturationControlsSupported;

    private ShaderProgram _shaders;

    public ShaderBatch(ShaderProgram shaders) {
        _shaders = shaders;
        _brightnessControlsSupported = _shaders.getUniformLocation(__brightnessId) != GLSL_VALUE_NOT_PRESENT;
        _contrastControlsSupported = _shaders.getUniformLocation(__contrastId) != GLSL_VALUE_NOT_PRESENT;
        _saturationControlsSupported = _shaders.getUniformLocation(__saturationId) != GLSL_VALUE_NOT_PRESENT;

        pushUniforms();

        if (!__shaderCapabilitiesDisplayed) {
            if (SpsConfig.get().displayLoggingEnabled) {
                Logger.info("Shaders initialized. Supported variables:  (Brightness: " + _brightnessControlsSupported + ", Contrast: " + _contrastControlsSupported + ", Saturation: " + _saturationControlsSupported + ")");
            }
            __shaderCapabilitiesDisplayed = true;
        }

        setShader(_shaders);
    }

    public void begin() {
        super.begin();
    }

    public void pushUniforms() {
        setBrightness(_brightness);
        setContrast(_contrast);
        setSaturation(_saturation);
    }

    private void setShaderUniform(final String id, final float value, final boolean supported) {
        if (supported) {
            int location = _shaders.getUniformLocation(id);
            _shaders.setUniformf(location, value);
        }
    }

    public void setBrightness(float brightness) {
        _brightness = brightness;
        setShaderUniform(__brightnessId, _brightness, _brightnessControlsSupported);
    }

    public void setContrast(float contrast) {
        _contrast = contrast;
        setShaderUniform(__contrastId, _contrast, _contrastControlsSupported);
    }

    public void setSaturation(float saturation) {
        _saturation = saturation;
        setShaderUniform(__saturationId, _saturation, _saturationControlsSupported);
    }
}