
import org.acplt.oncrpc.*;
import java.net.InetAddress;
import java.io.IOException;
import java.io.*;

public class Bank {

    static int opt;
    final static String OUTPUT_FILE_NAME = "output.txt";

    public static void main(String [] args) {

        // check the arguments
        if (args.length >5) {
	    System.out.println("usage: infoline host_name stu num ");
            System.exit(1);
        }



        //create an rpc client
        getAccTypeClient client = null;
        try {
            client = new getAccTypeClient(InetAddress.getByName(args[0]),
                                    OncRpcProtocols.ONCRPC_TCP);
        } catch ( Exception e ) {
            System.out.println("infoline: error when creating RPC client:");
            e.printStackTrace(System.out);
        }
        client.getClient().setTimeout(300*1000);

        System.out.print("Making request to server");


        String feature = args[1];

        if(feature.equals("GET_BALANCE"))
        {
            acc_id_num arg1 =new acc_id_num ();

	        arg1.value=args[2];
            try 
            {
            int res= client.GET_BALANCE_1(arg1);
            
            System.out.println("Result is:"+ res);
            
            BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME, true));
            bw.write(String.valueOf(res));
            bw.newLine();
            bw.flush();
            bw.close();
            } 
            catch ( Exception e ) {
	        System.out.println("Error contacting server");
            e.printStackTrace(System.out);
            return;
            }

        }else if (feature.equals("SET_BALANCE"))
        {
            acc_id_num arg1 =new acc_id_num ();

	        arg1.value=args[2];
            int x= Integer.parseInt(args[3]);
            try 
            {
            BALANCE res= client.SET_BALANCE_1(arg1,x);
            
            System.out.println("Result is:"+ res.account.value+ " "+ res.oldBalance+ " "+res.newBalance);
            
            BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME, true));
            String result =  res.account.value+ " "+ res.oldBalance+ " "+res.newBalance;
            bw.write(result);
            bw.newLine();
            bw.flush();
            bw.close();
            } 
            catch ( Exception e ) {
	        System.out.println("Error contacting server");
            e.printStackTrace(System.out);
            return;
            }

        }else if (feature.equals("TRANSACTION"))
        {
            acc_id_num arg1 =new acc_id_num ();
            acc_id_num arg2 =new acc_id_num ();
            
	        arg1.value=args[2];
            arg2.value=args[3];
            int x= Integer.parseInt(args[4]);
            System.out.println(arg1.value+ " "+ arg2.value+ " "+x);
            try 
            {
            TRANS res= client.TRANSACTION_1(arg1,arg2,x);
            
            BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME, true));
            if(res!=null)
            {
                System.out.println("Result is:"+ res.value[0].account.value+ " "+ res.value[0].oldBalance+ " "+res.value[0].newBalance);
                System.out.println("Result is:"+ res.value[1].account.value+ " "+ res.value[1].oldBalance+ " "+res.value[1].newBalance);
                bw.write(res.value[0].account.value+ " "+ res.value[0].oldBalance+ " "+res.value[0].newBalance );
                bw.newLine();
                bw.write(res.value[1].account.value+ " "+ res.value[1].oldBalance+ " "+res.value[1].newBalance);
                bw.newLine();
                bw.flush();
                bw.close();
            }
            
            } 
            catch ( Exception e ) {
	        System.out.println("Error contacting server");
            e.printStackTrace(System.out);
            return;
            }


        }else if (feature.equals("GET_TRANSACTION_HISTORY"))
        {

        }
    
        try 
            {
            client.close();
        } catch ( Exception e ) {
            System.out.println("infoline: error when closing client:");
            e.printStackTrace(System.out);
        }
        client = null;


	// make request object
	   
}
}
