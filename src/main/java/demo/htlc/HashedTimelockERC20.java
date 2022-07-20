package demo.htlc;


import io.reactivex.Flowable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple9;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

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
public class HashedTimelockERC20 extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50610e73806100206000396000f3fe608060405234801561001057600080fd5b5060043610610068577c0100000000000000000000000000000000000000000000000000000000600035046318492006811461006d5780637249fbb6146100a4578063e16c7d98146100c1578063fb334df714610138575b600080fd5b6100906004803603604081101561008357600080fd5b508035906020013561018c565b604080519115158252519081900360200190f35b610090600480360360208110156100ba57600080fd5b503561055d565b6100de600480360360208110156100d757600080fd5b5035610852565b60408051600160a060020a039a8b168152988a1660208a015296909816878701526060870194909452608086019290925260a0850152151560c0840152151560e08301526101008201929092529051908190036101200190f35b61017a600480360360a081101561014e57600080fd5b50600160a060020a03813581169160208101359160408201359160608101359091169060800135610900565b60408051918252519081900360200190f35b60008261019881610da1565b15156101ee576040805160e560020a62461bcd02815260206004820152601960248201527f636f6e7472616374496420646f6573206e6f7420657869737400000000000000604482015290519081900360640190fd5b8383600281604051602001808281526020019150506040516020818303038152906040526040518082805190602001908083835b602083106102415780518252601f199092019160209182019101610222565b51815160209384036101000a60001901801990921691161790526040519190930194509192505080830381855afa158015610280573d6000803e3d6000fd5b5050506040513d602081101561029557600080fd5b5051600083815260208190526040902060040154146102fe576040805160e560020a62461bcd02815260206004820152601c60248201527f686173686c6f636b206861736820646f6573206e6f74206d6174636800000000604482015290519081900360640190fd5b6000868152602081905260409020600101548690600160a060020a03163314610371576040805160e560020a62461bcd02815260206004820152601a60248201527f776974686472617761626c653a206e6f74207265636569766572000000000000604482015290519081900360640190fd5b60008181526020819052604090206006015460ff16156103db576040805160e560020a62461bcd02815260206004820152601f60248201527f776974686472617761626c653a20616c72656164792077697468647261776e00604482015290519081900360640190fd5b600081815260208190526040902060060154610100900460ff161561044a576040805160e560020a62461bcd02815260206004820152601e60248201527f776974686472617761626c653a20616c726561647920726566756e6465640000604482015290519081900360640190fd5b600087815260208181526040808320600781018a905560068101805460ff19166001908117909155600282015490820154600383015484517fa9059cbb000000000000000000000000000000000000000000000000000000008152600160a060020a03928316600482015260248101919091529351929591169363a9059cbb936044808201949293918390030190829087803b1580156104e957600080fd5b505af11580156104fd573d6000803e3d6000fd5b505050506040513d602081101561051357600080fd5b5050604080513381526020810189905281518a927f4151ddecd8a73ecfb7e8c7f6bc6e7f28d15a1c18eb245586c942e9d96a8cf811928290030190a2506001979650505050505050565b60008161056981610da1565b15156105bf576040805160e560020a62461bcd02815260206004820152601960248201527f636f6e7472616374496420646f6573206e6f7420657869737400000000000000604482015290519081900360640190fd5b6000838152602081905260409020548390600160a060020a0316331461062f576040805160e560020a62461bcd02815260206004820152601660248201527f726566756e6461626c653a206e6f742073656e64657200000000000000000000604482015290519081900360640190fd5b600081815260208190526040902060060154610100900460ff161561069e576040805160e560020a62461bcd02815260206004820152601c60248201527f726566756e6461626c653a20616c726561647920726566756e64656400000000604482015290519081900360640190fd5b60008181526020819052604090206006015460ff1615610708576040805160e560020a62461bcd02815260206004820152601d60248201527f726566756e6461626c653a20616c72656164792077697468647261776e000000604482015290519081900360640190fd5b60008181526020819052604090206005015442101561075b5760405160e560020a62461bcd028152600401808060200182810382526023815260200180610de26023913960400191505060405180910390fd5b60008481526020818152604080832060068101805461ff00191661010017905560028101548154600383015484517fa9059cbb000000000000000000000000000000000000000000000000000000008152600160a060020a03928316600482015260248101919091529351929591169363a9059cbb936044808201949293918390030190829087803b1580156107f057600080fd5b505af1158015610804573d6000803e3d6000fd5b505050506040513d602081101561081a57600080fd5b505060405185907f3fbd469ec3a5ce074f975f76ce27e727ba21c99176917b97ae2e713695582a1290600090a2506001949350505050565b60008060008060008060008060006108698a610da1565b151561088f575060009750879650869550859450849350839250829150819050806108f3565b50505060008781526020819052604090208054600182015460028301546003840154600485015460058601546006870154600790970154600160a060020a039687169d509486169b509490921698509650945090925060ff80831692610100900416905b9193959799909294969850565b600082338383811161095c576040805160e560020a62461bcd02815260206004820152601860248201527f746f6b656e20616d6f756e74206d757374206265203e20300000000000000000604482015290519081900360640190fd5b604080517fdd62ed3e000000000000000000000000000000000000000000000000000000008152600160a060020a0384811660048301523060248301529151839286169163dd62ed3e916044808301926020929190829003018186803b1580156109c557600080fd5b505afa1580156109d9573d6000803e3d6000fd5b505050506040513d60208110156109ef57600080fd5b50511015610a315760405160e560020a62461bcd028152600401808060200182810382526021815260200180610e056021913960400191505060405180910390fd5b8660008111610a745760405160e560020a62461bcd028152600401808060200182810382526023815260200180610dbf6023913960400191505060405180910390fd5b889450428801610a8386610da1565b15610ad8576040805160e560020a62461bcd02815260206004820152601760248201527f436f6e747261637420616c726561647920657869737473000000000000000000604482015290519081900360640190fd5b604080517f23b872dd000000000000000000000000000000000000000000000000000000008152336004820152306024820152604481018990529051600160a060020a038a16916323b872dd9160648083019260209291908290030181600087803b158015610b4657600080fd5b505af1158015610b5a573d6000803e3d6000fd5b505050506040513d6020811015610b7057600080fd5b50511515610bb25760405160e560020a62461bcd028152600401808060200182810382526022815260200180610e266022913960400191505060405180910390fd5b6101206040519081016040528033600160a060020a031681526020018c600160a060020a0316815260200189600160a060020a031681526020018881526020018b81526020018a8152602001600015158152602001600015158152602001600060010281525060008088815260200190815260200160002060008201518160000160006101000a815481600160a060020a030219169083600160a060020a0316021790555060208201518160010160006101000a815481600160a060020a030219169083600160a060020a0316021790555060408201518160020160006101000a815481600160a060020a030219169083600160a060020a03160217905550606082015181600301556080820151816004015560a0820151816005015560c08201518160060160006101000a81548160ff02191690831515021790555060e08201518160060160016101000a81548160ff02191690831515021790555061010082015181600701559050508a600160a060020a031633600160a060020a0316877f0b058ff4168d86780241d0ea96a32a0dece6001f93c970388ed124fff490b6ee8b8b8f8f6040518085600160a060020a0316600160a060020a0316815260200184815260200183815260200182815260200194505050505060405180910390a4505050505095945050505050565b600090815260208190526040902054600160a060020a031615159056fe74696d656c6f636b2074696d65206d75737420626520696e2074686520667574757265726566756e6461626c653a2074696d656c6f636b206e6f742079657420706173736564746f6b656e20616c6c6f77616e6365206d757374206265203e3d20616d6f756e747472616e7366657246726f6d2073656e64657220746f2074686973206661696c6564a165627a7a72305820a21a6edbdfa1aa30948f96cbd2fe40f7c47935068543d4be6d248383182a12880029";

    public static final String FUNC_WITHDRAWFUNDS = "withdrawFunds";

    public static final String FUNC_REFUND = "refund";

    public static final String FUNC_GETCONTRACT = "getContract";

    public static final String FUNC_DEPOSITFUNDS = "depositFunds";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final Event DEPOSITFUNDS_EVENT = new Event("DepositFunds",
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event WITHDRAWFUNDS_EVENT = new Event("WithdrawFunds",
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Address>() {}, new TypeReference<Bytes32>() {}));
    ;

    public static final Event REFUND_EVENT = new Event("Refund",
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}));
    ;

    @Deprecated
    protected HashedTimelockERC20(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected HashedTimelockERC20(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected HashedTimelockERC20(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected HashedTimelockERC20(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> withdrawFunds(byte[] _contractId, byte[] _preimage) {
        final Function function = new Function(
                FUNC_WITHDRAWFUNDS,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_contractId),
                        new org.web3j.abi.datatypes.generated.Bytes32(_preimage)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> refund(byte[] _contractId) {
        final Function function = new Function(
                FUNC_REFUND,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_contractId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple9<String, String, String, BigInteger, byte[], BigInteger, Boolean, Boolean, byte[]>> getContract(byte[] _contractId) {
        final Function function = new Function(FUNC_GETCONTRACT,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_contractId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}, new TypeReference<Bool>() {}, new TypeReference<Bytes32>() {}));
        return new RemoteFunctionCall<Tuple9<String, String, String, BigInteger, byte[], BigInteger, Boolean, Boolean, byte[]>>(function,
                new Callable<Tuple9<String, String, String, BigInteger, byte[], BigInteger, Boolean, Boolean, byte[]>>() {
                    @Override
                    public Tuple9<String, String, String, BigInteger, byte[], BigInteger, Boolean, Boolean, byte[]> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple9<String, String, String, BigInteger, byte[], BigInteger, Boolean, Boolean, byte[]>(
                                (String) results.get(0).getValue(),
                                (String) results.get(1).getValue(),
                                (String) results.get(2).getValue(),
                                (BigInteger) results.get(3).getValue(),
                                (byte[]) results.get(4).getValue(),
                                (BigInteger) results.get(5).getValue(),
                                (Boolean) results.get(6).getValue(),
                                (Boolean) results.get(7).getValue(),
                                (byte[]) results.get(8).getValue());
                    }
                });
    }

    public RemoteCall<BigInteger> balanceOf(String _owner) {
        Function function = new Function("balanceOf", Arrays.asList(new Address(_owner)), Arrays.asList(new TypeReference<Uint256>() {
        }));
        return this.executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> depositFunds(String _receiver, byte[] _hashlock, BigInteger _timelock, String _tokenContract, BigInteger _amount) {
        final Function function = new Function(
                FUNC_DEPOSITFUNDS,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _receiver),
                        new org.web3j.abi.datatypes.generated.Bytes32(_hashlock),
                        new org.web3j.abi.datatypes.generated.Uint256(_timelock),
                        new org.web3j.abi.datatypes.Address(160, _tokenContract),
                        new org.web3j.abi.datatypes.generated.Uint256(_amount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public List<DepositFundsEventResponse> getDepositFundsEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(DEPOSITFUNDS_EVENT, transactionReceipt);
        ArrayList<DepositFundsEventResponse> responses = new ArrayList<DepositFundsEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            DepositFundsEventResponse typedResponse = new DepositFundsEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.contractId = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.sender = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.receiver = (String) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.tokenContract = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.hashlock = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.timelock = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<DepositFundsEventResponse> depositFundsEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, DepositFundsEventResponse>() {
            @Override
            public DepositFundsEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(DEPOSITFUNDS_EVENT, log);
                if(eventValues == null)
                    return null;
                DepositFundsEventResponse typedResponse = new DepositFundsEventResponse();
                typedResponse.log = log;
                typedResponse.contractId = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.sender = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.receiver = (String) eventValues.getIndexedValues().get(2).getValue();
                typedResponse.tokenContract = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.hashlock = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.timelock = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<DepositFundsEventResponse> depositFundsEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DEPOSITFUNDS_EVENT));
        return depositFundsEventFlowable(filter);
    }

    public List<WithdrawFundsEventResponse> getWithdrawFundsEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(WITHDRAWFUNDS_EVENT, transactionReceipt);
        ArrayList<WithdrawFundsEventResponse> responses = new ArrayList<WithdrawFundsEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            WithdrawFundsEventResponse typedResponse = new WithdrawFundsEventResponse();
            typedResponse.log = eventValues.getLog();
//            typedResponse.contractId = (byte[]) eventValues.getIndexedValues().get(0).getValue();
//            typedResponse.receive = (String) eventValues.getNonIndexedValues().get(0).getValue();
//            typedResponse.preimage = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();

            typedResponse.contractId = eventValues.getLog().getTopics().get(1);
            typedResponse.receive = eventValues.getLog().getTopics().get(2);
            typedResponse.preimage = eventValues.getLog().getTopics().get(3);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<WithdrawFundsEventResponse> withdrawFundsEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, WithdrawFundsEventResponse>() {
            @Override
            public WithdrawFundsEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(WITHDRAWFUNDS_EVENT, log);
                WithdrawFundsEventResponse typedResponse = new WithdrawFundsEventResponse();
                typedResponse.log = log;
                typedResponse.contractId = eventValues.getLog().getTopics().get(1);
                typedResponse.receive = eventValues.getLog().getTopics().get(2);
                typedResponse.preimage = eventValues.getLog().getTopics().get(3);
                return typedResponse;
            }
        });
    }

    public Flowable<WithdrawFundsEventResponse> withdrawFundsEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(WITHDRAWFUNDS_EVENT));
        return withdrawFundsEventFlowable(filter);
    }

    public List<RefundEventResponse> getRefundEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(REFUND_EVENT, transactionReceipt);
        ArrayList<RefundEventResponse> responses = new ArrayList<RefundEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RefundEventResponse typedResponse = new RefundEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.contractId = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<RefundEventResponse> refundEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, RefundEventResponse>() {
            @Override
            public RefundEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(REFUND_EVENT, log);
                RefundEventResponse typedResponse = new RefundEventResponse();
                typedResponse.log = log;
                typedResponse.contractId = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<RefundEventResponse> refundEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REFUND_EVENT));
        return refundEventFlowable(filter);
    }

    @Deprecated
    public static HashedTimelockERC20 load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new HashedTimelockERC20(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static HashedTimelockERC20 load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new HashedTimelockERC20(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static HashedTimelockERC20 load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new HashedTimelockERC20(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static HashedTimelockERC20 load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new HashedTimelockERC20(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<HashedTimelockERC20> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(HashedTimelockERC20.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<HashedTimelockERC20> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(HashedTimelockERC20.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<HashedTimelockERC20> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(HashedTimelockERC20.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<HashedTimelockERC20> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(HashedTimelockERC20.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class DepositFundsEventResponse extends BaseEventResponse {
        public byte[] contractId;

        public String sender;

        public String receiver;

        public String tokenContract;

        public BigInteger amount;

        public byte[] hashlock;

        public BigInteger timelock;
    }

    public static class WithdrawFundsEventResponse extends BaseEventResponse {
        public String contractId;

        public String receive;

        public String preimage;
    }

    public static class RefundEventResponse extends BaseEventResponse {
        public byte[] contractId;
    }
}
