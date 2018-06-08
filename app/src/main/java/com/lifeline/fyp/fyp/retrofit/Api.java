package com.lifeline.fyp.fyp.retrofit;

/**
 * Created by Mujtaba_khalid on 1/29/2018.
 */

import android.content.Intent;

import com.lifeline.fyp.fyp.models.AddComment;
import com.lifeline.fyp.fyp.models.AerobicCategoryName;
import com.lifeline.fyp.fyp.models.AerobicResponse;
import com.lifeline.fyp.fyp.models.BMICalculator;
import com.lifeline.fyp.fyp.models.CheckingCalories;
import com.lifeline.fyp.fyp.models.Clothing;
import com.lifeline.fyp.fyp.models.CreatingFitnessPlan;
import com.lifeline.fyp.fyp.models.FitnessDietPlanResponse;
import com.lifeline.fyp.fyp.models.FitnessSessionSummary;
import com.lifeline.fyp.fyp.models.FoodCategory;
import com.lifeline.fyp.fyp.models.FootwearSuggestions;
import com.lifeline.fyp.fyp.models.HairStyle;
import com.lifeline.fyp.fyp.models.InsertingDietSession;
import com.lifeline.fyp.fyp.models.Member;
import com.lifeline.fyp.fyp.models.PlanObject;
import com.lifeline.fyp.fyp.models.RemainingNewsFeed;
import com.lifeline.fyp.fyp.models.RequestingDietSession;
import com.lifeline.fyp.fyp.models.SendingAerobicExerciseData;
import com.lifeline.fyp.fyp.models.SendingFDObject;
import com.lifeline.fyp.fyp.models.SendingPlan;
import com.lifeline.fyp.fyp.models.Signin;
import com.lifeline.fyp.fyp.models.SkinTip;
import com.lifeline.fyp.fyp.models.SunGlasses;
import com.lifeline.fyp.fyp.models.uploadingPhoto;
import com.lifeline.fyp.fyp.retrofit.responses.AerobicExercisesOuterResponse;
import com.lifeline.fyp.fyp.retrofit.responses.AllCategoryFoodItems;
import com.lifeline.fyp.fyp.retrofit.responses.AllCommentsOuterResponse;
import com.lifeline.fyp.fyp.retrofit.responses.BMIObject;
import com.lifeline.fyp.fyp.retrofit.responses.DeletingPlan;
import com.lifeline.fyp.fyp.retrofit.responses.DetailedCalorieResponse;
import com.lifeline.fyp.fyp.retrofit.responses.FetchingSessionResponse;
import com.lifeline.fyp.fyp.retrofit.responses.FitnessAllSessions;
import com.lifeline.fyp.fyp.retrofit.responses.FitnessResponseObject;
import com.lifeline.fyp.fyp.retrofit.responses.FoodItemsResponse;
import com.lifeline.fyp.fyp.retrofit.responses.FriendDetailsOuter;
import com.lifeline.fyp.fyp.retrofit.responses.FriendListResponse;
import com.lifeline.fyp.fyp.retrofit.responses.FriendSearchingFilter;
import com.lifeline.fyp.fyp.retrofit.responses.LifeStyleResponse;
import com.lifeline.fyp.fyp.retrofit.responses.InsertSessionResponse;
import com.lifeline.fyp.fyp.retrofit.responses.LikeUnLike;
import com.lifeline.fyp.fyp.retrofit.responses.MemberArray;
import com.lifeline.fyp.fyp.retrofit.responses.MemberObject;
import com.lifeline.fyp.fyp.retrofit.responses.CaloriesResponse;
import com.lifeline.fyp.fyp.retrofit.responses.NewsFeedOuterResponse;
import com.lifeline.fyp.fyp.retrofit.responses.NotificationOuterResponse;
import com.lifeline.fyp.fyp.retrofit.responses.OuterFitnessResponse;
import com.lifeline.fyp.fyp.retrofit.responses.PlanReport;
import com.lifeline.fyp.fyp.retrofit.responses.PlanResponse;
import com.lifeline.fyp.fyp.retrofit.responses.PostAllLikes;
import com.lifeline.fyp.fyp.retrofit.responses.PostingStatus;
import com.lifeline.fyp.fyp.retrofit.responses.ResponsePlanArray;
import com.lifeline.fyp.fyp.retrofit.responses.SearchResultResponseOuterArray;
import com.lifeline.fyp.fyp.retrofit.responses.SendingRequestResponse;
import com.lifeline.fyp.fyp.retrofit.responses.SpecificPostOuter;
import com.lifeline.fyp.fyp.retrofit.responses.StatusResponse;
import com.lifeline.fyp.fyp.retrofit.responses.StyleAccessoriesArray;
import com.lifeline.fyp.fyp.retrofit.responses.StyleAccessoriesObject;
import com.lifeline.fyp.fyp.retrofit.responses.UploadObject;
import com.lifeline.fyp.fyp.retrofit.responses.UserPostOuterResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Mujtaba_khalid on 11/30/2017.
 */

