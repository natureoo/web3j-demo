package demo.htlc;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.http.HttpService;
import org.web3j.quorum.Quorum;
import org.web3j.quorum.tx.ClientTransactionManager;
import org.web3j.tx.TransactionManager;
import utils.Environment;

import java.math.BigInteger;
import java.util.Collections;


/**
 * @author nature
 * @date 30/8/21 上午11:53
 * @email 924943578@qq.com
 */
public class HtlcQueryBalanceDemo {

    //22000000000L
    public static final BigInteger GAS_PRICE = BigInteger.valueOf(22000000000L);

    //4300000L
    public static final BigInteger GAS_LIMIT = BigInteger.valueOf(7300000L);



    public static void main(String[] args) throws Exception {
        use();
    }


    public static void use() throws Exception {

        Quorum web3j = Quorum.build(new HttpService(Environment.RPC_URL));

        String contractAddress = "0x5E5A4262Bc1254729EcBa99b6Aa03257a627dd32";

        String aAddress = "0x3761940D8aDd75AC0A2f37670a62A71c905475b5";//A
        String bAddress = "0xD8227ed611b855D74D1D0F60Da995956306c5577";//B

        String tokenContract = "0x69b726d0cec2c6d9026473b3e7ce91e71b58e70e";

        Credentials aCredentials = Credentials.create("2d5829a087b9c70d35965ba6357d2269692b6d070df1b941571d9fd1b8b841c4");//B
        Credentials bCredentials = Credentials.create("f279649ae6910401d77acb4e4e204244f93410429a0af02967fc110f4471f6ae");//B

        TransactionManager transactionManager = new ClientTransactionManager(
                web3j,aAddress , Collections.emptyList());


        HashedTimelockERC20 aHtlcContract =  HashedTimelockERC20.load(
                tokenContract, web3j, transactionManager, GAS_PRICE, GAS_LIMIT);


        BigInteger aBalance = aHtlcContract.balanceOf(aAddress).send();
        BigInteger bBalance = aHtlcContract.balanceOf(bAddress).send();
        System.out.println(aBalance);
        System.out.println(bBalance);

//
//        List<HashedTimelockERC20.DepositFundsEventResponse> aDepositFundsEvents = aHtlcContract.getDepositFundsEvents(transactionReceipt);
//        System.out.println(aDepositFundsEvents);
//
//        List<HashedTimelockERC20.DepositFundsEventResponse> bDepositFundsEvents = bHtlcContract.getDepositFundsEvents(transactionReceipt);
//        System.out.println(bDepositFundsEvents);

//        byte[] passwordHashBytes = Util.hexToByteArray("0x1ce831c7f229d0499a6bc6deca990b193c5d45dd59704a5a59a19815fa4170c7");
//        byte[] passwordBytes = Util.hexToByteArray("0x88502129ca1f25eda23d0a89de0b12166dcc1c9110e04171619092c4ed8f5f34");
//        TransactionReceipt transactionReceipt = aHtlcContract.withdrawFunds(passwordHashBytes, passwordBytes).send();
//        System.out.println(transactionReceipt);

        System.exit(0);
    }
}
