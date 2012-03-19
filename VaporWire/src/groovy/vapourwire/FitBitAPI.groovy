package vapourwire

import com.fitbit.api.client.FitbitApiClientAgent
import com.fitbit.api.client.service.FitbitAPIClientService
import com.fitbit.api.client.FitbitAPIEntityCache
import com.fitbit.api.client.FitbitApiCredentialsCache
import com.fitbit.api.client.FitbitApiSubscriptionStorage
import com.fitbit.api.client.FitbitApiEntityCacheMapImpl
import com.fitbit.api.client.FitbitApiCredentialsCacheMapImpl
import com.fitbit.api.client.FitbitApiSubscriptionStorageInMemoryImpl
import org.codehaus.groovy.grails.commons.ApplicationHolder

class FitBitAPI {

    private FitbitAPIClientService<FitbitApiClientAgent> fitBitAPI
    private FitbitAPIEntityCache entityCache
    private FitbitApiCredentialsCache credentialsCache
    private FitbitApiSubscriptionStorage subscriptionStore

    def grailsApplication

    private static FitBitAPI bean

    public static FitbitAPIClientService<FitbitApiClientAgent> getBean() {
        if (!bean) {
            bean = new FitBitAPI()
        }
        bean.fitBitAPI
    }

    public FitBitAPI() {
        grailsApplication = ApplicationHolder.application
        entityCache = new FitbitApiEntityCacheMapImpl();
        credentialsCache = new FitbitApiCredentialsCacheMapImpl();
        subscriptionStore = new FitbitApiSubscriptionStorageInMemoryImpl();
        fitBitAPI = new FitbitAPIClientService<FitbitApiClientAgent>(
                new FitbitApiClientAgent(
                        grailsApplication.config.apiBaseUrl as String,
                        grailsApplication.config.fitbitSiteBaseUrl as String,
                        credentialsCache
                ),
                grailsApplication.config.Consumer_key,
                grailsApplication.config.Consumer_secret,
                credentialsCache,
                entityCache,
                subscriptionStore
        );
    }
}
