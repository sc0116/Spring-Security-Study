package com.example.club.service;

import com.example.club.dto.NoteDTO;
import com.example.club.entity.Note;
import com.example.club.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService{

    private final NoteRepository noteRepository;

    @Override
    public Long register(NoteDTO noteDTO) {

        Note note = dtoToEntity(noteDTO);

        log.info("====================");
        log.info(note);

        noteRepository.save(note);

        return note.getNum();
    }

    @Override
    public NoteDTO get(Long num) {

        Optional<Note> result = noteRepository.findById(num);

        if (result.isPresent()) {
            return entityToDTO(result.get());
        }

        return null;
    }

    @Override
    public void modify(NoteDTO noteDTO) {

        Optional<Note> result = noteRepository.findById(noteDTO.getNum());

        if (result.isPresent()) {

            Note note = result.get();

            note.changeTitle(noteDTO.getTitle());
            note.changeContent(noteDTO.getContent());

            noteRepository.save(note);
        }
    }

    @Override
    public void remove(Long num) {

        noteRepository.deleteById(num);
    }

    @Override
    public List<NoteDTO> getAllWriter(String writerEmail) {

        List<Note> noteList = noteRepository.getList(writerEmail);

        return noteList.stream().map(note -> entityToDTO(note)).collect(Collectors.toList());
    }
}
