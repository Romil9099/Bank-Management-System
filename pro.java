import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Scanner;

class run {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        BankManagement bank = new BankManagement();
        bank.defaultadd();
        System.out.println(" ENTER YOUR CHOICE : ");
        System.out.println();
        while (true) {
            System.out.println();
            System.out.println("1 : DEPOSIT AMOUNT");
            System.out.println("2 : WITHDRAW AMOUNT");
            System.out.println("3 : TRANSFER AMOUNT");
            System.out.println("4 : SHOW ALL USER DETAIL");
            System.out.println("5 : SEARCH ACCOUNT");
            System.out.println("6 : EXIT");
            System.out.println();
            System.out.println("----------------- ");
            System.out.println();
            System.out.print("-->  ENTER CHOICE : R");
            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {
                case 1:
                    bank.DepositMoney();
                    break;
                case 2:
                    bank.WITHDRAWMoney();
                    break;
                case 3:
                    bank.TRANSFER();
                    break;
                case 4:
                    bank.print();
                    break;
                case 5:
                    System.out.println();
                    System.out.println("ENTER ACCOUNT NUMBER FOR UPDATE : ");
                    String AccNo = sc.nextLine();
                    bank.ONLYUSERPRINT(AccNo);
                    break;
                
                case 6:
                    System.exit(0);
            }
        }

    }
}

class Bank {
    String name;
    double balance;
    String accno;
    String phoneno;
    int pin;

    public Bank(int pin, String name, double balance, String accno, String phoneno) {
        this.pin = pin;
        this.name = name;
        this.balance = balance;
        this.accno = accno;
        this.phoneno = phoneno;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    @Override
    public String toString() {
        return "Name =" + name + "\n Balance =" + balance + "\n Account Number =" + accno + "\n phone no =" + phoneno;
    }

}

class BankManagement {
    Scanner sc = new Scanner(System.in);
    HashMap<String, Bank> mybank;
    int i = 1;

    BankManagement() {

        mybank = new HashMap<>();

    }

