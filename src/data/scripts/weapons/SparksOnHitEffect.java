package data.scripts.weapons;

import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.CombatEntityAPI;
import com.fs.starfarer.api.combat.DamagingProjectileAPI;
import com.fs.starfarer.api.combat.OnHitEffectPlugin;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.listeners.ApplyDamageResultAPI;
import org.lazywizard.lazylib.MathUtils;
import org.lwjgl.util.vector.Vector2f;

import java.awt.Color;

public abstract class SparksOnHitEffect implements OnHitEffectPlugin {

    protected abstract float getSparkChance();
    protected abstract Color getSparkColor();
    protected abstract float getParticleSize();
    protected abstract float getParticleBrightness();
    protected abstract float getParticleDuration();
    protected abstract int getParticleCount();
    protected abstract float getConeAngle();
    protected abstract float getMinVelocity();
    protected abstract float getMaxVelocity();

    @Override
    public void onHit(DamagingProjectileAPI projectile,
                      CombatEntityAPI target,
                      Vector2f point,
                      boolean shieldHit,
                      ApplyDamageResultAPI damageResult,
                      CombatEngineAPI engine) {

        if (target instanceof ShipAPI && !shieldHit && Math.random() <= getSparkChance()) {
            float speed = projectile.getVelocity().length();
            float facing = projectile.getFacing();
            float halfAngle = getConeAngle() / 2f;

            for (int i = 0; i < getParticleCount(); i++) {
                float angle = MathUtils.getRandomNumberInRange(facing - halfAngle, facing + halfAngle);
                float velocity = MathUtils.getRandomNumberInRange(speed * getMinVelocity(), speed * getMaxVelocity());
                Vector2f vector = MathUtils.getPointOnCircumference(null, velocity, angle);
                engine.addHitParticle(point, vector, getParticleSize(), getParticleBrightness(), getParticleDuration(), getSparkColor());
            }
        }
    }
}
