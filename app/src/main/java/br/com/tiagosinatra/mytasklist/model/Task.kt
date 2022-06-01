package br.com.tiagosinatra.mytasklist.model

import android.os.Parcelable
import br.com.tiagosinatra.mytasklist.helper.FirebaseHelper
import kotlinx.parcelize.Parcelize

@Parcelize
class Task (
    var id: String = "",
    var description: String = "",
    var status: Int = 0
) : Parcelable {
    init {
        this.id = FirebaseHelper.getDatabase().push().key ?: ""
    }
}