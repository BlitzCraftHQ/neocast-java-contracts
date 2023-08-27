package com.axlabs.boilerplate;

import io.neow3j.contract.SmartContract;
import io.neow3j.protocol.Neow3j;
import io.neow3j.protocol.core.response.InvocationResult;
import io.neow3j.protocol.http.HttpService;
import io.neow3j.types.Hash160;
import io.neow3j.wallet.Account;

import java.io.IOException;

public class OracleCheckResponse {

    private static final String ALICE_WIF = "KzrHihgvHGpF9urkSbrbRcgrxSuVhpDWkSfWvSg97pJ5YgbdHKCQ";
    private static final Account OWNER_ACCOUNT = Account.fromWIF(ALICE_WIF);
    // private static final String NODE = "http://localhost:50012";
    private static final String NODE = "http://seed2t5.neo.org:20332";

    public static void main(String[] args) throws IOException {
        Neow3j neow3j = Neow3j.build(new HttpService(NODE));

        // The contract CallOracleContract is deployed on testnet with the following
        // address.
        Hash160 contractHash = new Hash160("68c862aa58ca5158c22c5da927284e5f013d7ef9");
        SmartContract smartContract = new SmartContract(contractHash, neow3j);

        InvocationResult result = smartContract.callInvokeFunction("getStoredUrl").getInvocationResult();
        String storedURL = result.getStack().get(0).getString();
        System.out.println("Stored URL: " + storedURL);

        result = smartContract.callInvokeFunction("getStoredResponseCode").getInvocationResult();
        String storedResponseCode = result.getStack().get(0).getString();
        System.out.println("Stored response code: " + storedResponseCode);

        result = smartContract.callInvokeFunction("getStoredResponse").getInvocationResult();
        String storedResponse = result.getStack().get(0).getString();
        System.out.println("Stored value: " + storedResponse);
    }

}
