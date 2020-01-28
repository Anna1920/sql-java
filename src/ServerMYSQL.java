import java.awt.Color;

import java.net.*;

import java.sql.*;

import java.io.*;

import javax.swing.*;

import java.awt.event.*;

import javax.swing.table.*;

public class ServerMYSQL extends JFrame{

    ServerSocket sock;

    InputStream is;

    OutputStream os;

    String st;

    String stroka;

    JTable table1;JScrollPane sp;

    static String fio, forma;

    static int id,group,year,course;

    static String newfio, newforma;

    static int newid,newgroup,newyear,newcourse;

    Statement sq;

    DefaultTableModel DTM;

    String vibor_naim;

    Connection db;

    String colheads[]={"id", "fio", "group","course","year","forma"};

    static Object dataConditer[][];

    byte bytemessage[] = new byte[10000];

    public static final String URL = "jdbc:mysql://localhost:3306/dogovor?autoReconnect=true&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";

    public static final String USER = "root";

    public static final String PASSWORD = "root";

    Connection connection;

    public ServerMYSQL(String Title) throws SQLException, IOException{

        super(Title);

        setLayout(null);

        DTM=new DefaultTableModel(dataConditer,colheads);

        table1=new JTable(DTM);

        table1.setBackground(Color.getHSBColor(159, 216, 234));

        JScrollPane sp=new JScrollPane(table1,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        sp.setBounds(50,5,350,250);

        add(sp);

        this.setSize(500,450);

        this.setVisible(true);


        try{

            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            if (connection!=null){

                System.out.println("Соединение установлено");

            }}

        catch(Exception e){}

        StringBuffer x = new StringBuffer();

        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM dogovor");

        ResultSet Rs = pstm.executeQuery();

        while(Rs.next()){

            id=Rs.getInt(1);

            fio=Rs.getString(2);

            group=Rs.getInt(3);

            course=Rs.getInt(4);

            year=Rs.getInt(5);

            forma=Rs.getString(6);

            Object addingData[]= {id,fio,group,course,year,forma};

            DTM.addRow(addingData);

            st = (id+ "/" + fio +"/" +group+"/"+ course +"/"+year+"/"+forma+"/");

            x.append(st);

        }

        stroka=x.toString();

        sock=new ServerSocket(1024);

        while(true)

        {

            Socket client=sock.accept();

            System.out.println("fs");

            is=client.getInputStream();

            os=client.getOutputStream();

            bytemessage=stroka.getBytes();

            os.write(bytemessage);

            boolean flag = true;

            while(flag==true)

            {

                byte readmessage[] = new byte[10000];

                is.read(readmessage);

                String tempString=new String(readmessage);

                if(tempString.trim().compareTo("Delete")==0) {

                    String SQL = "DELETE FROM student WHERE id = ? ";

                    PreparedStatement pstmt = null;

                    int t=2;

                    pstmt = connection.prepareStatement(SQL);

                    pstmt.setInt(1, t);

                    pstmt.executeUpdate();

                } else if(tempString.trim().compareTo("Edit")==0){

                    String SQL = ("update student set group ='754893' where fio='fjs';");

                    PreparedStatement ps = null;

                    ps = connection.prepareStatement(SQL);

                    ps.executeUpdate();

                }

                else

                {

                    stroka= stroka+tempString;

                    String arrStr[] = tempString.split("/");

                    newid=Integer.parseInt(arrStr[0]);

                    newfio=arrStr[1];

                    newgroup=Integer.parseInt(arrStr[2]);

                    newcourse=Integer.parseInt(arrStr[3]);

                    newyear=Integer.parseInt(arrStr[4]);

                    newforma=arrStr[5];

                    Object addingData[]={newid,newfio,newgroup,newgroup,newcourse,newyear,newforma};

                    DTM.addRow(addingData);

                    try (PreparedStatement pstmt = connection.prepareStatement(

                            "INSERT into student(id, fio, group, course, year,forma) "

                                    + "VALUES(?, ?, ?, ?)")) {

                        pstmt.setInt(1, newid);

                        pstmt.setString(2, newfio);

                        pstmt.setInt(3, newgroup);

                        pstmt.setInt(4, newcourse);

                        pstmt.setInt(5, newyear);

                        pstmt.setString(6, newforma);

                        pstmt.executeUpdate();

                    }}

            }

        }}

    public static void main(String args[]) throws SQLException, IOException{

        ServerMYSQL classObj=new ServerMYSQL("Server");

    }}