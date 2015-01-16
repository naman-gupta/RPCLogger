
import org.acplt.oncrpc.*;
import java.net.InetAddress;
import java.io.IOException;
import java.io.*;

public class Bank {

    static int opt;
    final static String OUTPUT_FILE_NAME = "output.txt"; //Stores results for Client Requests/

    public static void main(String [] args) {

        // Checking the arguments
        if (args.length <2) {
	        System.out.println("usage: infoline host_name stu num ");
            System.exit(1);
        }else if(!args[1].equals("GET_BALANCE") && !args[1].equals("SET_BALANCE") && !args[1].equals("TRANSACTION") && !args[1].equals("GET_TRANSACTION_HISTORY"))
        {
            System.out.println("Invalid Operation : usage IP_ADDR GET_BALANCE|SET_BALANCE|TRANSACTION|GET_TRANSACTION_HISTORY");
            System.exit(1);
        }
        else if(args[1].equals("GET_BALANCE") && args.length!=3)
        {
            System.out.println("Usage : IP_Addr GET_BALANCE acc_id");
            System.exit(1);
        }
        else if(args[1].equals("SET_BALANCE") && args.length!=4)
        {
            System.out.println("Usage : IP_Addr SET_BALANCE acc_id x");
            System.exit(1);
        } 
        else if(args[1].equals("TRANSACTION") && args.length!=5)
        {
            System.out.println("Usage : IP_Addr TRANSACTION src_acc_id dst_acc_id x");
            System.exit(1);
        } 
        else if(args[1].equals("GET_TRANSACTION_HISTORY") && args.length!=3)
        {
            System.out.println("Usage : IP_Addr GET_TRANSACTION_HISTORY acc_id");
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

        System.out.println("Making request to server");


        String operation = args[1];

        if(operation.equals("GET_BALANCE"))
        {
            acc_id_num arg1 =new acc_id_num ();

	        arg1.value=args[2];
            try 
            {
            
                int res= client.GET_BALANCE_1(arg1);
                if(res!=1)
                {
                    BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME, true));
                    System.out.println("Result is:"+ arg1.value+ " "+ res);
                    bw.write(arg1.value+ " " + String.valueOf(res));
                    bw.newLine();
                    bw.flush();
                    bw.close();
                }
                else 
                {
                    System.out.println("No Entry for Account id :"+ arg1.value);
                }
            } 
            catch ( Exception e ) 
            {
	            System.out.println("Error contacting server");
                e.printStackTrace(System.out);
                return;
            }

        }
        else if (operation.equals("SET_BALANCE")) // OPERATAION  2 :add 'x' to the existing balance associated with acc_id
        {
            acc_id_num arg1 =new acc_id_num ();
	        arg1.value=args[2];
            int x= Integer.parseInt(args[3]);
            
            try 
            {
                BALANCE res= client.SET_BALANCE_1(arg1,x);
                if(null!=res && res.oldBalance >=0)
                {
            
                    System.out.println("Result is:"+ res.account.value+ " "+ res.oldBalance+ " "+res.newBalance);
                    BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME, true));
                    String result =  res.account.value+ " "+ res.oldBalance+ " "+res.newBalance;
                    bw.write(result);
                    bw.newLine();
                    bw.flush();
                    bw.close();
                }else
                {
                    System.out.println("No Entry for Account Id :" + arg1.value);
                }
            } 
            catch ( Exception e ) 
            {
	            System.out.println("Error contacting server");
                e.printStackTrace(System.out);
                return;
            }

        }
        else if (operation.equals("TRANSACTION"))//OPERATION 3 :Transfer 'x' from src_acc_id to dst_acc_id.
        {
            acc_id_num arg1 =new acc_id_num ();
            acc_id_num arg2 =new acc_id_num ();
            
	        arg1.value=args[2];
            arg2.value=args[3];
            int x= Integer.parseInt(args[4]);
            try 
            {
            TRANS res= client.TRANSACTION_1(arg1,arg2,x);
            
            BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME, true));
            if(res!=null)
            {
                System.out.println("Result is:"+ res.value[0].account.value+ " "+ res.value[0].oldBalance+ " "+res.value[1].account.value+ " "+res.value[1].oldBalance);
                System.out.println("Result is:"+ res.value[0].account.value+ " "+ res.value[0].newBalance+ " "+res.value[1].account.value+ " "+res.value[1].newBalance);
                
                bw.write(res.value[0].account.value+ " "+ res.value[0].oldBalance+ " "+res.value[1].account.value+ " "+res.value[1].oldBalance);
                bw.newLine();
                bw.write(res.value[0].account.value+ " "+ res.value[0].newBalance+ " "+res.value[1].account.value+ " "+res.value[1].newBalance);
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


        }else if (operation.equals("GET_TRANSACTION_HISTORY"))
        {
            acc_id_num arg1 =new acc_id_num ();

	        arg1.value=args[2];
            try 
            {
                HISTORY res= client.GET_TRANSACTION_1(arg1);
            
                BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME, true));
                if(res.transfer_amount>=0)
                {
  
                  while(res!=null)
                    {
                        System.out.println(res.receiver.value + " " +res.transfer_amount);
                        String result =  res.receiver.value+ " "+ res.transfer_amount;
                        bw.write(result);
                        bw.newLine();
                        bw.flush();
                        res=res.next;
                    }
                }
            
           
            bw.close();
            } 
            catch ( Exception e ) {
	        System.out.println("Error contacting server");
            e.printStackTrace(System.out);
            return;
            }


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
