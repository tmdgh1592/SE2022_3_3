package com.se.hanger.view.login

import com.se.hanger.data.model.Member
import com.se.hanger.view.base.BaseViewModel

class RegisterViewModel : BaseViewModel() {

    suspend fun registerMember(member: Member) {

    }

    suspend fun getMember(uid: String): Member? {
        return null
    }

    suspend fun updatePassword(uid: String, password: String) {

    }

    suspend fun updateEmail(uid: String, email: String) {

    }

    suspend fun updateUsername(uid: String, newUserName: String) {

    }
}