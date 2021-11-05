/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop_management_system;

import java.awt.CardLayout;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import sun.misc.FloatingDecimal;

/**
 *
 * @author User
 */
public class SellerHome_Page extends javax.swing.JFrame {

    private CardLayout cardLayout;
    Color panedefault;
    Color paneclick, paneclick1;
    Color paneenter;

    /**
     * Creates new form SellerHome_Page
     */
    public SellerHome_Page() {
        initComponents();
    }

    public SellerHome_Page(String uname, String uid) {
        initComponents();
        panedefault = new Color(218, 223, 225);
        paneclick = new Color(51, 110, 123);
        paneclick1 = new Color(149, 165, 166);
        paneenter = new Color(255, 51, 51);

        pane1.setBackground(panedefault);
        pane2.setBackground(panedefault);
        pane3.setBackground(panedefault);
        pane4.setBackground(panedefault);
        pane5.setBackground(panedefault);
        pane6.setBackground(panedefault);

        showname.setText(uname);
        sellerName.setText(uname);
        s_id.setText(uid);

        saveBtn.setVisible(false);
        backBtn.setVisible(false);
        addBtn.setVisible(false);
        showDate();
    }

    void showDate() {
        Date objDate = new Date();
        dateField.setText(objDate.toString());
    }

    void setscrollpane() {
        //qty.setValue(1);

    }

    // CREATE TEXT FILE
    void createFile() {
        File f = new File("C:\\Users\\User\\Desktop\\Shop Management System\\blankfile.txt");
        try {
            f.createNewFile();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        System.out.println("Show File Path: " + f.getPath());
    }

    void printReceipt() {
        MessageFormat header = new MessageFormat("Printing in progress");
        MessageFormat footer = new MessageFormat("Page {0, number, integer}");
        try {
            jTable1.print(JTable.PrintMode.NORMAL, header, footer);
        } catch (java.awt.print.PrinterException e) {
            System.err.format("No Printer Found", e.getMessage());
        }
    }
    // SHOW MOST SELLING PRODUCT INFO TABLE IN MOST SELL PAGE
    void showMSPTable(){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection;
            try {
                connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ShopManagementSystem;selectMethod=cursor", "sa", "123456");
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("SELECT TOP(5) P_ID, P_Name, ISNULL(Items_Sold, 0) AS Items_Sold FROM Products ORDER BY Items_Sold DESC");

                while (MSPTable.getRowCount() > 0) {
                    ((DefaultTableModel) MSPTable.getModel()).removeRow(0);
                }
                int col = rs.getMetaData().getColumnCount();
                while (rs.next()) {
                    Object[] rows = new Object[col];
                    for (int i = 1; i <= col; i++) {
                        rows[i - 1] = rs.getObject(i);
                    }
                    ((DefaultTableModel) MSPTable.getModel()).insertRow(rs.getRow() - 1, rows);

                    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
                    MSPTable.setDefaultRenderer(String.class, centerRenderer);
                }
                rs.close();
                st.close();
            } catch (SQLException ex) {
                Logger.getLogger(AdminHome_Page.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AdminHome_Page.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // SHOW MOST SELLING CUSTOMER INFO TABLE IN MOST SELL PAGE
    void showMSCTable(){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection;
            try {
                connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ShopManagementSystem;selectMethod=cursor", "sa", "123456");
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("SELECT TOP(5) cus.Cus_ID, cus.Cus_PhoneNo, ROUND(cus.Cus_Point, 2) AS Point, COUNT(o.OrderID) AS Total_Orders FROM Customer AS cus LEFT JOIN Orders AS o ON cus.Cus_ID = o.CustomerID GROUP BY cus.Cus_ID, cus.Cus_PhoneNo, cus.Cus_Point HAVING cus.Cus_ID IN (SELECT CustomerID FROM Orders WHERE oDate >= DATEADD(day, -30, GETDATE()) and oDate <= GETDATE()) ORDER BY Point DESC, Total_Orders ");

                while (MSCTable.getRowCount() > 0) {
                    ((DefaultTableModel) MSCTable.getModel()).removeRow(0);
                }
                int col = rs.getMetaData().getColumnCount();
                while (rs.next()) {
                    Object[] rows = new Object[col];
                    for (int i = 1; i <= col; i++) {
                        rows[i - 1] = rs.getObject(i);
                    }
                    ((DefaultTableModel) MSCTable.getModel()).insertRow(rs.getRow() - 1, rows);
                    
                    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
                    MSCTable.setDefaultRenderer(String.class, centerRenderer);
                }
                rs.close();
                st.close();
            } catch (SQLException ex) {
                Logger.getLogger(AdminHome_Page.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AdminHome_Page.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // SHOW MOST SELLING SELLER INFO TABLE IN MOST SELL PAGE
    void showMSSTable(){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection;
            try {
                connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ShopManagementSystem;selectMethod=cursor", "sa", "123456");
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("SELECT TOP(5) s.SellerID, s.SellerName, o.Total_Orders, od.Total_Pro FROM Seller s LEFT JOIN (SELECT SellerID, COUNT(*) AS Total_Orders FROM Orders GROUP BY SellerID) o ON o.SellerID = s.SellerID LEFT JOIN (SELECT SellerID, SUM(Quantity) AS Total_Pro FROM OrderDetail GROUP BY SellerID) od ON od.SellerID = s.SellerID WHERE s.SellerID IN (SELECT SellerID FROM Orders WHERE oDate >= DATEADD(day, -30, GETDATE()) and oDate <= GETDATE()) ORDER BY o.Total_Orders DESC");

                while (MSSTable.getRowCount() > 0) {
                    ((DefaultTableModel) MSSTable.getModel()).removeRow(0);
                }
                int col = rs.getMetaData().getColumnCount();
                while (rs.next()) {
                    Object[] rows = new Object[col];
                    for (int i = 1; i <= col; i++) {
                        rows[i - 1] = rs.getObject(i);
                    }
                    ((DefaultTableModel) MSSTable.getModel()).insertRow(rs.getRow() - 1, rows);
                    
                    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
                    MSSTable.setDefaultRenderer(String.class, centerRenderer);
                }
                rs.close();
                st.close();
            } catch (SQLException ex) {
                Logger.getLogger(AdminHome_Page.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AdminHome_Page.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // SHOW PRODUCTS TABLE IN PRODUCTS PAGE
    void showPTable() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection;
            try {
                connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ShopManagementSystem;selectMethod=cursor", "sa", "123456");
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("SELECT cat.C_ID, cat.C_Name, pro.P_ID, pro.P_Name, ROUND(pro.P_Price,2), pro.P_Quantity FROM Categories AS cat INNER JOIN Products AS pro ON cat.C_ID = pro.P_C_ID ORDER BY cat.C_ID, pro.P_ID");

                while (PTable.getRowCount() > 0) {
                    ((DefaultTableModel) PTable.getModel()).removeRow(0);
                }
                int col = rs.getMetaData().getColumnCount();
                while (rs.next()) {
                    Object[] rows = new Object[col];
                    for (int i = 1; i <= col; i++) {
                        rows[i - 1] = rs.getObject(i);
                    }
                    ((DefaultTableModel) PTable.getModel()).insertRow(rs.getRow() - 1, rows);
                }
                rs.close();
                st.close();
            } catch (SQLException ex) {
                Logger.getLogger(AdminHome_Page.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AdminHome_Page.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // SHOW ORDER TABLE IN ORDERS PAGE
    void showOrderTable() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection;
            try {
                connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ShopManagementSystem;selectMethod=cursor", "sa", "123456");
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("SELECT OrderID, CustomerID, SellerID, OrderDate, TotalAmount FROM Orders");

                while (OrderTable.getRowCount() > 0) {
                    ((DefaultTableModel) OrderTable.getModel()).removeRow(0);
                }
                int col = rs.getMetaData().getColumnCount();
                while (rs.next()) {
                    Object[] rows = new Object[col];
                    for (int i = 1; i <= col; i++) {
                        rows[i - 1] = rs.getObject(i);
                    }
                    ((DefaultTableModel) OrderTable.getModel()).insertRow(rs.getRow() - 1, rows);

                    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
                    OrderTable.setDefaultRenderer(String.class, centerRenderer);
                }
                rs.close();
                st.close();
            } catch (SQLException ex) {
                Logger.getLogger(AdminHome_Page.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AdminHome_Page.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabelClose = new javax.swing.JLabel();
        jLabelMin = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jHomeButtonPanel = new javax.swing.JPanel();
        pane1 = new javax.swing.JPanel();
        jHomeButtonLabel = new javax.swing.JLabel();
        jUserProfileButtonPanel = new javax.swing.JPanel();
        pane2 = new javax.swing.JPanel();
        jUserProfileButtonLabel = new javax.swing.JLabel();
        jProductsButtonPanel = new javax.swing.JPanel();
        pane3 = new javax.swing.JPanel();
        jEventsButtonLabel = new javax.swing.JLabel();
        jOrdersButtonPanel = new javax.swing.JPanel();
        pane4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jMostSellingButtonPanel = new javax.swing.JPanel();
        pane6 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLogOutButtonPanel = new javax.swing.JPanel();
        pane5 = new javax.swing.JPanel();
        jEventsButtonLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        HomePage = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        showname = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        cancelBtn1 = new javax.swing.JButton();
        addBtn = new javax.swing.JButton();
        productName = new javax.swing.JTextField();
        productID = new javax.swing.JTextField();
        searchBtn = new javax.swing.JButton();
        jPanel24 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        s_id = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        sellerName = new javax.swing.JTextField();
        dateField = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        stock = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        total = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        qty = new javax.swing.JSpinner();
        price = new javax.swing.JTextField();
        jPanel25 = new javax.swing.JPanel();
        exitBtn = new javax.swing.JButton();
        jPanel26 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel29 = new javax.swing.JLabel();
        subTotal = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        cashRtd = new javax.swing.JTextField();
        okBtn = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        cashRcd = new javax.swing.JTextField();
        confirmBtn = new javax.swing.JButton();
        cancelBtn2 = new javax.swing.JButton();
        jPanel27 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        c_id = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        c_mobile = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        c_point = new javax.swing.JTextField();
        addNewBtn = new javax.swing.JButton();
        saveBtn = new javax.swing.JButton();
        clearBtn = new javax.swing.JButton();
        backBtn = new javax.swing.JButton();
        jPanel28 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        c_mob_input = new javax.swing.JTextField();
        checkBtn = new javax.swing.JButton();
        toTal = new javax.swing.JTextField();
        totalT = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        discount = new javax.swing.JTextField();
        useBtn = new javax.swing.JButton();
        UserProfilePage = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jTextFieldId = new javax.swing.JTextField();
        jPanel18 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jTextFieldPhone = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldCity = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldCountry = new javax.swing.JTextField();
        jPanel30 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jTextFieldTotalOrder = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jTextFieldName = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jTextFieldBirthdate = new javax.swing.JTextField();
        jPanel15 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jTextFieldEmail = new javax.swing.JTextField();
        jPanel16 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jTextFieldGender = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldAddress = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel31 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        sellermessage = new javax.swing.JTextArea();
        jLabel40 = new javax.swing.JLabel();
        jButtonMessageSubmit = new javax.swing.JButton();
        ProductsPage = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        PTable = new javax.swing.JTable();
        SearchProduct = new javax.swing.JTextField();
        Pcat = new javax.swing.JComboBox<>();
        SearchBtn = new javax.swing.JButton();
        RefreshBtn = new javax.swing.JButton();
        OrdersPage = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        OrderTable = new javax.swing.JTable();
        MostSellsPage = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jPanel29 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        MSPTable = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        MSSTable = new javax.swing.JTable();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        MSCTable = new javax.swing.JTable();
        jLabel39 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel10.setBackground(new java.awt.Color(33, 49, 63));

        jLabelClose.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelClose.setForeground(new java.awt.Color(255, 255, 255));
        jLabelClose.setText(" X");
        jLabelClose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelCloseMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabelCloseMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabelCloseMouseExited(evt);
            }
        });

        jLabelMin.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabelMin.setForeground(new java.awt.Color(255, 255, 255));
        jLabelMin.setText(" -");
        jLabelMin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelMin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelMinMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabelMinMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabelMinMouseExited(evt);
            }
        });

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Office_icon.png"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 32)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Digital Shop Management System");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(225, 225, 225)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 273, Short.MAX_VALUE)
                .addComponent(jLabelMin, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelClose, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabelMin, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelClose))
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1170, 90));

        jPanel1.setBackground(new java.awt.Color(44, 62, 80));

        jHomeButtonPanel.setBackground(new java.awt.Color(218, 223, 225));
        jHomeButtonPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jHomeButtonPanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jHomeButtonPanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jHomeButtonPanelMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jHomeButtonPanelMousePressed(evt);
            }
        });

