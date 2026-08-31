package data.shipsystems.scripts;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipEngineControllerAPI.ShipEngineAPI;
import com.fs.starfarer.api.impl.combat.BaseShipSystemScript;
import org.lazywizard.lazylib.MathUtils;
import org.lwjgl.util.vector.Vector2f;

import java.awt.Color;

public class AMInjectorStats extends BaseShipSystemScript {

    private static final float MAX_SPEED_BONUS = 200f;
    private static final float ACCEL_BONUS = 300f;
    private static final float EXTRA_ENGINE_DAMAGE = 100f;
    private static final float MALFUNCTION_CHANCE = 50f;

    private static final float PARTICLE_COUNT_FACTOR = 15f;
    private static final float PARTICLE_DIST_MIN = 5f;
    private static final float PARTICLE_DIST_MAX = 20f;
    private static final float PARTICLE_DURATION = 0.1f;
    private static final float PARTICLE_SIZE_MIN = 15f;
    private static final float PARTICLE_SIZE_MAX = 20f;
    private static final Color PARTICLE_COLOR = new Color(255, 100, 235, 155);

    @Override
    public void apply(MutableShipStatsAPI stats, String id, State state, float effectLevel) {
        if (state == State.OUT) {
            stats.getMaxSpeed().unmodify(id);
        } else {
            stats.getMaxSpeed().modifyPercent(id, MAX_SPEED_BONUS * effectLevel);
            stats.getAcceleration().modifyPercent(id, ACCEL_BONUS * effectLevel);
            stats.getEngineDamageTakenMult().modifyPercent(id, EXTRA_ENGINE_DAMAGE * effectLevel);
            stats.getEngineMalfunctionChance().modifyPercent(id, MALFUNCTION_CHANCE * effectLevel);

            CombatEngineAPI combat = Global.getCombatEngine();
            if (combat != null && !combat.isPaused() && stats.getEntity() instanceof ShipAPI ship) {
                if (ship.getEngineController() != null) {
                    for (ShipEngineAPI engine : ship.getEngineController().getShipEngines()) {
                        if (engine.isActive() && !engine.isSystemActivated()) {
                            int count = Math.round(PARTICLE_COUNT_FACTOR * effectLevel);
                            for (int i = 0; i < count; i++) {
                                float dist = MathUtils.getRandomNumberInRange(PARTICLE_DIST_MIN, PARTICLE_DIST_MAX);
                                float size = MathUtils.getRandomNumberInRange(PARTICLE_SIZE_MIN, PARTICLE_SIZE_MAX);
                                float speed = dist / PARTICLE_DURATION;
                                Vector2f velocity = MathUtils.getRandomPointOnCircumference(ship.getVelocity(), speed);
                                combat.addHitParticle(engine.getLocation(), velocity, size, 1f, PARTICLE_DURATION, PARTICLE_COLOR);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void unapply(MutableShipStatsAPI stats, String id) {
        stats.getMaxSpeed().unmodify(id);
        stats.getAcceleration().unmodify(id);
        stats.getEngineDamageTakenMult().unmodify(id);
        stats.getEngineMalfunctionChance().unmodify(id);
    }

    @Override
    public StatusData getStatusData(int index, State state, float effectLevel) {
        return switch (index) {
            case 0 -> new StatusData("maximum speed +" + Math.round(MAX_SPEED_BONUS * effectLevel) + "%", false);
            case 1 -> new StatusData("acceleration +" + Math.round(ACCEL_BONUS * effectLevel) + "%", false);
            case 2 -> new StatusData("engine damage +" + Math.round(EXTRA_ENGINE_DAMAGE * effectLevel) + "%", false);
            case 3 -> new StatusData("engine malfunction chance +" + Math.round(MALFUNCTION_CHANCE * effectLevel) + "%", false);
            default -> null;
        };
    }
}