public interface Api {

    String Base_URL = "http://lifeline.gear.host/api/lifeline/";
    String FoodItems = "http://fooditems.gear.host/api/lifeline/";

    @POST("postmember")
        // for creating member.
    Call<Member> createMember(@Body Member member);


    @GET("getmemberbyemail")
        // for getting a specific member. members?email
    Call<MemberObject> fetchingUser(@Query("email") String email);

    @GET("getmemberbyid")
    Call<MemberObject> getmemberbyid(@Query("id") Integer id);

    @GET("members")
        // getting all the members.
    Call<MemberArray> gettingusers();

    @PUT("updatemember/{memberId}")
        // making change in the already created member. members/{id}
    Call<MemberObject> updateUser(@Path("memberId") Integer memberId, @Body Member member);


    @POST("getSunglassesSuggestions")
    Call<StyleAccessoriesArray> sunglassessugestion(@Body SunGlasses suggestionsglasses);

    @POST("getHairStylesuggestions")
    Call<StyleAccessoriesArray> hairssugestion(@Body HairStyle hairsugg);


    @POST("getfootwaresuggestions")
    Call<StyleAccessoriesArray> footwearsuggestions(@Body FootwearSuggestions hairsugg);

    // getting all the skinstips.
    @POST("getallskincaretips")
    Call<StyleAccessoriesArray> allskintips(@Body SkinTip skintip);

    @POST("getallskincaretips")
    Call<StyleAccessoriesObject> detailedskintip(@Body SkinTip skintip);

    @POST("getclothingsuggestions")
    Call<StyleAccessoriesArray> clothingsuggestions(@Body Clothing clothes);

    @POST("getfooditemsbycategory")
    Call<AllCategoryFoodItems> fooditems(@Body FoodCategory food);

    @POST("BMICalculator")
    Call<BMIObject> checking(@Body BMICalculator bmi);

    @POST("dietPlanCreator")
    Call<PlanResponse> creatingplan(@Body PlanObject po);

    //
//
    @POST("insertdietplan")
    Call<ResponsePlanArray> insertingPlan(@Body SendingPlan sp);
//
// @POST("insertdietplan")
// Call<SendingPlan> insertingPlan (@Body SendingPlan sp);


    @POST("getDietPlanByMemberId")
    Call<ResponsePlanArray> checkingplan(@Query("memberId") Integer memberId);

    @GET("getAll")
    Call<FoodItemsResponse> listoffood();

    @POST("getByName")
        // fetching the whole detail of the object.
    Call<DetailedCalorieResponse> calories(@Body CheckingCalories cc);

    @POST("getCaloriesFromMeasure")
    Call<CaloriesResponse> fetchingcaloriesbymeasure(@Body CheckingCalories cc1);

    @POST("getCaloriesFromGrams")
    Call<CaloriesResponse> fetchingcaloriesbygram(@Body CheckingCalories cc2);

    @POST("insertDietSession")
    Call<InsertSessionResponse> insertingSession(@Body InsertingDietSession ids);

    // requesting diet session.
    @POST("getPlansSession")
    Call<FetchingSessionResponse> fetchingsessions(@Body RequestingDietSession rds);

    @POST("updateDietPlan/{dietPlanId}")
    Call<SendingPlan> updateDietStatus(@Path("dietPlanId") Integer dietPlanId, @Body SendingPlan sp);

    @POST("createFitnessplan")
    Call<OuterFitnessResponse> fitness(@Body CreatingFitnessPlan sp);

    @POST("getFitnessPlanByMemberId")
    Call<FitnessResponseObject> checkingfitnessplan(@Query("memberId") Integer memberId);

    // experimenting.
    @POST("createDietFitnessPlan/{dietPlanId}")
    Call<FitnessResponseObject> cdfplan(@Query("dietPlanId") Integer dietPlanId);

    // it is the suggestions from which user will select the desired plan of diet.
    @POST("getFitnessDietPlan")
    Call<PlanResponse> fitnessdietplan(@Body PlanObject po);

    @POST("createFitnessDietPlan")
    Call<FitnessDietPlanResponse> insertingfitnessDietPlan(@Body SendingFDObject sp);


