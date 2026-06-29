package com.contactmanager.service;

import com.contactmanager.model.Contact;
import com.contactmanager.model.User;
import com.contactmanager.repository.ContactRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    // ─── Get all contacts of a user ───────────────────────────────
    public List<Contact> getAllContacts(Long userId) {
        return contactRepository.findByUserId(userId);
    }

    // ─── Search contacts ──────────────────────────────────────────
    public List<Contact> searchContacts(Long userId, String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return getAllContacts(userId);
        }
        return contactRepository.searchContacts(userId, keyword);
    }

    // ─── Get single contact (secure: checks user ownership) ───────
    public Contact getContactById(Long contactId, Long userId) {
        return contactRepository.findByIdAndUserId(contactId, userId)
            .orElseThrow(() -> new RuntimeException("Contact not found"));
    }

    // ─── Save new contact ─────────────────────────────────────────
    public void saveContact(Contact contact, User user) {
        contact.setUser(user);
        contactRepository.save(contact);
    }

    // ─── Update contact ───────────────────────────────────────────
    public void updateContact(Contact updatedContact, Long userId) {
        Contact existing = getContactById(updatedContact.getId(), userId);
        existing.setName(updatedContact.getName());
        existing.setEmail(updatedContact.getEmail());
        existing.setPhone(updatedContact.getPhone());
        existing.setAddress(updatedContact.getAddress());
        existing.setCategory(updatedContact.getCategory());
        contactRepository.save(existing);
    }

    // ─── Delete contact ───────────────────────────────────────────
    public void deleteContact(Long contactId, Long userId) {
        Contact contact = getContactById(contactId, userId);
        contactRepository.delete(contact);
    }

    // ─── Get contacts by category ─────────────────────────────────
    public List<Contact> getByCategory(Long userId, String category) {
        return contactRepository.findByUserIdAndCategory(userId, category);
    }

    // ─── Count contacts ───────────────────────────────────────────
    public long countContacts(Long userId) {
        return contactRepository.findByUserId(userId).size();
    }
}
