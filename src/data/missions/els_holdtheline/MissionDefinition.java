package data.missions.els_holdtheline;

import com.fs.starfarer.api.fleet.FleetGoal;
import com.fs.starfarer.api.fleet.FleetMemberType;
import com.fs.starfarer.api.mission.FleetSide;
import com.fs.starfarer.api.mission.MissionDefinitionAPI;
import com.fs.starfarer.api.mission.MissionDefinitionPlugin;

public class MissionDefinition implements MissionDefinitionPlugin {

    @Override
    public void defineMission(MissionDefinitionAPI api) {
        api.initFleet(FleetSide.PLAYER, "ISS", FleetGoal.ATTACK, false);
        api.initFleet(FleetSide.ENEMY, "HSS", FleetGoal.ATTACK, true);

        api.setFleetTagline(FleetSide.PLAYER, "Long Range Picket Group K21");
        api.setFleetTagline(FleetSide.ENEMY, "Luddic Path Raiding Fleet");

        api.addBriefingItem("Survival is no longer a tactical objective.");
        api.addBriefingItem("Hold the line.");

        api.addToFleet(FleetSide.PLAYER, "els_damascus_std", FleetMemberType.SHIP, "ASV Hamza ibn al-Qalanisi", true);
        api.addToFleet(FleetSide.PLAYER, "els_adept_std", FleetMemberType.SHIP, "ASV Colonel Tricaud", false);

        api.addToFleet(FleetSide.ENEMY, "hound_d_pirates_Standard", FleetMemberType.SHIP, "Eternal Purity", false);
        api.addToFleet(FleetSide.ENEMY, "hound_d_pirates_Standard", FleetMemberType.SHIP, "Purging Flame", false);
        api.addToFleet(FleetSide.ENEMY, "lasher_luddic_path_Raider", FleetMemberType.SHIP, "Terrible Swift Sword", false);
        api.addToFleet(FleetSide.ENEMY, "buffalo2_FS", FleetMemberType.SHIP, "ISS Nothing Personal", false);
        api.addToFleet(FleetSide.ENEMY, "cerberus_luddic_path_Attack", FleetMemberType.SHIP, "Holy Crusade", false);

        api.defeatOnShipLoss("");

        float width = 20000f;
        float height = 14000f;
        api.initMap(-width / 2f, width / 2f, -height / 2f, height / 2f);

        float minX = -width / 2f;
        float minY = -height / 2f;

        for (int i = 0; i < 25; i++) {
            float x = (float) Math.random() * width - width / 2f;
            float y = (float) Math.random() * height - height / 2f;
            float radius = 1000f + (float) Math.random() * 1000f;
            api.addNebula(x, y, radius);
        }

        api.addNebula(minX + width * 0.8f - 2000f, minY + height * 0.4f, 2000f);
        api.addNebula(minX + width * 0.8f - 2000f, minY + height * 0.5f, 2000f);
        api.addNebula(minX + width * 0.8f - 2000f, minY + height * 0.6f, 2000f);

        api.addObjective(minX + width * 0.4f + 1000f, minY + height * 0.4f, "nav_buoy");
        api.addAsteroidField(minX, minY + height * 0.5f, 0f, height, 20f, 70f, 50);
    }
}
