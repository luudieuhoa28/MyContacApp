package org.luuhoa.mycontact.service;

import org.luuhoa.mycontact.entity.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactService {

    Iterable<Contact> findAll();

    List<Contact> search(String term);

    Optional<Contact> findOne(Integer id);

    void save(Contact contact);

    void delete(Integer id);

}