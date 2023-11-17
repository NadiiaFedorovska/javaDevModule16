package com.example.javaDevModule16.repositories;

import com.example.javaDevModule16.entities.NoteEntity;

import java.util.List;

public interface INoteService {
    List<NoteEntity> listAll();

    NoteEntity add(NoteEntity noteEntity);

    void deleteById(long id);

    NoteEntity update(NoteEntity noteEntity);

    NoteEntity getById(long id);
}
