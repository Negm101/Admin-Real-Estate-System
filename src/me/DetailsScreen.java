package me;


import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class DetailsScreen {
    private JPanel jPanel;
    private JButton saveButton;
    private JTextField idField;
    private JTextField userNameField;
    private JTextField fullNameField;
    private JPasswordField passField;
    private JRadioButton tenantRadioButton;
    private JRadioButton agentRadioButton;
    private JPanel userTypePanel;
    private JLabel typeLabel;
    private JTextField phoneNumberField;
    public static JFrame frame = new JFrame("Details");
    public static final String ADD = "ADD";
    public static final String REGISTER = "REGISTER";

    public DetailsScreen(User user, String type) throws IOException {

        if (type.equals(REGISTER)) {
            saveButton.setText("Register");
            userTypePanel.setVisible(true);
            typeLabel.setVisible(true);
            agentRadioButton.setSelected(true);

        }


        idField.setText(user.getId());
        userNameField.setText(user.getUserName());
        fullNameField.setText(user.getFullName());
        passField.setText(user.getPassword());
        saveButton.setMargin(new Insets(4, 5, 4, 5));


        saveButton.addActionListener(e -> {
            user.setId(idField.getText());
            user.setUserName(userNameField.getText());
            user.setFullName(fullNameField.getText());
            user.setPassword(passField.getText());
            try {
                user.save();
                frame.setVisible(false);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        tenantRadioButton.addActionListener(e -> {
            agentRadioButton.setSelected(false);

            try {
                idField.setText(User.getNextId(User.TENANT_PREFIX));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        agentRadioButton.addActionListener(e -> {
            tenantRadioButton.setSelected(false);
            try {
                idField.setText(User.getNextId(User.AGENT_PREFIX));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        });
    }

    public static void lunch(User user, String type) throws IOException {
        Dimension frameDimension = new Dimension(300, 240);
        frame.setContentPane(new DetailsScreen(user, type).jPanel);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setSize(frameDimension);
        frame.setMinimumSize(frameDimension);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
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
        jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());
        final JLabel label1 = new JLabel();
        label1.setText("ID");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        jPanel.add(label1, gbc);
        final JLabel label2 = new JLabel();
        label2.setText("UserName");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 7;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        jPanel.add(label2, gbc);
        idField = new JTextField();
        idField.setEnabled(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel.add(idField, gbc);
        userNameField = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 7;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel.add(userNameField, gbc);
        final JLabel label3 = new JLabel();
        label3.setText("Full Name");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        jPanel.add(label3, gbc);
        final JLabel label4 = new JLabel();
        label4.setText("Password");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 8;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        jPanel.add(label4, gbc);
        saveButton = new JButton();
        saveButton.setMargin(new Insets(0, 0, 0, 0));
        saveButton.setText("Save");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 11;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel.add(saveButton, gbc);
        fullNameField = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel.add(fullNameField, gbc);
        passField = new JPasswordField();
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 8;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel.add(passField, gbc);
        userTypePanel = new JPanel();
        userTypePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        userTypePanel.setVisible(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 9;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
        jPanel.add(userTypePanel, gbc);
        tenantRadioButton = new JRadioButton();
        tenantRadioButton.setText("Tenant");
        userTypePanel.add(tenantRadioButton);
        agentRadioButton = new JRadioButton();
        agentRadioButton.setText("Agent");
        userTypePanel.add(agentRadioButton);
        typeLabel = new JLabel();
        typeLabel.setText("Type");
        typeLabel.setVisible(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 9;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        jPanel.add(typeLabel, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel.add(spacer1, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel.add(spacer2, gbc);
        final JPanel spacer3 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 12;
        gbc.fill = GridBagConstraints.VERTICAL;
        jPanel.add(spacer3, gbc);
        final JPanel spacer4 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.VERTICAL;
        jPanel.add(spacer4, gbc);
        final JLabel label5 = new JLabel();
        label5.setText("Phone Number");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        jPanel.add(label5, gbc);
        phoneNumberField = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel.add(phoneNumberField, gbc);
        final JPanel spacer5 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.VERTICAL;
        jPanel.add(spacer5, gbc);
        final JPanel spacer6 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.VERTICAL;
        jPanel.add(spacer6, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return jPanel;
    }

}
