package vaporwire

import com.fitbit.api.FitbitAPIException;
import com.fitbit.api.client.*;
import com.fitbit.api.common.model.user.UserInfo;
import javax.servlet.ServletException
import com.fitbit.api.model.APIResourceCredentials
import vapourwire.FitBitAPI
import org.apache.tools.ant.taskdefs.Sleep
import com.fitbit.api.model.FitbitUser
import org.joda.time.LocalDate
import com.fitbit.api.common.model.activities.Activity
import com.fitbit.api.common.model.activities.Activities
import com.fitbit.api.common.model.foods.Food
import com.fitbit.api.common.model.foods.Foods
import com.fitbit.api.model.APICollectionType

class MyController {

    def myService;

    static defaultAction = "getUserDetails"
    String subscriber_id = "1";

    def getUserDetails() {
        if (request.getParameter("completeAuthorization") != null) {
            forward(action: 'showUserInfo')
        }
        else {
            println "before"

            try {
                response.sendRedirect(FitBitAPI.getBean().getResourceOwnerAuthorizationURL(new LocalUserDetail("-"), grailsApplication.config.exampleBaseUrl + "/my/getUserDetails?completeAuthorization="));
            }
            catch (FitbitAPIException e) {
                throw new ServletException("Exception during performing authorization", e);
            }
        }
    }

    def showUserInfo() {
        APIResourceCredentials resourceCredentials = myService.getCredentials(params)
        UserInfo userInfo;
        try {
            userInfo = FitBitAPI.getBean().getClient().getUserInfo(new LocalUserDetail(resourceCredentials.getLocalUserId()));
            Activities activities = FitBitAPI.getBean().getClient().getActivities(new LocalUserDetail(resourceCredentials.getLocalUserId()), new FitbitUser(resourceCredentials.getLocalUserId()), LocalDate.now())
            Foods food = FitBitAPI.getBean().getClient().getFoods(new LocalUserDetail(resourceCredentials.localUserId), new FitbitUser(resourceCredentials.localUserId), LocalDate.now())
            FitBitAPI.getBean().getClient().subscribe(subscriber_id, new LocalUserDetail(resourceCredentials.localUserId), new FitbitUser(resourceCredentials.localUserId), APICollectionType.activities, resourceCredentials.getLocalUserId() + ":0")
            FitBitAPI.getBean().getClient().subscribe(subscriber_id, new LocalUserDetail(resourceCredentials.localUserId), new FitbitUser(resourceCredentials.localUserId), APICollectionType.foods, resourceCredentials.getLocalUserId() + ":1")
            println ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Subscribed From Food And Activities>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
            List subscriptions = FitBitAPI.getBean().getClient().getSubscriptions(new LocalUserDetail(resourceCredentials.getLocalUserId()))
            subscriptions.each {
                println it.collectionType
            }
            [userInfo: userInfo, activities: activities, foods: food, subscriptions: subscriptions]
        } catch (FitbitAPIException e) {
            throw new ServletException("Exception during getting user info", e);
        }
    }



    def subscribeForFoodAndActivities() {
        APIResourceCredentials resourceCredentials = myService.getCredentials(params)
        try {
            println ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>SUBSCRIBE>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
            FitBitAPI.getBean().getClient().subscribe(subscriber_id, new LocalUserDetail(resourceCredentials.getLocalUserId()), new FitbitUser(resourceCredentials.getLocalUserId()), APICollectionType.activities, resourceCredentials.getLocalUserId() + ":0")
            FitBitAPI.getBean().getClient().subscribe(subscriber_id, new LocalUserDetail(resourceCredentials.getLocalUserId()), new FitbitUser(resourceCredentials.getLocalUserId()), APICollectionType.foods, resourceCredentials.getLocalUserId() + ":1")
            println "*************************************SUBSCRIPTION DONE FOR FOOD AND ACTIVITIES********************************************************************"
            render "Subscription Done for food & Activities"
        }
        catch (FitbitAPIException e) {
            throw new FitbitAPIException("Exception occured while subscribing for food & activities" + e)
        }
    }


    def unSubscribeFromFoodAndActivities() {
        APIResourceCredentials resourceCredentials = myService.getCredentials(params)
        try {
            println ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>DOING UNSUBSCRIBE>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
            FitBitAPI.getBean().getClient().unsubscribe(subscriber_id, new LocalUserDetail(resourceCredentials.localUserId), new FitbitUser(resourceCredentials.localUserId), APICollectionType.activities, resourceCredentials.getLocalUserId() + ":0")
            FitBitAPI.getBean().getClient().unsubscribe(subscriber_id, new LocalUserDetail(resourceCredentials.localUserId), new FitbitUser(resourceCredentials.localUserId), APICollectionType.foods, resourceCredentials.getLocalUserId() + ":1")
            println ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>UnSubscribed From Food And Activities>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
        }
        catch (FitbitAPIException e) {
            throw new FitbitAPIException("Exception Occured while unsubscribing For Food And Activities")
        }
    }
}