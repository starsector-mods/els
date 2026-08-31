package data.scripts.weapons;

import com.fs.starfarer.api.combat.BeamAPI;
import com.fs.starfarer.api.combat.BeamEffectPlugin;
import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.DamageType;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.util.IntervalUtil;
import com.fs.starfarer.api.util.Misc;
import org.lazywizard.lazylib.MathUtils;
import org.lwjgl.util.vector.Vector2f;

import java.awt.Color;

public class HMaserBeamEffect implements BeamEffectPlugin {

    private static final Color BASE_COLOR_1 = new Color(250, 128, 172);
    private static final Color BASE_COLOR_2 = new Color(250, 220, 172);
    private static final float PARTICLE_DURATION = 0.6f;

    private final IntervalUtil fireInterval = new IntervalUtil(0.25f, 1.75f);

    @Override
    public void advance(float amount, CombatEngineAPI engine, BeamAPI beam) {
        if (beam == null || engine == null) return;

        if (beam.getDamageTarget() instanceof ShipAPI ship && beam.getBrightness() >= 1f) {
            fireInterval.advance(beam.getDamage().getDpsDuration());

            if (ship.getShield() == null || !ship.getShield().isWithinArc(beam.getTo())) {
                Vector2f to = beam.getTo();
                Vector2f from = beam.getFrom();
                Vector2f dir = Vector2f.sub(to, from, new Vector2f());
                if (dir.lengthSquared() > 0f) {
                    dir.normalise();
                }
                dir.scale(5f);

                Vector2f point = Vector2f.sub(to, dir, new Vector2f());
                float angleTo = (float) Math.toDegrees(Math.atan2(point.y - dir.y, point.x - dir.x));

                float pSpeed = MathUtils.getRandomNumberInRange(20f, 100f);
                float pAngle = MathUtils.getRandomNumberInRange(angleTo - 20f, angleTo + 20f);

                Color particleColor = Misc.interpolateColor(BASE_COLOR_1, BASE_COLOR_2, (float) Math.random());
                Vector2f pVel = MathUtils.getPointOnCircumference(null, pSpeed, pAngle);

                engine.addHitParticle(
                        point,
                        pVel,
                        MathUtils.getRandomNumberInRange(5f, 9f),
                        beam.getBrightness(),
                        PARTICLE_DURATION,
                        particleColor
                );

                if (fireInterval.intervalElapsed()) {
                    engine.applyDamage(
                            ship,
                            point,
                            beam.getDamage().getDamage(),
                            DamageType.ENERGY,
                            0f,
                            false,
                            false,
                            beam.getSource()
                    );
                }
            }
        }
    }
}
