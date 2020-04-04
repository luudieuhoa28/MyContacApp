package org.luuhoa.mycontact.controller;

import org.luuhoa.mycontact.entity.Contact;
import org.luuhoa.mycontact.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@Controller
@RequestMapping("/api")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping("/contact")
    public ResponseEntity<List<Contact>> getAllContact() {
        try {
            List<Contact> contacts = new ArrayList<>();
            contactService.findAll().forEach(contacts::add);
            if (contacts.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(contacts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/contact/{name}")
    public ResponseEntity<List<Contact>> search(@PathVariable("name") String name) {
        try {
            if (name == null) {
                List<Contact> contacts = new ArrayList<>();
                contactService.findAll().forEach(contacts::add);
                return new ResponseEntity<>(contacts, HttpStatus.OK);
            } else {
                List<Contact> contacts = contactService.search(name);
                if (contacts.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
                return new ResponseEntity<>(contacts, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @PostMapping("/contact")
    public ResponseEntity add(@RequestBody Contact contact) {
        try {
            contactService.save(contact);
            return new ResponseEntity(contact, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/contact/{id}")
    public ResponseEntity update(@PathVariable("id") Integer id, @RequestBody Contact contact) {
        try {
            Optional<Contact> tmpContact = contactService.findOne(id);
            if (tmpContact.isPresent()) {
                tmpContact.get().setName(contact.getName());
                tmpContact.get().setEmail(contact.getEmail());
                tmpContact.get().setPhone(contact.getPhone());
                contactService.save(tmpContact.get());
                return new ResponseEntity(tmpContact.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/contact/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        try {
            Optional<Contact> tmpContact = contactService.findOne(id);
            if (tmpContact.isPresent()) {
                contactService.delete(id);
                return new ResponseEntity(tmpContact, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