        javax.swing.GroupLayout pane1Layout = new javax.swing.GroupLayout(pane1);
        pane1.setLayout(pane1Layout);
        pane1Layout.setHorizontalGroup(
            pane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );
        pane1Layout.setVerticalGroup(
            pane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jHomeButtonLabel.setBackground(new java.awt.Color(255, 255, 255));
        jHomeButtonLabel.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jHomeButtonLabel.setForeground(new java.awt.Color(36, 37, 42));
        jHomeButtonLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/home_icon.png"))); // NOI18N
        jHomeButtonLabel.setText(" Home");

        javax.swing.GroupLayout jHomeButtonPanelLayout = new javax.swing.GroupLayout(jHomeButtonPanel);
        jHomeButtonPanel.setLayout(jHomeButtonPanelLayout);
        jHomeButtonPanelLayout.setHorizontalGroup(
            jHomeButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jHomeButtonPanelLayout.createSequentialGroup()
                .addComponent(pane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jHomeButtonLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jHomeButtonPanelLayout.setVerticalGroup(
            jHomeButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jHomeButtonLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
        );

        jUserProfileButtonPanel.setBackground(new java.awt.Color(218, 223, 225));
        jUserProfileButtonPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jUserProfileButtonPanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jUserProfileButtonPanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jUserProfileButtonPanelMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jUserProfileButtonPanelMousePressed(evt);
            }
        });

