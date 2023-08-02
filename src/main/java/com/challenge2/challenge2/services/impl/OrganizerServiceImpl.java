package com.challenge2.challenge2.services.impl;
import com.challenge2.challenge2.entities.Organizer;
import com.challenge2.challenge2.enums.OrganizerEnums;
import com.challenge2.challenge2.exceptions.NotFoundException;
import com.challenge2.challenge2.repositories.OrganizerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrganizerServiceImpl implements OrganizerService{

    private final OrganizerRepository organizerRepository;

    public OrganizerServiceImpl(OrganizerRepository organizerRepository) {

        this.organizerRepository = organizerRepository;
    }

    @Override
    public List<Organizer> getAllOrganizers() throws NotFoundException{
        return organizerRepository.findAll();
    }

    @Override
    public Optional<Organizer> getOrganizerById(Long id) throws NotFoundException{
        return organizerRepository.findById(id);

    }

    @Override
    public Organizer saveOrganizer(Organizer organizer) {
        if (!isValidRole(organizer.getRole())) {
            throw new NotFoundException("O campo 'role' é inválido! O 'role' deve ser: ScrumMaster, Instructor, Cordinator");
        }
        return organizerRepository.save(organizer);
    }

    public boolean isValidRole(OrganizerEnums role) {
        return role == OrganizerEnums.ScrumMaster || role == OrganizerEnums.Instructor || role == OrganizerEnums.Coordinator;
    }


    @Override
    public void deleteOrganizer(Long id) {

        organizerRepository.deleteById(id);
    }
}