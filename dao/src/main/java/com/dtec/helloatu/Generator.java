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




    /*private static Entity addCrime(Schema schema) {
        Entity crime = schema.addEntity("Crime");
        crime.addIdProperty().primaryKey().autoincrement();
        crime.addIntProperty("crimPosition").notNull();
        crime.addStringProperty("crimCategory").notNull();
        crime.addStringProperty("crimeInfo").notNull();
        crime.addStringProperty("informerName").notNull();
        crime.addStringProperty("informerPhone").notNull();
        crime.addStringProperty("informerAddress").notNull();
        crime.addStringProperty("informerEmail").notNull();
        crime.addStringProperty("infoDocument").notNull();
        crime.addStringProperty("infoPicture").notNull();
        crime.addStringProperty("infoVideo").notNull();
        crime.addStringProperty("infoAudio").notNull();
        crime.addStringProperty("occurrence").notNull();
        crime.addStringProperty("occurrenceInformer").notNull();
        crime.addStringProperty("division").notNull();
        crime.addStringProperty("divisionInformer").notNull();
        crime.addStringProperty("district").notNull();
        crime.addStringProperty("districtInformer").notNull();
        crime.addDateProperty("createdAt").notNull();
        return crime;
    }*/
    private static Entity addCrime(Schema schema) {
        Entity crime = schema.addEntity("Crime");

        crime.addIdProperty().primaryKey().autoincrement();
        crime.addStringProperty("appAuthToken");
        crime.addIntProperty("crimPosition");
        crime.addStringProperty("crimType");
        crime.addStringProperty("crimeInfo");
        crime.addStringProperty("placeOfCrime");
        crime.addStringProperty("thana");
        crime.addStringProperty("districtOrCountry");
        crime.addStringProperty("informerName");
        crime.addStringProperty("informerPhone");
        crime.addStringProperty("informerAddress");
        crime.addStringProperty("informerEmail");
        crime.addStringProperty("informerNID");
        crime.addStringProperty("placeOfInformer");
        crime.addStringProperty("informerThana");
        crime.addStringProperty("informerDistrictOrCountry");
        crime.addStringProperty("infoDocumentFilename");
        crime.addByteArrayProperty("infoDocumentContent");
        crime.addStringProperty("infoImageFilename");
        crime.addByteArrayProperty("infoImageContent");
        crime.addStringProperty("infoVideoFilename");
        crime.addByteArrayProperty("infoVideoContent");
        crime.addStringProperty("infoAudioFilename");
        crime.addByteArrayProperty("infoAudioContent");
        crime.addDateProperty("createdAt");

        return crime;
    }




   /* private static Entity addCrime(Schema schema) {
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
        crime.addDateProperty("createdAt").notNull();

        return crime;
    }*/


}
