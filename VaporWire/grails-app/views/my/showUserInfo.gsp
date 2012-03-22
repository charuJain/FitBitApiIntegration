<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'default.css')}" type="text/css"/>
</head>

<body>
<h2>UserInfo</h2>
<table>
    <tr>
        <td>Full Name:</td>
        <td>${userInfo.fullName}</td>
    </tr>
    <tr>
        <td>Display Name:</td>
        <td>${userInfo.displayName}</td>
    </tr>
    <tr>
        <td>Nickname:</td>
        <td>${userInfo.nickname}</td>
    </tr>
    <tr>
        <td>Gender:</td>
        <td>${userInfo.gender}</td>
    </tr>
    <tr>
        <td>Date of Birth:</td>
        <td>${userInfo.dateOfBirth}</td>
    </tr>
    <tr>
        <td>Height:</td>
        <td>${userInfo.height}</td>
    </tr>
    <tr>
        <td>Weight:</td>
        <td>${userInfo.weight}</td>
    </tr>
    <tr>
        <td>Stride Lenght Walking:</td>
        <td>${userInfo.strideLengthWalking}</td>
    </tr>
    <tr>
        <td>Stride Lenght Running:</td>
        <td>${userInfo.strideLengthRunning}</td>
    </tr>
    <tr>
        <td>City:</td>
        <td>${userInfo.city}</td>
    </tr>
    <tr>
        <td>State:</td>
        <td>${userInfo.state}</td>
    </tr>
    <tr>
        <td>Country:</td>
        <td>${userInfo.country}</td>
    </tr>
    <tr>
        <td>Timezone:</td>
        <td>${userInfo.timezone}</td>
    </tr>
    <tr>
        <td>Offset From UTC (Millis):</td>
        <td>${userInfo.offsetFromUTCMillis}</td>
    </tr>
    <tr>
        <td>Avatar:</td>
        <td>${userInfo.avatar}</td>
    </tr>
</table>


<h2>Your activities recorded on Fitbit</h2>

<div class="parag">
    <div class="columns">
        <div class="column">
            <div class="line">Calories Out</div>

            <div class="line red">${activities.summary.caloriesOut}</div>
        </div>

        <div class="column">
            <div class="line">Active Score</div>

            <div class="line red">${activities.summary.activeScore}</div>
        </div>

        <div class="column">
            <div class="line">Steps</div>

            <div class="line red">${activities.summary.steps}</div>
        </div>

        <div class="column">
            <div class="line">Sedentary Minutes</div>

            <div class="line red">${activities.summary.sedentaryMinutes}</div>
        </div>

        <div class="column">
            <div class="line">Lightly Active Minutes</div>

            <div class="line red">${activities.summary.lightlyActiveMinutes}</div>
        </div>

        <div class="column">
            <div class="line">Fairly Active Minutes</div>

            <div class="line red">${activities.summary.fairlyActiveMinutes}</div>
        </div>

        <div class="column">
            <div class="line">Very Active Minutes</div>

            <div class="line red">${activities.summary.veryActiveMinutes}</div>
        </div>
    </div>
</div>
<hr/>

<h2>Logged Activities:</h2>
<g:each in="${activities.activities}" var="activityLog">
    <h3>${activityLog.name}</h3>

    <div>Distance: ${activityLog.distance}</div>

    <div>Distance: ${activityLog.duration}</div>

    <div>Calories: ${activityLog.calories}</div>

    <div>Favorite?: ${activityLog.favorite}</div>
</g:each>

<h2>Your foods recorded on Fitbit</h2>

<h3>Summary</h3>

<div class="parag">
    <div class="columns">
        <div class="column">
            <div class="line">Calories</div>

            <div class="line red">${foods.summary.calories}</div>
        </div>

        <div class="column">
            <div class="line">Fat</div>

            <div class="line red">${foods.summary.fat}
                <span>g</span></div>
        </div>

        <div class="column">
            <div class="line">Fiber</div>

            <div class="line red">${foods.summary.fiber}
                <span>g</span></div>
        </div>

        <div class="column">
            <div class="line">Carbs</div>

            <div class="line red">${foods.summary.carbs}
                <span>g</span></div>
        </div>

        <div class="column">
            <div class="line">Sodium</div>

            <div class="line red">${foods.summary.sodium}
                <span>mg</span></div>
        </div>

        <div class="column">
            <div class="line">Protein</div>

            <div class="line red">${foods.summary.protein}
                <span>g</span></div>
        </div>

        <div class="column">
            <div class="line">Water</div>

        </div>
    </div>
</div>

<h2>Logged Foods:</h2>
<g:each in="${foods.foods}" var="foodLog">
    <h3>${foodLog.loggedFood.name}</h3>

    <div>
        Amount: ${foodLog.loggedFood.amount} ${foodLog.loggedFood.amount != 0.0 ? foodLog.loggedFood.unit.plural : foodLog.loggedFood.unit.name}</div>

    <div>Calories: ${foodLog.nutritionalValues.calories}</div>

    <div>Fat: ${foodLog.nutritionalValues.fat}</div>

    <div>Fiber: ${foodLog.nutritionalValues.fiber}</div>

    <div>Carbs: ${foodLog.nutritionalValues.carbs}</div>

    <div>Sodium: ${foodLog.nutritionalValues.sodium}</div>

    <div>Protein: ${foodLog.nutritionalValues.protein}</div>

    <div>Favorite?: ${foodLog.favorite}</div>

    <div>FoodAccessLevel : ${foodLog.loggedFood.accessLevel}</div>
</g:each>
<hr/>


<g:if test="${subscriptions}">
    <div class="parag left">
        <table class="blue">
            <thead>
            <tr>
                <th width="13%">Subscription ID</th>
                <th width="13%">Fitbit Owner Type</th>
                <th width="13%">Fitbit Owner ID</th>
                <th width="13%">Feed type</th>
            </tr>
            </thead>
            <g:each in="${subscriptions}" var="subscription">
                <tr>
                    <td>${subscription.subscriptionId}</td>
                    <td>${subscription.ownerType}</td>
                    <td>${subscription.ownerId}</td>
                    <td>${subscription.collectionType}</td>
                </tr>
            </g:each>
        </table>
    </div>
</g:if>
<g:else>
    <a href="${createLink(controller: 'my', action: 'subscribeForFoodAndActivities')}"><button>Create Subscription</button>
    </a>
</g:else>
</body>
</html>