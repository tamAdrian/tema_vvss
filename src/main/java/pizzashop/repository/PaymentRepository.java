package pizzashop.repository;

import javafx.collections.ObservableList;
import pizzashop.model.MenuDataModel;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class PaymentRepository {
    private static PaymentRepository single_instance = null;

    private  String filename = "data/payments.txt";
    private List<Payment> paymentList;

    public PaymentRepository() {
        this.paymentList = new ArrayList<>();
        readPayments();
    }
    public PaymentRepository(String fileName) {
        this.filename=fileName;
        this.paymentList = new ArrayList<>();
        readPayments();
    }
    public PaymentRepository(String fileName,boolean ok) {
        this.filename=fileName;
        if(ok==true){
        this.paymentList = new ArrayList<>();
        readPayments();}
    }
    public static PaymentRepository getInstance() {
        if (single_instance == null)
            single_instance = new PaymentRepository();

        return single_instance;
    }

    private void readPayments(){
        try {
            ClassLoader classLoader = PaymentRepository.class.getClassLoader();
            File file = new File(classLoader.getResource(filename).getFile());
            BufferedReader br = null;
            br = new BufferedReader(new FileReader(file));
            String line = null;
            while((line=br.readLine())!=null){
                Payment payment=getPayment(line);
                paymentList.add(payment);
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Payment getPayment(String line){
        Payment item=null;
        if (line==null|| line.equals("")) return null;
        StringTokenizer st=new StringTokenizer(line, ",");
        int tableNumber= Integer.parseInt(st.nextToken());
        String type= st.nextToken();
        double amount = Double.parseDouble(st.nextToken());
        item = new Payment(tableNumber, PaymentType.valueOf(type), amount);
        return item;
    }

    public void add(Payment payment){
        paymentList.add(payment);
        writeAll();
    }

    public List<Payment> getAll(){
        return paymentList;
    }

    public void writeAll(){
        ClassLoader classLoader = PaymentRepository.class.getClassLoader();
        File file = new File(classLoader.getResource(filename).getFile());

        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file));
            for (Payment p:paymentList) {
                System.out.println(p.toString());
                bw.write(p.toString());
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
