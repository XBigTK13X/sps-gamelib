#ifdef GL_ES
    precision mediump float;
#endif
varying vec4 v_color;
varying vec2 v_texCoords;

uniform sampler2D u_texture;

uniform float u_brightness;
uniform float u_contrast;
uniform float u_saturation;

void main() {
    vec4 color = v_color * texture2D(u_texture, v_texCoords);
	color += u_brightness;
    gl_FragColor = color;
}