package io.github.jakmodz.backend.services;

import io.github.jakmodz.backend.dtos.NoteDto;
import io.github.jakmodz.backend.models.Note;
import io.github.jakmodz.backend.models.User;

import java.util.List;
import java.util.UUID;

public interface NoteService {
    Note  createNote(NoteDto note, User user);
    List<Note> getAllNotes(User user);
    Note getNoteById(UUID noteId, User user);
    Note updateNoteById(UUID noteId, NoteDto noteDto, User user);
    void deleteNoteById(UUID noteId, User user);
    default Note transformToEntity(NoteDto noteDto){
        Note  note = new Note();
        note.setTitle(noteDto.getTitle());
        note.setContent(noteDto.getContent());
        return note;
    }
    default NoteDto transformToDto(Note note){
        NoteDto noteDto = new NoteDto();
        noteDto.setTitle(note.getTitle());
        noteDto.setContent(note.getContent());
        noteDto.setUuid(note.getUuid());
        noteDto.setCreated(note.getModified());
        return noteDto;
    }

}
