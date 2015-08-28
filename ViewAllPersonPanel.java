import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;
class ViewAllPersonPanel extends JPanel implements ActionListener{

    
    private JButton refresh;
    private JScrollPane personScrollPane;
    private JLabel imageLbl;
    private JLabel bgLbl;
    private final int x = 430,  y = 30;
	
	 // data base variable
	Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		
    public ViewAllPersonPanel() {

        

        setLayout(null);

        refresh = new JButton("Refresh");
        refresh.setBounds(x +197, y + 270, 100, 22);

        refresh.addActionListener(this);
        add(refresh);

        addImageAndBackGround();

    }

    private void addImageAndBackGround() {

    

        imageLbl = new JLabel("", new ImageIcon(getClass().getResource(
                "imgs/p_viewall.png")), SwingConstants.LEFT);
        imageLbl.setBounds(x+120, y, 256, 256);
        add(imageLbl);

        bgLbl = new JLabel("", new ImageIcon(getClass().getResource(
                "imgs/bground4.jpg")), SwingConstants.LEFT);
        bgLbl.setBounds(0, 0, 835, 551);
        add(bgLbl);

    }
	private void loadDataBase(){
			 try {
           Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
       }  catch (Exception e) {
           JOptionPane.showMessageDialog(null,""+e.getMessage(),"JDBC Driver Error",JOptionPane.WARNING_MESSAGE);
       }
				try{
				//connect to database
				con=DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=AddressBook.mdb;DriverID=22}");
				//con=DriverManager.getConnection("jdbc:odbc:AddressBook");
				st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				rs=st.executeQuery("Select * from Person");
				}
				catch(Exception eee){
				JOptionPane.showMessageDialog(null, eee.toString(), "Error Message2", JOptionPane.ERROR_MESSAGE);
	}}
	
	 public void actionPerformed(ActionEvent aEvent) {
		loadDataBase();
	 Vector rows = new Vector();
            Vector<String> row;

           try{
           while (rs.next()) 
		   
		  
		   {
                row = new Vector<String>();
                row.add(rs.getString(1));
                row.add(rs.getString(2));
                row.add(rs.getString(3));
                row.add(rs.getString(4));
                row.add(rs.getString(5));
               
                rows.add(row);
            }

            Vector cols = new Vector();
            cols.add("Name");
            cols.add("Phone");
            
            cols.add("Mobile");
            cols.add("Email");
            cols.add("Address");

            displayResult(rows, cols);
            validate();

            remove(imageLbl);
            remove(bgLbl);
            addImageAndBackGround();

        }
		catch(Exception eee){
				JOptionPane.showMessageDialog(null, eee.toString(), "Error Message2", JOptionPane.ERROR_MESSAGE);
	}
		}

     private void displayResult(Vector rows, Vector cols) {

        

        JTable Table = new JTable(rows, cols);
        Table.setBackground(new Color(238, 238, 238));
		Table.setEnabled(false);
        personScrollPane = new JScrollPane(Table);
        personScrollPane.setBounds(x - 390, y, 500, 320);
        personScrollPane.setAutoscrolls(true);
        add(personScrollPane);

    }
	 
	} //end of view all class
	