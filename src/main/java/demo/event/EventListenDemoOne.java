package demo.event;

import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.datatypes.Type;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.http.HttpService;
import org.web3j.quorum.Quorum;
import org.web3j.quorum.tx.ClientTransactionManager;
import org.web3j.tx.TransactionManager;

import java.util.Collections;
import java.util.List;

import static org.web3j.tx.Contract.GAS_LIMIT;
import static org.web3j.tx.ManagedTransaction.GAS_PRICE;


/**
 * @author nature
 * @date 30/8/21 上午11:53
 * @email 924943578@qq.com
 */
public class EventListenDemoOne {


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



        // Event definition hash
        String EVENT_HASH = EventEncoder.encode(Event.DATASTORED_EVENT);

// Filter
        EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, contractAddress);

        filter.addSingleTopic(EventEncoder.encode(Event.DATASTORED_EVENT));

        web3j.ethLogFlowable(filter).subscribe(log -> {
            String eventHash = log.getTopics().get(0); // Index 0 is the event definition hash

//            if(eventHash.equals(EVENT_HASH)) { // Only MyEvent. You can also use filter.addSingleTopic(MY_EVENT_HASH)
                System.out.println(log);

            List<Type> types = FunctionReturnDecoder.decode(log.getData(), Event.DATASTORED_EVENT.getParameters());
            System.out.println(types);
//            }

        });




        Thread.currentThread().join();
    }
}