    @POST("insertFitnessSession")
    Call<FitnessSessionSummary> fss(@Body FitnessSessionSummary sp);


    @POST("getFitnessPlanSessions/{fitnessPlanId}")
    Call<FitnessAllSessions> sessionresponse(@Path("fitnessPlanId") Integer fitnessPlanId, @Body FitnessSessionSummary sp);


    @POST("getExercisesTypes")
    Call<AerobicResponse> aerobicexercises();

    @POST("getExerciseSubcategories")
    Call<AerobicExercisesOuterResponse> aer(@Body AerobicCategoryName acn);

    @POST("getExercisesCalories")
    Call<SendingAerobicExerciseData> sae(@Body SendingAerobicExerciseData acn);

    @POST("deletePlan/{PlanId}")
    Call<DeletingPlan> deletingplan(@Path("PlanId") Integer PlanId, @Body DeletingPlan dp);

    @POST("gethairtypeList")
    Call<LifeStyleResponse> hairtypes();

    @POST("getFaceShapeList")
    Call<LifeStyleResponse> faceshapes();

    @POST("uploadPicture")
    Call<UploadObject> uploadFile(@Body uploadingPhoto up);

    @POST("addPost")
    Call<StatusResponse> status(@Body PostingStatus postingStatus);

    @POST("emailSearchResults")
    Call<SearchResultResponseOuterArray> searchResult(@Query("input") String input, @Query("memberId") Integer memberId);

    // testing.
    @POST("sendRequest")
    Call<SendingRequestResponse> sendingrequest(@Query("memberId") Integer memberId, @Query("friendId") Integer friendId);

    @POST("cancelRequest")
    Call<SendingRequestResponse> cancelRequest(@Query("memberId") Integer memberId, @Query("friendId") Integer friendId);

    @POST("getFriendList/{memberId}")
    Call<FriendListResponse> friendlist(@Path("memberId") Integer memberId);


    @POST("acceptRequest")
    Call<SendingRequestResponse> acceptrequest(@Query("memberId") Integer memberId, @Query("friendId") Integer friendId);

    @POST("rejectRequest")
    Call<SendingRequestResponse> rejectRequest(@Query("memberId") Integer memberId, @Query("friendId") Integer friendId);


    @POST("getProfile")
    Call<FriendDetailsOuter> getProfile(@Query("memberId") Integer memberId, @Query("profileId") Integer profileId);


    @POST("unfriend")
    Call<SendingRequestResponse> unfriend(@Query("memberId") Integer memberId, @Query("friendId") Integer friendId);

    @POST("getNewsfeed1/{memberId}")
    Call<NewsFeedOuterResponse> newsfeed(@Path("memberId") Integer memberId);

    @POST("getNewsfeed2")
    Call<NewsFeedOuterResponse> newsfeed2(@Body RemainingNewsFeed remainingNewsFeed);


    @POST("likePost")
    Call<LikeUnLike> like(@Query("memberId") Integer memberId, @Query("postId") Integer postId);


    @POST("unlikePost")
    Call<LikeUnLike> unlikePost(@Query("memberId") Integer memberId, @Query("postId") Integer postId);


    @POST("addComment")
    Call<AddComment> addingComment(@Body AddComment addComment);

    @POST("getUserPost/{memberId}")
    Call<UserPostOuterResponse> gettinguserpost (@Path("memberId") Integer memberId);

    @POST("getPostLikes/{postId}")
    Call<PostAllLikes> postAllLikes (@Path( "postId" ) Integer postId);

    @POST("getpostcomments/{postId}")
    Call<AllCommentsOuterResponse> commentsall(@Path( "postId" ) Integer postId);

    @POST("getallnotifications/{memberId}")
    Call<NotificationOuterResponse> notificationresponse (@Path( "memberId" ) Integer memeberId);


    @POST("plansSocialMediaReport/{memberId}")
    Call<PlanReport> planreport (@Path( "memberId" ) Integer memberId);

    @POST("getPostById/{postId}")
    Call<SpecificPostOuter> specificpost(@Path( "postId" )Integer postId);

    @POST("setAllMemberNotificationsSeen/{memberId}")
    Call<NewsFeedOuterResponse> seen (@Path( "memberId" ) Integer memberId);

    @POST("setNotificationSeen/{postId}")
    Call<NewsFeedOuterResponse> notificationseen (@Path( "postId" ) Integer postId);

    @POST("signIn")
    Call<MemberObject> signin (@Body Signin signin);

    @POST("friendListSearchResults")
    Call<FriendSearchingFilter> filter(@Query( "input" ) String input, @Query("memberId") Integer memberId);


}

