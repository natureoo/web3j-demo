package demo.event;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.1.
 */
@SuppressWarnings("rawtypes")
public class Event extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b5060cf8061001f6000396000f3fe6080604052348015600f57600080fd5b506004361060285760003560e01c80634abe305214602d575b600080fd5b60336035565b005b7f4d29b7f0ee29fd19bc5c40e7a45531c8498ce1293f4c3f7f42518934235f36ed604051608f9060408082526001908201819052606160f81b6060830152608060208301819052820152603160f91b60a082015260c00190565b60405180910390a156fea2646970667358221220a384c68791afd5f52ffed7c494255bd200b49f5f3b0fe9ff79146fe4a9af21ec64736f6c63430008060033";

    public static final String FUNC_STOREDATA = "storeData";

    public static final org.web3j.abi.datatypes.Event DATASTORED_EVENT = new org.web3j.abi.datatypes.Event("DataStored", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
    ;

    @Deprecated
    protected Event(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Event(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Event(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Event(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<DataStoredEventResponse> getDataStoredEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(DATASTORED_EVENT, transactionReceipt);
        ArrayList<DataStoredEventResponse> responses = new ArrayList<DataStoredEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            DataStoredEventResponse typedResponse = new DataStoredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.data1 = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.data2 = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<DataStoredEventResponse> dataStoredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, DataStoredEventResponse>() {
            @Override
            public DataStoredEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(DATASTORED_EVENT, log);
                DataStoredEventResponse typedResponse = new DataStoredEventResponse();
                typedResponse.log = log;
                typedResponse.data1 = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.data2 = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<DataStoredEventResponse> dataStoredEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DATASTORED_EVENT));
        return dataStoredEventFlowable(filter);
    }

    public RemoteCall<TransactionReceipt> storeData() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_STOREDATA, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Event load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Event(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Event load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Event(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Event load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Event(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Event load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Event(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Event> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Event.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Event> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Event.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Event> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Event.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Event> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Event.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class DataStoredEventResponse {
        public String data1;

        public String data2;

        public Log log;

    }
}
