package data.scripts.world.systems;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.JumpPointAPI;
import com.fs.starfarer.api.campaign.PlanetAPI;
import com.fs.starfarer.api.campaign.PlanetSpecAPI;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.campaign.StarSystemAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.ids.Conditions;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.ids.Industries;
import com.fs.starfarer.api.impl.campaign.ids.Ranks;
import com.fs.starfarer.api.impl.campaign.ids.Skills;
import com.fs.starfarer.api.impl.campaign.ids.Submarkets;
import com.fs.starfarer.api.impl.campaign.ids.Terrain;
import com.fs.starfarer.api.impl.campaign.terrain.AsteroidFieldTerrainPlugin.AsteroidFieldParams;

import java.awt.Color;

public class Rousseau {

    public static void generate(SectorAPI sector) {
        float starTrojanDistance = 9500f;
        float brumaireTrojanDistance = 3100f;

        StarSystemAPI system = sector.createStarSystem("Rousseau");
        system.getLocation().set(500f, 6500f);
        system.setBackgroundTextureFilename("graphics/backgrounds/background6.jpg");

        // Star
        PlanetAPI star = system.initStar(
                "rousseau",
                "star_white",
                750f,
                1100f,
                5f,
                0.6f,
                2f
        );

        // Rousseau Trojans
        SectorEntityToken trojansL4 = system.addTerrain(Terrain.ASTEROID_FIELD,
                new AsteroidFieldParams(400f, 600f, 16, 24, 4f, 16f, "Rousseau L4 Trojans"));
        SectorEntityToken trojansL5 = system.addTerrain(Terrain.ASTEROID_FIELD,
                new AsteroidFieldParams(400f, 600f, 16, 24, 4f, 16f, "Rousseau L5 Asteroids"));
        trojansL4.setCircularOrbit(star, 230f + 60f, starTrojanDistance, 450f);
        trojansL5.setCircularOrbit(star, 230f - 60f, starTrojanDistance, 450f);

        system.addAsteroidBelt(star, 150, 3100f, 128f, 60f, 80f, Terrain.ASTEROID_BELT, "The Inner Belt");

        // Inner planets
        PlanetAPI sophie = system.addPlanet("planet_sophie", star, "Sophie", "irradiated", 60f, 100f, 1500f, 200f);
        PlanetAPI heloise = system.addPlanet("planet_heloise", star, "Heloise", "barren", 90f, 75f, 3000f, 250f);

        PlanetSpecAPI sophieSpec = sophie.getSpec();
        sophieSpec.setPlanetColor(new Color(220, 245, 255, 255));
        sophieSpec.setAtmosphereColor(new Color(150, 120, 100, 250));
        sophieSpec.setCloudColor(new Color(150, 120, 120, 150));
        sophie.applySpecChanges();

        // Brumaire
        PlanetAPI brumaire = system.addPlanet("planet_brumaire", star, "Brumaire", "gas_giant", 0f, 400f, 6800f, 350f);
        PlanetSpecAPI brumaireSpec = brumaire.getSpec();
        brumaireSpec.setPlanetColor(new Color(225, 120, 255, 255));
        brumaireSpec.setAtmosphereColor(new Color(250, 200, 220, 150));
        brumaireSpec.setCloudColor(new Color(255, 205, 225, 150));
        brumaireSpec.setIconColor(new Color(245, 105, 205, 255));
        brumaireSpec.setAtmosphereThickness(0.7f);
        brumaireSpec.setGlowTexture(Global.getSettings().getSpriteName("hab_glows", "banded"));
        brumaireSpec.setGlowColor(new Color(255, 125, 200, 85));
        brumaireSpec.setUseReverseLightForGlow(true);
        brumaire.applySpecChanges();

        // Brumaire rings & moons
        system.addRingBand(brumaire, "misc", "rings1", 256f, 2, Color.white, 256f, 700f, 33f, Terrain.RING, null);
        system.addRingBand(brumaire, "misc", "rings1", 256f, 3, Color.white, 256f, 875f, 33f, Terrain.RING, null);
        system.addRingBand(brumaire, "misc", "rings1", 256f, 1, Color.white, 256f, 1630f, 90f, Terrain.RING, "The Fog Band");
        system.addRingBand(brumaire, "misc", "rings1", 256f, 2, Color.white, 256f, 1720f, 33f, Terrain.RING, "The Fog Band");

        PlanetAPI fraternite = system.addPlanet("moon_fraternite", brumaire, "Fraternité", "water", 30f, 85f, 1310f, 50f);
        PlanetSpecAPI fraterniteSpec = fraternite.getSpec();
        fraterniteSpec.setPlanetColor(new Color(255, 255, 220, 255));
        fraterniteSpec.setGlowTexture(Global.getSettings().getSpriteName("hab_glows", "aurorae"));
        fraterniteSpec.setGlowColor(new Color(220, 130, 225, 162));
        fraterniteSpec.setUseReverseLightForGlow(true);
        fraterniteSpec.setAtmosphereThickness(0.56f);
        fraterniteSpec.setAtmosphereColor(new Color(200, 240, 245, 150));
        fraternite.applySpecChanges();

        system.addPlanet("moon_egalite", brumaire, "Égalité", "rocky_ice", 45f, 45f, 1430f, 65f);

        // Liberté & Station
        PlanetAPI liberte = system.addPlanet("planet_liberte", brumaire, "Liberté", "terran", 0f, 120f, 1050f, 42f);
        PlanetSpecAPI liberteSpec = liberte.getSpec();
        liberteSpec.setGlowTexture(Global.getSettings().getSpriteName("hab_glows", "sindria"));
        liberteSpec.setGlowColor(new Color(255, 255, 255, 255));
        liberteSpec.setUseReverseLightForGlow(true);
        liberte.applySpecChanges();
        liberte.setInteractionImage("illustrations", "city_from_above");

        SectorEntityToken liberteStation = system.addCustomEntity(
                "station_liberte",
                "Al Qasbah Orbital",
                "station_side03",
                "directory"
        );
        liberteStation.setCircularOrbitPointingDown(liberte, 45f, 300f, 50f);
        liberteStation.setInteractionImage("illustrations", "terran_orbit");
        liberteStation.setCustomDescriptionId("station_liberte");

        // 0.98a Market Creation
        createLiberteMarket(liberte, liberteStation);

        // Brumaire Trojans & Relay
        SectorEntityToken brumaireL4Trojans = system.addTerrain(Terrain.ASTEROID_FIELD,
                new AsteroidFieldParams(200f, 400f, 16, 24, 4f, 16f, "Brumaire L4 Trojans"));
        SectorEntityToken brumaireL5Trojans = system.addTerrain(Terrain.ASTEROID_FIELD,
                new AsteroidFieldParams(200f, 400f, 16, 24, 4f, 16f, "Brumaire L4 Asteroids"));
        brumaireL4Trojans.setCircularOrbit(brumaire, 230f + 60f, brumaireTrojanDistance, 450f);
        brumaireL5Trojans.setCircularOrbit(brumaire, 230f - 60f, brumaireTrojanDistance, 450f);

        SectorEntityToken relay = system.addCustomEntity("liberte_relay", "Liberté Relay", "comm_relay", "directory");
        relay.setCircularOrbit(brumaire, 230f + 60f, brumaireTrojanDistance, 450f);

        JumpPointAPI jumpPoint = Global.getFactory().createJumpPoint("liberte_jump_point", "Liberté Crossing");
        jumpPoint.setCircularOrbit(star, 230f + 60f, starTrojanDistance, 450f);
        jumpPoint.setRelatedPlanet(liberte);
        jumpPoint.setStandardWormholeToHyperspaceVisual();
        system.addEntity(jumpPoint);

        system.autogenerateHyperspaceJumpPoints(true, true);
    }