    void defaultadd() throws Exception {
        String Driver = "com.mysql.cj.jdbc.Driver";
        Class.forName(Driver);
        // System.out.println("Driver loaded");

        String url = "jdbc:mysql://localhost:3306/lj";
        String user = "root";
        String pass = "";
        Connection con = DriverManager.getConnection(url, user, pass);
        Statement st = con.createStatement();

        String sql = "INSERT INTO BankDataBase values(1,'112201','MEET',5000.0,9328936902,1107)";
        int x = st.executeUpdate(sql);
        Bank b = new Bank(1107, "MEET", 5000.0, "112201", "9328936902");
        mybank.put("112201", b);
        String sql1 = "INSERT INTO BankDataBase values(2,'112202','MIT',9000.0,9328936901,1108)";
        int a = st.executeUpdate(sql1);
        Bank b2 = new Bank(1108, "MIT", 9000.0, "112202", "9328936901");
        mybank.put("112202", b2);
        String sql2 = "INSERT INTO BankDataBase values(3,'112203','MAYUR',5000.0,9328936903,1109)";
        int Z = st.executeUpdate(sql2);
        Bank b3 = new Bank(1109, "MAYUR", 5000.0, "112203", "9328936903");
        mybank.put("112203", b3);
        String sql3 = "INSERT INTO BankDataBase values(4,'112204','PRIT',10000.0,9328936904,1110)";
        int xa = st.executeUpdate(sql3);
        Bank b4 = new Bank(1110, "PRIT", 10000.0, "112204", "9328936904");
        mybank.put("112204", b4);
        String sql4 = "INSERT INTO BankDataBase values(5,'112205','PRINCE',15000.0,9328936802,1111)";
        int xb = st.executeUpdate(sql4);
        Bank b5 = new Bank(1111, "PRINCE", 15000.0, "112205", "9328936802");
        mybank.put("112205", b5);
        String sql5 = "INSERT INTO BankDataBase values(6,'112206','RAHUL',50000.0,9328936992,1112)";
        int xc = st.executeUpdate(sql5);
        

    }
    void print() throws Exception {
        String Driver = "com.mysql.cj.jdbc.Driver";
        Class.forName(Driver);
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lj", "root", "");
        Statement st = con.createStatement();
        String sql = "select * from BankDataBase";
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            System.out.println();
            System.out.println("ACCOUNT NO :- " + rs.getString(2));
            System.out.println("NAME :- " + rs.getString(3));
            System.out.println("BALANCE :- " + rs.getDouble(4));
            System.out.println("PHONE NO :- " + rs.getLong(5));
            System.out.println();
        }
    }
    void TRANSFER() throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.println("ENTER ACCOUNT NUMBER : ");
        String accno = sc.nextLine();
        System.out.println();
        if (mybank.containsKey(accno)) {
            Bank b2 = mybank.get(accno);
            System.out.print("ENTER SECURITY PIN : ");
            int pin = sc.nextInt();
            sc.nextLine();
            if (b2.pin == pin) {

                System.out.println("--- ACCOUNT SUCCESSFULLY VERIFIED ");
                System.out.println();
                System.out.print("-> ENTER ACCOUNT NUMBER TO GET TRANSFERED AMOUNT :");
                String accno1 = sc.nextLine();
                if (mybank.containsKey(accno1)) {
                    Bank b1 = mybank.get(accno1);
                    System.out.println("ENTER SECURITY PIN : ");
                    int pin2 = sc.nextInt();
                    sc.nextLine();
                    if (b1.pin == pin2) {

                        System.out.println("--- SECOND ACCOUNT SUCCESSFULLY VERIFIED ");
                        System.out.println();
                        System.out.print("ENTER AMOUNT TO TRANSFER  :");
                        double amount = sc.nextDouble();

                        if (b2.balance > amount) {
                            b2.balance = b2.balance - amount;
                           
                            b1.balance = b1.balance + amount;
                            
                            String sql = "UPDATE BankDataBase SET Balance = " + b2.balance + "where AccNo = '" + accno
                                    + "'";
                            String sql2 = "UPDATE BankDataBase SET Balance = " + b1.balance + "where AccNo = '" + accno1
                                    + "'";

                            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lj", "root", "");
                            Statement st = con.createStatement();
                            int r = st.executeUpdate(sql);
                            int s = st.executeUpdate(sql2);
                        } else {
                            System.out.println("--- USER HAVE INEFFICIENT AMOUNT TO TRANSFER :");
                            System.out.println("THANK YOU !!!");
                        }

                    
                    } else {
                        System.out.println("--- SECURITY PIN NOT MATCHING  ");
                        System.out.println("THANK YOU !!!");
                        System.out.println("------------");
                        System.out.println();
                    }
                } else {
                    System.out.println("--- ACCOUNT NOT EXIST IN DATABASE ");
                    System.out.println("THANK YOU !!!");
                    System.out.println("------------");
                    System.out.println();
                }
            } else {
                System.out.println();
                System.out.println("--- SECURITY PIN NOT MATCHING  ");
                System.out.println("THANK YOU !!!");
                System.out.println("------------");
                System.out.println();
            }
        } else {
            System.out.println("--- ACCOUNT NOT EXIST IN DATABASE ");
            System.out.println("THANK YOU !!!");
            System.out.println("------------");
            System.out.println();
        }

    }

    void DepositMoney() throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.println("ENTER ACCOUNT NUMBER : ");
        String accno = sc.nextLine();
        System.out.println();
        if (mybank.containsKey(accno)) {
            Bank b1 = mybank.get(accno);
            System.out.println("ENTER SECURITY PIN : ");
            int pin2 = sc.nextInt();
            sc.nextLine();
            if (b1.pin == pin2) {
                System.out.println("--- ACCOUNT SUCCESSFULLY VERIFIED ");
                System.out.println();
                System.out.print("ENTER AMOUNT TO DEPOSIT  :");
                double amount = sc.nextDouble();
                Bank b = mybank.get(accno);
                b.balance = b.balance + amount;
             

                double newamount = b.balance;
                System.out.println("--- BALANCE DEPOSITED SUCCESSFULLY ");
                System.out.println("THANK YOU !!!");
                System.out.println("-------------             ");
                String sql = "UPDATE BankDataBase SET Balance = " + newamount + "where AccNo = '" + accno + "'";

                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lj", "root", "");
                Statement st = con.createStatement();
                int r = st.executeUpdate(sql);
            } else {
                System.out.println("--- SECURITY PIN NOT MATCHING  ");
                System.out.println("THANK YOU !!!");
                System.out.println("------------");
                System.out.println();
            }

        } else {
            System.out.println("--- ACCOUNT NOT EXIST IN DATABASE ");
            System.out.println("THANK YOU !!!");
            System.out.println("------------");
            System.out.println();
        }

    }

    void WITHDRAWMoney() throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.println("ENTER ACCOUNT NUMBER : ");
        String accno = sc.nextLine();
        System.out.println();
        if (mybank.containsKey(accno)) {
            Bank b2 = mybank.get(accno);
            System.out.print("ENTER SECURITY PIN : ");
            int pin = sc.nextInt();
            sc.nextLine();
            if (b2.pin == pin) {
                System.out.println("--- ACCOUNT SUCCESSFULLY VERIFIED ");
                System.out.println();
                System.out.print("ENTER AMOUNT TO WITHDRAW  :");
                double amount = sc.nextDouble();
                Bank b = mybank.get(accno);
                if (b.balance < amount) {
                    System.out.println("--- USER HAVE INEFFICIENT AMOUNT TO WITHDRAW ");
                    System.out.println("THANK YOU !!! ");
                    System.out.println("--------------------             ");
                } else {
                

                    b.balance = b.balance - amount;
                    double newamount = b.balance;
                    System.out.println("--- AMOUNT WITHDRAW SUCCESSFULLY DONE");
                    System.out.println("THANK YOU !!!");
                    System.out.println("--------------------");
                    String sql = "UPDATE BankDataBase SET Balance = " + newamount + "where AccNo = '" + accno + "'";
                    String url = "jdbc:mysql://localhost:3306/lj";
                    String user = "root";
                    String pass = "";
                    Connection con = DriverManager.getConnection(url, user, pass);
                    Statement st = con.createStatement();
                    int r = st.executeUpdate(sql);
                }
            } else {
                System.out.println("--- SECURITY PIN NOT MATCHING  ");
                System.out.println("THANK YOU !!!");
                System.out.println("------------");
                System.out.println();
            }

        } else {
            System.out.println("--- ACCOUNT NOT EXIST IN DATABASE ");
            System.out.println("THANK YOU !!!");
            System.out.println("------------");
            System.out.println();
        }
    }

    void ONLYUSERPRINT(String AccNo) throws Exception {
        if (mybank.containsKey(AccNo)) {
            Bank b2 = mybank.get(AccNo);
            System.out.print("ENTER SECURITY PIN : ");
            int pin = sc.nextInt();
            sc.nextLine();
            if (b2.pin == pin) {
                String Driver = "com.mysql.cj.jdbc.Driver";
                Class.forName(Driver);
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lj", "root", "");
                Statement st = con.createStatement();
                String sql = "select * from BankDataBase where AccNo = '" + AccNo + "'";
                ResultSet rs = st.executeQuery(sql);
                while (rs.next()) {
                    System.out.println();
                    System.out.println("ACCOUNT NO :- " + rs.getString(2));
                    System.out.println("NAME :- " + rs.getString(3));
                    System.out.println("BALANCE :- " + rs.getDouble(4));
                    System.out.println("PHONE NO :- " + rs.getLong(5));
                    System.out.println();
                }
            } else {
                System.out.println("--- SECURITY PIN NOT MATCHING  ");
                System.out.println("THANK YOU !!!");
                System.out.println("------------");
                System.out.println();
            }
        } else {
            System.out.println("--- ACCOUNT NOT EXIST IN DATABASE ");
            System.out.println("THANK YOU !!!");
            System.out.println("------------");
            System.out.println();
        }
    }

}