package com.example.pokequiz.model.service.implementation

import android.provider.ContactsContract.Profile
import com.example.pokequiz.USER_PROFILE
import com.example.pokequiz.model.UserProfile
import com.example.pokequiz.model.service.AccountService
import com.example.pokequiz.model.service.ProfileService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.Firebase
import kotlinx.coroutines.flow.flatMapLatest


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await


class ProfileServiceImpl @Inject constructor(private val auth: AccountService,
) : ProfileService {

    @OptIn(ExperimentalCoroutinesApi::class)
    override val userProfile: Flow<List<UserProfile?>>
        get() =
            auth.currentUser.flatMapLatest { note ->
                Firebase.firestore
                    .collection(USER_PROFILES)
                    .whereEqualTo(USER_ID_FIELD, note?.id)
                    .dataObjects()
            }
    override suspend fun createProfile(userprofile: UserProfile) {
        val profileWithUserId = userprofile.copy(userId = auth.currentUserId)
        Firebase.firestore
            .collection(USER_PROFILES)
            .add(profileWithUserId).await()
    }

    override suspend fun readProfile(profileId: String): UserProfile? {
        return Firebase.firestore
            .collection(USER_PROFILES)
            .document(profileId).get().await().toObject()
    }

    override suspend fun updateProfile(profile: UserProfile) {
        Firebase.firestore
            .collection(USER_PROFILES)
            .document(profile.id).set(profile).await()
    }

    override suspend fun deleteProfile(profileId: String) {
        Firebase.firestore
            .collection(USER_PROFILES)
            .document(profileId).delete().await()
    }


    companion object {
        private const val USER_ID_FIELD = "userId"
        private const val USER_PROFILES = "USER_PROFILE"
    }
}
