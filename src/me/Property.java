package me;
// Property ID, Agent ID, Owner Name, Property Type, Property Status, Property Address, Property Price, Payment Type

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Property {
    public static final String ALL = "";

    private String propertyID;
    private String agentID;
    private String OwnerFullName;
    private String propertyType;
    private String propertyStatus;
    private String propertyAddress;
    private int propertyPrice;
    private String paymentType;

    public Property(String propertyID, String agentID, String ownerFullName, String propertyType, String propertyStatus, String propertyAddress, int propertyPrice, String paymentType) {
        this.propertyID = propertyID;
        this.agentID = agentID;
        OwnerFullName = ownerFullName;
        this.propertyType = propertyType;
        this.propertyStatus = propertyStatus;
        this.propertyAddress = propertyAddress;
        this.propertyPrice = propertyPrice;
        this.paymentType = paymentType;
    }

    public String getPropertyID() {
        return propertyID;
    }

    public String getAgentID() {
        return agentID;
    }

    public String getOwnerFullName() {
        return OwnerFullName;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public String getPropertyStatus() {
        return propertyStatus;
    }

    public String getPropertyAddress() {
        return propertyAddress;
    }

    public int getPropertyPrice() {
        return propertyPrice;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public static Property[] getAll(String[] keyWords) throws IOException {
        int lines = (int) new BufferedReader(new FileReader(Global.propertyDataPath)).lines().count();
        String record;
        String[] arrayRecord = new String[8];
        BufferedReader br = new BufferedReader(new FileReader(Global.propertyDataPath));
        Property[] properties = new Property[lines];
        int a = 0;

        while ((record = br.readLine()) != null) {
            if (containsWords(record, keyWords)){
                StringTokenizer st = new StringTokenizer(record, ",");
                int count = st.countTokens();
                for (int i = 0; i < count; i++) {
                    arrayRecord[i] = st.nextToken();
                }
                properties[a] = new Property(arrayRecord[0],arrayRecord[1], arrayRecord[2], arrayRecord[3], arrayRecord[4],arrayRecord[5],Integer.parseInt(arrayRecord[6]),arrayRecord[7]);
                a++;
            }
        }

        br.close();
        return properties;
    }

    private static boolean containsWords(String str, String[] items) {
        boolean found = true;
        for (String item : items) {
            if (!str.contains(item)) {
                found = false;
                break;
            }
        }
        return found;
    }

    public static int getCount(String id) throws IOException {
        String record;
        BufferedReader br = new BufferedReader(new FileReader(Global.propertyDataPath));
        int a = 0;
        while ((record = br.readLine()) != null) {
            if (record.contains(id)){
                a++;
            }
        }
        br.close();
        return a;
    }

    public static Property getByPropertyID(String ID) throws IOException {
        String record;
        Property property = null;
        String[] arrayRecord = new String[8];
        BufferedReader br = new BufferedReader(new FileReader(Global.propertyDataPath));

        while ((record = br.readLine()) != null) {

            StringTokenizer st = new StringTokenizer(record, ",");
            if (record.contains(ID)) {
                int count = st.countTokens();
                for (int i = 0; i < count; i++) {
                    arrayRecord[i] = st.nextToken();
                }
                property = new Property(arrayRecord[0],arrayRecord[1], arrayRecord[2], arrayRecord[3], arrayRecord[4],arrayRecord[5],Integer.parseInt(arrayRecord[6]),arrayRecord[7]);

            }
        }
        br.close();
        return property;
    }
}