    private static MarketAPI createLiberteMarket(SectorEntityToken primary, SectorEntityToken station) {
        MarketAPI market = Global.getFactory().createMarket(primary.getId() + "_market", "Liberté", 7);
        market.setFactionId("directory");
        market.setPrimaryEntity(primary);
        market.getTariff().modifyFlat("generator", 0.30f);

        // 0.98a Conditions
        market.addCondition(Conditions.POPULATION_7);
        market.addCondition(Conditions.HABITABLE);
        market.addCondition(Conditions.TERRAN);
        market.addCondition(Conditions.MILD_CLIMATE);
        market.addCondition(Conditions.ORGANICS_ABUNDANT);
        market.addCondition(Conditions.FARMLAND_BOUNTIFUL);
        market.addCondition(Conditions.REGIONAL_CAPITAL);

        // 0.98a Industries
        market.addIndustry(Industries.POPULATION);
        market.addIndustry(Industries.MEGAPORT);
        market.addIndustry(Industries.HIGHCOMMAND);
        market.addIndustry(Industries.HEAVYBATTERIES);
        market.addIndustry(Industries.STARFORTRESS_MID);
        market.addIndustry(Industries.FARMING);
        market.addIndustry(Industries.LIGHTINDUSTRY);

        // 0.98a Submarkets
        market.addSubmarket(Submarkets.SUBMARKET_STORAGE);
        market.getSubmarket(Submarkets.SUBMARKET_STORAGE).setFaction(Global.getSector().getFaction(Factions.PLAYER));
        market.addSubmarket(Submarkets.SUBMARKET_OPEN);
        market.addSubmarket(Submarkets.GENERIC_MILITARY);
        market.addSubmarket(Submarkets.SUBMARKET_BLACK);

        // Market Administrator with 0.98a standard skills
        PersonAPI admin = Global.getFactory().createPerson();
        admin.setFaction("directory");
        admin.setGender(com.fs.starfarer.api.characters.FullName.Gender.FEMALE);
        admin.setRankId(Ranks.SPACE_ADMIRAL);
        admin.setPostId(Ranks.POST_FACTION_LEADER);
        admin.setPortraitSprite("graphics/portraits/portrait_hegemony03.png");
        admin.getStats().setSkillLevel(Skills.INDUSTRIAL_PLANNING, 1);
        admin.getStats().setSkillLevel(Skills.PLANETARY_OPERATIONS, 1);
        market.setAdmin(admin);
        market.getCommDirectory().addPerson(admin, 0);

        market.getConnectedEntities().add(station);
        station.setMarket(market);
        station.setFaction("directory");

        primary.setMarket(market);
        primary.setFaction("directory");

        Global.getSector().getEconomy().addMarket(market, true);
        return market;
    }
}
