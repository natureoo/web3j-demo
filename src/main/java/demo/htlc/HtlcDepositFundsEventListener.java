package demo.htlc;

import io.reactivex.Flowable;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.http.HttpService;
import org.web3j.quorum.Quorum;
import org.web3j.tx.gas.StaticGasProvider;
import utils.Environment;
import utils.Util;

import static org.web3j.tx.Contract.GAS_LIMIT;
import static org.web3j.tx.ManagedTransaction.GAS_PRICE;


/**
 * @author nature
 * @date 30/8/21 上午11:53
 * @email 924943578@qq.com
 */
public class HtlcDepositFundsEventListener {


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


//        EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, contractAddress);
//
//        Flowable<HashedTimelockERC20.DepositFundsEventResponse> depositFundsEventResponseFlowable = aHtlcContract.depositFundsEventFlowable(filter);
//
//        depositFundsEventResponseFlowable.subscribe(event -> {
//            System.out.println(event);
//        });

        Flowable<HashedTimelockERC20.DepositFundsEventResponse> aDepositFundsEventResponseFlowable = aHtlcContract.depositFundsEventFlowable(DefaultBlockParameterName.LATEST, DefaultBlockParameterName.LATEST);
        aDepositFundsEventResponseFlowable.subscribe(event -> {
            String passwordHash = Util.toHexString(event.hashlock);
            System.out.println("ApasswordHash: " + passwordHash);
        });

        Flowable<HashedTimelockERC20.DepositFundsEventResponse> bDepositFundsEventResponseFlowable = bHtlcContract.depositFundsEventFlowable(DefaultBlockParameterName.LATEST, DefaultBlockParameterName.LATEST);
        bDepositFundsEventResponseFlowable.subscribe(event -> {
            String passwordHash = Util.toHexString(event.hashlock);
            System.out.println("BpasswordHash: " + passwordHash);
        });

        Thread.currentThread().join();
    }
}
