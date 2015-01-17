Task 1: Process to execute the program

Generation of Stub and Skeleton
1. Copy jrpcgen.jar, oncrpc.jar to the current directory. (Instruction to generate these jar files is provided in the Assignment Instruction)
2. Execute :
   java -jar jrpcgen.jar getAccType.x

Compile:
1. Compile Client (Bank.java), Server (getAccTypeInterface.java), and other stun and Skeleton generated in Step 1:
   javac -classpath .:oncrpc.jar *.java

Execution
1. GET_BALANCE ::
   java -classpath .:oncrpc.jar Bank 127.0.0.1 GET_BALANCE acc_id

2. SET_BALANCE ::
   java -classpath .:oncrpc.jar Bank 127.0.0.1 SET_BALANCE acc_id x

3. TRANSACTION ::
   java -classpath .:oncrpc.jar Bank 127.0.0.1 TRANSACTION src_acc_id dst_acc_id x

4. GET_TRANSACTION_HISTORY ::
   java -classpath .:oncrpc.jar Bank 127.0.0.1 GET_TRANSACTION_HISTORY acc_id


Note: We have also provided error handling code at various level.

1. If input format is wrong
   For Example : java -classpath .:oncrpc.jar Bank 127.0.0.1 DUMMY 15
   Output : Invalid Operation : usage IP_ADDR GET_ACCOUNT_TYPE|GET_BALANCE|SET_BALANCE|TRANSACTION|GET_TRANSACTION_HISTORY

    java -classpath .:oncrpc.jar Bank 127.0.0.1 GET_BALANCE 15 4
    Output : Usage : IP_Addr GET_BALANCE acc_id

2. Execution
   2.1 If account is not present in acc-new.txt  
        java -classpath .:oncrpc.jar Bank 127.0.0.1 GET_BALANCE 15
        Output : No Entry for Account id :15
 
        java -classpath .:oncrpc.jar Bank 127.0.0.1 TRANSACTION 44 4 2
        Output : Error Either of the account was not available

    2.2 If Negative Amount is used
        java -classpath .:oncrpc.jar Bank 127.0.0.1 SET_BALANCE 4 -10
        Output : Invalid Amount

        java -classpath .:oncrpc.jar Bank 127.0.0.1 TRANSACTION 4 1 -10 
        Output : Invalid Amount

In case of Error no logging is done in output.txt. However output is shown on console 
