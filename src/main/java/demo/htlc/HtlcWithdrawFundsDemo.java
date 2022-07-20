package demo.htlc;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.quorum.Quorum;
import org.web3j.tx.gas.StaticGasProvider;
import utils.Environment;
import utils.Util;

import java.math.BigInteger;


/**
 * @author nature
 * @date 30/8/21 上午11:53
 * @email 924943578@qq.com
 */
public class HtlcWithdrawFundsDemo {

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
        String byAddress = "0xD8227ed611b855D74D1D0F60Da995956306c5577";//B



        Credentials aCredentials = Credentials.create("2d5829a087b9c70d35965ba6357d2269692b6d070df1b941571d9fd1b8b841c4");//B
        Credentials bCredentials = Credentials.create("f279649ae6910401d77acb4e4e204244f93410429a0af02967fc110f4471f6ae");//B


        HashedTimelockERC20 aHtlcContract =  HashedTimelockERC20.load(
                contractAddress, web3j, aCredentials, new StaticGasProvider(GAS_PRICE, GAS_LIMIT));

        HashedTimelockERC20 bHtlcContract =  HashedTimelockERC20.load(
                contractAddress, web3j, bCredentials, new StaticGasProvider(GAS_PRICE, GAS_LIMIT));


        //(String _receiver, byte[] _hashlock, BigInteger _timelock, String _tokenContract, BigInteger _amount)
        byte[] bytes = Util.hexToByteArray("0x79656598bed120efef00be38c5e680ee4196e70dd4680f5bba06f00c5c306149");


        //b -> a
//        TransactionReceipt transactionReceipt = bHtlcContract.depositFunds(aAddress, bytes, BigInteger.valueOf(1000L),
//                "0x69b726d0cec2c6d9026473b3e7ce91e71b58e70e",new BigInteger("1000000")).send();
//        System.out.println(transactionReceipt);

//
//        List<HashedTimelockERC20.DepositFundsEventResponse> aDepositFundsEvents = aHtlcContract.getDepositFundsEvents(transactionReceipt);
//        System.out.println(aDepositFundsEvents);
//
//        List<HashedTimelockERC20.DepositFundsEventResponse> bDepositFundsEvents = bHtlcContract.getDepositFundsEvents(transactionReceipt);
//        System.out.println(bDepositFundsEvents);

        byte[] passwordHashBytes = Util.hexToByteArray("0x09a046c892c2332a7cb4fb00ee719d085e278dc6367576db7f8433fe8159adf1");
        byte[] passwordBytes = Util.hexToByteArray("0xc6bef8a769e1186177fd147d2ea7af5fa419b7cdd06415ed8d6cf8cfa1dcad3a");
        TransactionReceipt transactionReceipt = aHtlcContract.withdrawFunds(passwordHashBytes, passwordBytes).send();
        System.out.println(transactionReceipt);

//        List<HashedTimelockERC20.WithdrawFundsEventResponse> withdrawFundsEvents = bHtlcContract.getWithdrawFundsEvents(transactionReceipt);
//
//        System.out.println(withdrawFundsEvents);
        System.exit(0);
    }
}
