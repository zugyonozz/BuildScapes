package com.example.buildscapes.util

import android.net.Uri
import com.example.buildscapes.data.model.Post
import com.example.buildscapes.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.UUID

class FirebaseManager {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    // Auth
    fun getCurrentUser(): FirebaseUser? = auth.currentUser
    fun getCurrentUserId(): String? = auth.currentUser?.uid

    // User Operations
    suspend fun saveUserProfile(user: User): Result<Unit> {
        return try {
            firestore.collection("users")
                .document(user.uid)
                .set(user)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUserProfile(userId: String): Result<User> {
        return try {
            val snapshot = firestore.collection("users")
                .document(userId)
                .get()
                .await()

            val user = snapshot.toObject(User::class.java)
            if (user != null) {
                Result.success(user)
            } else {
                Result.failure(Exception("User not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateUserProfile(userId: String, updates: Map<String, Any>): Result<Unit> {
        return try {
            firestore.collection("users")
                .document(userId)
                .update(updates)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Image Upload
    suspend fun uploadProfileImage(userId: String, imageUri: Uri): Result<String> {
        return try {
            val ref = storage.reference
                .child("profile_images/$userId/${UUID.randomUUID()}.jpg")

            ref.putFile(imageUri).await()
            val downloadUrl = ref.downloadUrl.await().toString()
            Result.success(downloadUrl)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun uploadPostImage(userId: String, imageUri: Uri): Result<String> {
        return try {
            val ref = storage.reference
                .child("post_images/$userId/${UUID.randomUUID()}.jpg")

            ref.putFile(imageUri).await()
            val downloadUrl = ref.downloadUrl.await().toString()
            Result.success(downloadUrl)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Post Operations
    suspend fun createPost(post: Post): Result<String> {
        return try {
            val docRef = firestore.collection("posts").document()
            val postWithId = post.copy(postId = docRef.id)

            docRef.set(postWithId).await()

            // Update user posts count
            getCurrentUserId()?.let { userId ->
                firestore.collection("users")
                    .document(userId)
                    .update("postsCount", com.google.firebase.firestore.FieldValue.increment(1))
                    .await()
            }

            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUserPosts(userId: String): Result<List<Post>> {
        return try {
            val snapshot = firestore.collection("posts")
                .whereEqualTo("userId", userId)
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get()
                .await()

            val posts = snapshot.toObjects(Post::class.java)
            Result.success(posts)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getAllPosts(): Result<List<Post>> {
        return try {
            val snapshot = firestore.collection("posts")
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .limit(50)
                .get()
                .await()

            val posts = snapshot.toObjects(Post::class.java)
            Result.success(posts)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deletePost(postId: String): Result<Unit> {
        return try {
            // Get post data first
            val postSnapshot = firestore.collection("posts")
                .document(postId)
                .get()
                .await()

            val post = postSnapshot.toObject(Post::class.java)

            // Delete post
            firestore.collection("posts")
                .document(postId)
                .delete()
                .await()

            // Update user posts count
            post?.userId?.let { userId ->
                firestore.collection("users")
                    .document(userId)
                    .update("postsCount", com.google.firebase.firestore.FieldValue.increment(-1))
                    .await()
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}