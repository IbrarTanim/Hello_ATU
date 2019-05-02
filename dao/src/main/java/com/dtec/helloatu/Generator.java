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
        Entity user = addUser(schema);
    }

    /**
     * Create user's Properties
     *
     * @return DBUser entity
     */
    private static Entity addUser(Schema schema) {
        Entity user = schema.addEntity("User");
        user.addIdProperty().primaryKey().autoincrement();
        user.addStringProperty("name").notNull();
        user.addStringProperty("password").notNull();
        user.addStringProperty("pic").notNull();
        user.addBooleanProperty("active").notNull();
        return user;
    }


}
