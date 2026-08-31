package data.missions.els_test;

import com.fs.starfarer.api.fleet.FleetGoal;
import com.fs.starfarer.api.fleet.FleetMemberType;
import com.fs.starfarer.api.mission.FleetSide;
import com.fs.starfarer.api.mission.MissionDefinitionAPI;
import com.fs.starfarer.api.mission.MissionDefinitionPlugin;

public class MissionDefinition implements MissionDefinitionPlugin {

    @Override
    public void defineMission(MissionDefinitionAPI api) {
        api.initFleet(FleetSide.PLAYER, "ASV", FleetGoal.ATTACK, false);
        api.initFleet(FleetSide.ENEMY, "SIM", FleetGoal.ATTACK, true);

        api.setFleetTagline(FleetSide.PLAYER, "Directory Technical Commission Evaluation Force");
        api.setFleetTagline(FleetSide.ENEMY, "Automated Simulation Target Squadron");

        api.addBriefingItem("Evaluate the performance of Directory hulls, strike craft, and energy weapon arrays.");
        api.addBriefingItem("Destroy all automated target ships.");

        // Player Fleet - Full Showcase of ELS Roster
        api.addToFleet(FleetSide.PLAYER, "els_chevalier-ss_custom", FleetMemberType.SHIP, "ASV Prototype 01", true);
        api.addToFleet(FleetSide.PLAYER, "els_hallebarde_assault", FleetMemberType.SHIP, "ASV Valmy", false);
        api.addToFleet(FleetSide.PLAYER, "els_dupleix_support", FleetMemberType.SHIP, "ASV Suffren", false);
        api.addToFleet(FleetSide.PLAYER, "els_lancier_elite", FleetMemberType.SHIP, "ASV Guisarme", false);
        api.addToFleet(FleetSide.PLAYER, "els_adept_std", FleetMemberType.SHIP, "ASV Sentinelle", false);
        api.addToFleet(FleetSide.PLAYER, "els_damascus_recon", FleetMemberType.SHIP, "ASV Damascus", false);
        api.addToFleet(FleetSide.PLAYER, "els_mistral_m_std", FleetMemberType.SHIP, "ASV Mistral Gunship", false);

        // Target Fleet - Varied Combat Targets
        api.addToFleet(FleetSide.ENEMY, "dominator_Support", FleetMemberType.SHIP, "Target Drone Alpha (Heavy Cruiser)", false);
        api.addToFleet(FleetSide.ENEMY, "hammerhead_Balanced", FleetMemberType.SHIP, "Target Drone Beta (Destroyer)", false);
        api.addToFleet(FleetSide.ENEMY, "enforcer_Balanced", FleetMemberType.SHIP, "Target Drone Gamma (Destroyer)", false);
        api.addToFleet(FleetSide.ENEMY, "sunder_CS", FleetMemberType.SHIP, "Target Drone Delta (Destroyer)", false);
        api.addToFleet(FleetSide.ENEMY, "lasher_Standard", FleetMemberType.SHIP, "Target Drone Epsilon (Frigate)", false);
        api.addToFleet(FleetSide.ENEMY, "brawler_tritachyon_Standard", FleetMemberType.SHIP, "Target Drone Zeta (Frigate)", false);

        api.defeatOnShipLoss("");

        // Map Setup
        float width = 24000f;
        float height = 18000f;
        api.initMap(-width / 2f, width / 2f, -height / 2f, height / 2f);

        float minX = -width / 2f;
        float minY = -height / 2f;

        for (int i = 0; i < 20; i++) {
            float x = (float) Math.random() * width - width / 2f;
            float y = (float) Math.random() * height - height / 2f;
            float radius = 800f + (float) Math.random() * 1200f;
            api.addNebula(x, y, radius);
        }

        api.addObjective(minX + width * 0.5f, minY + height * 0.5f, "nav_buoy");
        api.addObjective(minX + width * 0.3f, minY + height * 0.6f, "sensor_array");
        api.addObjective(minX + width * 0.7f, minY + height * 0.4f, "comm_relay");

        api.addAsteroidField(minX, minY + height * 0.5f, 0f, height, 20f, 70f, 60);
    }
}
