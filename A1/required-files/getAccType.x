const ID_NUM_SIZE = 5;
const NO_ACC = 0;

typedef string  acc_id_num<ID_NUM_SIZE>;
struct BALANCE {
        int oldBalance;
        int newBalance;
        acc_id_num account;
};

struct HISTORY {
    acc_id_num receiver;
    int transfer_amount;
    struct HISTORY *next;
};

typedef BALANCE TRANS[2];

program BANK_ACCOUNT_PROG {
	version ACC_VERS_1 {
                char   GET_ACC_TYPE(acc_id_num) = 1; //1 is the number assigned to this function
                int  GET_BALANCE(acc_id_num) = 2 ;
                BALANCE  SET_BALANCE(acc_id_num,int) =3;
                TRANS TRANSACTION(acc_id_num,acc_id_num,int)=4;               

        }=1; //1 is the number assigned to this version
}=9999; //9999 is the number assigned to this program

