package demo.other;

import io.blk.erc20.generated.Erc20Token;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.http.HttpService;
import org.web3j.quorum.Quorum;
import org.web3j.quorum.tx.ClientTransactionManager;
import org.web3j.tx.TransactionManager;
import utils.Environment;

import java.math.BigInteger;
import java.util.Collections;

import static org.web3j.tx.Contract.GAS_LIMIT;
import static org.web3j.tx.ManagedTransaction.GAS_PRICE;

/**
 * @author nature
 * @date 30/8/21 上午11:53
 * @email 924943578@qq.com
 */
public class Erc20TokenUse {


    public static void main(String[] args){
        use();
    }


    public static void use() {

        Quorum web3j = Quorum.build(new HttpService(Environment.RPC_URL));
        String contractAddress = "0x69B726d0ceC2C6D9026473b3e7CE91E71B58E70E";
        Credentials credentials = Credentials.create("2d5829a087b9c70d35965ba6357d2269692b6d070df1b941571d9fd1b8b841c4");//可以根据私钥生成
        String address = "";
        String myAddress = "0x3761940D8aDd75AC0A2f37670a62A71c905475b5";
        TransactionManager transactionManager = new ClientTransactionManager(
                web3j,myAddress , Collections.emptyList());


        Erc20Token contract =  Erc20Token.load(
                contractAddress, web3j, transactionManager, GAS_PRICE, GAS_LIMIT);

        BigInteger amount = BigInteger.ONE;
        try {
//            contract.balanceOf(myAddress).send().toString();
//            System.out.println(contract.balanceOf(myAddress).send().toString());


            String toAddress = "0x8F22EA26c253780BAdb317c039FcFF3d69C23829";
            String transfer = contract.transfer(toAddress, 1111).send().toString();
            System.out.println(transfer);


            //etc..
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
