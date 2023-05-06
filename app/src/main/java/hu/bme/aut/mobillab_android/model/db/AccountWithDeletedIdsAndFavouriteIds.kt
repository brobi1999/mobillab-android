package hu.bme.aut.mobillab_android.model.db

import androidx.room.Embedded
import androidx.room.Relation

data class AccountWithDeletedIdsAndFavouriteIds(
    @Embedded val account: Account,

    @Relation(
        parentColumn = "username",
        entityColumn = "containerUsername"
    )
    var favIds: List<FavouriteId>,

    @Relation(
        parentColumn = "username",
        entityColumn = "containerUsername"
    )
    var delIds: List<DeletedId>
)