package me;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Locale;

public class AdminScreen {

    private static final int searchTermIndex = 0;
    private static final int propertyStatusIndex = 1;
    private static final int ownerIndex = 2;
    private static final int setCommentsIndex = 3;
    private static final int paymentTypeIndex = 4;
    private static final int propertyTypeIndex = 5;


    private JPanel mainPanel;
    private JButton adminTabButton;
    private JButton agentTabButton;
    private JButton tenantTabButton;
    private JButton deleteButton;
    private JButton addButton;
    private JButton saveButton;
    private JButton findButton;
    private JTable propertyTable;
    private JTable usersTable;
    private JTextField idField;
    private JTextField usernameField;
    private JTextField fullNameField;
    private JPasswordField passwordField;
    private JTextField propertyOwnerNameField;
    private JTextField propertyAgentNameField;
    private JTextField propertyAgentPhoneNumberField;
    private JTextField propertyTypeField;
    private JTextField propertyStatusField;
    private JTextField propertyAddressField;
    private JTextField propertyPriceField;
    private JTextField propertyIdField;
    private JTextField propertyAgentIdField;
    private JTextField phoneNumberField;
    private JTextField propertyPaymentType;
    private JTextField searchTextField;
    private JTextField textField1;
    private JComboBox paymentTypeComboBox;
    private JComboBox ownerComboBox;
    private JComboBox propertyStatusComboBox;
    private JComboBox propertyTypeComboBox;
    private JComboBox commentsComboBox;
    private JTabbedPane tabbedPane;


