package vaporwire

import com.fitbit.api.client.FitbitApiClientAgent
import com.fitbit.api.client.service.FitbitAPIClientService
import com.fitbit.api.client.FitbitAPIEntityCache
import com.fitbit.api.client.FitbitApiCredentialsCache
import com.fitbit.api.client.FitbitApiSubscriptionStorage
import com.fitbit.api.common.model.user.UserInfo

class UserController {

    def getPushNotifications() {
        println ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
//        println params.userInfo.firstName
//        println params.userInfo.displayName
        [userInfo: params.userInfo]
    }
}
