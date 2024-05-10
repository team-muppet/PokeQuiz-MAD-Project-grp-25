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
import com.google.firebase.storage.storage
import com.google.firebase.firestore.toObject
import com.google.firebase.Firebase
import kotlinx.coroutines.flow.flatMapLatest
import android.net.Uri
import java.io.File
import java.util.UUID


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await


class ProfileServiceImpl @Inject constructor(private val auth: AccountService,
) : ProfileService {

    @OptIn(ExperimentalCoroutinesApi::class)
    override val userProfile: Flow<List<UserProfile?>>
        get() =
            auth.currentUser.flatMapLatest { profile ->
                Firebase.firestore
                    .collection(USER_PROFILES)
                    .whereEqualTo(USER_ID_FIELD, profile?.id)
                    .dataObjects()
            }
    @OptIn(ExperimentalCoroutinesApi::class)
    override val allUserProfiles: Flow<List<UserProfile>>
        get() = Firebase.firestore
                    .collection(USER_PROFILES)
                    .dataObjects()
    @OptIn(ExperimentalCoroutinesApi::class)
    override val currentProfile: Flow<UserProfile>
        get() = auth.currentUser.flatMapLatest { profile ->
            Firebase.firestore
                .collection(USER_PROFILES)
                .whereEqualTo(USER_ID_FIELD, profile?.id)
                .dataObjects<UserProfile>()
                .map { profiles ->
                    profiles.firstOrNull() ?: UserProfile() // Assuming there's only one matching profile
                }
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

    override suspend fun uploadProfilePicture(profilePicture: String) {
        try {
            // Convert the profile picture string URI to a Uri object
            val uri = Uri.parse(profilePicture)

            // Create a storage reference from our app
            val storage = Firebase.storage
            val storageRef = storage.reference

            // Create a reference to "profilePictures" folder and a unique filename
            val profilePicsRef = storageRef.child("public/${UUID.randomUUID()}.jpg")

            // Upload the profile picture to Firestore Storage
            profilePicsRef.putFile(uri).await()

            // Log success message or any further processing
            println("Profile picture uploaded successfully.")
        } catch (e: Exception) {
            // Handle any errors that occur during the upload process
            println("Error uploading profile picture: ${e.message}")
        }
    }



    companion object {
        private const val USER_ID_FIELD = "userId"
        private const val USER_PROFILES = "USER_PROFILE"
    }
}
