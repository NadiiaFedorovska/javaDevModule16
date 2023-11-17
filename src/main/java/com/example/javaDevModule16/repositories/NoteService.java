package com.example.javaDevModule16.repositories;

import com.example.javaDevModule16.entities.NoteEntity;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.javaDevModule16.repositories.NoteNotFoundException.*;

@Service
public class NoteService implements INoteService {
    private static final Logger LOGGER = Logger.getLogger(NoteService.class);
    private final List<NoteEntity> noteEntityList = new ArrayList<>();
    long iteratorById;

    public NoteEntity add(NoteEntity noteEntity) {
        long id = generateUniqueId();
        noteEntity.setId(id);
        noteEntityList.add(noteEntity);
        LOGGER.info("add NoteEntity " + noteEntity + "to note Entity List");
        return noteEntity;
    }


    private long generateUniqueId() {
        Random random = new Random();
        long id = random.nextInt(100);
        LOGGER.info("generate Unique Id " + id);
        return id;
    }

    public List<NoteEntity> listAll() {
        LOGGER.info("return note Entity List");
        return new ArrayList<>(noteEntityList);
    }

    public void resetIteratorId() {
        iteratorById = 0;
    }

    public void validateId(long id) {
        boolean found = false;
        resetIteratorId();
        for (NoteEntity note : noteEntityList) {
            iteratorById++;
            if (note.getId() == id) {
                found = true;
                break;
            }
        }
        if (!found) {
            LOGGER.error("Note not found with id " + id);
            throw new NoteNotFoundException(ERROR_MESSAGE + id);
        }
        LOGGER.info("id was validated");
    }

    @Override
    public NoteEntity getById(long id) {
        validateId(id);
        NoteEntity getNote = noteEntityList.get((int) iteratorById - 1);
        LOGGER.info("Note by id " + id + "is " + getNote);
        return getNote;
    }

    @Override
    public NoteEntity update(NoteEntity noteEntity) {
        int id = (int) noteEntity.getId();
        NoteEntity foundNote = getById(id);
        foundNote.setTitle(noteEntity.getTitle());
        foundNote.setContent(noteEntity.getContent());
        LOGGER.info("Note was updated " + foundNote);
        return foundNote;
    }

    @Override
    public void deleteById(long id) {
        validateId(id);
        noteEntityList.remove((int) iteratorById - 1);
        LOGGER.info("Note was deleted");
    }
}