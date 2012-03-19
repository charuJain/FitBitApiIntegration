package vaporwire

import com.fitbit.api.FitbitAPIException;
import com.fitbit.api.client.*;
import com.fitbit.api.common.model.user.UserInfo;
import javax.servlet.ServletException
import com.fitbit.api.model.APIResourceCredentials
import vapourwire.FitBitAPI

class MyController {

    def myService;

    static defaultAction = "getUserDetails"

    def getUserDetails() {
        if (request.getParameter("completeAuthorization") != null) {
            forward(action: 'onCompleteAuthorizing')
        }
        else {
            try {
                response.sendRedirect(FitBitAPI.getBean().getResourceOwnerAuthorizationURL(new LocalUserDetail("-"), grailsApplication.config.exampleBaseUrl + "/my/getUserDetails?completeAuthorization="));
            }
            catch (FitbitAPIException e) {
                throw new ServletException("Exception during performing authorization", e);
            }
        }
    }

    def onCompleteAuthorizing() {
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
        try {
            UserInfo userInfo = FitBitAPI.getBean().getClient().getUserInfo(new LocalUserDetail(resourceCredentials.getLocalUserId()));
            [userInfo: userInfo]
        } catch (FitbitAPIException e) {
            throw new ServletException("Exception during getting user info", e);
        }
    }
}