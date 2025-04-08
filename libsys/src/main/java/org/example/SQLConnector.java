package org.example;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.sql.*;

public class SQLConnector {
    public static Stage MainStage;
    public static Stage For_register;
    public static Stage For_sys;
    public static Stage Book_borrow;
    public static Stage Book_query;
    public static Stage Book_register;
    public static Stage Lib_MA;
    public static ChoiceBox<String> For_register_types;
    public static String Logintype = "";
    public static String logdate = "";
    public static int user_id;
    private static final String URL = "jdbc:mysql://localhost:3306/userdata";
    private static final String USER = "root";
    private static final String PASSWORD = "1532789460h";
    public static List<String> books = new ArrayList<>();
    public static int limit = 3;

    public static Connection Connect_sql() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("SQL Connection test success!");
            return connection;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("SQL Connection test failed!");

        } catch (SQLException e) {
            e.printStackTrace();

        }
        System.out.println("SQL Connection test failed!");
        return connection;
    }

    public static void update_infor(int id,String type){
        Connection c = Connect_sql();
        String update1 = "update userdata.Users set logintime = \"" + logdate + "\" where userid = " + id +";";
        String update2 = "update userdata.Users set usertype = \"" + type + "\" where userid = " + id +";";
        try {
            PreparedStatement preparedStatement = c.prepareStatement(update1);
            int rows = preparedStatement.executeUpdate();
            System.out.println("Update 1 successfully - " + rows);

            PreparedStatement preparedStatements = c.prepareStatement(update2);
            rows = preparedStatements.executeUpdate();
            System.out.println("Update 2 successfully - " + rows);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Update failed");
        }

    }

    public static boolean verify_user_infor(String user_name,String pass_word,String root_name){
        Connection connection = Connect_sql();

        String search = "select userid from userdata.Users where username = \"" + user_name + "\" and password = \"" + pass_word + "\" and rootname = \"" + root_name + "\";";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(search);
            ResultSet set = preparedStatement.executeQuery();
            set.next();
            user_id = set.getInt("userid");

            if(user_id >= 0){
                System.out.println("Pass the verification");
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error!");
            return false;
        }
        return false;


    }

    public static String get_localtime(){
        LocalDateTime now = LocalDateTime.now();
        String time = String.valueOf(now.getMonth().getValue()) + "/" + String.valueOf(now.getDayOfMonth()) + "/" + String.valueOf(now.getYear());
        return time;
    }

    public static String get_rootname(){
        Random random = new Random();
        String s = "";
        for (int i = 0; i < 4; i++) {
            int t = random.nextInt(0,9);
            s += String.valueOf(t);
        }
        return s;
    }

    public static void set_register(String re_username,String re_password,String type){
        Connection c = Connect_sql();
        String insert = "insert into userdata.Users(username, password, logintime, usertype, rootname) values(\""
                + re_username + "\",\"" + re_password + "\",\"" + get_localtime() + "\",\"" + type + "\",\"" + get_rootname() + "\");";

        System.out.println(insert);
        try {
            PreparedStatement preparedStatement = c.prepareStatement(insert);
            int rows = preparedStatement.executeUpdate();
            System.out.println("Update 1 successfully - " + rows);


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Update failed");
        }
    }

    public static void get_book_infor(){
        Connection c = Connect_sql();
        if(!(books.isEmpty())){
            books.clear();
        }
        String get_infor = "SELECT booktype,bookname,bookanthur,ifborrowed from userdata.Books;";
        try {
            PreparedStatement preparedStatement = c.prepareStatement(get_infor);
            ResultSet set = preparedStatement.executeQuery();
            while (set.next()){
                books.add(set.getString("booktype"));
                books.add(set.getString("bookname"));
                books.add(set.getString("bookanthur"));
                if(set.getInt("ifborrowed") == 0){
                    books.add("Vacant");
                }else{
                    books.add("Possessed");
                }


            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static String get_bookintro(String bookname){
        Connection c = Connect_sql();
        String get = "select bookintro from userdata.Books where bookname = \'" + bookname + "\';";
        String Return = "";
        try{
            PreparedStatement preparedStatement = c.prepareStatement(get);
            ResultSet set = preparedStatement.executeQuery();
            while (set.next()){
                Return = set.getString("bookintro");
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if(Return.length() != 0){
            return Return;
        }
        return "NULL";
    }

    public static void Update_bookstatus(int status,String bookname){
        Connection c = Connect_sql();
        String set_borrowed = "";
        if(status == 0){
            set_borrowed = "update userdata.Books set ifborrowed = 1,borrowedtime = borrowedtime + 1 where bookname = \'" + bookname + "\';";
        }else{
            String time = get_localtime();
            set_borrowed = "update userdata.Books set ifborrowed = 0,returntime = \'" + time + "\' where bookname = \'" + bookname + "\';";
        }
        System.out.println(set_borrowed);
        try {
            PreparedStatement preparedStatement = c.prepareStatement(set_borrowed);
            int rows = preparedStatement.executeUpdate();
            System.out.println("Update book status! -- "+ rows);
        } catch (SQLException e) {
            System.out.println("Update failed");
            throw new RuntimeException(e);

        }

    }

    public static String get_bookdate(String bookname){
        Connection c = Connect_sql();
        String get = "select bookdate from userdata.Books where bookname = \'" + bookname + "\';";
        try {
            PreparedStatement preparedStatement = c.prepareStatement(get);
            ResultSet set = preparedStatement.executeQuery();
            while(set.next()){
                return set.getString("bookdate");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return "NULL";
    }

    public static String get_booksimageaddress(String bookname){
        Connection c = Connect_sql();
        String get = "select bookimg from userdata.Books where bookname = \'" + bookname + "\';";
        try {
            PreparedStatement preparedStatement = c.prepareStatement(get);
            ResultSet set = preparedStatement.executeQuery();
            while(set.next()){
                return set.getString("bookimg");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return "NULL";
    }

    public static List<String> book_query(String hint,int search_mode){
        Connection c = Connect_sql();
        String statement = "";
        List<String> n = new ArrayList<>();
        if(search_mode == 0){
            statement = "select booktype,bookname,bookanthur,bookdate,booksid,borrowedtime,ifborrowed from userdata.Books where bookname = \'" + hint + "\' or bookanthur = \'" + hint + "\';";
        }else{
            statement = "select booktype,bookname,bookanthur,bookdate,booksid,borrowedtime,ifborrowed from userdata.Books where bookname like \'%" + hint + "%\' or bookanthur like \'%" + hint + "%\';";
        }
        try {
            PreparedStatement preparedStatement = c.prepareStatement(statement);
            ResultSet set = preparedStatement.executeQuery();
            while(set.next()){
                n.add("Book type:" + set.getString("booktype"));
                n.add("Book name:" + set.getString("bookname"));
                n.add("Anthur:" + set.getString("bookanthur"));
                n.add("Publish date:" + set.getString("bookdate"));
                n.add("Book id: " + String.valueOf(set.getInt("booksid")));
                n.add("Totally borrowed times:" + String.valueOf(set.getInt("borrowedtime")));
                if(set.getInt("ifborrowed") == 0){
                    n.add("Status: Vacant");
                }else{
                    n.add("Status: Possessed");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        if(n.size() == 0){
            return null;
        }
        return n;
    }

    public static void Update_books(String newbookname,String newbooktype,String newbookdate,String newbookaddress,String newbookintro,String newbookanthur){
        Connection c = Connect_sql();
        String update = "insert into userdata.Books(bookname,bookanthur,booktype,bookdate,ifborrowed,bookintro,bookimg,borrowedtime,returntime) values(\"" + newbookname + "\",\"" + newbookanthur + "\",\"" + newbooktype +"\",\"" + newbookdate + "\",0,\"" + newbookintro + "\",\"" + newbookaddress + "\",0,\"NULL\");";
        System.out.println(update);
        try {
            PreparedStatement preparedStatement = c.prepareStatement(update);
            int rows = preparedStatement.executeUpdate();
            System.out.println("Update books successfully - " + rows);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Update books failed");
        }
    }

    public static String[][] get_topbooks(){
        Connection c = Connect_sql();
        String[][] tmp = new String[limit][3];
        String get = "select bookname,borrowedtime,booktype from userdata.Books order by borrowedtime desc limit " + String.valueOf(limit) + ";";
        try {
            PreparedStatement preparedStatement = c.prepareStatement(get);
            ResultSet set = preparedStatement.executeQuery();
            int i = 0;
            int j = 0;
            while(set.next()){
                tmp[i][j] = set.getString("bookname");
                j++;
                tmp[i][j] = String.valueOf(set.getInt("borrowedtime"));
                j++;
                tmp[i][j] = set.getString("booktype");
                i++;
                j = 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return tmp;


    }

    public static List<String> get_allbooks(){
        List<String> tmp = new ArrayList<>();
        Connection c = Connect_sql();
        String get = "select booksid,bookname,bookanthur,bookdate,ifborrowed,borrowedtime,booktype from userdata.Books";
        try {
            PreparedStatement preparedStatement = c.prepareStatement(get);
            ResultSet set = preparedStatement.executeQuery();
            int i = 0;
            while(set.next()){
                tmp.add(String.valueOf(set.getInt("booksid")));
                tmp.add(set.getString("bookname"));
                tmp.add(set.getString("bookanthur"));
                tmp.add(set.getString("bookdate"));
                if(set.getInt("ifborrowed") == 0){
                    tmp.add("Vacant");
                }else{
                    tmp.add("Possessed");
                }
                tmp.add(String.valueOf(set.getInt("borrowedtime")));
                tmp.add(set.getString("booktype"));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return tmp;
    }

    public static void Delete_books(int id){
        Connection c = Connect_sql();
        String del = "delete from userdata.Books where booksid = " + id + ";";
        try {
            PreparedStatement preparedStatement = c.prepareStatement(del);
            int rows = preparedStatement.executeUpdate();
            System.out.println("Delete successfully - " + rows);

        }catch (SQLException e){
            System.out.println("Delete failed");
            throw new RuntimeException(e);
        }

    }
    public static void main(String[] args){
        //verify_user_infor("123","123","123");
        //update_infor(1,);
        //Connect_sql();
        //get_book_infor();
        //for (String s:books){
            //System.out.println(s);
        //}
    }
}