package data.util;

public class ELSAnimationUtils {

    public static float map(float inMin, float inMax, float outMin, float outMax, float x) {
        if (inMax == inMin) return outMin;
        return (x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin;
    }
}
