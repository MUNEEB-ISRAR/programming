package data;

import database.CriminalDatabase;
import models.*;
import models.criminals.*;
import utils.Utils;

/**
 * Sample Data Loader
 * Loads demo data for testing and demonstration.
 */
public class SampleDataLoader {

    /**
     * Load all sample data into database
     */
    public static void loadSampleData(CriminalDatabase database) {
        System.out.println("Loading sample data...");

        try {
            loadSampleCriminals(database);
            loadSampleCrimeScenes(database);

            System.out.println(" Sample data loaded successfully!");
            System.out.println(database.getSummary());

        } catch (Exception e) {
            System.err.println("Error loading sample data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Load sample criminals
     */
    private static void loadSampleCriminals(CriminalDatabase database) {

        // Serial Killer 1
        SerialKiller sk1 = new SerialKiller("CRIM00001", "Kashif", 35, "Male");
        sk1.setPsychologicalProfile("Organized, high IQ, narcissistic personality");
        sk1.setSignature("Leaves a red rose at each scene");
        sk1.setVictimCount(7);
        sk1.setVictimType("Young women, 20-30 years old");
        sk1.setOrganized(true);
        sk1.addKnownLocation("Lahore");
        sk1.addKnownLocation("University District");
        sk1.addPriorCrime("Murder");
        sk1.addPriorCrime("Assault");
        database.addCriminal(sk1);

        // Thief 1
        Thief t1 = new Thief("CRIM00002", "Ali", 28, "Male");
        t1.setPsychologicalProfile("Risk-taker, opportunistic, street-smart");
        t1.setSpecialization("burglary");
        t1.addStolenValue(75000);
        t1.setPreferredTarget("Residential homes");
        t1.addKnownLocation("Shadrah");
        t1.addKnownLocation("Lahore");
        t1.addPriorCrime("Theft");
        t1.addPriorCrime("Breaking and Entering");
        database.addCriminal(t1);

        // Violent Offender 1
        ViolentOffender vo1 = new ViolentOffender("CRIM00003", "Rizwan", 31, "Male");
        vo1.setPsychologicalProfile("Anger issues, explosive temperament, substance abuse");
        vo1.setWeaponPreference("knife");
        vo1.setImpulseControl(false);
        vo1.setSubstanceAbuse(true);
        vo1.setTriggerType("anger and perceived disrespect");
        vo1.addKnownLocation("Lahore");
        vo1.addKnownLocation("Industrial Area");
        vo1.addPriorCrime("Assault");
        vo1.addPriorCrime("Battery");
        database.addCriminal(vo1);

        // Fraudster 1
        Fraudster f1 = new Fraudster("CRIM00004", "Ronaldo", 42, "Male");
        f1.setPsychologicalProfile("Manipulative, charming, highly intelligent");
        f1.setFraudType("financial fraud");
        f1.addDefraudedAmount(350000);
        f1.setMethodOfContact("phone and email");
        f1.setUsesOnlineTools(true);
        f1.addKnownLocation("Business District");
        f1.addPriorCrime("Fraud");
        f1.addPriorCrime("Identity Theft");
        database.addCriminal(f1);

        // Arsonist 1
        Arsonist a1 = new Arsonist("CRIM00005", "Aliya", 29, "Female");
        a1.setPsychologicalProfile("Pyromania, thrill-seeking, antisocial");
        a1.setAccelerantType("gasoline");
        a1.setTargetType("commercial");
        a1.setMotivation("excitement");
        a1.incrementFireCount();
        a1.incrementFireCount();
        a1.incrementFireCount();
        a1.addKnownLocation("Industrial Area");
        a1.addPriorCrime("Arson");
        database.addCriminal(a1);

        // Drug Trafficker 1
        DrugTrafficker dt1 = new DrugTrafficker("CRIM00006", "Ayesha", 38, "Female");
        dt1.setPsychologicalProfile("Ruthless, business-minded, violent when necessary");
        dt1.setPrimaryDrug("cocaine");
        dt1.setOperationScale("REGIONAL");
        dt1.setHasCartelConnections(true);
        dt1.setUsesViolence(true);
        dt1.addStreetValue(500000);
        dt1.addKnownLocation("Port Area");
        dt1.addKnownLocation("Highway 95");
        dt1.addPriorCrime("DrugTrafficking");
        database.addCriminal(dt1);

        // Cyber Criminal 1
        CyberCriminal cc1 = new CyberCriminal("CRIM00007", "Alex", 25, "Male");
        cc1.setPsychologicalProfile("Highly intelligent, socially awkward, ideologically motivated");
        cc1.setSpecialization("hacking");
        cc1.setSkillLevel("EXPERT");
        cc1.setPartOfGroup(true);
        cc1.setPreferredTarget("corporate");
        cc1.addSystemCompromised();
        cc1.addSystemCompromised();
        cc1.addFinancialDamage(250000);
        cc1.addKnownLocation("Online");
        cc1.addPriorCrime("CyberCrime");
        database.addCriminal(cc1);

        // Robber 1
        Robber r1 = new Robber("CRIM00008", "Tommy", 33, "Male");
        r1.setPsychologicalProfile("Aggressive, impulsive, desperate for money");
        r1.setTargetType("store");
        r1.setWeaponType("gun");
        r1.setWorksInGroup(false);
        r1.incrementRobberyCount();
        r1.incrementRobberyCount();
        r1.addStolenAmount(15000);
        r1.addKnownLocation("Commercial District");
        r1.addPriorCrime("Robbery");
        database.addCriminal(r1);

        System.out.println("Loaded 8 sample criminals");
    }

    /**
     * Load sample crime scenes
     */
    private static void loadSampleCrimeScenes(CriminalDatabase database) {

        // Crime Scene 1: Murder
        CrimeScene scene1 = new CrimeScene("SCENE0001", "Murder", "Downtown Park");
        scene1.setDescription("Victim found in secluded area of park, organized crime scene");
        scene1.addCharacteristic("organization", "organized");
        scene1.addCharacteristic("time", "night");
        scene1.setVictimProfile("Female, age 25, professional");

        Evidence e1 = new Evidence("SCENE0001-E001", "Document", "Red rose left at scene", "Downtown Park");
        e1.addAttribute("signature", "red rose");
        scene1.addEvidence(e1);

        scene1.secureScene("Detective Sarah Johnson");
        database.addCrimeScene(scene1);

        // Crime Scene 2: Theft
        CrimeScene scene2 = new CrimeScene("SCENE0002", "Theft", "Industrial area");
        scene2.setDescription("Residential burglary, entry through back window");
        scene2.addCharacteristic("entry_method", "back window");
        scene2.addCharacteristic("time", "day");

        Evidence e2 = new Evidence("SCENE0002-E001", "Trace", "Pry marks on window", "Lahore");
        scene2.addEvidence(e2);

        database.addCrimeScene(scene2);

        // Crime Scene 3: Assault
        CrimeScene scene3 = new CrimeScene("SCENE0003", "Assault", "Bar District - Murphy's Pub");
        scene3.setDescription("Violent altercation outside bar, victim stabbed");
        scene3.addCharacteristic("weapon", "knife");
        scene3.addCharacteristic("time", "late night");

        Evidence e3 = new Evidence("SCENE0003-E001", "Weapon", "Knife recovered from scene", "Murphy's Pub");
        e3.addAttribute("type", "knife");
        scene3.addEvidence(e3);

        Evidence e4 = new Evidence("SCENE0003-E002", "Witness", "Bar patron witnessed fight", "Murphy's Pub");
        e4.addAttribute("description", "Male, 30s, aggressive behavior");
        scene3.addEvidence(e4);

        database.addCrimeScene(scene3);

        System.out.println("Loaded 3 sample crime scenes");
    }
}
