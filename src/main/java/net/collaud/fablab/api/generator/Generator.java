package net.collaud.fablab.api.generator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import net.collaud.fablab.api.data.MotionStockEO;

/**
 *
 * @author Fabien Vuilleumier
 */
public class Generator {

    private final BackendEOGenerator EO;
    private BackendBaseGenerator base;
    private BackendAngularListEditGenerator angular;

    /*
     * BEGIN CHANGE SECTION
     Must be a table of : 
    
     {"java type", "java name", "nullable ? [t,f]", "db type"} 
     */
    /*FIRST*/
    private final String CLASS_NAME = "MotionStock";
    private final String TABLE_NAME = "t_motion_stock";

    private final String[][] FIELDS = new String[][]{
        {"Integer", "id", "f", "INT"},
        {"Date", "motionDate", "f", "DATE"},
        {"Double", "quantity", "f", "FLOAT"},
        {"String", "io", "f", "VARCHAR"},
        {"SupplyEO", "supply", "f", "INT"},
        {"UserEO", "user", "f", "INT"}};

    private final boolean WRITE = false;
    private final String[] ROLES = new String[]{"SUPPLY_MANAGE"};
    private final Map<String, String> nestedObjectReprAttr = new HashMap<>();

    /*SECOND*/
    private final Class KLAZZ = MotionStockEO.class;

    public static void main(String[] args) {
        Generator agl = new Generator();
        agl.getNestedObjectReprAttr().put("supply", "code");
        agl.getNestedObjectReprAttr().put("user", "lastname");
        //agl.runEO();
        agl.runBase();
        agl.runAngular(agl.getNestedObjectReprAttr());
    }

    /*END CHANGE SECTION */
    private Generator() {
        this.EO = BackendEOGenerator.getInstance(CLASS_NAME, TABLE_NAME, FIELDS);
    }

    private void runEO() {
        try {
            EO.genereEO();

        } catch (IOException ex) {
            System.out.println("Error : " + ex.getMessage());
        }
    }

    private void runBase() {
        try {
            this.base = BackendBaseGenerator.getInstance(KLAZZ);
            base.genere(WRITE, ROLES);
        } catch (IOException ex) {
            System.out.println("Error : " + ex.getMessage());
        }
    }

    private void runAngular(final Map<String, String> nestedObjectReprAttr) {
        try {
            this.angular = BackendAngularListEditGenerator.getInstance(KLAZZ, nestedObjectReprAttr);
            angular.genere(WRITE, "", ROLES);
        } catch (IOException ex) {
            System.out.println("Error : " + ex.getMessage());
        }
    }

    public Map<String, String> getNestedObjectReprAttr() {
        return nestedObjectReprAttr;
    }
}
