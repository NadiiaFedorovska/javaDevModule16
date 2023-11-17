package com.example.javaDevModule16.controllers;

import com.example.javaDevModule16.entities.NoteEntity;
import com.example.javaDevModule16.repositories.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/note")
public class NoteController {
    private final NoteService noteService;
    private List<NoteEntity> notes;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public String startHello() {
        return "hello";
    }

    @PostMapping
    public String startToCreate() {
        return "redirect:/note/create";
    }

    @PostMapping("/create")
    public String createNote(@ModelAttribute NoteEntity newNote) {
        noteService.add(newNote);
        return "redirect:/note/list";
    }

    @GetMapping("/create")
    public String createNotePage(Model model) {
        model.addAttribute("notes", new NoteEntity());
        return "newNote";
    }

    @GetMapping("/list")
    public String getNoteList(Model model) {
        notes = noteService.listAll();
        model.addAttribute("notes", notes);
        return "noteList";
    }

    @PostMapping("/search")
    public String getNoteForId(@RequestParam(name = "id") long id, Model model) {
        NoteEntity note = noteService.getById(id);
        model.addAttribute("note", note);
        return "noteSearch";
    }

    @GetMapping("/update")
    public String updateId(@RequestParam long id, Model model) {
        NoteEntity searchNote = noteService.getById(id);
        model.addAttribute("note", searchNote);
        return "noteUpdate";
    }

    @PostMapping("/update")
    public String updateNote(@ModelAttribute NoteEntity noteEntity) {
        noteService.update(noteEntity);
        return "redirect:/note/list";
    }

    @PostMapping("/delete")
    public String deleteNote(@RequestParam(name = "id") long id) {
        noteService.deleteById(id);
        return "redirect:/note/list";
    }
}