package com.contactmanager.repository;

import com.contactmanager.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    // Get all contacts for a specific user
    List<Contact> findByUserId(Long userId);

    // Search contacts by name or phone for a specific user
    @Query("SELECT c FROM Contact c WHERE c.user.id = :userId AND " +
           "(LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           " c.phone LIKE CONCAT('%', :keyword, '%'))")
    List<Contact> searchContacts(@Param("userId") Long userId,
                                 @Param("keyword") String keyword);

    // Find contact by id and user id (prevent cross-user access)
    Optional<Contact> findByIdAndUserId(Long id, Long userId);

    // Get contacts by category for a user
    List<Contact> findByUserIdAndCategory(Long userId, String category);
}
