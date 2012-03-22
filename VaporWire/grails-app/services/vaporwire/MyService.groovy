package vaporwire

import javax.servlet.ServletException
import javax.servlet.ServletConfig
import com.fitbit.api.model.APIResourceCredentials
import vapourwire.FitBitAPI
import com.fitbit.api.client.LocalUserDetail
import com.fitbit.api.FitbitAPIException

class MyService {

    APIResourceCredentials getCredentials(Map params) {
        String tempTokenReceived = params.oauth_token;
        String tempTokenVerifier = params.oauth_verifier;
        APIResourceCredentials resourceCredentials = FitBitAPI.getBean().getResourceCredentialsByTempToken(tempTokenReceived);
        if (resourceCredentials == null) {
            throw new ServletException("Unrecognized temporary token when attempting to complete authorization: " + tempTokenReceived);
        }
        // Get token credentials only if necessary:
        if (!resourceCredentials.isAuthorized()) {
            // The verifier is required in the request to get token credentials:
            resourceCredentials.setTempTokenVerifier(tempTokenVerifier);
            try {
                FitBitAPI.getBean().getTokenCredentials(new LocalUserDetail(resourceCredentials.getLocalUserId()));
            } catch (FitbitAPIException e) {
                throw new ServletException("Unable to finish authorization with Fitbit.", e);
            }
        }
        return resourceCredentials
    }
}
