package com.axlabs.boilerplate;

import static io.neow3j.contract.Token.toFractions;
import static io.neow3j.transaction.AccountSigner.none;
import static io.neow3j.types.ContractParameter.integer;
import static io.neow3j.types.ContractParameter.string;

import java.math.BigDecimal;
import java.math.BigInteger;

import io.neow3j.contract.SmartContract;
import io.neow3j.protocol.Neow3j;
import io.neow3j.protocol.http.HttpService;
import io.neow3j.transaction.TransactionBuilder;
import io.neow3j.types.Hash160;
import io.neow3j.types.Hash256;
import io.neow3j.wallet.Account;

public class OracleMakeRequest {

    private static final String ALICE_WIF = "KzrHihgvHGpF9urkSbrbRcgrxSuVhpDWkSfWvSg97pJ5YgbdHKCQ";
    private static final Account OWNER_ACCOUNT = Account.fromWIF(ALICE_WIF);
    // private static final String NODE = "http://localhost:50012";
    private static final String NODE = "http://seed2t5.neo.org:20332";

    public static void main(String[] args) throws Throwable {

        Neow3j neow3j = Neow3j.build(new HttpService(NODE));

        // The contract CallOracleContract is deployed on testnet with the following
        // address.
        Hash160 contractHash = new Hash160("68c862aa58ca5158c22c5da927284e5f013d7ef9");
        SmartContract smartContract = new SmartContract(contractHash, neow3j);

        BigInteger gasForResponse = toFractions(BigDecimal.valueOf(3), 8);
        TransactionBuilder b = smartContract.invokeFunction("request",
                // Change the index 1 to 2 (i.e., 'todos/2') in order to see whether the
                // contract storage was actually
                // updated.
                string("http://jsonplaceholder.typicode.com/todos/3"),
                string(""), // the filter
                integer(gasForResponse)); // the amount of GAS provided for creating the response tx
        Hash256 hash = b.signers(none(OWNER_ACCOUNT))
                .sign()
                .send()
                .getSendRawTransaction()
                .getHash();

        System.out.println("Request transaction hash: " + hash);
    }

}