package com.famy.us.feature.note.createNote.navigation

import com.famy.us.core.utils.navigation.Destination

/**
 * Object representing all destination possibles in the Create Note flow.
 */
object CreateNoteNavigation {

    /**
     * The Destination to give a name screen.
     */
    object GiveName : Destination("give_name")

    /**
     * Destination to give a note description screen.
     */
    object GiveDescription: Destination("give_description")

    /**
     * Destination to give a score screen.
     */
    object GiveScore: Destination("give_score")
}
