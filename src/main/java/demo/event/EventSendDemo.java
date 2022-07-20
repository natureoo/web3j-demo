package demo.event;

import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthMining;
import org.web3j.protocol.http.HttpService;
import org.web3j.quorum.Quorum;
import org.web3j.quorum.tx.ClientTransactionManager;
import org.web3j.tx.TransactionManager;

import java.util.Collections;

import static org.web3j.tx.Contract.GAS_LIMIT;
import static org.web3j.tx.ManagedTransaction.GAS_PRICE;


/**
 * @author nature
 * @date 30/8/21 上午11:53
 * @email 924943578@qq.com
 */
public class EventSendDemo {


    public static void main(String[] args) throws Exception {
        use();
    }

    private static String URL = "Http://localhost:8546";//GETH

    public static void use() throws Exception {

        Quorum web3j = Quorum.build(new HttpService(URL));
        String contractAddress = "0xa677b9f33a077d642eb2e71ea13d9008bb3437cb";
        String myAddress = "0x87f34d01eb6dd0e96794ead03f86cd0a8b610c11";
        TransactionManager transactionManager = new ClientTransactionManager(
                web3j,myAddress , Collections.emptyList());


        Event eventContract =  Event.load(
                contractAddress, web3j, transactionManager, GAS_PRICE, GAS_LIMIT);

        eventContract.storeData().sendAsync();

        Request<?, EthMining> ethMiningRequest = web3j.ethMining();

        System.exit(0);
    }
}
