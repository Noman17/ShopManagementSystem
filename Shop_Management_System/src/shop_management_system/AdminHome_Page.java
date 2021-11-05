/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop_management_system;

import AppPackage.AnimationClass;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.RowFilter;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author User
 */
public class AdminHome_Page extends javax.swing.JFrame {

    private CardLayout cardLayout;
    Color panedefault;
    Color paneclick, paneclick1;
    Color paneenter;
    AnimationClass AC = new AnimationClass();

    public AdminHome_Page() {
        initComponents();
    }

    public AdminHome_Page(String uname) {
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

        showname.setText(uname);
        showCTable();
    }

    // SHOW CATEGORY TABLE IN ADMIN HOME PAGE 
    void showCatTable() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection;
            try {
                connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ShopManagementSystem;selectMethod=cursor", "sa", "123456");
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("select * from Categories");

                while (CategoryTable.getRowCount() > 0) {
                    ((DefaultTableModel) CategoryTable.getModel()).removeRow(0);
                }
                int col = rs.getMetaData().getColumnCount();
                while (rs.next()) {
                    Object[] rows = new Object[col];
                    for (int i = 1; i <= col; i++) {
                        rows[i - 1] = rs.getObject(i);
                    }
                    ((DefaultTableModel) CategoryTable.getModel()).insertRow(rs.getRow() - 1, rows);

                    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
                    CategoryTable.setDefaultRenderer(String.class, centerRenderer);
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

    // SHOW PRODUCT TABLE IN ADMIN HOME PAGE 
    void showProTable() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection;
            try {
                connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ShopManagementSystem;selectMethod=cursor", "sa", "123456");
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("SELECT pro.P_ID, pro.P_Name, pro.P_Quantity, pro.P_Price, cat.C_ID, cat.C_Name FROM Products AS pro INNER JOIN Categories AS cat ON pro.P_C_ID = cat.C_ID");

                while (ProTable.getRowCount() > 0) {
                    ((DefaultTableModel) ProTable.getModel()).removeRow(0);
                }
                int col = rs.getMetaData().getColumnCount();
                while (rs.next()) {
                    Object[] rows = new Object[col];
                    for (int i = 1; i <= col; i++) {
                        rows[i - 1] = rs.getObject(i);
                    }
                    ((DefaultTableModel) ProTable.getModel()).insertRow(rs.getRow() - 1, rows);
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

    // SHOW CUSTOMER TABLE IN ADMIN HOME PAGE 
    void showCTable() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection;
            try {
                connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ShopManagementSystem;selectMethod=cursor", "sa", "123456");
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("SELECT cus.Cus_ID, cus.Cus_PhoneNo, cus.Cus_LastPurchaseDate, ROUND(cus.Cus_Point, 2), COUNT(o.OrderID) AS Total_Orders FROM Customer AS cus LEFT JOIN Orders AS o ON cus.Cus_ID = o.CustomerID GROUP BY cus.Cus_ID, cus.Cus_PhoneNo, cus.Cus_LastPurchaseDate, cus.Cus_Point ORDER BY cus.Cus_ID");

                while (CustomerTable.getRowCount() > 0) {
                    ((DefaultTableModel) CustomerTable.getModel()).removeRow(0);
                }
                int col = rs.getMetaData().getColumnCount();
                while (rs.next()) {
                    Object[] rows = new Object[col];
                    for (int i = 1; i <= col; i++) {
                        rows[i - 1] = rs.getObject(i);
                    }
                    ((DefaultTableModel) CustomerTable.getModel()).insertRow(rs.getRow() - 1, rows);

                    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
                    CustomerTable.setDefaultRenderer(String.class, centerRenderer);
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

    // SHOW SELLER TABLE IN ADMIN HOME PAGE 
    void showSTable() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection;
            try {
                connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ShopManagementSystem;selectMethod=cursor", "sa", "123456");
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("SELECT s.SellerID, s.SellerName, s.Email, s.PhoneNo, s.Address, s.Password, COUNT(o.OrderID) AS Total_Orders FROM Seller AS s LEFT JOIN Orders AS o ON s.SellerID = o.SellerID GROUP BY s.SellerID, s.SellerName, s.Email, s.PhoneNo, s.Address, s.Password ORDER BY s.SellerID");

                while (STable.getRowCount() > 0) {
                    ((DefaultTableModel) STable.getModel()).removeRow(0);
                }
                int col = rs.getMetaData().getColumnCount();
                while (rs.next()) {
                    Object[] rows = new Object[col];
                    for (int i = 1; i <= col; i++) {
                        rows[i - 1] = rs.getObject(i);
                    }
                    ((DefaultTableModel) STable.getModel()).insertRow(rs.getRow() - 1, rows);
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

    class ShowIt implements Runnable {

        @Override
        public void run() {
            //JTable table = new JTable();
            MessagesTable.getColumnModel().addColumnModelListener(new WrapColListener(MessagesTable));
            MessagesTable.setDefaultRenderer(Object.class, new JTPRenderer());

            // examples:
            //    table.setIntercellSpacing( new Dimension( 40, 20 ));
            //    table.setIntercellSpacing( new Dimension( 4, 2 ));
            Vector<Vector<String>> dataVector = new Vector<Vector<String>>();
            String lorem1 = "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore";
            String lorem2 = "et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum";

            for (int i = 0; i < 12; i++) {
                Vector<String> row = null;
                if (i % 4 == 0) {
                    row = new Vector<String>(Arrays.asList(new String[]{"iggle", lorem1, "poggle", "poke"}));
                } else if (i % 4 == 1) {
                    row = new Vector<String>(Arrays.asList(new String[]{lorem2, "piggle", "poggle", lorem1}));
                } else if (i % 4 == 2) {
                    row = new Vector<String>(Arrays.asList(new String[]{lorem1, "piggle", lorem2, "poke"}));
                } else {
                    row = new Vector<String>(Arrays.asList(new String[]{"iggle", lorem2, "poggle", lorem2}));
                }
                dataVector.add(row);
            }
            Vector<String> columnIdentifiers = new Vector<String>(Arrays.asList(new String[]{"iggle", "piggle", "poggle"}));
            MessagesTable.getTableHeader().setFont(MessagesTable.getTableHeader().getFont().deriveFont(20f).deriveFont(Font.BOLD));
            ((DefaultTableModel) MessagesTable.getModel()).setDataVector(dataVector, columnIdentifiers);
            //MessagesTable.setBorder(new EtchedBorder(EtchedBorder.RAISED));
            //MessagesTable.setGridColor(Color.BLUE);
            MessagesTable.setShowHorizontalLines(true);
            MessagesTable.setShowVerticalLines(true);
            //JFrame frame = new JFrame("MultiWrapColTable");
            //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //JScrollPane jsp = new JScrollPane(MessagesTable);
            //getContentPane().add(jsp);
            //frame.pack();
            //frame.setBounds(50, 50, 800, 500);
            //frame.setVisible(true);
        }
    }

    class JTPRenderer extends JTextPane implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            setText(value.toString());
            return this;
        }
    }

    class WrapColListener implements TableColumnModelListener {

        JTable m_table;

        WrapColListener(JTable table) {
            m_table = table;
        }

        void refresh_row_heights() {
            int n_rows = m_table.getRowCount();
            int n_cols = m_table.getColumnCount();
            int intercell_width = m_table.getIntercellSpacing().width;
            int intercell_height = m_table.getIntercellSpacing().height;
            TableColumnModel col_model = m_table.getColumnModel();
            // these null checks are due to concurrency considerations... much can change between the col margin change
            // event and the call to refresh_row_heights (although not in this SSCCE...)
            if (col_model == null) {
                return;
            }
            // go through ALL rows, calculating row heights
            for (int row = 0; row < n_rows; row++) {
                int pref_row_height = 1;
                // calculate row heights from cell, setting width constraint by means of setBounds...
                for (int col = 0; col < n_cols; col++) {
                    Object value = m_table.getValueAt(row, col);
                    TableCellRenderer renderer = m_table.getCellRenderer(row, col);
                    if (renderer == null) {
                        return;
                    }
                    Component comp = renderer.getTableCellRendererComponent(m_table, value, false, false,
                            row, col);
                    if (comp == null) {
                        return;
                    }
                    int col_width = col_model.getColumn(col).getWidth();
                    // constrain width of component
                    comp.setBounds(new Rectangle(0, 0, col_width - intercell_width, Integer.MAX_VALUE));
                    // getPreferredSize then returns "true" height as a function of attributes (e.g. font) and word-wrapping
                    int pref_cell_height = comp.getPreferredSize().height + intercell_height;
                    if (pref_cell_height > pref_row_height) {
                        pref_row_height = pref_cell_height;
                    }
                }
                if (pref_row_height != m_table.getRowHeight(row)) {
                    m_table.setRowHeight(row, pref_row_height);
                }
            }
        }

        @Override
        public void columnAdded(TableColumnModelEvent e) {
            refresh_row_heights();

        }

        @Override
        public void columnRemoved(TableColumnModelEvent e) {
            // probably no need to call refresh_row_heights

        }

        @Override
        public void columnMoved(TableColumnModelEvent e) {
            // probably no need to call refresh_row_heights

        }

        @Override
        public void columnMarginChanged(ChangeEvent e) {
            refresh_row_heights();
        }

        @Override
        public void columnSelectionChanged(ListSelectionEvent e) {
            // probably no need to call refresh_row_heights

        }
    }

    /*static class WordWrapCellRenderer extends JTextArea implements TableCellRenderer {

        WordWrapCellRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value.toString());
            setSize(table.getColumnModel().getColumn(column).getWidth(), getPreferredSize().height);
            if (table.getRowHeight(row) != getPreferredSize().height) {
                table.setRowHeight(row, getPreferredSize().height);
            }
            return this;
        }
    }*/
    // SHOW SELLER MESSAGE TABLE IN MESSAGES PAGE
    private void showMessageTable() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection;
            try {
                connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ShopManagementSystem;selectMethod=cursor", "sa", "123456");
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("SELECT MessageID, SellerID, Topic FROM Message ORDER BY MessageID DESC");

                while (MessagesTable.getRowCount() > 0) {
                    ((DefaultTableModel) MessagesTable.getModel()).removeRow(0);
                }
                int col = rs.getMetaData().getColumnCount();
                while (rs.next()) {
                    Object[] rows = new Object[col];
                    for (int i = 1; i <= col; i++) {
                        rows[i - 1] = rs.getObject(i);
                    }
                    ((DefaultTableModel) MessagesTable.getModel()).insertRow(rs.getRow() - 1, rows);

                    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
                    MessagesTable.setDefaultRenderer(String.class, centerRenderer);
                    //MessagesTable.getColumnModel().getColumn(2).setCellRenderer(new WordWrapCellRenderer());
                    TableColumnModel cmodel = MessagesTable.getColumnModel();
                    TextAreaRenderer textAreaRenderer = new TextAreaRenderer();
                    TextAreaEditor textEditor = new TextAreaEditor();
                    //JScrollPane sp = new JScrollPane();
                    //JPanel p = new JPanel(new GridLayout());
                    //sp.add(MessagesTable);
                    //getContentPane().add(sp);
                    //sp.add(New GridLayout());
                    

                    cmodel.getColumn(0).setCellRenderer(new DefaultTableCellRenderer());
                    cmodel.getColumn(1).setCellRenderer(new DefaultTableCellRenderer());
                    cmodel.getColumn(2).setCellRenderer(textAreaRenderer);
                    
                    cmodel.getColumn(0).setCellEditor(textEditor);
                    cmodel.getColumn(1).setCellEditor(textEditor);
                    cmodel.getColumn(2).setCellEditor(textEditor);
                    
                    //MessagesTable.setBorder(new EtchedBorder(EtchedBorder.RAISED));
                    //MessagesTable.setGridColor(Color.BLUE);
                    //MessagesTable.setShowHorizontalLines(true);
                    //MessagesTable.setShowVerticalLines(true);
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

    // SHOW PRODUCT REVENUE TABLE IN ADMIN HOME PAGE 
    public void showPITable() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection;
            try {
                connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ShopManagementSystem;selectMethod=cursor", "sa", "123456");
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("SELECT P_ID, P_Name, ROUND(P_Price, 2), ISNULL(Items_Sold, 0), ROUND(P_Price * ISNULL(Items_Sold, 0), 2) AS Revenue, RANK() OVER (ORDER BY P_Price * Items_Sold DESC) AS Revenue_Rank FROM Products");

                while (PITable.getRowCount() > 0) {
                    ((DefaultTableModel) PITable.getModel()).removeRow(0);
                }
                int col = rs.getMetaData().getColumnCount();
                while (rs.next()) {
                    Object[] rows = new Object[col];
                    for (int i = 1; i <= col; i++) {
                        rows[i - 1] = rs.getObject(i);
                    }
                    ((DefaultTableModel) PITable.getModel()).insertRow(rs.getRow() - 1, rows);

                    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
                    PITable.setDefaultRenderer(String.class, centerRenderer);
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

    // GET CATEGORY IN PRODUCT PAGE OF ADMIN HOME PAGE 
    public void getCategory() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection;
            try {
                connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ShopManagementSystem;selectMethod=cursor", "sa", "123456");
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("select * from Categories");
                while (rs.next()) {
                    String myCat = rs.getString("C_ID");
                    ProCat.addItem(myCat);
                }
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
        jSellerButtonPanel = new javax.swing.JPanel();
        pane2 = new javax.swing.JPanel();
        jUserProfileButtonLabel = new javax.swing.JLabel();
        jCategoryButtonPanel = new javax.swing.JPanel();
        pane3 = new javax.swing.JPanel();
        jEventsButtonLabel = new javax.swing.JLabel();
        jProductsButtonPanel = new javax.swing.JPanel();
        pane4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jRevenueButtonPanel = new javax.swing.JPanel();
        pane6 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLogOutButtonPanel = new javax.swing.JPanel();
        pane5 = new javax.swing.JPanel();
        jEventsButtonLabel1 = new javax.swing.JLabel();
        jMessageButtonPanel = new javax.swing.JPanel();
        pane7 = new javax.swing.JPanel();
        jEventsButtonLabel2 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        HomePage = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        showname = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        CustomerTable = new javax.swing.JTable();
        SearchCus = new javax.swing.JTextField();
        Cuscat = new javax.swing.JComboBox<>();
        RefreshBtn1 = new javax.swing.JButton();
        SearchBtn1 = new javax.swing.JButton();
        SellerPage = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jUPDATEButton5 = new javax.swing.JButton();
        jDELETEButton5 = new javax.swing.JButton();
        jCLEARButton5 = new javax.swing.JButton();
        SID = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        STable = new javax.swing.JTable();
        SName = new javax.swing.JTextField();
        SPhone = new javax.swing.JTextField();
        SSearch = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        SEmail = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        SAddress = new javax.swing.JTextField();
        Pcat1 = new javax.swing.JComboBox<>();
        SearchBtn2 = new javax.swing.JButton();
        RefreshBtn2 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        CategoryPage = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        CatID = new javax.swing.JTextField();
        CatName = new javax.swing.JTextField();
        CatDescription = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jADDButton4 = new javax.swing.JButton();
        jUPDATEButton4 = new javax.swing.JButton();
        jDELETEButton4 = new javax.swing.JButton();
        jCLEARButton4 = new javax.swing.JButton();
        jLabel34 = new javax.swing.JLabel();
        CatSearch = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        CategoryTable = new javax.swing.JTable();
        ProductsPage = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        ProID = new javax.swing.JTextField();
        ProName = new javax.swing.JTextField();
        ProQuantity = new javax.swing.JTextField();
        ProPrice = new javax.swing.JTextField();
        ProCat = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jADDButton = new javax.swing.JButton();
        jUPDATEButton = new javax.swing.JButton();
        jDELETEButton = new javax.swing.JButton();
        jCLEARButton = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        ProSearch = new javax.swing.JTextField();
        jScrollPane8 = new javax.swing.JScrollPane();
        ProTable = new javax.swing.JTable();
        Pcat = new javax.swing.JComboBox<>();
        SearchBtn = new javax.swing.JButton();
        RefreshBtn = new javax.swing.JButton();
        RevenuePage = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        PITable = new javax.swing.JTable();
        jPanel22 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        MessagesPage = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        MessagesTable = new javax.swing.JTable();

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 268, Short.MAX_VALUE)
                .addComponent(jLabelMin, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelClose, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabelClose)
                        .addComponent(jLabelMin, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addComponent(jHomeButtonLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jHomeButtonPanelLayout.setVerticalGroup(
            jHomeButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jHomeButtonLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
        );

        jSellerButtonPanel.setBackground(new java.awt.Color(218, 223, 225));
        jSellerButtonPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jSellerButtonPanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jSellerButtonPanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jSellerButtonPanelMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jSellerButtonPanelMousePressed(evt);
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
        jUserProfileButtonLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/staffs_icon.png"))); // NOI18N
        jUserProfileButtonLabel.setText(" Seller");

        javax.swing.GroupLayout jSellerButtonPanelLayout = new javax.swing.GroupLayout(jSellerButtonPanel);
        jSellerButtonPanel.setLayout(jSellerButtonPanelLayout);
        jSellerButtonPanelLayout.setHorizontalGroup(
            jSellerButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jSellerButtonPanelLayout.createSequentialGroup()
                .addComponent(pane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jUserProfileButtonLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jSellerButtonPanelLayout.setVerticalGroup(
            jSellerButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jUserProfileButtonLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
        );

        jCategoryButtonPanel.setBackground(new java.awt.Color(218, 223, 225));
        jCategoryButtonPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jCategoryButtonPanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jCategoryButtonPanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jCategoryButtonPanelMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jCategoryButtonPanelMousePressed(evt);
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
        jEventsButtonLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/category_icon.png"))); // NOI18N
        jEventsButtonLabel.setText(" Category");

        javax.swing.GroupLayout jCategoryButtonPanelLayout = new javax.swing.GroupLayout(jCategoryButtonPanel);
        jCategoryButtonPanel.setLayout(jCategoryButtonPanelLayout);
        jCategoryButtonPanelLayout.setHorizontalGroup(
            jCategoryButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jCategoryButtonPanelLayout.createSequentialGroup()
                .addComponent(pane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jEventsButtonLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                .addGap(53, 53, 53))
        );
        jCategoryButtonPanelLayout.setVerticalGroup(
            jCategoryButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pane3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jEventsButtonLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
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
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/product_icon.png"))); // NOI18N
        jLabel4.setText(" Product");

        javax.swing.GroupLayout jProductsButtonPanelLayout = new javax.swing.GroupLayout(jProductsButtonPanel);
        jProductsButtonPanel.setLayout(jProductsButtonPanelLayout);
        jProductsButtonPanelLayout.setHorizontalGroup(
            jProductsButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jProductsButtonPanelLayout.createSequentialGroup()
                .addComponent(pane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 79, Short.MAX_VALUE))
        );
        jProductsButtonPanelLayout.setVerticalGroup(
            jProductsButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pane4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
        );

        jRevenueButtonPanel.setBackground(new java.awt.Color(218, 223, 225));
        jRevenueButtonPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRevenueButtonPanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jRevenueButtonPanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jRevenueButtonPanelMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jRevenueButtonPanelMousePressed(evt);
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

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(36, 37, 42));
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/revenue_icon.png"))); // NOI18N
        jLabel14.setText(" Revenue");

        javax.swing.GroupLayout jRevenueButtonPanelLayout = new javax.swing.GroupLayout(jRevenueButtonPanel);
        jRevenueButtonPanel.setLayout(jRevenueButtonPanelLayout);
        jRevenueButtonPanelLayout.setHorizontalGroup(
            jRevenueButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jRevenueButtonPanelLayout.createSequentialGroup()
                .addComponent(pane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jRevenueButtonPanelLayout.setVerticalGroup(
            jRevenueButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pane6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
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
                    .addComponent(jEventsButtonLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(54, 54, 54)))
        );
        jLogOutButtonPanelLayout.setVerticalGroup(
            jLogOutButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pane5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jLogOutButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jEventsButtonLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE))
        );

        jMessageButtonPanel.setBackground(new java.awt.Color(218, 223, 225));
        jMessageButtonPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMessageButtonPanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jMessageButtonPanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jMessageButtonPanelMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMessageButtonPanelMousePressed(evt);
            }
        });

        javax.swing.GroupLayout pane7Layout = new javax.swing.GroupLayout(pane7);
        pane7.setLayout(pane7Layout);
        pane7Layout.setHorizontalGroup(
            pane7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );
        pane7Layout.setVerticalGroup(
            pane7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jEventsButtonLabel2.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jEventsButtonLabel2.setForeground(new java.awt.Color(36, 37, 42));
        jEventsButtonLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/message_icon.png"))); // NOI18N
        jEventsButtonLabel2.setText(" Messages");

        javax.swing.GroupLayout jMessageButtonPanelLayout = new javax.swing.GroupLayout(jMessageButtonPanel);
        jMessageButtonPanel.setLayout(jMessageButtonPanelLayout);
        jMessageButtonPanelLayout.setHorizontalGroup(
            jMessageButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jMessageButtonPanelLayout.createSequentialGroup()
                .addComponent(pane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jEventsButtonLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(53, 53, 53))
        );
        jMessageButtonPanelLayout.setVerticalGroup(
            jMessageButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pane7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jEventsButtonLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSellerButtonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jHomeButtonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jCategoryButtonPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jProductsButtonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRevenueButtonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLogOutButtonPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jMessageButtonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jHomeButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSellerButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jMessageButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jCategoryButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jProductsButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jRevenueButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLogOutButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(54, Short.MAX_VALUE))
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

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel42.setText("Customer Information");

        CustomerTable.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        CustomerTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "PHONE NO.", "LAST PURCHASE", "POINT", "TOTAL ORDERS"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        CustomerTable.setIntercellSpacing(new java.awt.Dimension(0, 0));
        CustomerTable.setRowHeight(25);
        jScrollPane1.setViewportView(CustomerTable);
        if (CustomerTable.getColumnModel().getColumnCount() > 0) {
            CustomerTable.getColumnModel().getColumn(1).setPreferredWidth(100);
            CustomerTable.getColumnModel().getColumn(2).setPreferredWidth(230);
        }

        SearchCus.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        SearchCus.setForeground(new java.awt.Color(153, 153, 153));
        SearchCus.setText("Search...");
        SearchCus.setPreferredSize(new java.awt.Dimension(6, 28));
        SearchCus.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                SearchCusFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                SearchCusFocusLost(evt);
            }
        });

        Cuscat.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Cuscat.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Total Orders", "Point" }));

        RefreshBtn1.setText("Refresh");
        RefreshBtn1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RefreshBtn1MouseClicked(evt);
            }
        });

        SearchBtn1.setText("Search");
        SearchBtn1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SearchBtn1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel42)
                                .addGap(18, 18, 18)
                                .addComponent(SearchCus, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Cuscat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addComponent(RefreshBtn1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(SearchBtn1)))))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SearchCus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Cuscat, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SearchBtn1)
                    .addComponent(RefreshBtn1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout HomePageLayout = new javax.swing.GroupLayout(HomePage);
        HomePage.setLayout(HomePageLayout);
        HomePageLayout.setHorizontalGroup(
            HomePageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, 875, Short.MAX_VALUE)
            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 875, Short.MAX_VALUE)
        );
        HomePageLayout.setVerticalGroup(
            HomePageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomePageLayout.createSequentialGroup()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab1", HomePage);

        jPanel2.setBackground(new java.awt.Color(189, 195, 199));

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel36.setText("Seller ID");

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel38.setText("Name");
        jLabel38.setMaximumSize(new java.awt.Dimension(99, 22));
        jLabel38.setMinimumSize(new java.awt.Dimension(99, 22));
        jLabel38.setPreferredSize(new java.awt.Dimension(99, 22));

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel39.setText("Search");
        jLabel39.setMaximumSize(new java.awt.Dimension(99, 22));
        jLabel39.setMinimumSize(new java.awt.Dimension(99, 22));
        jLabel39.setPreferredSize(new java.awt.Dimension(99, 22));

        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel40.setText("Phone No");
        jLabel40.setMaximumSize(new java.awt.Dimension(99, 22));
        jLabel40.setMinimumSize(new java.awt.Dimension(99, 22));
        jLabel40.setPreferredSize(new java.awt.Dimension(99, 22));

        jUPDATEButton5.setBackground(new java.awt.Color(255, 255, 255));
        jUPDATEButton5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jUPDATEButton5.setText("UPDATE");
        jUPDATEButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jUPDATEButton5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jUPDATEButton5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jUPDATEButton5MouseExited(evt);
            }
        });

        jDELETEButton5.setBackground(new java.awt.Color(255, 255, 255));
        jDELETEButton5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jDELETEButton5.setText("DELETE");
        jDELETEButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jDELETEButton5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jDELETEButton5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jDELETEButton5MouseExited(evt);
            }
        });

        jCLEARButton5.setBackground(new java.awt.Color(255, 255, 255));
        jCLEARButton5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jCLEARButton5.setText("CLEAR");
        jCLEARButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jCLEARButton5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jCLEARButton5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jCLEARButton5MouseExited(evt);
            }
        });

        SID.setEditable(false);
        SID.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel41.setText("Seller List");

        STable.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        STable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NAME", "EMAIL", "PHONE NO", "ADDRESS", "PASSWORD", "Order Taken"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        STable.setIntercellSpacing(new java.awt.Dimension(0, 0));
        STable.setRowHeight(25);
        STable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                STableMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(STable);
        if (STable.getColumnModel().getColumnCount() > 0) {
            STable.getColumnModel().getColumn(2).setPreferredWidth(180);
            STable.getColumnModel().getColumn(3).setPreferredWidth(100);
        }

        SName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        SPhone.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        SSearch.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        SSearch.setForeground(new java.awt.Color(153, 153, 153));
        SSearch.setText("Search Sellers...");
        SSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                SSearchFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                SSearchFocusLost(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel11.setText("Email");
        jLabel11.setMaximumSize(new java.awt.Dimension(99, 22));
        jLabel11.setMinimumSize(new java.awt.Dimension(99, 22));
        jLabel11.setPreferredSize(new java.awt.Dimension(99, 22));

        SEmail.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel12.setText("Address");
        jLabel12.setMaximumSize(new java.awt.Dimension(99, 22));
        jLabel12.setMinimumSize(new java.awt.Dimension(99, 22));
        jLabel12.setPreferredSize(new java.awt.Dimension(99, 22));

        SAddress.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        Pcat1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Pcat1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Name", "Address", "Order Taken" }));

        SearchBtn2.setText("Search");
        SearchBtn2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SearchBtn2MouseClicked(evt);
            }
        });

        RefreshBtn2.setText("Refresh");
        RefreshBtn2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RefreshBtn2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(111, 111, 111)
                .addComponent(jUPDATEButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jDELETEButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(142, 142, 142)
                .addComponent(jCLEARButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(98, 98, 98))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(RefreshBtn2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SearchBtn2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Pcat1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel36)
                            .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(46, 46, 46)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(SID, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SName, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(65, 65, 65)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                                .addComponent(SPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(SAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(SSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(64, 64, 64))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel41)
                .addGap(374, 374, 374))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(SName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(SAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Pcat1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(SearchBtn2)
                        .addComponent(RefreshBtn2)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jUPDATEButton5))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCLEARButton5)
                            .addComponent(jDELETEButton5))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setPreferredSize(new java.awt.Dimension(643, 74));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Sellers");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(667, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout SellerPageLayout = new javax.swing.GroupLayout(SellerPage);
        SellerPage.setLayout(SellerPageLayout);
        SellerPageLayout.setHorizontalGroup(
            SellerPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 875, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        SellerPageLayout.setVerticalGroup(
            SellerPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SellerPageLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab2", SellerPage);

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));
        jPanel21.setPreferredSize(new java.awt.Dimension(643, 74));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(102, 102, 102));
        jLabel19.setText("Category");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel14.setBackground(new java.awt.Color(189, 195, 199));
        jPanel14.setPreferredSize(new java.awt.Dimension(875, 599));

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel32.setText("Category ID");

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel33.setText("Name");
        jLabel33.setMaximumSize(new java.awt.Dimension(99, 22));
        jLabel33.setMinimumSize(new java.awt.Dimension(99, 22));
        jLabel33.setPreferredSize(new java.awt.Dimension(99, 22));

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel35.setText("Description");
        jLabel35.setMaximumSize(new java.awt.Dimension(99, 22));
        jLabel35.setMinimumSize(new java.awt.Dimension(99, 22));
        jLabel35.setPreferredSize(new java.awt.Dimension(99, 22));

        CatID.setEditable(false);
        CatID.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        CatName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        CatDescription.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel37.setText("Categories List");

        jADDButton4.setBackground(new java.awt.Color(255, 255, 255));
        jADDButton4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jADDButton4.setText("ADD");
        jADDButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jADDButton4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jADDButton4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jADDButton4MouseExited(evt);
            }
        });

        jUPDATEButton4.setBackground(new java.awt.Color(255, 255, 255));
        jUPDATEButton4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jUPDATEButton4.setText("UPDATE");
        jUPDATEButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jUPDATEButton4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jUPDATEButton4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jUPDATEButton4MouseExited(evt);
            }
        });

        jDELETEButton4.setBackground(new java.awt.Color(255, 255, 255));
        jDELETEButton4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jDELETEButton4.setText("DELETE");
        jDELETEButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jDELETEButton4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jDELETEButton4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jDELETEButton4MouseExited(evt);
            }
        });

        jCLEARButton4.setBackground(new java.awt.Color(255, 255, 255));
        jCLEARButton4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jCLEARButton4.setText("CLEAR");
        jCLEARButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jCLEARButton4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jCLEARButton4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jCLEARButton4MouseExited(evt);
            }
        });

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel34.setText("Search");
        jLabel34.setMaximumSize(new java.awt.Dimension(99, 22));
        jLabel34.setMinimumSize(new java.awt.Dimension(99, 22));
        jLabel34.setPreferredSize(new java.awt.Dimension(99, 22));

        CatSearch.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        CatSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                CatSearchKeyReleased(evt);
            }
        });

        CategoryTable.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        CategoryTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NAME", "DESCRIPTION"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        CategoryTable.setIntercellSpacing(new java.awt.Dimension(0, 0));
        CategoryTable.setRowHeight(25);
        CategoryTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CategoryTableMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(CategoryTable);
        if (CategoryTable.getColumnModel().getColumnCount() > 0) {
            CategoryTable.getColumnModel().getColumn(2).setPreferredWidth(200);
        }

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel37)
                .addGap(352, 352, 352))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane6)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel32))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CatID, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CatName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(52, 52, 52)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(71, 71, 71)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CatSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CatDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel14Layout.createSequentialGroup()
                        .addComponent(jADDButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(108, 108, 108)
                        .addComponent(jUPDATEButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(99, 99, 99)
                        .addComponent(jDELETEButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCLEARButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(41, 41, 41))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CatID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CatDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(CatName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(CatSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jADDButton4)
                    .addComponent(jUPDATEButton4)
                    .addComponent(jDELETEButton4)
                    .addComponent(jCLEARButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout CategoryPageLayout = new javax.swing.GroupLayout(CategoryPage);
        CategoryPage.setLayout(CategoryPageLayout);
        CategoryPageLayout.setHorizontalGroup(
            CategoryPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, 875, Short.MAX_VALUE)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        CategoryPageLayout.setVerticalGroup(
            CategoryPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CategoryPageLayout.createSequentialGroup()
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab3", CategoryPage);

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));
        jPanel20.setPreferredSize(new java.awt.Dimension(643, 74));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(102, 102, 102));
        jLabel18.setText("Products");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(189, 195, 199));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Product ID");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("Name");
        jLabel3.setMaximumSize(new java.awt.Dimension(99, 22));
        jLabel3.setMinimumSize(new java.awt.Dimension(99, 22));
        jLabel3.setPreferredSize(new java.awt.Dimension(99, 22));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("Quatity");
        jLabel5.setMaximumSize(new java.awt.Dimension(99, 22));
        jLabel5.setMinimumSize(new java.awt.Dimension(99, 22));
        jLabel5.setPreferredSize(new java.awt.Dimension(99, 22));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setText("Price");
        jLabel8.setMaximumSize(new java.awt.Dimension(99, 22));
        jLabel8.setMinimumSize(new java.awt.Dimension(99, 22));
        jLabel8.setPreferredSize(new java.awt.Dimension(99, 22));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setText("Category ID");
        jLabel9.setMaximumSize(new java.awt.Dimension(99, 22));
        jLabel9.setMinimumSize(new java.awt.Dimension(99, 22));
        jLabel9.setPreferredSize(new java.awt.Dimension(99, 22));

        ProID.setEditable(false);
        ProID.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        ProName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        ProQuantity.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        ProPrice.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        ProCat.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel10.setText("Product List");

        jADDButton.setBackground(new java.awt.Color(255, 255, 255));
        jADDButton.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jADDButton.setText("ADD");
        jADDButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jADDButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jADDButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jADDButtonMouseExited(evt);
            }
        });

        jUPDATEButton.setBackground(new java.awt.Color(255, 255, 255));
        jUPDATEButton.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jUPDATEButton.setText("UPDATE");
        jUPDATEButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jUPDATEButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jUPDATEButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jUPDATEButtonMouseExited(evt);
            }
        });

        jDELETEButton.setBackground(new java.awt.Color(255, 255, 255));
        jDELETEButton.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jDELETEButton.setText("DELETE");
        jDELETEButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jDELETEButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jDELETEButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jDELETEButtonMouseExited(evt);
            }
        });

        jCLEARButton.setBackground(new java.awt.Color(255, 255, 255));
        jCLEARButton.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jCLEARButton.setText("CLEAR");
        jCLEARButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jCLEARButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jCLEARButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jCLEARButtonMouseExited(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel13.setText("Search");
        jLabel13.setMaximumSize(new java.awt.Dimension(99, 22));
        jLabel13.setMinimumSize(new java.awt.Dimension(99, 22));
        jLabel13.setPreferredSize(new java.awt.Dimension(99, 22));

        ProSearch.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        ProSearch.setForeground(new java.awt.Color(153, 153, 153));
        ProSearch.setText("Search Products...");
        ProSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ProSearchFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                ProSearchFocusLost(evt);
            }
        });

        ProTable.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        ProTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NAME", "QUANTITY", "PRICE", "CATEGORY ID", "CATEGORY"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        ProTable.setIntercellSpacing(new java.awt.Dimension(0, 0));
        ProTable.setRowHeight(25);
        ProTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ProTableMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(ProTable);
        if (ProTable.getColumnModel().getColumnCount() > 0) {
            ProTable.getColumnModel().getColumn(1).setPreferredWidth(200);
            ProTable.getColumnModel().getColumn(5).setPreferredWidth(150);
        }

        Pcat.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Pcat.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Name", "Category", "Price" }));

        SearchBtn.setText("Search");
        SearchBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SearchBtnMouseClicked(evt);
            }
        });
        SearchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchBtnActionPerformed(evt);
            }
        });

        RefreshBtn.setText("Refresh");
        RefreshBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RefreshBtnMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(358, 358, 358))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 830, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                        .addGap(571, 571, 571)
                        .addComponent(RefreshBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SearchBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Pcat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                            .addGap(36, 36, 36)
                                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                            .addContainerGap()
                                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGap(36, 36, 36)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(53, 53, 53)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(ProID, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ProName, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ProCat, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(74, 74, 74)
                                .addComponent(jADDButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jUPDATEButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(82, 82, 82)))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jDELETEButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jCLEARButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(52, 52, 52)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ProSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ProQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ProPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(37, 37, 37))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ProID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ProQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ProName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ProPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ProCat, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ProSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Pcat, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(SearchBtn)
                        .addComponent(RefreshBtn)))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCLEARButton)
                            .addComponent(jDELETEButton)
                            .addComponent(jUPDATEButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jADDButton)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout ProductsPageLayout = new javax.swing.GroupLayout(ProductsPage);
        ProductsPage.setLayout(ProductsPageLayout);
        ProductsPageLayout.setHorizontalGroup(
            ProductsPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, 875, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        ProductsPageLayout.setVerticalGroup(
            ProductsPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProductsPageLayout.createSequentialGroup()
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab4", ProductsPage);

        jPanel7.setBackground(new java.awt.Color(189, 195, 199));

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel43.setText("Product Information");

        PITable.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        PITable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NAME", "PRICE", "ITEMS SOLD", "REVENUE", "REVENUE RANK"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        PITable.setIntercellSpacing(new java.awt.Dimension(0, 0));
        PITable.setRowHeight(25);
        jScrollPane2.setViewportView(PITable);
        if (PITable.getColumnModel().getColumnCount() > 0) {
            PITable.getColumnModel().getColumn(1).setPreferredWidth(200);
        }

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel43)
                .addGap(306, 306, 306))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 809, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 549, Short.MAX_VALUE))
        );

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));
        jPanel22.setPreferredSize(new java.awt.Dimension(643, 74));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(102, 102, 102));
        jLabel20.setText("Revenue");

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(667, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout RevenuePageLayout = new javax.swing.GroupLayout(RevenuePage);
        RevenuePage.setLayout(RevenuePageLayout);
        RevenuePageLayout.setHorizontalGroup(
            RevenuePageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RevenuePageLayout.createSequentialGroup()
                .addGroup(RevenuePageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, 875, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        RevenuePageLayout.setVerticalGroup(
            RevenuePageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RevenuePageLayout.createSequentialGroup()
                .addComponent(jPanel22, 63, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab5", RevenuePage);

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));
        jPanel23.setPreferredSize(new java.awt.Dimension(643, 74));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(102, 102, 102));
        jLabel21.setText("Messages");

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel24.setBackground(new java.awt.Color(189, 195, 199));

        MessagesTable.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        MessagesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Message ID", "Seller ID", "Topic"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        MessagesTable.setIntercellSpacing(new java.awt.Dimension(0, 0));
        MessagesTable.setRowHeight(80);
        jScrollPane3.setViewportView(MessagesTable);
        if (MessagesTable.getColumnModel().getColumnCount() > 0) {
            MessagesTable.getColumnModel().getColumn(0).setPreferredWidth(30);
            MessagesTable.getColumnModel().getColumn(1).setPreferredWidth(40);
            MessagesTable.getColumnModel().getColumn(2).setPreferredWidth(450);
        }

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 799, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                .addGap(0, 44, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 555, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout MessagesPageLayout = new javax.swing.GroupLayout(MessagesPage);
        MessagesPage.setLayout(MessagesPageLayout);
        MessagesPageLayout.setHorizontalGroup(
            MessagesPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, 875, Short.MAX_VALUE)
            .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        MessagesPageLayout.setVerticalGroup(
            MessagesPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MessagesPageLayout.createSequentialGroup()
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab6", MessagesPage);

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

    private void jLabelCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelCloseMouseClicked
        System.exit(0);
    }//GEN-LAST:event_jLabelCloseMouseClicked

    private void jLabelCloseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelCloseMouseEntered
        jLabelClose.setForeground(Color.red);
    }//GEN-LAST:event_jLabelCloseMouseEntered

    private void jLabelCloseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelCloseMouseExited
        jLabelClose.setForeground(Color.white);
    }//GEN-LAST:event_jLabelCloseMouseExited

    private void jLabelMinMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelMinMouseClicked
        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_jLabelMinMouseClicked

    private void jLabelMinMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelMinMouseEntered
        jLabelMin.setForeground(Color.gray);
    }//GEN-LAST:event_jLabelMinMouseEntered

    private void jLabelMinMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelMinMouseExited
        jLabelMin.setForeground(Color.white);
    }//GEN-LAST:event_jLabelMinMouseExited

    private void jHomeButtonPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jHomeButtonPanelMouseClicked
        jTabbedPane1.setSelectedIndex(0);
        showCTable();
    }//GEN-LAST:event_jHomeButtonPanelMouseClicked

    private void jHomeButtonPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jHomeButtonPanelMouseEntered
        jHomeButtonPanel.setBackground(paneenter);
    }//GEN-LAST:event_jHomeButtonPanelMouseEntered

    private void jHomeButtonPanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jHomeButtonPanelMouseExited
        jHomeButtonPanel.setBackground(panedefault);
    }//GEN-LAST:event_jHomeButtonPanelMouseExited

    private void jHomeButtonPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jHomeButtonPanelMousePressed
        pane1.setBackground(paneclick);
        jHomeButtonPanel.setBackground(paneclick1);
        pane2.setBackground(panedefault);
        pane3.setBackground(panedefault);
        pane4.setBackground(panedefault);
        pane5.setBackground(panedefault);
        pane6.setBackground(panedefault);
        pane7.setBackground(panedefault);
    }//GEN-LAST:event_jHomeButtonPanelMousePressed

    private void jSellerButtonPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jSellerButtonPanelMouseClicked
        jTabbedPane1.setSelectedIndex(1);
        showSTable();
    }//GEN-LAST:event_jSellerButtonPanelMouseClicked

    private void jSellerButtonPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jSellerButtonPanelMouseEntered
        jSellerButtonPanel.setBackground(paneenter);
    }//GEN-LAST:event_jSellerButtonPanelMouseEntered

    private void jSellerButtonPanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jSellerButtonPanelMouseExited
        jSellerButtonPanel.setBackground(panedefault);
    }//GEN-LAST:event_jSellerButtonPanelMouseExited

    private void jSellerButtonPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jSellerButtonPanelMousePressed
        pane2.setBackground(paneclick);
        jSellerButtonPanel.setBackground(paneclick1);
        pane1.setBackground(panedefault);
        pane3.setBackground(panedefault);
        pane4.setBackground(panedefault);
        pane5.setBackground(panedefault);
        pane6.setBackground(panedefault);
        pane7.setBackground(panedefault);
    }//GEN-LAST:event_jSellerButtonPanelMousePressed

    private void jCategoryButtonPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCategoryButtonPanelMouseClicked
        jTabbedPane1.setSelectedIndex(2);
        showCatTable();
    }//GEN-LAST:event_jCategoryButtonPanelMouseClicked

    private void jCategoryButtonPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCategoryButtonPanelMouseEntered
        jCategoryButtonPanel.setBackground(paneenter);
    }//GEN-LAST:event_jCategoryButtonPanelMouseEntered

    private void jCategoryButtonPanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCategoryButtonPanelMouseExited
        jCategoryButtonPanel.setBackground(panedefault);
    }//GEN-LAST:event_jCategoryButtonPanelMouseExited

    private void jCategoryButtonPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCategoryButtonPanelMousePressed
        pane3.setBackground(paneclick);
        jCategoryButtonPanel.setBackground(paneclick1);
        pane1.setBackground(panedefault);
        pane2.setBackground(panedefault);
        pane4.setBackground(panedefault);
        pane5.setBackground(panedefault);
        pane6.setBackground(panedefault);
        pane7.setBackground(panedefault);
    }//GEN-LAST:event_jCategoryButtonPanelMousePressed

    private void jProductsButtonPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jProductsButtonPanelMouseClicked
        jTabbedPane1.setSelectedIndex(3);
        getCategory();
        showProTable();
    }//GEN-LAST:event_jProductsButtonPanelMouseClicked

    private void jProductsButtonPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jProductsButtonPanelMouseEntered
        jProductsButtonPanel.setBackground(paneenter);
    }//GEN-LAST:event_jProductsButtonPanelMouseEntered

    private void jProductsButtonPanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jProductsButtonPanelMouseExited
        jProductsButtonPanel.setBackground(panedefault);
    }//GEN-LAST:event_jProductsButtonPanelMouseExited

    private void jProductsButtonPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jProductsButtonPanelMousePressed
        pane4.setBackground(paneclick);
        jProductsButtonPanel.setBackground(paneclick1);
        pane1.setBackground(panedefault);
        pane2.setBackground(panedefault);
        pane3.setBackground(panedefault);
        pane5.setBackground(panedefault);
        pane6.setBackground(panedefault);
        pane7.setBackground(panedefault);
    }//GEN-LAST:event_jProductsButtonPanelMousePressed

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

    private void jLogOutButtonPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLogOutButtonPanelMouseEntered
        jLogOutButtonPanel.setBackground(paneenter);
    }//GEN-LAST:event_jLogOutButtonPanelMouseEntered

    private void jLogOutButtonPanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLogOutButtonPanelMouseExited
        jLogOutButtonPanel.setBackground(panedefault);
    }//GEN-LAST:event_jLogOutButtonPanelMouseExited

    private void jLogOutButtonPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLogOutButtonPanelMousePressed
        pane5.setBackground(paneclick);
        jLogOutButtonPanel.setBackground(paneclick1);
        pane1.setBackground(panedefault);
        pane2.setBackground(panedefault);
        pane3.setBackground(panedefault);
        pane4.setBackground(panedefault);
        pane6.setBackground(panedefault);
        pane7.setBackground(panedefault);
    }//GEN-LAST:event_jLogOutButtonPanelMousePressed

    private void jADDButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jADDButtonMouseEntered
        // set jbutton background
        jADDButton.setBackground(new Color(149, 165, 166));
    }//GEN-LAST:event_jADDButtonMouseEntered

    private void jADDButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jADDButtonMouseExited
        // set jbutton background
        jADDButton.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_jADDButtonMouseExited

    private void jUPDATEButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jUPDATEButtonMouseEntered
        // set jbutton background
        jUPDATEButton.setBackground(new Color(149, 165, 166));
    }//GEN-LAST:event_jUPDATEButtonMouseEntered

    private void jUPDATEButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jUPDATEButtonMouseExited
        // set jbutton background
        jUPDATEButton.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_jUPDATEButtonMouseExited

    private void jDELETEButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDELETEButtonMouseEntered
        // set jbutton background
        jDELETEButton.setBackground(new Color(149, 165, 166));
    }//GEN-LAST:event_jDELETEButtonMouseEntered

    private void jDELETEButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDELETEButtonMouseExited
        // set jbutton background
        jDELETEButton.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_jDELETEButtonMouseExited

    private void jCLEARButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCLEARButtonMouseEntered
        // set jbutton background
        jCLEARButton.setBackground(new Color(149, 165, 166));
    }//GEN-LAST:event_jCLEARButtonMouseEntered

    private void jCLEARButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCLEARButtonMouseExited
        // set jbutton background
        jCLEARButton.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_jCLEARButtonMouseExited

    private void jADDButton4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jADDButton4MouseEntered
        // set jbutton background
        jADDButton4.setBackground(new Color(149, 165, 166));
    }//GEN-LAST:event_jADDButton4MouseEntered

    private void jADDButton4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jADDButton4MouseExited
        // set jbutton background
        jADDButton4.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_jADDButton4MouseExited

    private void jUPDATEButton4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jUPDATEButton4MouseEntered
        // set jbutton background
        jUPDATEButton4.setBackground(new Color(149, 165, 166));
    }//GEN-LAST:event_jUPDATEButton4MouseEntered

    private void jUPDATEButton4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jUPDATEButton4MouseExited
        // set jbutton background
        jUPDATEButton4.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_jUPDATEButton4MouseExited

    private void jDELETEButton4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDELETEButton4MouseEntered
        // set jbutton background
        jDELETEButton4.setBackground(new Color(149, 165, 166));
    }//GEN-LAST:event_jDELETEButton4MouseEntered

    private void jDELETEButton4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDELETEButton4MouseExited
        // set jbutton background
        jDELETEButton4.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_jDELETEButton4MouseExited

    private void jCLEARButton4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCLEARButton4MouseEntered
        // set jbutton background
        jCLEARButton4.setBackground(new Color(149, 165, 166));
    }//GEN-LAST:event_jCLEARButton4MouseEntered

    private void jCLEARButton4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCLEARButton4MouseExited
        // set jbutton background
        jCLEARButton4.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_jCLEARButton4MouseExited

    private void jADDButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jADDButton4MouseClicked
        String catname = CatName.getText();
        String catdes = CatDescription.getText();

        if (CatName.getText().isEmpty() || CatDescription.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Missing Information");
        } else {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection connection;
                try {
                    connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ShopManagementSystem;selectMethod=cursor", "sa", "123456");
                    String addQuery = "INSERT INTO Categories (C_Name, Description) VALUES (?,?)";
                    PreparedStatement ps = connection.prepareStatement(addQuery);
                    ps.setString(1, catname);
                    ps.setString(2, catdes);
                    int i = ps.executeUpdate();
                    if (i > 0) {
                        JOptionPane.showMessageDialog(null, "Category Added Successfully");
                        showCatTable();
                        CatName.setText("");
                        CatDescription.setText("");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(AdminHome_Page.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AdminHome_Page.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jADDButton4MouseClicked

    private void CategoryTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CategoryTableMouseClicked
        DefaultTableModel model = (DefaultTableModel) CategoryTable.getModel();
        int myIndex = CategoryTable.getSelectedRow();
        CatID.setText(model.getValueAt(myIndex, 0).toString());
        CatName.setText(model.getValueAt(myIndex, 1).toString());
        CatDescription.setText(model.getValueAt(myIndex, 2).toString());
    }//GEN-LAST:event_CategoryTableMouseClicked

    private void jCLEARButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCLEARButton4MouseClicked
        CatID.setText("");
        CatName.setText("");
        CatDescription.setText("");
    }//GEN-LAST:event_jCLEARButton4MouseClicked

    private void jDELETEButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDELETEButton4MouseClicked
        String CID = CatID.getText();

        if (CatID.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter The Category To Be Deleted");
        } else {
            int x = JOptionPane.showConfirmDialog(this, "Are You Sure To Remove This Category", "Remove Category", JOptionPane.YES_NO_OPTION);
            if (x == 0) {
                try {
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    Connection connection;
                    try {
                        connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ShopManagementSystem;selectMethod=cursor", "sa", "123456");
                        String delQuery = "DELETE from Categories WHERE C_ID = " + CID;
                        PreparedStatement ps = connection.prepareStatement(delQuery);
                        int i = ps.executeUpdate();
                        if (i > 0) {
                            JOptionPane.showMessageDialog(null, "Category Deleted Successfully");
                            showCatTable();
                            CatID.setText("");
                            CatName.setText("");
                            CatDescription.setText("");
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(AdminHome_Page.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(AdminHome_Page.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_jDELETEButton4MouseClicked

    private void jUPDATEButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jUPDATEButton4MouseClicked
        if (CatID.getText().isEmpty() || CatName.getText().isEmpty() || CatDescription.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Missing Information");
        } else {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection connection;
                try {
                    connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ShopManagementSystem;selectMethod=cursor", "sa", "123456");
                    String updateQuery = "UPDATE Categories SET C_Name = '" + CatName.getText() + "'" + ", Description = '" + CatDescription.getText() + "'" + " WHERE C_ID =" + CatID.getText();
                    PreparedStatement ps = connection.prepareStatement(updateQuery);

                    int i = ps.executeUpdate();
                    if (i > 0) {
                        JOptionPane.showMessageDialog(null, "Category Updated Successfully");
                        showCatTable();
                        CatID.setText("");
                        CatName.setText("");
                        CatDescription.setText("");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(AdminHome_Page.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AdminHome_Page.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jUPDATEButton4MouseClicked

    private void CatSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CatSearchKeyReleased
        DefaultTableModel table = (DefaultTableModel) CategoryTable.getModel();
        String search = CatSearch.getText().toString();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(table);
        CategoryTable.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(search));
    }//GEN-LAST:event_CatSearchKeyReleased

    private void jADDButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jADDButtonMouseClicked
        String proname = ProName.getText();
        String proquantity = ProQuantity.getText();
        String proprice = ProPrice.getText();
        String procat = ProCat.getSelectedItem().toString();

        if (ProName.getText().isEmpty() || ProQuantity.getText().isEmpty() || ProPrice.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Missing Information");
        } else {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection connection;
                try {
                    connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ShopManagementSystem;selectMethod=cursor", "sa", "123456");
                    String addQuery = "INSERT INTO Products (P_Name, P_C_ID, P_Price, P_Quantity) VALUES (?,?,?,?)";
                    PreparedStatement ps = connection.prepareStatement(addQuery);
                    ps.setString(1, proname);
                    ps.setString(2, procat);
                    ps.setString(3, proprice);
                    ps.setString(4, proquantity);
                    int i = ps.executeUpdate();
                    if (i > 0) {
                        JOptionPane.showMessageDialog(null, "Product Added Successfully");
                        showProTable();
                        ProName.setText("");
                        ProQuantity.setText("");
                        ProPrice.setText("");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(AdminHome_Page.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AdminHome_Page.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jADDButtonMouseClicked

    private void jCLEARButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCLEARButtonMouseClicked
        ProID.setText("");
        ProName.setText("");
        ProQuantity.setText("");
        ProPrice.setText("");
    }//GEN-LAST:event_jCLEARButtonMouseClicked

    private void jDELETEButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDELETEButtonMouseClicked
        String PID = ProID.getText();

        if (ProID.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter The Product To Be Deleted");
        } else {
            int x = JOptionPane.showConfirmDialog(this, "Are You Sure To Remove This Product", "Remove Product", JOptionPane.YES_NO_OPTION);
            if (x == 0) {
                try {
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    Connection connection;
                    try {
                        connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ShopManagementSystem;selectMethod=cursor", "sa", "123456");
                        String delQuery = "DELETE from Products WHERE P_ID = " + PID;
                        PreparedStatement ps = connection.prepareStatement(delQuery);
                        int i = ps.executeUpdate();
                        if (i > 0) {
                            JOptionPane.showMessageDialog(null, "Product Deleted Successfully");
                            showProTable();
                            ProID.setText("");
                            ProName.setText("");
                            ProQuantity.setText("");
                            ProPrice.setText("");
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(AdminHome_Page.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(AdminHome_Page.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_jDELETEButtonMouseClicked

    private void jUPDATEButton5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jUPDATEButton5MouseClicked
        if (SID.getText().isEmpty() || SName.getText().isEmpty() || SPhone.getText().isEmpty() || SEmail.getText().isEmpty() || SAddress.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Missing Information");
        } else {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection connection;
                try {
                    connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ShopManagementSystem;selectMethod=cursor", "sa", "123456");
                    String updateQuery = "UPDATE Seller SET SellerName = '" + SName.getText() + "'" + ", Email = '" + SEmail.getText() + "'" + ", PhoneNo = '" + SPhone.getText() + "'" + ", Address = '" + SAddress.getText() + "'" + " WHERE SellerID =" + SID.getText();
                    PreparedStatement ps = connection.prepareStatement(updateQuery);

                    int i = ps.executeUpdate();
                    if (i > 0) {
                        JOptionPane.showMessageDialog(null, "Seller Updated Successfully");
                        showSTable();
                        SID.setText("");
                        SName.setText("");
                        SEmail.setText("");
                        SPhone.setText("");
                        SAddress.setText("");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(AdminHome_Page.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AdminHome_Page.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jUPDATEButton5MouseClicked

    private void jUPDATEButton5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jUPDATEButton5MouseEntered
        // set jbutton background
        jUPDATEButton5.setBackground(new Color(149, 165, 166));
    }//GEN-LAST:event_jUPDATEButton5MouseEntered

    private void jUPDATEButton5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jUPDATEButton5MouseExited
        // set jbutton background
        jUPDATEButton5.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_jUPDATEButton5MouseExited

    private void jDELETEButton5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDELETEButton5MouseClicked
        String SellerID = SID.getText();

        if (SID.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter The Seller ID To Be Deleted");
        } else {
            int x = JOptionPane.showConfirmDialog(this, "Are You Sure To Remove This Seller", "Remove Seller", JOptionPane.YES_NO_OPTION);
            if (x == 0) {
                try {
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    Connection connection;
                    try {
                        connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ShopManagementSystem;selectMethod=cursor", "sa", "123456");
                        String delQuery = "DELETE from Seller WHERE SellerID = " + SellerID;
                        PreparedStatement ps = connection.prepareStatement(delQuery);
                        int i = ps.executeUpdate();
                        if (i > 0) {
                            JOptionPane.showMessageDialog(null, "Category Deleted Successfully");
                            showSTable();
                            SID.setText("");
                            SName.setText("");
                            SPhone.setText("");
                            SEmail.setText("");
                            SAddress.setText("");
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(AdminHome_Page.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(AdminHome_Page.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_jDELETEButton5MouseClicked

    private void jDELETEButton5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDELETEButton5MouseEntered
        // set jbutton background
        jDELETEButton5.setBackground(new Color(149, 165, 166));
    }//GEN-LAST:event_jDELETEButton5MouseEntered

    private void jDELETEButton5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDELETEButton5MouseExited
        // set jbutton background
        jDELETEButton5.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_jDELETEButton5MouseExited

    private void jCLEARButton5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCLEARButton5MouseClicked
        SID.setText("");
        SName.setText("");
        SPhone.setText("");
        SEmail.setText("");
        SAddress.setText("");
    }//GEN-LAST:event_jCLEARButton5MouseClicked

    private void jCLEARButton5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCLEARButton5MouseEntered
        // set jbutton background
        jCLEARButton5.setBackground(new Color(149, 165, 166));
    }//GEN-LAST:event_jCLEARButton5MouseEntered

    private void jCLEARButton5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCLEARButton5MouseExited
        // set jbutton background
        jCLEARButton5.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_jCLEARButton5MouseExited

    private void STableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_STableMouseClicked
        DefaultTableModel model = (DefaultTableModel) STable.getModel();
        int myIndex = STable.getSelectedRow();
        SID.setText(model.getValueAt(myIndex, 0).toString());
        SName.setText(model.getValueAt(myIndex, 1).toString());
        SEmail.setText(model.getValueAt(myIndex, 2).toString());
        SPhone.setText(model.getValueAt(myIndex, 3).toString());
        SAddress.setText(model.getValueAt(myIndex, 4).toString());
    }//GEN-LAST:event_STableMouseClicked

    private void jUPDATEButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jUPDATEButtonMouseClicked
        if (ProID.getText().isEmpty() || ProName.getText().isEmpty() || ProQuantity.getText().isEmpty() || ProPrice.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Missing Information");
        } else {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection connection;
                try {
                    connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ShopManagementSystem;selectMethod=cursor", "sa", "123456");
                    String updateQuery = "UPDATE Products SET P_Name = '" + ProName.getText() + "'" + ", P_C_ID = '" + ProCat.getSelectedItem().toString() + "'" + ", P_Price = '" + ProPrice.getText() + "'" + ", P_Quantity = '" + ProQuantity.getText() + "'" + " WHERE P_ID =" + ProID.getText();
                    PreparedStatement ps = connection.prepareStatement(updateQuery);

                    int i = ps.executeUpdate();
                    if (i > 0) {
                        JOptionPane.showMessageDialog(null, "Product Updated Successfully");
                        showProTable();
                        ProID.setText("");
                        ProName.setText("");
                        ProQuantity.setText("");
                        ProPrice.setText("");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(AdminHome_Page.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AdminHome_Page.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jUPDATEButtonMouseClicked

    private void ProTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ProTableMouseClicked
        DefaultTableModel model = (DefaultTableModel) ProTable.getModel();
        int myIndex = ProTable.getSelectedRow();
        ProID.setText(model.getValueAt(myIndex, 0).toString());
        ProName.setText(model.getValueAt(myIndex, 1).toString());
        ProQuantity.setText(model.getValueAt(myIndex, 2).toString());
        ProPrice.setText(model.getValueAt(myIndex, 3).toString());
    }//GEN-LAST:event_ProTableMouseClicked

    private void ProSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ProSearchFocusGained
        // clear the textfield on focus if the text is "Search Products..."
        if (ProSearch.getText().trim().toString().equals("Search Products...")) {
            ProSearch.setText("");
            ProSearch.setForeground(Color.black);
        }
    }//GEN-LAST:event_ProSearchFocusGained

    private void ProSearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ProSearchFocusLost
        // if the text field is equal to username or empty
        // we will set the "XX-XX-XX-XXX" text in the field
        // on focus lost event
        if (ProSearch.getText().trim().equals("") || ProSearch.getText().trim().toString().equals("Search Products...")) {
            ProSearch.setText("Search Products...");
            ProSearch.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_ProSearchFocusLost

    private void SearchBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SearchBtnMouseClicked
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection;
            try {
                connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ShopManagementSystem;selectMethod=cursor", "sa", "123456");
                Statement st = connection.createStatement();
                ResultSet rs = null;
                if (Pcat.getSelectedItem().toString().equals("Name")) {
                    if (ProSearch.getText().isEmpty() || ProSearch.getText().equals("Search Products...")) {
                        rs = st.executeQuery("SELECT pro.P_ID, pro.P_Name, pro.P_Quantity, ROUND(pro.P_Price, 2), cat.C_ID, cat.C_Name FROM Categories AS cat INNER JOIN Products AS pro ON cat.C_ID = pro.P_C_ID ORDER BY pro.P_Name, cat.C_Name");
                    } else {
                        rs = st.executeQuery("SELECT pro.P_ID, pro.P_Name, pro.P_Quantity, ROUND(pro.P_Price, 2), cat.C_ID, cat.C_Name FROM Categories AS cat INNER JOIN Products AS pro ON cat.C_ID = pro.P_C_ID WHERE pro.P_Name IN (SELECT P_Name FROM Products WHERE P_Name LIKE '%" + ProSearch.getText() + "%') ORDER BY pro.P_Name, cat.C_Name");
                    }
                } else if (Pcat.getSelectedItem().toString().equals("Category")) {
                    if (ProSearch.getText().isEmpty() || ProSearch.getText().equals("Search Products...")) {
                        rs = st.executeQuery("SELECT pro.P_ID, pro.P_Name, pro.P_Quantity, ROUND(pro.P_Price, 2), cat.C_ID, cat.C_Name FROM Categories AS cat INNER JOIN Products AS pro ON cat.C_ID = pro.P_C_ID ORDER BY cat.C_Name, pro.P_Name");
                    } else {
                        rs = st.executeQuery("SELECT pro.P_ID, pro.P_Name, pro.P_Quantity, ROUND(pro.P_Price, 2), cat.C_ID, cat.C_Name FROM Categories AS cat INNER JOIN Products AS pro ON cat.C_ID = pro.P_C_ID WHERE cat.C_Name IN (SELECT C_Name FROM Categories WHERE C_Name LIKE '%" + ProSearch.getText() + "%') ORDER BY cat.C_Name, pro.P_Name");
                    }
                } else if (Pcat.getSelectedItem().toString() == "Price") {
                    if (ProSearch.getText().isEmpty() || ProSearch.getText().equals("Search Products...")) {
                        rs = st.executeQuery("SELECT pro.P_ID, pro.P_Name, pro.P_Quantity, ROUND(pro.P_Price, 2), cat.C_ID, cat.C_Name FROM Categories AS cat INNER JOIN Products AS pro ON cat.C_ID = pro.P_C_ID ORDER BY pro.P_Price");
                    } else {
                        rs = st.executeQuery("SELECT pro.P_ID, pro.P_Name, pro.P_Quantity, ROUND(pro.P_Price, 2), cat.C_ID, cat.C_Name FROM Categories AS cat INNER JOIN Products AS pro ON cat.C_ID = pro.P_C_ID WHERE pro.P_Price IN (SELECT P_Price FROM Products WHERE P_Price " + ProSearch.getText() + ") ORDER BY pro.P_Price");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Missing Information");
                }

                while (ProTable.getRowCount() > 0) {
                    ((DefaultTableModel) ProTable.getModel()).removeRow(0);
                }
                int col = rs.getMetaData().getColumnCount();
                while (rs.next()) {
                    Object[] rows = new Object[col];
                    for (int i = 1; i <= col; i++) {
                        rows[i - 1] = rs.getObject(i);
                    }
                    ((DefaultTableModel) ProTable.getModel()).insertRow(rs.getRow() - 1, rows);
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
        showProTable();
    }//GEN-LAST:event_RefreshBtnMouseClicked

    private void jRevenueButtonPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRevenueButtonPanelMouseClicked
        jTabbedPane1.setSelectedIndex(4);
        showPITable();
    }//GEN-LAST:event_jRevenueButtonPanelMouseClicked

    private void jRevenueButtonPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRevenueButtonPanelMouseEntered
        jRevenueButtonPanel.setBackground(paneenter);
    }//GEN-LAST:event_jRevenueButtonPanelMouseEntered

    private void jRevenueButtonPanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRevenueButtonPanelMouseExited
        jRevenueButtonPanel.setBackground(panedefault);
    }//GEN-LAST:event_jRevenueButtonPanelMouseExited

    private void jRevenueButtonPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRevenueButtonPanelMousePressed
        pane6.setBackground(paneclick);
        jRevenueButtonPanel.setBackground(paneclick1);
        pane1.setBackground(panedefault);
        pane2.setBackground(panedefault);
        pane3.setBackground(panedefault);
        pane4.setBackground(panedefault);
        pane5.setBackground(panedefault);
        pane7.setBackground(panedefault);
    }//GEN-LAST:event_jRevenueButtonPanelMousePressed

    private void SearchCusFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_SearchCusFocusGained
        // clear the textfield on focus if the text is "Search Products..."
        if (SearchCus.getText().trim().toString().equals("Search...")) {
            SearchCus.setText("");
            SearchCus.setForeground(Color.black);
        }
    }//GEN-LAST:event_SearchCusFocusGained

    private void SearchCusFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_SearchCusFocusLost
        // if the text field is equal to username or empty
        // we will set the "XX-XX-XX-XXX" text in the field
        // on focus lost event
        if (SearchCus.getText().trim().equals("") || SearchCus.getText().trim().toString().equals("Search...")) {
            SearchCus.setText("Search...");
            SearchCus.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_SearchCusFocusLost

    private void RefreshBtn1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RefreshBtn1MouseClicked
        showCTable();
    }//GEN-LAST:event_RefreshBtn1MouseClicked

    private void SearchBtn1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SearchBtn1MouseClicked
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection;
            try {
                connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ShopManagementSystem;selectMethod=cursor", "sa", "123456");
                Statement st = connection.createStatement();
                ResultSet rs = null;
                if (Cuscat.getSelectedItem().toString().equals("Total Orders")) {
                    if (SearchCus.getText().isEmpty() || SearchCus.getText().equals("Search...")) {
                        rs = st.executeQuery("SELECT cus.Cus_ID, cus.Cus_PhoneNo, cus.Cus_LastPurchaseDate, ROUND(cus.Cus_Point, 2), COUNT(o.OrderID) AS Total_Orders FROM Customer AS cus LEFT JOIN Orders AS o ON cus.Cus_ID = o.CustomerID GROUP BY cus.Cus_ID, cus.Cus_PhoneNo, cus.Cus_LastPurchaseDate, cus.Cus_Point ORDER BY Total_Orders, cus.Cus_ID");
                    } else {
                        rs = st.executeQuery("SELECT cus.Cus_ID, cus.Cus_PhoneNo, cus.Cus_LastPurchaseDate, ROUND(cus.Cus_Point, 2), COUNT(o.OrderID) AS Total_Orders FROM Customer AS cus LEFT JOIN Orders AS o ON cus.Cus_ID = o.CustomerID GROUP BY cus.Cus_ID, cus.Cus_PhoneNo, cus.Cus_LastPurchaseDate, cus.Cus_Point HAVING COUNT(o.OrderID) " + SearchCus.getText() + " ORDER BY Total_Orders, cus.Cus_ID");
                    }
                } else if (Cuscat.getSelectedItem().toString() == "Point") {
                    if (SearchCus.getText().isEmpty() || SearchCus.getText().equals("Search...")) {
                        rs = st.executeQuery("SELECT cus.Cus_ID, cus.Cus_PhoneNo, cus.Cus_LastPurchaseDate, ROUND(cus.Cus_Point, 2), COUNT(o.OrderID) AS Total_Orders FROM Customer AS cus LEFT JOIN Orders AS o ON cus.Cus_ID = o.CustomerID GROUP BY cus.Cus_ID, cus.Cus_PhoneNo, cus.Cus_LastPurchaseDate, cus.Cus_Point ORDER BY cus.Cus_Point DESC, cus.Cus_ID");
                    } else {
                        rs = st.executeQuery("SELECT cus.Cus_ID, cus.Cus_PhoneNo, cus.Cus_LastPurchaseDate, ROUND(cus.Cus_Point, 2), COUNT(o.OrderID) AS Total_Orders FROM Customer AS cus LEFT JOIN Orders AS o ON cus.Cus_ID = o.CustomerID GROUP BY cus.Cus_ID, cus.Cus_PhoneNo, cus.Cus_LastPurchaseDate, cus.Cus_Point HAVING Cus_Point " + SearchCus.getText() + " ORDER BY cus.Cus_Point DESC, cus.Cus_ID");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Missing Information");
                }

                while (CustomerTable.getRowCount() > 0) {
                    ((DefaultTableModel) CustomerTable.getModel()).removeRow(0);
                }
                int col = rs.getMetaData().getColumnCount();
                while (rs.next()) {
                    Object[] rows = new Object[col];
                    for (int i = 1; i <= col; i++) {
                        rows[i - 1] = rs.getObject(i);
                    }
                    ((DefaultTableModel) CustomerTable.getModel()).insertRow(rs.getRow() - 1, rows);
                }
                rs.close();
                st.close();

            } catch (SQLException ex) {
                Logger.getLogger(SellerHome_Page.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SellerHome_Page.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_SearchBtn1MouseClicked

    private void SearchBtn2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SearchBtn2MouseClicked
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection;
            try {
                connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ShopManagementSystem;selectMethod=cursor", "sa", "123456");
                Statement st = connection.createStatement();
                ResultSet rs = null;
                if (Pcat1.getSelectedItem().toString().equals("Name")) {
                    if (SSearch.getText().isEmpty() || SSearch.getText().equals("Search Sellers...")) {
                        rs = st.executeQuery("SELECT s.SellerID, s.SellerName, s.Email, s.PhoneNo, s.Address, s.Password, COUNT(o.OrderID) AS Total_Orders FROM Seller AS s LEFT JOIN Orders AS o ON s.SellerID = o.SellerID GROUP BY s.SellerID, s.SellerName, s.Email, s.PhoneNo, s.Address, s.Password ORDER BY s.SellerName, s.SellerID");
                    } else {
                        rs = st.executeQuery("SELECT s.SellerID, s.SellerName, s.Email, s.PhoneNo, s.Address, s.Password, COUNT(o.OrderID) AS Total_Orders FROM Seller AS s LEFT JOIN Orders AS o ON s.SellerID = o.SellerID GROUP BY s.SellerID, s.SellerName, s.Email, s.PhoneNo, s.Address, s.Password HAVING s.SellerName LIKE '%" + SSearch.getText() + "%' ORDER BY s.SellerName, s.SellerID");
                    }
                } else if (Pcat1.getSelectedItem().toString() == "Address") {
                    if (SSearch.getText().isEmpty() || SSearch.getText().equals("Search Sellers...")) {
                        rs = st.executeQuery("SELECT s.SellerID, s.SellerName, s.Email, s.PhoneNo, s.Address, s.Password, COUNT(o.OrderID) AS Total_Orders FROM Seller AS s LEFT JOIN Orders AS o ON s.SellerID = o.SellerID GROUP BY s.SellerID, s.SellerName, s.Email, s.PhoneNo, s.Address, s.Password ORDER BY s.Address, s.SellerID");
                    } else {
                        rs = st.executeQuery("SELECT s.SellerID, s.SellerName, s.Email, s.PhoneNo, s.Address, s.Password, COUNT(o.OrderID) AS Total_Orders FROM Seller AS s LEFT JOIN Orders AS o ON s.SellerID = o.SellerID GROUP BY s.SellerID, s.SellerName, s.Email, s.PhoneNo, s.Address, s.Password HAVING s.Address LIKE '%" + SSearch.getText() + "%' ORDER BY s.Address, s.SellerID");
                    }
                } else if (Pcat1.getSelectedItem().toString() == "Order Taken") {
                    if (SSearch.getText().isEmpty() || SSearch.getText().equals("Search Sellers...")) {
                        rs = st.executeQuery("SELECT s.SellerID, s.SellerName, s.Email, s.PhoneNo, s.Address, s.Password, COUNT(o.OrderID) AS Total_Orders FROM Seller AS s LEFT JOIN Orders AS o ON s.SellerID = o.SellerID GROUP BY s.SellerID, s.SellerName, s.Email, s.PhoneNo, s.Address, s.Password ORDER BY Total_Orders, s.SellerID");
                    } else {
                        rs = st.executeQuery("SELECT s.SellerID, s.SellerName, s.Email, s.PhoneNo, s.Address, s.Password, COUNT(o.OrderID) AS Total_Orders FROM Seller AS s LEFT JOIN Orders AS o ON s.SellerID = o.SellerID GROUP BY s.SellerID, s.SellerName, s.Email, s.PhoneNo, s.Address, s.Password HAVING COUNT(o.OrderID) " + SSearch.getText() + "ORDER BY Total_Orders, s.SellerID");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Missing Information");
                }

                while (STable.getRowCount() > 0) {
                    ((DefaultTableModel) STable.getModel()).removeRow(0);
                }
                int col = rs.getMetaData().getColumnCount();
                while (rs.next()) {
                    Object[] rows = new Object[col];
                    for (int i = 1; i <= col; i++) {
                        rows[i - 1] = rs.getObject(i);
                    }
                    ((DefaultTableModel) STable.getModel()).insertRow(rs.getRow() - 1, rows);
                }
                rs.close();
                st.close();
            } catch (SQLException ex) {
                Logger.getLogger(SellerHome_Page.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SellerHome_Page.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_SearchBtn2MouseClicked

    private void RefreshBtn2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RefreshBtn2MouseClicked
        showSTable();
    }//GEN-LAST:event_RefreshBtn2MouseClicked

    private void SSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_SSearchFocusGained
        // clear the textfield on focus if the text is "Search Products..."
        if (SSearch.getText().trim().toString().equals("Search Sellers...")) {
            SSearch.setText("");
            SSearch.setForeground(Color.black);
        }
    }//GEN-LAST:event_SSearchFocusGained

    private void SSearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_SSearchFocusLost
        // if the text field is equal to username or empty
        // we will set the "XX-XX-XX-XXX" text in the field
        // on focus lost event
        if (SSearch.getText().trim().equals("") || SSearch.getText().trim().toString().equals("Search Sellers...")) {
            SSearch.setText("Search Sellers...");
            SSearch.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_SSearchFocusLost

    private void jMessageButtonPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMessageButtonPanelMouseClicked
        jTabbedPane1.setSelectedIndex(5);
        showMessageTable();
        //EventQueue.invokeLater(new ShowIt());
    }//GEN-LAST:event_jMessageButtonPanelMouseClicked

    private void jMessageButtonPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMessageButtonPanelMouseEntered
        jMessageButtonPanel.setBackground(paneenter);
    }//GEN-LAST:event_jMessageButtonPanelMouseEntered

    private void jMessageButtonPanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMessageButtonPanelMouseExited
        jMessageButtonPanel.setBackground(panedefault);
    }//GEN-LAST:event_jMessageButtonPanelMouseExited

    private void jMessageButtonPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMessageButtonPanelMousePressed
        pane7.setBackground(paneclick);
        jMessageButtonPanel.setBackground(paneclick1);
        pane1.setBackground(panedefault);
        pane2.setBackground(panedefault);
        pane3.setBackground(panedefault);
        pane4.setBackground(panedefault);
        pane5.setBackground(panedefault);
        pane6.setBackground(panedefault);
    }//GEN-LAST:event_jMessageButtonPanelMousePressed

    private void SearchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SearchBtnActionPerformed

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
            java.util.logging.Logger.getLogger(AdminHome_Page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminHome_Page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminHome_Page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminHome_Page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminHome_Page().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField CatDescription;
    private javax.swing.JTextField CatID;
    private javax.swing.JTextField CatName;
    private javax.swing.JTextField CatSearch;
    private javax.swing.JPanel CategoryPage;
    private javax.swing.JTable CategoryTable;
    private javax.swing.JComboBox<String> Cuscat;
    private javax.swing.JTable CustomerTable;
    private javax.swing.JPanel HomePage;
    private javax.swing.JPanel MessagesPage;
    private javax.swing.JTable MessagesTable;
    private javax.swing.JTable PITable;
    private javax.swing.JComboBox<String> Pcat;
    private javax.swing.JComboBox<String> Pcat1;
    private javax.swing.JComboBox<String> ProCat;
    private javax.swing.JTextField ProID;
    private javax.swing.JTextField ProName;
    private javax.swing.JTextField ProPrice;
    private javax.swing.JTextField ProQuantity;
    private javax.swing.JTextField ProSearch;
    private javax.swing.JTable ProTable;
    private javax.swing.JPanel ProductsPage;
    private javax.swing.JButton RefreshBtn;
    private javax.swing.JButton RefreshBtn1;
    private javax.swing.JButton RefreshBtn2;
    private javax.swing.JPanel RevenuePage;
    private javax.swing.JTextField SAddress;
    private javax.swing.JTextField SEmail;
    private javax.swing.JTextField SID;
    private javax.swing.JTextField SName;
    private javax.swing.JTextField SPhone;
    private javax.swing.JTextField SSearch;
    private javax.swing.JTable STable;
    private javax.swing.JButton SearchBtn;
    private javax.swing.JButton SearchBtn1;
    private javax.swing.JButton SearchBtn2;
    private javax.swing.JTextField SearchCus;
    private javax.swing.JPanel SellerPage;
    private javax.swing.JButton jADDButton;
    private javax.swing.JButton jADDButton4;
    private javax.swing.JButton jCLEARButton;
    private javax.swing.JButton jCLEARButton4;
    private javax.swing.JButton jCLEARButton5;
    private javax.swing.JPanel jCategoryButtonPanel;
    private javax.swing.JButton jDELETEButton;
    private javax.swing.JButton jDELETEButton4;
    private javax.swing.JButton jDELETEButton5;
    private javax.swing.JLabel jEventsButtonLabel;
    private javax.swing.JLabel jEventsButtonLabel1;
    private javax.swing.JLabel jEventsButtonLabel2;
    private javax.swing.JLabel jHomeButtonLabel;
    private javax.swing.JPanel jHomeButtonPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
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
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelClose;
    private javax.swing.JLabel jLabelMin;
    private javax.swing.JPanel jLogOutButtonPanel;
    private javax.swing.JPanel jMessageButtonPanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jProductsButtonPanel;
    private javax.swing.JPanel jRevenueButtonPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JPanel jSellerButtonPanel;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton jUPDATEButton;
    private javax.swing.JButton jUPDATEButton4;
    private javax.swing.JButton jUPDATEButton5;
    private javax.swing.JLabel jUserProfileButtonLabel;
    public static final javax.swing.JLabel name = new javax.swing.JLabel();
    private javax.swing.JPanel pane1;
    private javax.swing.JPanel pane2;
    private javax.swing.JPanel pane3;
    private javax.swing.JPanel pane4;
    private javax.swing.JPanel pane5;
    private javax.swing.JPanel pane6;
    private javax.swing.JPanel pane7;
    private javax.swing.JLabel showname;
    // End of variables declaration//GEN-END:variables
}
