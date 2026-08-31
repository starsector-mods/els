package data.scripts.weapons;

import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.DamagingProjectileAPI;
import com.fs.starfarer.api.combat.EveryFrameWeaponEffectPlugin;
import com.fs.starfarer.api.combat.WeaponAPI;
import org.lazywizard.lazylib.MathUtils;
import org.lazywizard.lazylib.VectorUtils;
import org.lwjgl.util.vector.Vector2f;

import java.awt.Color;

public class PositronBlasterParticleFX implements EveryFrameWeaponEffectPlugin {

    private static final float ANGLE_MAX = 30f;
    private static final float PARTICLE_DURATION = 0.275f;
    private static final Color PARTICLE_COLOR = new Color(255, 100, 235, 120);
    private static final float PARTICLE_VELOCITY_SCALE = 0.25f;

    @Override
    public void advance(float amount, CombatEngineAPI engine, WeaponAPI weapon) {
        if (engine == null || engine.isPaused() || weapon == null || weapon.getShip() == null) return;

        if (Math.random() > 0.15f + amount) {
            for (DamagingProjectileAPI projectile : engine.getProjectiles()) {
                if (projectile == null) continue;
                if (!projectile.isFading() && !projectile.didDamage()
                        && "els_positron_bolt".equals(projectile.getProjectileSpecId())
                        && projectile.getSource() == weapon.getShip()) {

                    float angleOffset = MathUtils.getRandomNumberInRange(-ANGLE_MAX, ANGLE_MAX);
                    Vector2f vel = projectile.getVelocity();
                    Vector2f negVel = new Vector2f(-vel.x * PARTICLE_VELOCITY_SCALE, -vel.y * PARTICLE_VELOCITY_SCALE);
                    VectorUtils.rotate(negVel, angleOffset, negVel);

                    float size = MathUtils.getRandomNumberInRange(8f, 14f);
                    engine.addHitParticle(
                            projectile.getLocation(),
                            negVel,
                            size,
                            1f,
                            PARTICLE_DURATION,
                            PARTICLE_COLOR
                    );
                }
            }
        }
    }
}
