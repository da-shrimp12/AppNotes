package com.anhld.appnotes.listeners;

import com.anhld.appnotes.entities.Note;

public interface NotesListener {
    void onNoteClicked(Note note, int position);
}
