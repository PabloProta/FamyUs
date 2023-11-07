package com.famy.us.feature.note.navigation

import com.famy.us.core.utils.navigation.Destination

/**
 * Object representing all destination possibles by the Note Menu screen.
 */
object NoteMenuNavigation {

    /**
     * Navigate to the Note content screen.
     *
     * @property noteId the note id that user are going to see its content.
     */
    data class NoteContent(val noteId: String = "{taskId}") : Destination("note_content", noteId)

    /**
     * Create task screen.
     */
    object CreateTask : Destination("create_task")

    /**
     * Object representing the note menu destination.
     */
    object NoteMenu: Destination("menus/note")
}
