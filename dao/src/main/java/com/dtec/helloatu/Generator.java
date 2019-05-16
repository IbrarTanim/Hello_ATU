package com.dtec.helloatu;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class Generator {

    private static final String PROJECT_DIR = System.getProperty("user.dir").replace("\\", "/");
    private static final String OUT_DIR = PROJECT_DIR + "/app/src/main/java";


    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, "com.dtec.helloatu.dao");
        addTables(schema);
        new DaoGenerator().generateAll(schema, OUT_DIR);
    }


    /**
     * Create tables and the relationships between them
     */
    private static void addTables(Schema schema) {
        /* entities */
        Entity crime = addCrime(schema);
    }

    /**
     * Create user's Properties
     *
     * @return DBUser entity
     */
    private static Entity addCrime(Schema schema) {
        Entity crime = schema.addEntity("Crime");
        crime.addIdProperty().primaryKey().autoincrement();
        crime.addIntProperty("crimPosition").notNull();
        crime.addStringProperty("crimeInfo").notNull();
        crime.addStringProperty("informerName").notNull();
        crime.addStringProperty("informerPhone").notNull();
        crime.addStringProperty("informerAddress").notNull();
        crime.addStringProperty("infoDocument").notNull();
        crime.addStringProperty("infoPicture").notNull();
        crime.addStringProperty("infoVideo").notNull();
        crime.addStringProperty("infoAudio").notNull();
        crime.addIntProperty("occurrence").notNull();
        crime.addIntProperty("occurrenceInformer").notNull();
        crime.addIntProperty("division").notNull();
        crime.addIntProperty("divisionInformer").notNull();
        crime.addIntProperty("district").notNull();
        crime.addIntProperty("districtInformer").notNull();
        return crime;
    }


}
