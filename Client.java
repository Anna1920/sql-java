import java.io.*;

import java.net.InetAddress;

import java.net.Socket;

import java.net.UnknownHostException;

import java.util.logging.Level;

import java.util.logging.Logger;

import javafx.application.Application;

import javafx.scene.Scene;

import javafx.scene.control.*;

import javafx.scene.layout.GridPane;

import javafx.stage.Stage;

import javax.swing.JTable;

import javax.swing.table.DefaultTableModel;

public class Client extends Application {

    Socket sock = null;

    InputStream is = null;

    OutputStream os = null;

    JTable table1;

    DefaultTableModel DTM;

    static String fio, forma;

    static int id,group,year,course;

    byte bytemessage[] = new byte[10000];

    String message;

    byte clientMessage[]=new byte[5000];

    byte bytemessage2[] = new byte[100];

    @Override

    public void start(Stage primaryStage) {

        primaryStage.setTitle("Client");

        GridPane root = new GridPane();

        root.setHgap(10);

        root.setVgap(10);

        Label l1= new Label("ID");

        Label l2= new Label("FIO");

        Label l3= new Label("GROUP");

        Label l4= new Label("COURSE");

        Label l5= new Label("YEAR");

        Label l6= new Label("FORMA");

        Button btn1= new Button ("Отправить");

        Button btn2=new Button("Удалить");

        Button btn3=new Button("Редактировать");

        TextField tf2 = new TextField();

        TextField tf3=new TextField();

        TextField tf4=new TextField();

        TextField tf5=new TextField();

        TextField tf6=new TextField();

        TextField tf7=new TextField();

        root.add(btn2,3,15);

        root.add(btn3,6,15);

        root.add(tf6,6,20);

        root.add(tf7,7,20);

        root.add(tf4,4,20);

        root.add(tf5,5,20);

        root.add(btn1, 5,22);

        root.add(tf2,2,20);

        root.add(tf3,3,20);

        root.add(l1,2,18);

        root.add(l2,3,18);

        root.add(l3,4,18);

        root.add(l4,5,18);

        root.add(l5,6,18);

        root.add(l6,7,18);

        try {

            sock=new Socket(InetAddress.getByName("localhost"),1024);

            is=sock.getInputStream();

            os=sock.getOutputStream();

            is.read(clientMessage);

        } catch (UnknownHostException ex) {

        } catch (IOException ex) {

        }

        btn1.setOnAction(e -> {

            try {

                id=Integer.parseInt(tf2.getText());

                fio=tf3.getText();

                group=Integer.parseInt(tf4.getText());

                course=Integer.parseInt(tf5.getText());

                year=Integer.parseInt(tf6.getText());

                forma=tf7.getText();

                message = (id+ "/" + fio +"/" +group+"/"+ course+ "/"+year+"/"+forma+"/");

                bytemessage=message.getBytes();

                os.write(bytemessage);

            } catch (Exception ex) {

                ex.printStackTrace();

            }

        });

        btn2.setOnAction(e -> {

            try{

                String h ="Delete";

                bytemessage2=h.getBytes();

                os.write(bytemessage2);

            }

            catch(Exception fe){}

        });

        btn3.setOnAction(e -> {

            try{

                String h ="Edit";

                bytemessage2=h.getBytes();

                os.write(bytemessage2);

            }

            catch(Exception fe){}

        });

        final Scene scene = new Scene(root, 800, 600);

        primaryStage.setScene(scene);

        primaryStage.show();

    }

    public static void main(String[] args) {

        launch(args);

    }}