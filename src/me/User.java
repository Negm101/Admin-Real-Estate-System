package me;

import java.io.*;
import java.util.StringTokenizer;

public class User {
    public static final String ADMIN_PREFIX = "AD";
    public static final String TENANT_PREFIX = "TE";
    public static final String AGENT_PREFIX = "AG";
    public static final String ALL = "";
    
    private String userName;
    private String fullName;
    private String password;
    private String id;
    private String phoneNumber;
    

    public User(String userName, String password, String fullName, String id, String phoneNumber) {
        this.userName = userName;
        this.fullName = fullName;
        this.password = password;
        this.id = id;
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPassword() {
        return password;
    }

    public String getId() {
        return id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public static User[] getAll(String prefix) throws IOException {
        int lines = (int) new BufferedReader(new FileReader(Global.adminDataPath)).lines().count();
        String record;
        String[] arrayRecord = new String[5];
        BufferedReader br = new BufferedReader(new FileReader(Global.adminDataPath));
        User[] users = new User[lines];
        int a = 0;
        while ((record = br.readLine()) != null) {
           if (record.contains(prefix)){
               StringTokenizer st = new StringTokenizer(record, ",");
               int count = st.countTokens();
               for (int i = 0; i < count; i++) {
                   arrayRecord[i] = st.nextToken();
               }
               users[a] = new User(arrayRecord[0],arrayRecord[1], arrayRecord[2], arrayRecord[3], arrayRecord[4]);
               a++;
           }
        }
        br.close();
        return users;
    }

    public static int getCount(String prefix) throws IOException {
        String record;
        BufferedReader br = new BufferedReader(new FileReader(Global.adminDataPath));
        int a = 0;
        while ((record = br.readLine()) != null) {
            if (record.contains(prefix)){
                a++;
            }
        }
        br.close();
        return a;
    }

    public static String getNextId(String prefix) throws IOException{
        int[] ids = new int[getCount(prefix)];

        for (int i = 0; i < getCount(prefix); i++){
            int id = Integer.parseInt(getAll(prefix)[i].getId().substring(2));
            ids[i] = id;
        }
        return prefix + getLargest(ids, ids.length);
    }

    private static int getLargest(int[] a, int total){
        int temp;
        for (int i = 0; i < total; i++)
        {
            for (int j = i + 1; j < total; j++)
            {
                if (a[i] > a[j])
                {
                    temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
            }
        }
        return a[total-1] + 1;
    }



    public void save() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(Global.adminDataPath, true));
        bw.write(this.userName + "," + this.password + "," + this.fullName + "," + this.id);
        bw.flush();
        bw.newLine();
        bw.close();

    }

    public void update() throws IOException {
        String   record2;
        File db = new File(Global.adminDataPath);
        File tempDB = new File(Global.adminDataPathTemp);
        BufferedWriter bw = new BufferedWriter(new FileWriter(tempDB));
        BufferedReader br2 = new BufferedReader(new FileReader(db));

        while ((record2 = br2.readLine()) != null) {
            if (record2.contains(this.id)) {
                bw.write(this.userName + "," + this.password + "," + this.fullName + "," + this.id);
            } else {
                bw.write(record2);
            }
            bw.flush();
            bw.newLine();
        }

        bw.close();
        br2.close();
        db.delete();
        boolean success = tempDB.renameTo(db);
        System.out.println(success);
        System.out.println("Deleted: " + this.id);

    }

    public void delete() throws IOException {
        String record;
        File tempDB = new File(Global.adminDataPathTemp);
        File db = new File(Global.adminDataPath);
        BufferedReader br = new BufferedReader(new FileReader(db));
        BufferedWriter bw = new BufferedWriter(new FileWriter(tempDB));

        while ((record = br.readLine()) != null) {
            if (record.contains(this.id))
                continue;
            bw.write(record);
            bw.flush();
            bw.newLine();
        }
        br.close();
        bw.close();
        db.delete();
        tempDB.renameTo(db);
        System.out.println("Deleted: " + this.fullName);
    }

    public static User getByID(String ID) throws IOException {
        String record;
        User user = null;
        String[] arrayRecord = new String[5];
        BufferedReader br = new BufferedReader(new FileReader(Global.adminDataPath));

        while ((record = br.readLine()) != null) {

            StringTokenizer st = new StringTokenizer(record, ",");
            if (record.contains(ID)) {
                int count = st.countTokens();
                for (int i = 0; i < count; i++) {
                    arrayRecord[i] = st.nextToken();
                }
                user = new User(arrayRecord[0],arrayRecord[1], arrayRecord[2], arrayRecord[3], arrayRecord[4]);

            }
        }
        br.close();
        return user;
    }


    public static boolean check(String name, String password, String prefix) throws IOException {
        String record;
        String[] arrayRecord = new String[5];
        BufferedReader br = new BufferedReader(new FileReader(Global.adminDataPath));

        while ((record = br.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(record, ",");
            if (record.contains(name)) {
                int count = st.countTokens();
                for (int i = 0; i < count; i++) {
                    arrayRecord[i] = st.nextToken();
                }
                if (arrayRecord[0].equals(name) && arrayRecord[1].equals(password) && arrayRecord[3].contains(prefix)) {
                    return true;
                }
            }
        }
        br.close();
        System.out.println("UserName: " + arrayRecord[0] + " : " + name);
        System.out.println("Password: " + arrayRecord[1] + " : " + password);
        System.out.println("ID: " + arrayRecord[3] + " : " + prefix);
        return false;
    }


    
    
}