    private User selectedUser = null;
    private Property selectedProperty = null;
    private String currentTab = User.ADMIN_PREFIX;
    private final String[] keyWords = {Property.ALL, "", "", "", "", ""};
    private final DefaultTableModel adminTableModel = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final DefaultTableModel propertyTableModel = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public AdminScreen() throws IOException {

        initializeUsersTable();
        initializePropertyTable();
        setOwnerComboBox();
        setPropertyTypeComboBox();

        adminTabButton.addActionListener(e -> {
            try {
                clearAllTextFields();
                updateAdminTable(adminTableModel, User.ADMIN_PREFIX);
                usersTable.clearSelection();
                currentTab = User.ADMIN_PREFIX;
                deleteButton.setEnabled(false);
                saveButton.setEnabled(false);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        agentTabButton.addActionListener(e -> {
            try {
                clearAllTextFields();
                updateAdminTable(adminTableModel, User.AGENT_PREFIX);
                usersTable.clearSelection();
                currentTab = User.AGENT_PREFIX;
                deleteButton.setEnabled(false);
                saveButton.setEnabled(false);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        tenantTabButton.addActionListener(e -> {
            try {
                clearAllTextFields();
                updateAdminTable(adminTableModel, User.TENANT_PREFIX);
                usersTable.clearSelection();
                currentTab = User.TENANT_PREFIX;
                deleteButton.setEnabled(false);
                saveButton.setEnabled(false);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        usersTable.getSelectionModel().addListSelectionListener(event -> {
            deleteButton.setEnabled(true);
            saveButton.setEnabled(true);
            System.out.println("Selected row: " + usersTable.getSelectedRow());
            try {
                String id = usersTable.getValueAt(usersTable.getSelectedRow(), 0).toString();
                selectedUser = User.getByID(id);
                setAdminDetails(selectedUser);
            } catch (IOException | ArrayIndexOutOfBoundsException e) {
                System.out.println(e);
            }

        });

        propertyTable.getSelectionModel().addListSelectionListener(event -> {
            System.out.println("Selected row: " + usersTable.getSelectedRow());
            try {
                String id = propertyTable.getValueAt(propertyTable.getSelectedRow(), 0).toString();
                selectedProperty = Property.getByPropertyID(id);
                setPropertyDetails(selectedProperty);
            } catch (IOException | ArrayIndexOutOfBoundsException e) {
                System.out.println(e);
            }

        });

        deleteButton.addActionListener(e -> {
            try {
                selectedUser.delete();
                clearAllTextFields();
                updateAdminTable(adminTableModel, currentTab);

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        });

        addButton.addActionListener(e -> {
            try {
                DetailsScreen.lunch(new User("", "", "", User.getNextId(currentTab), ""), DetailsScreen.ADD);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        saveButton.addActionListener(e -> {
            selectedUser.setId(idField.getText());
            selectedUser.setFullName(fullNameField.getText());
            selectedUser.setUserName(usernameField.getText());
            selectedUser.setPassword(passwordField.getText());
            try {
                selectedUser.update();
                updateAdminTable(adminTableModel, currentTab);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        paymentTypeComboBox.addActionListener(e -> {
            if (paymentTypeComboBox.getSelectedItem().toString().equals("All")) {
                keyWords[paymentTypeIndex] = "";
            } else
                keyWords[paymentTypeIndex] = paymentTypeComboBox.getSelectedItem().toString();

        });

        propertyStatusComboBox.addActionListener(e -> {
            if (propertyStatusComboBox.getSelectedItem().toString().equals("All")) {
                keyWords[propertyStatusIndex] = "";
            } else
                keyWords[propertyStatusIndex] = propertyStatusComboBox.getSelectedItem().toString();

        });

        ownerComboBox.addActionListener(e -> {
            if (ownerComboBox.getSelectedItem().toString().equals("All")) {
                keyWords[ownerIndex] = "";
            } else
                keyWords[ownerIndex] = ownerComboBox.getSelectedItem().toString();

        });

        findButton.addActionListener(e -> {
            try {
                if (searchTextField.getText().equals(""))
                    keyWords[searchTermIndex] = "";
                else
                    keyWords[searchTermIndex] = searchTextField.getText();

                updatePropertyTable(propertyTableModel);

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        propertyTypeComboBox.addActionListener(e -> {
            if (propertyTypeComboBox.getSelectedItem().toString().equals("All")) {
                keyWords[propertyTypeIndex] = "";
            } else
                keyWords[propertyTypeIndex] = propertyTypeComboBox.getSelectedItem().toString();

        });
    }

    public static void lunch() throws IOException {
        Global.setTheme();
        JFrame frame = new JFrame("Admin");
        frame.setContentPane(new AdminScreen().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setMinimumSize(new Dimension(1200, 600));
        frame.setPreferredSize(new Dimension(1200, 600));
    }

    private void initializeUsersTable() throws IOException {
        propertyTableModel.addColumn("Property ID");
        propertyTableModel.addColumn("Owner full name");
        updatePropertyTable(propertyTableModel);
        propertyTable.setSize(100, 500);
        propertyTable.setModel(propertyTableModel);
    }

    private void initializePropertyTable() throws IOException {
        adminTableModel.addColumn("ID");
        adminTableModel.addColumn("Full Name");
        updateAdminTable(adminTableModel, User.ADMIN_PREFIX);
        usersTable.setSize(100, 500);
        usersTable.setModel(adminTableModel);
    }

    private void updateAdminTable(DefaultTableModel model, String type) throws IOException {
        model.setRowCount(0);
        User[] user = User.getAll(type);

        for (int i = 0; i < User.getCount(type); i++)
            model.addRow(new Object[]{user[i].getId(), user[i].getFullName()});

        usersTable.updateUI();
    }

    private void updatePropertyTable(DefaultTableModel model) throws IOException {
        model.setRowCount(0);
        Property[] property = Property.getAll(keyWords);

        for (Property value : property) {
            try {
                model.addRow(new Object[]{value.getPropertyID(), value.getOwnerFullName()});
            } catch (NullPointerException w) {
                break;
            }
        }

        clearAllTextFields();
        propertyTable.updateUI();
    }

    private void setAdminDetails(User user) {
        idField.setText(user.getId());
        usernameField.setText(user.getUserName());
        fullNameField.setText(user.getFullName());
        passwordField.setText(user.getPassword());
        phoneNumberField.setText(user.getPhoneNumber());
        mainPanel.updateUI();
    }

    private void setPropertyDetails(Property property) throws IOException {
        User agent = User.getByID(property.getAgentID());

        propertyIdField.setText(property.getPropertyID());
        propertyAgentIdField.setText(property.getAgentID());
        propertyOwnerNameField.setText(property.getOwnerFullName());
        propertyAgentPhoneNumberField.setText(agent.getPhoneNumber());
        propertyAgentNameField.setText(agent.getFullName());
        propertyTypeField.setText(property.getPropertyType());
        propertyStatusField.setText(property.getPropertyStatus());
        propertyAddressField.setText(property.getPropertyAddress());
        propertyPriceField.setText("RM " + String.format(Locale.US, "%,d", property.getPropertyPrice()));
        propertyPaymentType.setText(property.getPaymentType());
    }

    private void clearAllTextFields() {
        idField.setText("");
        usernameField.setText("");
        fullNameField.setText("");
        passwordField.setText("");
        phoneNumberField.setText("");
        propertyIdField.setText("");
        propertyAgentIdField.setText("");
        propertyOwnerNameField.setText("");
        propertyAgentPhoneNumberField.setText("");
        propertyAgentNameField.setText("");
        propertyTypeField.setText("");
        propertyStatusField.setText("");
        propertyAddressField.setText("");
        propertyPriceField.setText("");
        propertyPaymentType.setText("");
        mainPanel.updateUI();
    }

    private void setOwnerComboBox() throws IOException {
        Property[] property = Property.getAll(new String[]{Property.ALL});
        LinkedHashSet<String> nameList = new LinkedHashSet<>();

        for (Property value : property) {
            nameList.add(value.getOwnerFullName());
        }
        String[] newArray = nameList.toArray(new String[0]);
        for (String s : newArray) {
            ownerComboBox.addItem(s);
        }
    }

    private void setPropertyTypeComboBox() throws IOException {
        Property[] property = Property.getAll(new String[]{Property.ALL});
        LinkedHashSet<String> propertyList = new LinkedHashSet<>();

        for (Property value : property) {
            propertyList.add(value.getPropertyType());
        }

        String[] newArray = propertyList.toArray(new String[0]);
        for (String s : newArray) {
            propertyTypeComboBox.addItem(s);
        }
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        tabbedPane = new JTabbedPane();
        tabbedPane.setTabPlacement(2);
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(tabbedPane, gbc);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        tabbedPane.addTab("Users", panel1);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(panel2, gbc);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(panel3, gbc);
        final JLabel label1 = new JLabel();
        label1.setText("ID");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(label1, gbc);
        final JLabel label2 = new JLabel();
        label2.setText("Username");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(label2, gbc);
        final JLabel label3 = new JLabel();
        label3.setText("Full Name");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(label3, gbc);
        usernameField = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 6;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel3.add(usernameField, gbc);
        fullNameField = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 8;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel3.add(fullNameField, gbc);
        passwordField = new JPasswordField();
        passwordField.setEditable(false);
        passwordField.setEnabled(false);
        passwordField.setFocusable(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel3.add(passwordField, gbc);
        final JLabel label4 = new JLabel();
        label4.setText("Password");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(label4, gbc);
        final JLabel label5 = new JLabel();
        label5.setText("No. of Property");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 12;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(label5, gbc);
        textField1 = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 12;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel3.add(textField1, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel3.add(spacer1, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel3.add(spacer2, gbc);
        final JPanel spacer3 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel3.add(spacer3, gbc);
        final JPanel spacer4 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 13;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel3.add(spacer4, gbc);
        final JPanel spacer5 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel3.add(spacer5, gbc);
        final JPanel spacer6 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel3.add(spacer6, gbc);
        final JPanel spacer7 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel3.add(spacer7, gbc);
        final JPanel spacer8 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 11;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel3.add(spacer8, gbc);
        idField = new JTextField();
        idField.setEditable(true);
        idField.setEnabled(true);
        idField.setFocusable(false);
        idField.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel3.add(idField, gbc);
        final JLabel label6 = new JLabel();
        label6.setText("Phone Number");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 10;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(label6, gbc);
        phoneNumberField = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 10;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel3.add(phoneNumberField, gbc);
        final JPanel spacer9 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 9;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel3.add(spacer9, gbc);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new CardLayout(0, 0));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel2.add(panel4, gbc);
        final JLabel label7 = new JLabel();
        label7.setHorizontalAlignment(0);
        label7.setHorizontalTextPosition(0);
        label7.setText("Details");
        panel4.add(label7, "Card1");
        final JScrollPane scrollPane1 = new JScrollPane();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel2.add(scrollPane1, gbc);
        usersTable = new JTable();
        usersTable.setRequestFocusEnabled(false);
        scrollPane1.setViewportView(usersTable);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel2.add(panel5, gbc);
        deleteButton = new JButton();
        deleteButton.setEnabled(false);
        deleteButton.setText("Delete");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel5.add(deleteButton, gbc);
        addButton = new JButton();
        addButton.setText("ADD");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel5.add(addButton, gbc);
        final JPanel spacer10 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel5.add(spacer10, gbc);
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel2.add(panel6, gbc);
        saveButton = new JButton();
        saveButton.setEnabled(false);
        saveButton.setText("Save");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel6.add(saveButton, gbc);
        final JPanel spacer11 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel6.add(spacer11, gbc);
        final JPanel spacer12 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel6.add(spacer12, gbc);
        final JPanel spacer13 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel2.add(spacer13, gbc);
        final JPanel spacer14 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel2.add(spacer14, gbc);
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel2.add(panel7, gbc);
        adminTabButton = new JButton();
        adminTabButton.setSelected(true);
        adminTabButton.setText("Admins");
        panel7.add(adminTabButton);
        agentTabButton = new JButton();
        agentTabButton.setHorizontalTextPosition(11);
        agentTabButton.setText("Agents");
        panel7.add(agentTabButton);
        tenantTabButton = new JButton();
        tenantTabButton.setText("Tenants");
        panel7.add(tenantTabButton);
        final JPanel spacer15 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(spacer15, gbc);
        final JPanel spacer16 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel1.add(spacer16, gbc);
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new GridBagLayout());
        tabbedPane.addTab("Properties", panel8);
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel8.add(panel9, gbc);
        final JPanel panel10 = new JPanel();
        panel10.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel9.add(panel10, gbc);
        propertyStatusComboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("All");
        defaultComboBoxModel1.addElement("Active");
        defaultComboBoxModel1.addElement("Inactive");
        propertyStatusComboBox.setModel(defaultComboBoxModel1);
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel10.add(propertyStatusComboBox, gbc);
        final JPanel spacer17 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel10.add(spacer17, gbc);
        final JPanel spacer18 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel10.add(spacer18, gbc);
        final JPanel spacer19 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel10.add(spacer19, gbc);
        ownerComboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("All");
        ownerComboBox.setModel(defaultComboBoxModel2);
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel10.add(ownerComboBox, gbc);
        paymentTypeComboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel3 = new DefaultComboBoxModel();
        defaultComboBoxModel3.addElement("All");
        defaultComboBoxModel3.addElement("Buy");
        defaultComboBoxModel3.addElement("Rent");
        paymentTypeComboBox.setModel(defaultComboBoxModel3);
        paymentTypeComboBox.setName("Type");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel10.add(paymentTypeComboBox, gbc);
        final JPanel spacer20 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel10.add(spacer20, gbc);
        final JPanel spacer21 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel10.add(spacer21, gbc);
        searchTextField = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        panel10.add(searchTextField, gbc);
        findButton = new JButton();
        findButton.setText("Find");
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 1;
        gbc.gridwidth = 5;
        gbc.fill = GridBagConstraints.BOTH;
        panel10.add(findButton, gbc);
        final JPanel spacer22 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel10.add(spacer22, gbc);
        final JPanel spacer23 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel10.add(spacer23, gbc);
        final JPanel spacer24 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 10;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel10.add(spacer24, gbc);
        final JPanel spacer25 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 8;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel10.add(spacer25, gbc);
        propertyTypeComboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel4 = new DefaultComboBoxModel();
        defaultComboBoxModel4.addElement("All");
        propertyTypeComboBox.setModel(defaultComboBoxModel4);
        gbc = new GridBagConstraints();
        gbc.gridx = 7;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel10.add(propertyTypeComboBox, gbc);
        commentsComboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel5 = new DefaultComboBoxModel();
        defaultComboBoxModel5.addElement("Show Comments");
        defaultComboBoxModel5.addElement("Hide Comments");
        commentsComboBox.setModel(defaultComboBoxModel5);
        gbc = new GridBagConstraints();
        gbc.gridx = 9;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel10.add(commentsComboBox, gbc);
        final JPanel panel11 = new JPanel();
        panel11.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel9.add(panel11, gbc);
        final JPanel spacer26 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel11.add(spacer26, gbc);
        final JPanel panel12 = new JPanel();
        panel12.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel8.add(panel12, gbc);
        final JLabel label8 = new JLabel();
        label8.setText("Property Price");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 17;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel12.add(label8, gbc);
        final JLabel label9 = new JLabel();
        label9.setText("Property Address");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 15;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel12.add(label9, gbc);
        final JLabel label10 = new JLabel();
        label10.setText("Property Status");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 13;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel12.add(label10, gbc);
        final JLabel label11 = new JLabel();
        label11.setText("Property Type");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel12.add(label11, gbc);
        final JLabel label12 = new JLabel();
        label12.setText("Agent Phone Number ");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel12.add(label12, gbc);
        final JLabel label13 = new JLabel();
        label13.setText("Agent Name");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel12.add(label13, gbc);
        final JLabel label14 = new JLabel();
        label14.setText("Owner Name");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel12.add(label14, gbc);
        propertyOwnerNameField = new JTextField();
        propertyOwnerNameField.setEditable(true);
        propertyOwnerNameField.setEnabled(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel12.add(propertyOwnerNameField, gbc);
        propertyAgentNameField = new JTextField();
        propertyAgentNameField.setEditable(true);
        propertyAgentNameField.setEnabled(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel12.add(propertyAgentNameField, gbc);
        propertyAgentPhoneNumberField = new JTextField();
        propertyAgentPhoneNumberField.setEditable(true);
        propertyAgentPhoneNumberField.setEnabled(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 9;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel12.add(propertyAgentPhoneNumberField, gbc);
        propertyTypeField = new JTextField();
        propertyTypeField.setEditable(true);
        propertyTypeField.setEnabled(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 11;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel12.add(propertyTypeField, gbc);
        propertyStatusField = new JTextField();
        propertyStatusField.setEditable(true);
        propertyStatusField.setEnabled(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 13;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel12.add(propertyStatusField, gbc);
        propertyAddressField = new JTextField();
        propertyAddressField.setEditable(true);
        propertyAddressField.setEnabled(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 15;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel12.add(propertyAddressField, gbc);
        propertyPriceField = new JTextField();
        propertyPriceField.setEditable(true);
        propertyPriceField.setEnabled(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 17;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel12.add(propertyPriceField, gbc);
        final JPanel spacer27 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel12.add(spacer27, gbc);
        final JPanel spacer28 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel12.add(spacer28, gbc);
        final JPanel spacer29 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel12.add(spacer29, gbc);
        final JPanel spacer30 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 10;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel12.add(spacer30, gbc);
        final JPanel spacer31 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 12;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel12.add(spacer31, gbc);
        final JPanel spacer32 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 14;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel12.add(spacer32, gbc);
        final JPanel spacer33 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 16;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel12.add(spacer33, gbc);
        final JLabel label15 = new JLabel();
        label15.setText("Property ID");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel12.add(label15, gbc);
        final JLabel label16 = new JLabel();
        label16.setText("Agent ID");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel12.add(label16, gbc);
        propertyIdField = new JTextField();
        propertyIdField.setEditable(true);
        propertyIdField.setEnabled(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel12.add(propertyIdField, gbc);
        propertyAgentIdField = new JTextField();
        propertyAgentIdField.setEditable(true);
        propertyAgentIdField.setEnabled(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel12.add(propertyAgentIdField, gbc);
        final JPanel spacer34 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel12.add(spacer34, gbc);
        final JPanel spacer35 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel12.add(spacer35, gbc);
        final JLabel label17 = new JLabel();
        label17.setText("Payment");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 19;
        gbc.anchor = GridBagConstraints.WEST;
        panel12.add(label17, gbc);
        propertyPaymentType = new JTextField();
        propertyPaymentType.setEditable(true);
        propertyPaymentType.setEnabled(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 19;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel12.add(propertyPaymentType, gbc);
        final JPanel spacer36 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 18;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel12.add(spacer36, gbc);
        final JPanel spacer37 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel12.add(spacer37, gbc);
        final JPanel panel13 = new JPanel();
        panel13.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 4;
        gbc.fill = GridBagConstraints.BOTH;
        panel8.add(panel13, gbc);
        final JScrollPane scrollPane2 = new JScrollPane();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel13.add(scrollPane2, gbc);
        propertyTable = new JTable();
        propertyTable.setRequestFocusEnabled(false);
        scrollPane2.setViewportView(propertyTable);
        final JPanel spacer38 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel13.add(spacer38, gbc);
        final JPanel spacer39 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel8.add(spacer39, gbc);
        final JPanel spacer40 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel8.add(spacer40, gbc);
        final JPanel spacer41 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        mainPanel.add(spacer41, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

}
