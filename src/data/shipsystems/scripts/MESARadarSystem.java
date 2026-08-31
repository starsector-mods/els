package data.shipsystems.scripts;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.WeaponAPI.WeaponType;
import com.fs.starfarer.api.impl.combat.BaseShipSystemScript;
import org.lazywizard.lazylib.MathUtils;

import java.awt.Color;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class MESARadarSystem extends BaseShipSystemScript {

    private CombatEngineAPI engine;
    private static final float RANGE = 5000f;
    private static final float ACCURACY_BONUS = 0.75f;
    private static final float RANGE_BONUS = 25f;
    private static final float SENSOR_BONUS = 25f;
    private static final Color WEAPON_GLOW = new Color(150, 255, 200, 100);

    private static final EnumSet<WeaponType> WEAPONS_AFFECTED = EnumSet.of(WeaponType.BALLISTIC, WeaponType.ENERGY);

    private final Set<ShipAPI> buffed = new HashSet<>();
    private static final String STATIC_ID = "adeptAWACS";

    @Override
    public void apply(MutableShipStatsAPI myStats, String id, State state, float effectLevel) {
        if (engine != Global.getCombatEngine()) {
            engine = Global.getCombatEngine();
            buffed.clear();
        }
        if (engine == null) return;
        if (!(myStats.getEntity() instanceof ShipAPI thisShip)) return;

        for (ShipAPI ship : engine.getShips()) {
            if (ship == null || !ship.isAlive() || ship.getOwner() != thisShip.getOwner()) {
                continue;
            }

            MutableShipStatsAPI stats = ship.getMutableStats();
            if (stats == null) continue;

            if (MathUtils.getDistance(ship, thisShip) <= RANGE) {
                stats.getAutofireAimAccuracy().modifyFlat(STATIC_ID, ACCURACY_BONUS);
                stats.getBallisticWeaponRangeBonus().modifyPercent(STATIC_ID, RANGE_BONUS);
                stats.getEnergyWeaponRangeBonus().modifyPercent(STATIC_ID, RANGE_BONUS);
                stats.getSensorStrength().modifyPercent(STATIC_ID, SENSOR_BONUS);
                ship.setWeaponGlow(effectLevel, WEAPON_GLOW, WEAPONS_AFFECTED);
                buffed.add(ship);
            } else if (buffed.contains(ship)) {
                stats.getAutofireAimAccuracy().unmodify(STATIC_ID);
                stats.getBallisticWeaponRangeBonus().unmodify(STATIC_ID);
                stats.getEnergyWeaponRangeBonus().unmodify(STATIC_ID);
                stats.getSensorStrength().unmodify(STATIC_ID);
                ship.setWeaponGlow(0f, WEAPON_GLOW, WEAPONS_AFFECTED);
                buffed.remove(ship);
            }
        }
    }

    @Override
    public void unapply(MutableShipStatsAPI myStats, String id) {
        for (ShipAPI ship : buffed) {
            if (ship == null) continue;
            MutableShipStatsAPI stats = ship.getMutableStats();
            if (stats == null) continue;
            stats.getAutofireAimAccuracy().unmodify(STATIC_ID);
            stats.getBallisticWeaponRangeBonus().unmodify(STATIC_ID);
            stats.getEnergyWeaponRangeBonus().unmodify(STATIC_ID);
            stats.getSensorStrength().unmodify(STATIC_ID);
            ship.setWeaponGlow(0f, WEAPON_GLOW, WEAPONS_AFFECTED);
        }
        buffed.clear();
    }

    @Override
    public StatusData getStatusData(int index, State state, float effectLvl) {
        return switch (index) {
            case 0 -> new StatusData("improved fire control", false);
            case 1 -> new StatusData("weapon range +" + (int) (RANGE_BONUS * effectLvl) + "%", false);
            case 2 -> new StatusData("sensor range +" + (int) (SENSOR_BONUS * effectLvl) + "%", false);
            default -> null;
        };
    }
}
