package data.shipsystems.scripts.ai;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipSystemAIScript;
import com.fs.starfarer.api.combat.ShipSystemAPI;
import com.fs.starfarer.api.combat.ShipwideAIFlags;
import com.fs.starfarer.api.util.IntervalUtil;
import org.lazywizard.lazylib.MathUtils;
import org.lwjgl.util.vector.Vector2f;

public class MESARadarAI implements ShipSystemAIScript {

    private ShipSystemAPI system;
    private ShipAPI ship;
    private final IntervalUtil tracker = new IntervalUtil(1f, 1.5f);
    private static final float ACTIV_RANGE = 5000f;

    @Override
    public void init(ShipAPI ship, ShipSystemAPI system, ShipwideAIFlags flags, CombatEngineAPI engine) {
        this.ship = ship;
        this.system = system;
    }

    @Override
    public void advance(float amount, Vector2f missileDangerDir, Vector2f collisionDangerDir, ShipAPI target) {
        tracker.advance(amount);
        if (tracker.intervalElapsed()) {
            CombatEngineAPI engine = Global.getCombatEngine();
            if (engine == null || ship == null || system == null) return;

            int friendlyShipsInRange = 0;
            for (ShipAPI other : engine.getShips()) {
                if (other == null || other == ship || other.isHulk() || !other.isAlive()) {
                    continue;
                }
                if (other.getOwner() == ship.getOwner() && MathUtils.getDistance(other, ship) <= ACTIV_RANGE) {
                    friendlyShipsInRange++;
                }
            }

            if (friendlyShipsInRange > 0 && !system.isActive()) {
                activateSystem();
            } else if (friendlyShipsInRange == 0 && system.isActive()) {
                deactivateSystem();
            }
        }
    }

    private void deactivateSystem() {
        if (system != null && system.isOn() && ship != null) {
            ship.useSystem();
        }
    }

    private void activateSystem() {
        if (system != null && !system.isOn() && ship != null) {
            ship.useSystem();
        }
    }
}
