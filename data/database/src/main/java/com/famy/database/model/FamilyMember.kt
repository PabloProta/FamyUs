package com.famy.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Model that represents the Family Member that will
 * make the tasks.
 *
 * @property id is the Identifier for the Member.
 * @property name is the name of the member.
 * @property tasks the task that was assigned by it.
 * @property score the total score sum all task assigned.
 * @property isAdmin if the family member is an admin.
 */
@Entity(tableName = "family_member")
data class FamilyMember(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "task") val tasks: List<HomeTask>,
    @ColumnInfo(name = "score") val score: Int,
    @ColumnInfo(name = "is_admin") val isAdmin: Boolean,
)