        javax.swing.GroupLayout pane2Layout = new javax.swing.GroupLayout(pane2);
        pane2.setLayout(pane2Layout);
        pane2Layout.setHorizontalGroup(
            pane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );
        pane2Layout.setVerticalGroup(
            pane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jUserProfileButtonLabel.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jUserProfileButtonLabel.setForeground(new java.awt.Color(36, 37, 42));
        jUserProfileButtonLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/user_icon.png"))); // NOI18N
        jUserProfileButtonLabel.setText(" User Profile");

        javax.swing.GroupLayout jUserProfileButtonPanelLayout = new javax.swing.GroupLayout(jUserProfileButtonPanel);
        jUserProfileButtonPanel.setLayout(jUserProfileButtonPanelLayout);
        jUserProfileButtonPanelLayout.setHorizontalGroup(
            jUserProfileButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jUserProfileButtonPanelLayout.createSequentialGroup()
                .addComponent(pane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jUserProfileButtonLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                .addContainerGap())
        );
        jUserProfileButtonPanelLayout.setVerticalGroup(
            jUserProfileButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jUserProfileButtonLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
        );

        jProductsButtonPanel.setBackground(new java.awt.Color(218, 223, 225));
        jProductsButtonPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jProductsButtonPanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jProductsButtonPanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jProductsButtonPanelMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jProductsButtonPanelMousePressed(evt);
            }
        });

        javax.swing.GroupLayout pane3Layout = new javax.swing.GroupLayout(pane3);
        pane3.setLayout(pane3Layout);
        pane3Layout.setHorizontalGroup(
            pane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );
        pane3Layout.setVerticalGroup(
            pane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jEventsButtonLabel.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jEventsButtonLabel.setForeground(new java.awt.Color(36, 37, 42));
        jEventsButtonLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/product_icon.png"))); // NOI18N
        jEventsButtonLabel.setText(" Products");

        javax.swing.GroupLayout jProductsButtonPanelLayout = new javax.swing.GroupLayout(jProductsButtonPanel);
        jProductsButtonPanel.setLayout(jProductsButtonPanelLayout);
        jProductsButtonPanelLayout.setHorizontalGroup(
            jProductsButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jProductsButtonPanelLayout.createSequentialGroup()
                .addComponent(pane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jEventsButtonLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(53, 53, 53))
        );
        jProductsButtonPanelLayout.setVerticalGroup(
            jProductsButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pane3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jEventsButtonLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
        );

        jOrdersButtonPanel.setBackground(new java.awt.Color(218, 223, 225));
        jOrdersButtonPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jOrdersButtonPanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jOrdersButtonPanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jOrdersButtonPanelMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jOrdersButtonPanelMousePressed(evt);
            }
        });

        javax.swing.GroupLayout pane4Layout = new javax.swing.GroupLayout(pane4);
        pane4.setLayout(pane4Layout);
        pane4Layout.setHorizontalGroup(
            pane4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );
        pane4Layout.setVerticalGroup(
            pane4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(36, 37, 42));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/order_icon.png"))); // NOI18N
        jLabel4.setText(" Orders");

        javax.swing.GroupLayout jOrdersButtonPanelLayout = new javax.swing.GroupLayout(jOrdersButtonPanel);
        jOrdersButtonPanel.setLayout(jOrdersButtonPanelLayout);
        jOrdersButtonPanelLayout.setHorizontalGroup(
            jOrdersButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jOrdersButtonPanelLayout.createSequentialGroup()
                .addComponent(pane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jOrdersButtonPanelLayout.setVerticalGroup(
            jOrdersButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pane4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
        );

        jMostSellingButtonPanel.setBackground(new java.awt.Color(218, 223, 225));
        jMostSellingButtonPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMostSellingButtonPanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jMostSellingButtonPanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jMostSellingButtonPanelMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMostSellingButtonPanelMousePressed(evt);
            }
        });

        javax.swing.GroupLayout pane6Layout = new javax.swing.GroupLayout(pane6);
        pane6.setLayout(pane6Layout);
        pane6Layout.setHorizontalGroup(
            pane6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );
        pane6Layout.setVerticalGroup(
            pane6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(36, 37, 42));
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/sales_icon.png"))); // NOI18N
        jLabel8.setText(" Most Sells");

        javax.swing.GroupLayout jMostSellingButtonPanelLayout = new javax.swing.GroupLayout(jMostSellingButtonPanel);
        jMostSellingButtonPanel.setLayout(jMostSellingButtonPanelLayout);
        jMostSellingButtonPanelLayout.setHorizontalGroup(
            jMostSellingButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jMostSellingButtonPanelLayout.createSequentialGroup()
                .addComponent(pane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 30, Short.MAX_VALUE))
        );
        jMostSellingButtonPanelLayout.setVerticalGroup(
            jMostSellingButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pane6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
        );

        jLogOutButtonPanel.setBackground(new java.awt.Color(218, 223, 225));
        jLogOutButtonPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLogOutButtonPanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLogOutButtonPanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLogOutButtonPanelMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLogOutButtonPanelMousePressed(evt);
            }
        });

        javax.swing.GroupLayout pane5Layout = new javax.swing.GroupLayout(pane5);
        pane5.setLayout(pane5Layout);
        pane5Layout.setHorizontalGroup(
            pane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );
        pane5Layout.setVerticalGroup(
            pane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 74, Short.MAX_VALUE)
        );

        jEventsButtonLabel1.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jEventsButtonLabel1.setForeground(new java.awt.Color(36, 37, 42));
        jEventsButtonLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\User\\Desktop\\Shop Management System\\Shop_Management_System\\src\\images\\logout_icon.png")); // NOI18N
        jEventsButtonLabel1.setText(" Log Out");

        javax.swing.GroupLayout jLogOutButtonPanelLayout = new javax.swing.GroupLayout(jLogOutButtonPanel);
        jLogOutButtonPanel.setLayout(jLogOutButtonPanelLayout);
        jLogOutButtonPanelLayout.setHorizontalGroup(
            jLogOutButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLogOutButtonPanelLayout.createSequentialGroup()
                .addComponent(pane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jLogOutButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLogOutButtonPanelLayout.createSequentialGroup()
                    .addContainerGap(21, Short.MAX_VALUE)
                    .addComponent(jEventsButtonLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
        jLogOutButtonPanelLayout.setVerticalGroup(
            jLogOutButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pane5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jLogOutButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jEventsButtonLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jUserProfileButtonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jHomeButtonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLogOutButtonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jProductsButtonPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jMostSellingButtonPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jOrdersButtonPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jHomeButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jUserProfileButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jProductsButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jOrdersButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jMostSellingButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLogOutButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(146, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 290, 680));

        jTabbedPane1.setMinimumSize(new java.awt.Dimension(41, 84));

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));
        jPanel19.setPreferredSize(new java.awt.Dimension(643, 74));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(102, 102, 102));
        jLabel17.setText(" Home");

        name.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        name.setForeground(new java.awt.Color(102, 102, 102));
        name.setText("Logged in as:");

        showname.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        showname.setText("ABC");

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 288, Short.MAX_VALUE)
                .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(showname, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(showname, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(name, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(189, 195, 199));
        jPanel6.setPreferredSize(new java.awt.Dimension(880, 599));

        jPanel13.setBackground(new java.awt.Color(255, 153, 0));
        jPanel13.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.gray, java.awt.Color.gray));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("Product ID");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel14.setText("Product Name");

        cancelBtn1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cancelBtn1.setText("CANCEL");
        cancelBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtn1ActionPerformed(evt);
            }
        });

        addBtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        addBtn.setText("ADD");
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });

        productName.setEditable(false);
        productName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        productID.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        searchBtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        searchBtn.setText("SEARCH");
        searchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBtnActionPerformed(evt);
            }
        });

        jPanel24.setBackground(new java.awt.Color(255, 204, 102));
        jPanel24.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.gray, java.awt.Color.gray));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setText("Seller ID");

        s_id.setEditable(false);
        s_id.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setText("Seller Name");

        sellerName.setEditable(false);
        sellerName.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        dateField.setEditable(false);
        dateField.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel34.setText("Date");

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel21)
                .addGap(18, 18, 18)
                .addComponent(s_id, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel22)
                .addGap(18, 18, 18)
                .addComponent(sellerName, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel34)
                .addGap(18, 18, 18)
                .addComponent(dateField)
                .addContainerGap())
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(s_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)
                    .addComponent(sellerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel23.setText("Total");

        stock.setEditable(false);
        stock.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel24.setText("In Stock");

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel25.setText("Price");

        total.setEditable(false);
        total.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel26.setText("Qty");

        qty.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        qty.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                qtyStateChanged(evt);
            }
        });

        price.setEditable(false);
        price.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jPanel25.setBackground(new java.awt.Color(204, 204, 204));
        jPanel25.setForeground(new java.awt.Color(153, 153, 153));

        exitBtn.setBackground(new java.awt.Color(255, 255, 255));
        exitBtn.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        exitBtn.setForeground(new java.awt.Color(255, 0, 0));
        exitBtn.setText("EXIT");
        exitBtn.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.black, java.awt.Color.black));
        exitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(exitBtn)
                .addContainerGap())
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(exitBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel26.setBackground(new java.awt.Color(255, 204, 0));
        jPanel26.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.gray, java.awt.Color.gray));

        jTable1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product ID", "Product Name", "Quantity", "Price", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(180);
        }

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(263, 263, 263))
        );

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel29.setText("Sub Total");

        subTotal.setEditable(false);
        subTotal.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel28.setText("Cash Returned");

        cashRtd.setEditable(false);
        cashRtd.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        okBtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        okBtn.setText("OK");
        okBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okBtnActionPerformed(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel27.setText("Cash Received");

        cashRcd.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        confirmBtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        confirmBtn.setText("CONFIRM");
        confirmBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmBtnActionPerformed(evt);
            }
        });

        cancelBtn2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cancelBtn2.setText("CANCEL");
        cancelBtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtn2ActionPerformed(evt);
            }
        });

        jPanel27.setBackground(new java.awt.Color(255, 204, 0));
        jPanel27.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.gray, java.awt.Color.gray));

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel31.setText("Customer ID");

        c_id.setEditable(false);
        c_id.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel32.setText("Mobile NO.");

        c_mobile.setEditable(false);
        c_mobile.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel33.setText("Point");

        c_point.setEditable(false);
        c_point.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        addNewBtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        addNewBtn.setText("ADD NEW");
        addNewBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewBtnActionPerformed(evt);
            }
        });

        saveBtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        saveBtn.setText("SAVE");
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });

        clearBtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        clearBtn.setText("CLEAR");
        clearBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearBtnActionPerformed(evt);
            }
        });

        backBtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        backBtn.setText("BACK");
        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel31)
                    .addComponent(jLabel32)
                    .addComponent(jLabel33))
                .addGap(18, 18, 18)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel27Layout.createSequentialGroup()
                        .addComponent(c_id, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(saveBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                        .addComponent(backBtn))
                    .addGroup(jPanel27Layout.createSequentialGroup()
                        .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(c_point, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                            .addComponent(c_mobile))
                        .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel27Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(addNewBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel27Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(clearBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(c_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(backBtn)
                    .addComponent(saveBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel32)
                    .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(c_mobile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(clearBtn)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(c_point, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33)
                    .addComponent(addNewBtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel28.setBackground(new java.awt.Color(255, 204, 102));
        jPanel28.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.gray, java.awt.Color.gray));

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 153, 102));
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("Customer Mobile No.");

        c_mob_input.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        checkBtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        checkBtn.setText("CHECK");
        checkBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(14, 14, 14))
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addComponent(c_mob_input)
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel28Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(checkBtn)
                .addContainerGap())
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(c_mob_input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(checkBtn))
        );

        toTal.setEditable(false);
        toTal.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        totalT.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        totalT.setText("Total");

        jLabel35.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel35.setText("Discount");

        discount.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        useBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        useBtn.setText("USE");
        useBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                useBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel14)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                                        .addComponent(jLabel26)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(qty, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(22, 22, 22)))
                                .addGap(15, 15, 15)
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel13Layout.createSequentialGroup()
                                        .addComponent(jLabel23)
                                        .addGap(28, 28, 28)
                                        .addComponent(total))
                                    .addComponent(productName)))
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addGroup(jPanel13Layout.createSequentialGroup()
                                        .addGap(126, 126, 126)
                                        .addComponent(productID, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(searchBtn)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24)
                            .addComponent(jLabel25))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(price, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(cancelBtn1)
                                .addGap(18, 18, 18)
                                .addComponent(addBtn))
                            .addComponent(stock))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel13Layout.createSequentialGroup()
                                        .addComponent(totalT)
                                        .addGap(73, 73, 73))
                                    .addComponent(jLabel27)
                                    .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel13Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(toTal, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel13Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(subTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cashRcd, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(jLabel28)
                                .addGap(18, 18, 18)
                                .addComponent(cashRtd, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addGap(105, 105, 105)
                                .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel35)
                                .addGap(18, 18, 18)
                                .addComponent(discount, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(useBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(okBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cancelBtn2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(confirmBtn)))))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(13, 13, 13)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(productID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(searchBtn)
                            .addComponent(jLabel24)
                            .addComponent(stock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(price, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25)
                            .addComponent(productName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel26)
                            .addComponent(qty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23)
                            .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cancelBtn1)
                            .addComponent(addBtn)))
                    .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(okBtn)
                    .addComponent(cancelBtn2)
                    .addComponent(confirmBtn)
                    .addComponent(toTal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalT)
                    .addComponent(jLabel35)
                    .addComponent(discount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(useBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(subTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cashRcd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel27))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cashRtd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28))
                        .addContainerGap())
                    .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 599, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout HomePageLayout = new javax.swing.GroupLayout(HomePage);
        HomePage.setLayout(HomePageLayout);
        HomePageLayout.setHorizontalGroup(
            HomePageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, 875, Short.MAX_VALUE)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 875, Short.MAX_VALUE)
        );
        HomePageLayout.setVerticalGroup(
            HomePageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomePageLayout.createSequentialGroup()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab1", HomePage);

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));

        jPanel17.setBackground(new java.awt.Color(204, 204, 255));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Seller ID");

        jTextFieldId.setEditable(false);
        jTextFieldId.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addGap(23, 23, 23)
                .addComponent(jTextFieldId, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                    .addComponent(jTextFieldId, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("Phone No");

        jTextFieldPhone.setEditable(false);
        jTextFieldPhone.setBackground(new java.awt.Color(204, 204, 255));
        jTextFieldPhone.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addComponent(jTextFieldPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel8.setBackground(new java.awt.Color(204, 204, 255));
        jPanel8.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanel8.setPreferredSize(new java.awt.Dimension(230, 50));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("City");

        jTextFieldCity.setEditable(false);
        jTextFieldCity.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTextFieldCity, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldCity, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel14.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanel14.setPreferredSize(new java.awt.Dimension(230, 50));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Country");

        jTextFieldCountry.setEditable(false);
        jTextFieldCountry.setBackground(new java.awt.Color(204, 204, 255));
        jTextFieldCountry.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTextFieldCountry, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldCountry, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel30.setPreferredSize(new java.awt.Dimension(230, 50));

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel38.setText("Total Sells");

        jTextFieldTotalOrder.setEditable(false);
        jTextFieldTotalOrder.setBackground(new java.awt.Color(204, 204, 255));
        jTextFieldTotalOrder.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel38)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTextFieldTotalOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(jTextFieldTotalOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                    .addComponent(jPanel30, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setText("Seller Name");

        jTextFieldName.setEditable(false);
        jTextFieldName.setBackground(new java.awt.Color(204, 204, 255));
        jTextFieldName.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTextFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldName, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel11.setBackground(new java.awt.Color(204, 204, 255));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel15.setText("Birth Date");

        jTextFieldBirthdate.setEditable(false);
        jTextFieldBirthdate.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 133, Short.MAX_VALUE)
                .addComponent(jTextFieldBirthdate, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldBirthdate, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel15.setBackground(new java.awt.Color(204, 204, 255));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel16.setText("Email");

        jTextFieldEmail.setEditable(false);
        jTextFieldEmail.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTextFieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel12.setText("Gender");

        jTextFieldGender.setEditable(false);
        jTextFieldGender.setBackground(new java.awt.Color(204, 204, 255));
        jTextFieldGender.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTextFieldGender, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextFieldGender, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel7.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanel7.setPreferredSize(new java.awt.Dimension(561, 60));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Address");

        jTextFieldAddress.setEditable(false);
        jTextFieldAddress.setBackground(new java.awt.Color(204, 204, 255));
        jTextFieldAddress.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTextFieldAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldAddress, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setPreferredSize(new java.awt.Dimension(643, 74));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText(" User Profile");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel31.setBackground(new java.awt.Color(255, 255, 255));

        sellermessage.setBackground(new java.awt.Color(204, 255, 255));
        sellermessage.setColumns(20);
        sellermessage.setFont(new java.awt.Font("Monospaced", 0, 16)); // NOI18N
        sellermessage.setRows(5);
        jScrollPane6.setViewportView(sellermessage);

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6)
                .addContainerGap())
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6)
                .addContainerGap())
        );

        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel40.setText("Send Message");

        jButtonMessageSubmit.setBackground(new java.awt.Color(255, 255, 255));
        jButtonMessageSubmit.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jButtonMessageSubmit.setForeground(new java.awt.Color(0, 153, 0));
        jButtonMessageSubmit.setText("Submit");
        jButtonMessageSubmit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonMessageSubmitMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonMessageSubmitMouseExited(evt);
            }
        });
        jButtonMessageSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMessageSubmitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout UserProfilePageLayout = new javax.swing.GroupLayout(UserProfilePage);
        UserProfilePage.setLayout(UserProfilePageLayout);
        UserProfilePageLayout.setHorizontalGroup(
            UserProfilePageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 875, Short.MAX_VALUE)
            .addGroup(UserProfilePageLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(UserProfilePageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(UserProfilePageLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(UserProfilePageLayout.createSequentialGroup()
                        .addComponent(jLabel40)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, UserProfilePageLayout.createSequentialGroup()
                        .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonMessageSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        UserProfilePageLayout.setVerticalGroup(
            UserProfilePageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UserProfilePageLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(UserProfilePageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(UserProfilePageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(UserProfilePageLayout.createSequentialGroup()
                        .addComponent(jLabel40)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(UserProfilePageLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonMessageSubmit)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("tab2", UserProfilePage);

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));
        jPanel21.setPreferredSize(new java.awt.Dimension(643, 74));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(102, 102, 102));
        jLabel19.setText("Products");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(667, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel9.setBackground(new java.awt.Color(189, 195, 199));

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel41.setText("Product List");

        PTable.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        PTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "CATEGORY ID", "CATEGORY", "ID", "NAME", "PRICE", "QUANTITY"
            }
        ));
        PTable.setIntercellSpacing(new java.awt.Dimension(0, 0));
        PTable.setRowHeight(25);
        jScrollPane7.setViewportView(PTable);
        if (PTable.getColumnModel().getColumnCount() > 0) {
            PTable.getColumnModel().getColumn(1).setPreferredWidth(150);
            PTable.getColumnModel().getColumn(3).setPreferredWidth(200);
        }

        SearchProduct.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        SearchProduct.setForeground(new java.awt.Color(153, 153, 153));
        SearchProduct.setText("Search Products...");
        SearchProduct.setPreferredSize(new java.awt.Dimension(6, 28));
        SearchProduct.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                SearchProductFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                SearchProductFocusLost(evt);
            }
        });

        Pcat.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Pcat.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Name", "Category", "Price" }));

        SearchBtn.setText("Search");
        SearchBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SearchBtnMouseClicked(evt);
            }
        });

        RefreshBtn.setText("Refresh");
        RefreshBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RefreshBtnMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(364, 364, 364)
                        .addComponent(jLabel41)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(SearchProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Pcat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane7))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(RefreshBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SearchBtn)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SearchProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Pcat, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SearchBtn)
                    .addComponent(RefreshBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout ProductsPageLayout = new javax.swing.GroupLayout(ProductsPage);
        ProductsPage.setLayout(ProductsPageLayout);
        ProductsPageLayout.setHorizontalGroup(
            ProductsPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, 875, Short.MAX_VALUE)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        ProductsPageLayout.setVerticalGroup(
            ProductsPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProductsPageLayout.createSequentialGroup()
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab3", ProductsPage);

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));
        jPanel20.setPreferredSize(new java.awt.Dimension(643, 74));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(102, 102, 102));
        jLabel18.setText("Orders");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(667, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel22.setBackground(new java.awt.Color(189, 195, 199));

        OrderTable.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        OrderTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ORDER ID", "CUSTOMER ID", "SELLER ID", "ORDER DATE", "TOTAL AMOUNT"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        OrderTable.setIntercellSpacing(new java.awt.Dimension(0, 0));
        OrderTable.setRowHeight(25);
        jScrollPane2.setViewportView(OrderTable);
        if (OrderTable.getColumnModel().getColumnCount() > 0) {
            OrderTable.getColumnModel().getColumn(3).setPreferredWidth(230);
        }

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addGap(0, 29, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout OrdersPageLayout = new javax.swing.GroupLayout(OrdersPage);
        OrdersPage.setLayout(OrdersPageLayout);
        OrdersPageLayout.setHorizontalGroup(
            OrdersPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, 875, Short.MAX_VALUE)
            .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        OrdersPageLayout.setVerticalGroup(
            OrdersPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OrdersPageLayout.createSequentialGroup()
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab4", OrdersPage);

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));
        jPanel23.setPreferredSize(new java.awt.Dimension(643, 74));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(102, 102, 102));
        jLabel20.setText("Most Sells");

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel29.setBackground(new java.awt.Color(189, 195, 199));

        MSPTable.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        MSPTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NAME", "ITEM SOLD"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        MSPTable.setIntercellSpacing(new java.awt.Dimension(0, 0));
        MSPTable.setRowHeight(25);
        jScrollPane3.setViewportView(MSPTable);
        if (MSPTable.getColumnModel().getColumnCount() > 0) {
            MSPTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        }

        MSSTable.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        MSSTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NAME", "ORDER TAKEN", "ITEM SOLD"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        MSSTable.setIntercellSpacing(new java.awt.Dimension(0, 0));
        MSSTable.setRowHeight(25);
        jScrollPane4.setViewportView(MSSTable);
        if (MSSTable.getColumnModel().getColumnCount() > 0) {
            MSSTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        }

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel36.setText("Product Info");

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel37.setText("Seller Info");

        MSCTable.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        MSCTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "PHONE NO.", "POINT", "TOTAL ORDERS"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        MSCTable.setIntercellSpacing(new java.awt.Dimension(0, 0));
        MSCTable.setRowHeight(25);
        jScrollPane5.setViewportView(MSCTable);
        if (MSCTable.getColumnModel().getColumnCount() > 0) {
            MSCTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        }

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel39.setText("Customer Info");

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane5))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                        .addGap(0, 352, Short.MAX_VALUE)
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                                .addComponent(jLabel37)
                                .addGap(365, 365, 365))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                                .addComponent(jLabel36)
                                .addGap(359, 359, 359))))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel39)
                .addGap(335, 335, 335))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel39)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout MostSellsPageLayout = new javax.swing.GroupLayout(MostSellsPage);
        MostSellsPage.setLayout(MostSellsPageLayout);
        MostSellsPageLayout.setHorizontalGroup(
            MostSellsPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, 875, Short.MAX_VALUE)
            .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        MostSellsPageLayout.setVerticalGroup(
            MostSellsPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MostSellsPageLayout.createSequentialGroup()
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab5", MostSellsPage);

        jPanel3.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 60, 880, 710));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLogOutButtonPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLogOutButtonPanelMousePressed
        pane5.setBackground(paneclick);
        jLogOutButtonPanel.setBackground(paneclick1);
        pane1.setBackground(panedefault);
        pane2.setBackground(panedefault);
        pane3.setBackground(panedefault);
        pane4.setBackground(panedefault);
    }//GEN-LAST:event_jLogOutButtonPanelMousePressed

    private void jLogOutButtonPanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLogOutButtonPanelMouseExited
        jLogOutButtonPanel.setBackground(panedefault);
    }//GEN-LAST:event_jLogOutButtonPanelMouseExited

    private void jLogOutButtonPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLogOutButtonPanelMouseEntered
        jLogOutButtonPanel.setBackground(paneenter);
    }//GEN-LAST:event_jLogOutButtonPanelMouseEntered

    private void jLogOutButtonPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLogOutButtonPanelMouseClicked
        int x = JOptionPane.showConfirmDialog(this, "Are you sure to logout", "Confirm Logout", JOptionPane.YES_NO_OPTION);

        if (x == 0) {
            StaffLogin_Page stlgp = new StaffLogin_Page();
            stlgp.setVisible(true);
            stlgp.pack();
            stlgp.setLocationRelativeTo(null);
            stlgp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.dispose();
        }
    }//GEN-LAST:event_jLogOutButtonPanelMouseClicked

    private void jMostSellingButtonPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMostSellingButtonPanelMousePressed
        pane6.setBackground(paneclick);
        jMostSellingButtonPanel.setBackground(paneclick1);
        pane1.setBackground(panedefault);
        pane2.setBackground(panedefault);
        pane3.setBackground(panedefault);
        pane4.setBackground(panedefault);
        pane5.setBackground(panedefault);
    }//GEN-LAST:event_jMostSellingButtonPanelMousePressed

    private void jMostSellingButtonPanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMostSellingButtonPanelMouseExited
        jMostSellingButtonPanel.setBackground(panedefault);
    }//GEN-LAST:event_jMostSellingButtonPanelMouseExited

    private void jMostSellingButtonPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMostSellingButtonPanelMouseEntered
        jMostSellingButtonPanel.setBackground(paneenter);
    }//GEN-LAST:event_jMostSellingButtonPanelMouseEntered

    private void jMostSellingButtonPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMostSellingButtonPanelMouseClicked
        jTabbedPane1.setSelectedIndex(4);
        showMSPTable();
        showMSSTable();
        showMSCTable();
    }//GEN-LAST:event_jMostSellingButtonPanelMouseClicked

    private void jOrdersButtonPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jOrdersButtonPanelMousePressed
        pane4.setBackground(paneclick);
        jOrdersButtonPanel.setBackground(paneclick1);
        pane1.setBackground(panedefault);
        pane2.setBackground(panedefault);
        pane3.setBackground(panedefault);
        pane5.setBackground(panedefault);
        pane6.setBackground(panedefault);
    }//GEN-LAST:event_jOrdersButtonPanelMousePressed

    private void jOrdersButtonPanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jOrdersButtonPanelMouseExited
        jOrdersButtonPanel.setBackground(panedefault);
    }//GEN-LAST:event_jOrdersButtonPanelMouseExited

    private void jOrdersButtonPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jOrdersButtonPanelMouseEntered
        jOrdersButtonPanel.setBackground(paneenter);
    }//GEN-LAST:event_jOrdersButtonPanelMouseEntered

    private void jOrdersButtonPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jOrdersButtonPanelMouseClicked
        jTabbedPane1.setSelectedIndex(3);
        showOrderTable();
    }//GEN-LAST:event_jOrdersButtonPanelMouseClicked

    private void jProductsButtonPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jProductsButtonPanelMousePressed
        pane3.setBackground(paneclick);
        jProductsButtonPanel.setBackground(paneclick1);
        pane1.setBackground(panedefault);
        pane2.setBackground(panedefault);
        pane4.setBackground(panedefault);
        pane5.setBackground(panedefault);
        pane6.setBackground(panedefault);
    }//GEN-LAST:event_jProductsButtonPanelMousePressed

    private void jProductsButtonPanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jProductsButtonPanelMouseExited
        jProductsButtonPanel.setBackground(panedefault);
    }//GEN-LAST:event_jProductsButtonPanelMouseExited

    private void jProductsButtonPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jProductsButtonPanelMouseEntered
        jProductsButtonPanel.setBackground(paneenter);
    }//GEN-LAST:event_jProductsButtonPanelMouseEntered

    private void jProductsButtonPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jProductsButtonPanelMouseClicked
        jTabbedPane1.setSelectedIndex(2);
        showPTable();
    }//GEN-LAST:event_jProductsButtonPanelMouseClicked

    private void jUserProfileButtonPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jUserProfileButtonPanelMousePressed
        pane2.setBackground(paneclick);
        jUserProfileButtonPanel.setBackground(paneclick1);
        pane1.setBackground(panedefault);
        pane3.setBackground(panedefault);
        pane4.setBackground(panedefault);
        pane5.setBackground(panedefault);
        pane6.setBackground(panedefault);
    }//GEN-LAST:event_jUserProfileButtonPanelMousePressed

    private void jUserProfileButtonPanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jUserProfileButtonPanelMouseExited
        jUserProfileButtonPanel.setBackground(panedefault);
    }//GEN-LAST:event_jUserProfileButtonPanelMouseExited

    private void jUserProfileButtonPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jUserProfileButtonPanelMouseEntered
        jUserProfileButtonPanel.setBackground(paneenter);
    }//GEN-LAST:event_jUserProfileButtonPanelMouseEntered

    private void jUserProfileButtonPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jUserProfileButtonPanelMouseClicked
        jTabbedPane1.setSelectedIndex(1);
        try {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection connection = DriverManager.getConnection(
                        "jdbc:sqlserver://localhost:1433;databaseName=ShopManagementSystem;selectMethod=cursor", "sa", "123456");
                String query = "SELECT s.SellerID, s.SellerName, s.Email, s.PhoneNo, s.Gender, s.BirthDate, s.Address, s.City, s.Country, COUNT(o.OrderID) AS Total_Orders FROM Seller AS s LEFT JOIN Orders AS o ON s.SellerID = o.SellerID GROUP BY s.SellerID, s.SellerName, s.Email, s.PhoneNo, s.Gender, s.BirthDate, s.Address, s.City, s.Country HAVING SellerName = ?";
                PreparedStatement pst = connection.prepareStatement(query);
                pst.setString(1, showname.getText());
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    String sid = rs.getString("SellerID");
                    jTextFieldId.setText(sid);
                    String empname = rs.getString("SellerName");
                    jTextFieldName.setText(empname);
                    String email = rs.getString("Email");
                    jTextFieldEmail.setText(email);
                    String phnNo = rs.getString("PhoneNo");
                    jTextFieldPhone.setText(phnNo);
                    String gender = rs.getString("Gender");
                    jTextFieldGender.setText(gender);
                    String birth = rs.getString("BirthDate");
                    jTextFieldBirthdate.setText(birth);
                    String addr = rs.getString("Address");
                    jTextFieldAddress.setText(addr);
                    String city = rs.getString("City");
                    jTextFieldCity.setText(city);
                    String country = rs.getString("Country");
                    jTextFieldCountry.setText(country);
                    String orders = rs.getString("Total_Orders");
                    jTextFieldTotalOrder.setText(orders);
                    rs.close();
                    pst.close();
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SellerHome_Page.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SellerHome_Page.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jUserProfileButtonPanelMouseClicked

    private void jHomeButtonPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jHomeButtonPanelMousePressed
        pane1.setBackground(paneclick);
        jHomeButtonPanel.setBackground(paneclick1);
        pane2.setBackground(panedefault);
        pane3.setBackground(panedefault);
        pane4.setBackground(panedefault);
        pane5.setBackground(panedefault);
        pane6.setBackground(panedefault);
    }//GEN-LAST:event_jHomeButtonPanelMousePressed

    private void jHomeButtonPanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jHomeButtonPanelMouseExited
        jHomeButtonPanel.setBackground(panedefault);
    }//GEN-LAST:event_jHomeButtonPanelMouseExited

    private void jHomeButtonPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jHomeButtonPanelMouseEntered
        jHomeButtonPanel.setBackground(paneenter);
    }//GEN-LAST:event_jHomeButtonPanelMouseEntered

    private void jHomeButtonPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jHomeButtonPanelMouseClicked
        jTabbedPane1.setSelectedIndex(0);
        try {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection connection = DriverManager.getConnection(
                        "jdbc:sqlserver://localhost:1433;databaseName=ShopManagementSystem;selectMethod=cursor", "sa", "123456");
                String query = "SELECT SellerID, SellerName FROM Seller WHERE SellerName = ?";
                PreparedStatement pst = connection.prepareStatement(query);
                pst.setString(1, showname.getText());
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    String sid = rs.getString("SellerID");
                    s_id.setText(sid);
                    String empname = rs.getString("SellerName");
                    sellerName.setText(empname);

                    rs.close();
                    pst.close();
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SellerHome_Page.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SellerHome_Page.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jHomeButtonPanelMouseClicked

    private void jLabelMinMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelMinMouseExited
        jLabelMin.setForeground(Color.white);
    }//GEN-LAST:event_jLabelMinMouseExited

    private void jLabelMinMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelMinMouseEntered
        jLabelMin.setForeground(Color.gray);
    }//GEN-LAST:event_jLabelMinMouseEntered

    private void jLabelMinMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelMinMouseClicked
        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_jLabelMinMouseClicked

    private void jLabelCloseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelCloseMouseExited
        jLabelClose.setForeground(Color.white);
    }//GEN-LAST:event_jLabelCloseMouseExited

    private void jLabelCloseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelCloseMouseEntered
        jLabelClose.setForeground(Color.red);
    }//GEN-LAST:event_jLabelCloseMouseEntered

    private void jLabelCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelCloseMouseClicked
        System.exit(0);
    }//GEN-LAST:event_jLabelCloseMouseClicked

    private void SearchProductFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_SearchProductFocusGained
        // clear the textfield on focus if the text is "Search Products..."
        if (SearchProduct.getText().trim().toString().equals("Search Products...")) {
            SearchProduct.setText("");
            SearchProduct.setForeground(Color.black);
        }
    }//GEN-LAST:event_SearchProductFocusGained

    private void SearchProductFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_SearchProductFocusLost
        // if the text field is equal to username or empty
        // we will set the "XX-XX-XX-XXX" text in the field
        // on focus lost event
        if (SearchProduct.getText().trim().equals("") || SearchProduct.getText().trim().toString().equals("Search Products...")) {
            SearchProduct.setText("Search Products...");
            SearchProduct.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_SearchProductFocusLost

    private void SearchBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SearchBtnMouseClicked
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection;
            try {
                connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ShopManagementSystem;selectMethod=cursor", "sa", "123456");
                Statement st = connection.createStatement();
                ResultSet rs = null;
                if (Pcat.getSelectedItem().toString().equals("Name")) {
                    if (SearchProduct.getText().isEmpty() || SearchProduct.getText().equals("Search Products...")) {
                        rs = st.executeQuery("SELECT cat.C_ID, cat.C_Name, pro.P_ID, pro.P_Name, ROUND(pro.P_Price, 2), pro.P_Quantity FROM Categories AS cat INNER JOIN Products AS pro ON cat.C_ID = pro.P_C_ID ORDER BY pro.P_Name, cat.C_Name");
                    } else {
                        rs = st.executeQuery("SELECT cat.C_ID, cat.C_Name, pro.P_ID, pro.P_Name, ROUND(pro.P_Price, 2), pro.P_Quantity FROM Categories AS cat INNER JOIN Products AS pro ON cat.C_ID = pro.P_C_ID WHERE pro.P_Name IN (SELECT P_Name FROM Products WHERE P_Name LIKE '%" + SearchProduct.getText() + "%') ORDER BY pro.P_Name, cat.C_Name");
                    }
                } else if (Pcat.getSelectedItem().toString().equals("Category")) {
                    if (SearchProduct.getText().isEmpty() || SearchProduct.getText().equals("Search Products...")) {
                        rs = st.executeQuery("SELECT cat.C_ID, cat.C_Name, pro.P_ID, pro.P_Name, ROUND(pro.P_Price, 2), pro.P_Quantity FROM Categories AS cat INNER JOIN Products AS pro ON cat.C_ID = pro.P_C_ID ORDER BY cat.C_Name, pro.P_Name");
                    } else {
                        rs = st.executeQuery("SELECT cat.C_ID, cat.C_Name, pro.P_ID, pro.P_Name, ROUND(pro.P_Price, 2), pro.P_Quantity FROM Categories AS cat INNER JOIN Products AS pro ON cat.C_ID = pro.P_C_ID WHERE cat.C_Name IN (SELECT C_Name FROM Categories WHERE C_Name LIKE '%" + SearchProduct.getText() + "%') ORDER BY cat.C_Name, pro.P_Name");
                    }
                } else if (Pcat.getSelectedItem().toString() == "Price") {
                    if (SearchProduct.getText().isEmpty() || SearchProduct.getText().equals("Search Products...")) {
                        rs = st.executeQuery("SELECT cat.C_ID, cat.C_Name, pro.P_ID, pro.P_Name, ROUND(pro.P_Price, 2), pro.P_Quantity FROM Categories AS cat INNER JOIN Products AS pro ON cat.C_ID = pro.P_C_ID ORDER BY pro.P_Price");
                    } else {
                        rs = st.executeQuery("SELECT cat.C_ID, cat.C_Name, pro.P_ID, pro.P_Name, ROUND(pro.P_Price, 2), pro.P_Quantity FROM Categories AS cat INNER JOIN Products AS pro ON cat.C_ID = pro.P_C_ID WHERE pro.P_Price IN (SELECT P_Price FROM Products WHERE P_Price " + SearchProduct.getText() + ") ORDER BY pro.P_Price");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Missing Information");
                }

                while (PTable.getRowCount() > 0) {
                    ((DefaultTableModel) PTable.getModel()).removeRow(0);
                }
                int col = rs.getMetaData().getColumnCount();
                while (rs.next()) {
                    Object[] rows = new Object[col];
                    for (int i = 1; i <= col; i++) {
                        rows[i - 1] = rs.getObject(i);
                    }
                    ((DefaultTableModel) PTable.getModel()).insertRow(rs.getRow() - 1, rows);
                }
                rs.close();
                st.close();

            } catch (SQLException ex) {
                Logger.getLogger(SellerHome_Page.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SellerHome_Page.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_SearchBtnMouseClicked

    private void RefreshBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RefreshBtnMouseClicked
        showPTable();
    }//GEN-LAST:event_RefreshBtnMouseClicked

    private void cancelBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtn1ActionPerformed
        productID.setText("");
        productName.setText("");
        price.setText("");
        stock.setText("");
        total.setText("");
        setscrollpane();
    }//GEN-LAST:event_cancelBtn1ActionPerformed

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
        String mob = c_mobile.getText();
        if (total.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please add product");
        } else {
            float myint = Float.parseFloat(total.getText());
            if (myint > 0 && !mob.equals("")) {
                DefaultTableModel model = new DefaultTableModel();
                model = (DefaultTableModel) jTable1.getModel();

                model.addRow(new Object[]{
                    productID.getText(),
                    productName.getText(),
                    qty.getValue().toString(),
                    price.getText(),
                    total.getText(),});

                double sum = 0.0;
                for (int i = 0; i < jTable1.getRowCount(); i++) {
                    sum = sum + Double.parseDouble(jTable1.getValueAt(i, 4).toString());
                }

                toTal.setText(Double.toString(sum));
                subTotal.setText(Double.toString(sum));

                productID.setText("");
                productName.setText("");
                price.setText("");
                stock.setText("");
                total.setText("");
                setscrollpane();
            }
        }
        if (mob.equals("")) {
            JOptionPane.showMessageDialog(null, "Please enter Customer Information");
        }
    }//GEN-LAST:event_addBtnActionPerformed

    private void searchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBtnActionPerformed
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection;
            try {
                connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ShopManagementSystem;selectMethod=cursor", "sa", "123456");
                String query = "SELECT P_Name, P_Price, P_Quantity FROM Products WHERE P_ID = ?";
                PreparedStatement pst = connection.prepareStatement(query);
                pst.setString(1, productID.getText());
                ResultSet rs = pst.executeQuery();

                while (rs.next()) {
                    productName.setText(rs.getString("P_Name"));
                    price.setText(rs.getString("P_Price"));
                    stock.setText(rs.getString("P_Quantity"));
                }

                int quantity = Integer.parseInt(qty.getValue().toString());
                float Price = Float.parseFloat(price.getText());
                float Total = quantity * Price;
                total.setText(String.valueOf(Total));
            } catch (SQLException ex) {
                Logger.getLogger(AdminHome_Page.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AdminHome_Page.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_searchBtnActionPerformed

    private void qtyStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_qtyStateChanged
        int quantity = Integer.parseInt(qty.getValue().toString());
        float Price = Float.parseFloat(price.getText());

        float Total = quantity * Price;
        total.setText(String.valueOf(Total));

        int inStock = Integer.parseInt(stock.getText());
        if (quantity <= 0) {
            addBtn.setVisible(false);
            JOptionPane.showMessageDialog(null, "Select at least one product");
        } else {
            addBtn.setVisible(true);
            if (inStock < quantity) {
                addBtn.setVisible(false);
                JOptionPane.showMessageDialog(null, "Not Enough Product in Stock");
            } else {
                addBtn.setVisible(true);
            }
        }
    }//GEN-LAST:event_qtyStateChanged

    private void confirmBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmBtnActionPerformed
        if (!(cashRtd.getText().equals("")) && !(c_mobile.getText().equals(""))) {
            //insert values in the Orders Table
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection connection = DriverManager.getConnection(
                        "jdbc:sqlserver://localhost:1433;databaseName=ShopManagementSystem;selectMethod=cursor", "sa", "123456");
                
                java.util.Date date = new java.util.Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                
                String query = "INSERT INTO Orders(CustomerID, SellerID, OrderDate, TotalAmount, AmountPaid, AmountRtd, oDate) VALUES (?,?,?,?,?,?,?)";
                PreparedStatement pst = connection.prepareStatement(query);
                pst.setString(1, c_id.getText());
                pst.setString(2, s_id.getText());
                pst.setString(3, dateField.getText());
                pst.setString(4, subTotal.getText());
                pst.setString(5, cashRcd.getText());
                pst.setString(6, cashRtd.getText());
                pst.setDate(7, sqlDate);
                pst.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //get the lastID from Orders Table
            String getOrderID = null;
            String getQuantity = null;
            String setQuantity = null;
            String pushQuantity = null;
            String pushSoldQuantity = null;
            
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection connection = DriverManager.getConnection(
                        "jdbc:sqlserver://localhost:1433;databaseName=ShopManagementSystem;selectMethod=cursor", "sa", "123456");
                String query = "SELECT OrderID AS lastID FROM Orders WHERE OrderID = IDENT_CURRENT('Orders')";
                PreparedStatement pst = connection.prepareStatement(query);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    getOrderID = rs.getString("lastID");
                }
            } catch (ClassNotFoundException | SQLException e) {

            }
            //insert values in the OrderDetail Table
            DefaultTableModel model = new DefaultTableModel();
            model = (DefaultTableModel) jTable1.getModel();

            for (int i = 0; i < jTable1.getRowCount(); i++) {
                String productID = jTable1.getValueAt(i, 0).toString();
                String quantity = jTable1.getValueAt(i, 2).toString();
                String price = jTable1.getValueAt(i, 4).toString();
                //insert into OrderDetail Table according to cart value
                try {
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    Connection connection = DriverManager.getConnection(
                            "jdbc:sqlserver://localhost:1433;databaseName=ShopManagementSystem;selectMethod=cursor", "sa", "123456");
                    String query = "INSERT INTO OrderDetail(OrderID, ProductID, Quantity, Price, SellerID) VALUES (?,?,?,?,?)";
                    PreparedStatement pst = connection.prepareStatement(query);
                    pst.setString(1, getOrderID);
                    pst.setString(2, productID);
                    pst.setString(3, quantity);
                    pst.setString(4, price);
                    pst.setString(5, s_id.getText());
                    pst.execute();
                } catch (Exception e) {

                }
            }
            
            //Update values in the Stock of Product Table
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                String productID = jTable1.getValueAt(i, 0).toString();
                String quantity = jTable1.getValueAt(i, 2).toString();
                try {
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    Connection connection = DriverManager.getConnection(
                            "jdbc:sqlserver://localhost:1433;databaseName=ShopManagementSystem;selectMethod=cursor", "sa", "123456");
                    String query = "SELECT P_Quantity, Items_Sold FROM Products WHERE P_ID = " + productID;
                    PreparedStatement pst = connection.prepareStatement(query);
                    ResultSet rs = pst.executeQuery();
                    while (rs.next()) {
                        getQuantity = rs.getString("P_Quantity");
                        setQuantity = rs.getString("Items_Sold");
                    }
                } catch (ClassNotFoundException | SQLException e) {

                }
                
                int newQuantity = Integer.parseInt(getQuantity.trim()) - Integer.valueOf(quantity);
                pushQuantity = Integer.toString(newQuantity);
                try {
                    int squantity = Integer.parseInt(setQuantity.trim()) + Integer.valueOf(quantity);
                    pushSoldQuantity = Integer.toString(squantity);
                } catch (NullPointerException e) {
                    System.out.print("Caught NullPointerException");
                }
                
                //update stock in the Product Table
                try {
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    Connection connection = DriverManager.getConnection(
                            "jdbc:sqlserver://localhost:1433;databaseName=ShopManagementSystem;selectMethod=cursor", "sa", "123456");
                    String Query = "UPDATE Products SET P_Quantity = " + pushQuantity + ", Items_Sold = " + pushSoldQuantity + " WHERE P_ID = " + productID;
                    PreparedStatement pst = connection.prepareStatement(Query);
                    pst.executeUpdate();
                } catch (Exception e) {

                }
            }
            //for updating point    
            String CustomerID = c_id.getText();
            Double dPoint = Double.parseDouble(c_point.getText());
            Double value = Double.parseDouble(toTal.getText());
            while (value >= 100) {
                value = value / 100;
                value = dPoint + value;
                Double push = (Double) value;
                String s = String.valueOf(push);
                try {
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    Connection connection = DriverManager.getConnection(
                            "jdbc:sqlserver://localhost:1433;databaseName=ShopManagementSystem;selectMethod=cursor", "sa", "123456");
                    String Query = "UPDATE Customer SET Cus_Point = " + push + " WHERE Cus_ID = " + CustomerID;
                    PreparedStatement pst = connection.prepareStatement(Query);
                    pst.executeUpdate();
                } catch (Exception e) {

                }
            }

            if (!discount.getText().equals("")) {
                float dp = Float.parseFloat(c_point.getText());
                float dis = Integer.parseInt(discount.getText());
                dis = dp - dis;
                try {
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    Connection connection = DriverManager.getConnection(
                            "jdbc:sqlserver://localhost:1433;databaseName=ShopManagementSystem;selectMethod=cursor", "sa", "123456");
                    String Query = "UPDATE Customer SET Cus_Point = " + dis + "WHERE Cus_ID = " + CustomerID;
                    PreparedStatement pst = connection.prepareStatement(Query);
                    pst.executeUpdate();
                } catch (Exception e) {

                }
            }
            //Update customer last purchase
            String lastTrans = dateField.getText();
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection connection = DriverManager.getConnection(
                        "jdbc:sqlserver://localhost:1433;databaseName=ShopManagementSystem;selectMethod=cursor", "sa", "123456");
                String Query2 = "UPDATE Customer SET Cus_LastPurchaseDate = ? WHERE Cus_ID =" + CustomerID;
                //System.out.println(lastTrans);
                PreparedStatement pst = connection.prepareStatement(Query2);
                pst.setString(1, dateField.getText());
                pst.executeUpdate();
            } catch (Exception e) {

            }
            
            JOptionPane.showMessageDialog(null, "Successful");
        } else {
            JOptionPane.showMessageDialog(null, "Add Product and Customer Information");
        }
        printReceipt();
        //////table side clearing
        productID.setText("");
        productName.setText("");
        price.setText("");
        stock.setText("");
        total.setText("");

        toTal.setText("");
        subTotal.setText("");
        cashRcd.setText("");
        cashRtd.setText("");
        discount.setText("");

        DefaultTableModel model = new DefaultTableModel();
        model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        ///// customer side clearing
        c_mob_input.setText("");
        c_id.setText("");
        c_mobile.setText("");
        c_point.setText("");

        setscrollpane();
    }//GEN-LAST:event_confirmBtnActionPerformed

    private void cancelBtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtn2ActionPerformed
        toTal.setText("");
        productID.setText("");
        productName.setText("");
        price.setText("");
        stock.setText("");
        total.setText("");

        subTotal.setText("");
        cashRcd.setText("");
        cashRtd.setText("");
        toTal.setText("");
        discount.setText("");
        setscrollpane();

        DefaultTableModel model = new DefaultTableModel();
        model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
    }//GEN-LAST:event_cancelBtn2ActionPerformed

    private void okBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okBtnActionPerformed
        try {
            float subtotal = Float.parseFloat(subTotal.getText());
            float cashReceived = Float.parseFloat(cashRcd.getText());

            if (subtotal <= cashReceived) {
                float cashReturned = cashReceived - subtotal;
                cashRtd.setText(String.valueOf(cashReturned));
            } else {
                JOptionPane.showMessageDialog(null, "Not Enough Money");
            }
        } catch (Exception e) {

        }
    }//GEN-LAST:event_okBtnActionPerformed

    private void checkBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBtnActionPerformed
        if (c_mob_input.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter Customer Information");
        } else {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection connection = DriverManager.getConnection(
                        "jdbc:sqlserver://localhost:1433;databaseName=ShopManagementSystem;selectMethod=cursor", "sa", "123456");
                String sql = "SELECT Cus_ID, Cus_PhoneNo, ROUND(Cus_Point, 2) AS Point FROM Customer Where Cus_PhoneNo = ?";
                PreparedStatement pst = connection.prepareStatement(sql);
                pst.setString(1, c_mob_input.getText());
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    c_id.setText(rs.getString("Cus_ID"));
                    c_mobile.setText(rs.getString("Cus_PhoneNo"));
                    c_point.setText(rs.getString("Point"));
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Mobile Number", "Checking Error", 2);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_checkBtnActionPerformed

    private void addNewBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNewBtnActionPerformed
        discount.setText("");
        c_mob_input.setText("");
        c_id.setText("");
        c_mobile.setText("");
        c_point.setText("");

        c_mob_input.setEditable(false);
        c_mobile.setEditable(true);
        // c_point.setEditable(false);

        addNewBtn.setVisible(false);
        backBtn.setVisible(true);
        saveBtn.setVisible(true);
        clearBtn.setVisible(false);
    }//GEN-LAST:event_addNewBtnActionPerformed

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
        if (c_mobile.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "At least Provide a MOBILE NUMBER");
        } else {
            // saves in database
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection connection = DriverManager.getConnection(
                        "jdbc:sqlserver://localhost:1433;databaseName=ShopManagementSystem;selectMethod=cursor", "sa", "123456");
                String query = "INSERT INTO Customer(Cus_PhoneNo, Cus_Point, Cus_LastPurchaseDate) VALUES (?,?,?)";
                PreparedStatement pst = connection.prepareStatement(query);
                pst.setString(1, c_mobile.getText());
                pst.setString(2, c_point.getText());
                pst.setString(3, dateField.getText());
                pst.execute();

                c_mob_input.setEditable(true);
                c_mobile.setEditable(false);
                c_point.setEditable(false);

                addNewBtn.setVisible(true);
                backBtn.setVisible(false);
                saveBtn.setVisible(false);
                clearBtn.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }

            JOptionPane.showMessageDialog(null, "Successfully added Customer Information");
            // shows the recently saved id
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection connection = DriverManager.getConnection(
                        "jdbc:sqlserver://localhost:1433;databaseName=ShopManagementSystem;selectMethod=cursor", "sa", "123456");
                String sql = "SELECT Cus_ID, Cus_PhoneNo, Cus_Point FROM Customer WHERE Cus_PhoneNo =?";
                PreparedStatement pst = connection.prepareStatement(sql);
                pst.setString(1, c_mobile.getText());
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    c_id.setText(rs.getString("Cus_ID"));
                    c_mobile.setText(rs.getString("Cus_PhoneNo"));
                    c_point.setText(rs.getString("Cus_Point"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_saveBtnActionPerformed

    private void clearBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearBtnActionPerformed
        discount.setText("");
        c_mob_input.setText("");
        c_id.setText("");
        c_mobile.setText("");
        c_point.setText("");
    }//GEN-LAST:event_clearBtnActionPerformed

    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBtnActionPerformed
        c_mob_input.setEditable(true);
        c_mobile.setEditable(false);
        c_point.setEditable(false);

        addNewBtn.setVisible(true);
        backBtn.setVisible(false);
        saveBtn.setVisible(false);
        clearBtn.setVisible(true);
    }//GEN-LAST:event_backBtnActionPerformed

    private void useBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_useBtnActionPerformed
        if (discount.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Discount Amount Not Inserted");
        } else {
            double mydis = Double.parseDouble(discount.getText());
            double mypoint = Double.parseDouble(c_point.getText());

            if (mydis <= mypoint) {
                double total = Double.parseDouble(toTal.getText());
                total = total - mydis;
                String subtotal = String.valueOf(total);
                subTotal.setText(subtotal);
            } else {
                JOptionPane.showMessageDialog(null, "Discount Amount Exceeded\n Maximum Authorised Discount Given");
                String s = String.valueOf(mypoint);
                discount.setText(s);
                mydis = mypoint;
                double total = Double.parseDouble(toTal.getText());
                total = total - mydis;
                String subtotal = String.valueOf(total);
                subTotal.setText(subtotal);
            }
        }
    }//GEN-LAST:event_useBtnActionPerformed

    private void exitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_exitBtnActionPerformed

    private void jButtonMessageSubmitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonMessageSubmitMouseEntered
        // set jbutton background
        jButtonMessageSubmit.setBackground(new Color(149,165,166));
    }//GEN-LAST:event_jButtonMessageSubmitMouseEntered

    private void jButtonMessageSubmitMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonMessageSubmitMouseExited
        // set jbutton background
        jButtonMessageSubmit.setBackground(new Color(255,255,255));
    }//GEN-LAST:event_jButtonMessageSubmitMouseExited

    private void jButtonMessageSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMessageSubmitActionPerformed
        try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection connection = DriverManager.getConnection(
                        "jdbc:sqlserver://localhost:1433;databaseName=ShopManagementSystem;selectMethod=cursor", "sa", "123456");                             
                
                String query = "INSERT INTO Message(SellerID, Topic) VALUES (?,?)";
                PreparedStatement pst = connection.prepareStatement(query);
                pst.setString(1, jTextFieldId.getText());
                pst.setString(2, sellermessage.getText());
                pst.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        try {
            String str = sellermessage.getText();
            File mFile = new File("message.txt");
            FileInputStream fis = new FileInputStream(mFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String result = "";
            String line = "";
            while( (line = br.readLine()) != null){
                result = result + line; 
            }

            result = str + result;

            mFile.delete();
            FileOutputStream fos = new FileOutputStream(mFile);
            fos.write(result.getBytes());
            fos.flush();
            /*FileWriter file = new FileWriter("message.txt", true);
            BufferedWriter b = new BufferedWriter(file);
            b.write(str);
            b.newLine();
            b.close();
            file.close();*/
            /*File file = new File("message.txt");
            if(!file.exists()) {
                file.createNewFile();
            }
            PrintWriter pw = new PrintWriter(file);
            pw.println(str);
            pw.close();*/
            System.out.println("Done");
            JOptionPane.showMessageDialog(null, "Message successfully sent");
            sellermessage.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButtonMessageSubmitActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SellerHome_Page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SellerHome_Page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SellerHome_Page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SellerHome_Page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SellerHome_Page().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel HomePage;
    private javax.swing.JTable MSCTable;
    private javax.swing.JTable MSPTable;
    private javax.swing.JTable MSSTable;
    private javax.swing.JPanel MostSellsPage;
    private javax.swing.JTable OrderTable;
    private javax.swing.JPanel OrdersPage;
    private javax.swing.JTable PTable;
    private javax.swing.JComboBox<String> Pcat;
    private javax.swing.JPanel ProductsPage;
    private javax.swing.JButton RefreshBtn;
    private javax.swing.JButton SearchBtn;
    private javax.swing.JTextField SearchProduct;
    private javax.swing.JPanel UserProfilePage;
    private javax.swing.JButton addBtn;
    private javax.swing.JButton addNewBtn;
    private javax.swing.JButton backBtn;
    private javax.swing.JTextField c_id;
    private javax.swing.JTextField c_mob_input;
    private javax.swing.JTextField c_mobile;
    private javax.swing.JTextField c_point;
    private javax.swing.JButton cancelBtn1;
    private javax.swing.JButton cancelBtn2;
    private javax.swing.JTextField cashRcd;
    private javax.swing.JTextField cashRtd;
    private javax.swing.JButton checkBtn;
    private javax.swing.JButton clearBtn;
    private javax.swing.JButton confirmBtn;
    private javax.swing.JTextField dateField;
    private javax.swing.JTextField discount;
    private javax.swing.JButton exitBtn;
    private javax.swing.JButton jButtonMessageSubmit;
    private javax.swing.JLabel jEventsButtonLabel;
    private javax.swing.JLabel jEventsButtonLabel1;
    private javax.swing.JLabel jHomeButtonLabel;
    private javax.swing.JPanel jHomeButtonPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelClose;
    private javax.swing.JLabel jLabelMin;
    private javax.swing.JPanel jLogOutButtonPanel;
    private javax.swing.JPanel jMostSellingButtonPanel;
    private javax.swing.JPanel jOrdersButtonPanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jProductsButtonPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextFieldAddress;
    private javax.swing.JTextField jTextFieldBirthdate;
    private javax.swing.JTextField jTextFieldCity;
    private javax.swing.JTextField jTextFieldCountry;
    private javax.swing.JTextField jTextFieldEmail;
    private javax.swing.JTextField jTextFieldGender;
    private javax.swing.JTextField jTextFieldId;
    private javax.swing.JTextField jTextFieldName;
    private javax.swing.JTextField jTextFieldPhone;
    private javax.swing.JTextField jTextFieldTotalOrder;
    private javax.swing.JLabel jUserProfileButtonLabel;
    private javax.swing.JPanel jUserProfileButtonPanel;
    public static final javax.swing.JLabel name = new javax.swing.JLabel();
    private javax.swing.JButton okBtn;
    private javax.swing.JPanel pane1;
    private javax.swing.JPanel pane2;
    private javax.swing.JPanel pane3;
    private javax.swing.JPanel pane4;
    private javax.swing.JPanel pane5;
    private javax.swing.JPanel pane6;
    private javax.swing.JTextField price;
    private javax.swing.JTextField productID;
    private javax.swing.JTextField productName;
    private javax.swing.JSpinner qty;
    private javax.swing.JTextField s_id;
    private javax.swing.JButton saveBtn;
    private javax.swing.JButton searchBtn;
    private javax.swing.JTextField sellerName;
    private javax.swing.JTextArea sellermessage;
    private javax.swing.JLabel showname;
    private javax.swing.JTextField stock;
    private javax.swing.JTextField subTotal;
    private javax.swing.JTextField toTal;
    private javax.swing.JTextField total;
    private javax.swing.JLabel totalT;
    private javax.swing.JButton useBtn;
    // End of variables declaration//GEN-END:variables
}
