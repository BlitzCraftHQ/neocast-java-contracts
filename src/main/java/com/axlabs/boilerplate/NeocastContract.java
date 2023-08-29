package com.axlabs.boilerplate;

import io.neow3j.devpack.ByteString;
import io.neow3j.devpack.Storage;
import io.neow3j.devpack.annotations.Permission;
import io.neow3j.devpack.contracts.OracleContract;
import io.neow3j.devpack.annotations.Trust;

@Permission(contract = "*", methods = "*")
@Trust(contract = "*")
public class NeocastContract {

    // Use the OracleMakeRequest example to invoke the request method, and the
    // OracleCheckResponse example to check
    // whether the storage was updated accordingly.
    // Check out the oracle service documentation here:
    // https://docs.neo.org/docs/en-us/advanced/oracle.html

    public static void sendNotification(String topicId, String name, String description, String mediaUrl,
            String websiteUrl) {
        new OracleContract()
                .request("https://neocast.blitzcrafthq.com/api/notifications/create?topicID=" + topicId + "&name="
                        + name + "&description=" + description + "&websiteURL=" + websiteUrl + "&mediaURL=" + mediaUrl,
                        "", "callback", null, 1);
    }

    public static void createTopic(String name, String description, String websiteUrl, String logoUrl,
            String webhookUrl) {
        new OracleContract()
                .request("https://neocast.blitzcrafthq.com/api/topics/create?" + "name="
                        + name + "&description=" + description + "&websiteURL=" + websiteUrl,
                        "", "callback", null, 1);
    }

    public static void callback(String url, Object userData, int responseCode, ByteString response) {
        Storage.put(Storage.getStorageContext(), 0, url);
        Storage.put(Storage.getStorageContext(), 1, responseCode);
        Storage.put(Storage.getStorageContext(), 2, response);
    }

    public static String getStoredUrl() {
        return Storage.getString(Storage.getReadOnlyContext(), 0);
    }

    public static int getStoredResponseCode() {
        return Storage.getIntOrZero(Storage.getReadOnlyContext(), 1);
    }

    public static ByteString getStoredResponse() {
        return Storage.get(Storage.getReadOnlyContext(), 2);
    }

}
