package com.genzen.zenspire.data.firestore

import com.google.firebase.firestore.FirebaseFirestore

object FirestoreClient {
    val firestoreInstance: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }
}