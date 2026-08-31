package data.scripts.weapons;

import java.awt.Color;

public class StingerSparks extends SparksOnHitEffect {

    private static final Color SPARK_COLOR = new Color(255, 175, 100, 255);

    @Override
    protected float getSparkChance() {
        return 0.25f;
    }

    @Override
    protected Color getSparkColor() {
        return SPARK_COLOR;
    }

    @Override
    protected float getParticleSize() {
        return 5f;
    }

    @Override
    protected float getParticleBrightness() {
        return 255f;
    }

    @Override
    protected float getParticleDuration() {
        return 0.5f;
    }

    @Override
    protected int getParticleCount() {
        return 1;
    }

    @Override
    protected float getConeAngle() {
        return 90f;
    }

    @Override
    protected float getMinVelocity() {
        return 0.07f;
    }

    @Override
    protected float getMaxVelocity() {
        return 0.175f;
    }
}
