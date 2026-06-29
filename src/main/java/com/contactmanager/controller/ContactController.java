package com.contactmanager.controller;

import com.contactmanager.model.Contact;
import com.contactmanager.model.User;
import com.contactmanager.service.ContactService;
import com.contactmanager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService contactService;
    private final UserService userService;

    public ContactController(ContactService contactService, UserService userService) {
        this.contactService = contactService;
        this.userService = userService;
    }

    // ─── Helper: Get logged-in user ───────────────────────────────
    private User getLoggedInUser(Authentication auth) {
        return userService.getUserByEmail(auth.getName());
    }

    // ─── List All Contacts ────────────────────────────────────────
    @GetMapping
    public String listContacts(@RequestParam(required = false) String keyword,
                               Authentication auth,
                               Model model) {
        User user = getLoggedInUser(auth);
        var contacts = contactService.searchContacts(user.getId(), keyword);

        model.addAttribute("contacts", contacts);
        model.addAttribute("keyword", keyword);
        model.addAttribute("user", user);
        model.addAttribute("totalContacts", contactService.countContacts(user.getId()));
        return "contacts";
    }

    // ─── Show Add Contact Form ────────────────────────────────────
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("contact", new Contact());
        model.addAttribute("pageTitle", "Add Contact");
        return "contact-form";
    }

    // ─── Save New Contact ─────────────────────────────────────────
    @PostMapping("/add")
    public String saveContact(@Valid @ModelAttribute("contact") Contact contact,
                              BindingResult result,
                              Authentication auth,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Add Contact");
            return "contact-form";
        }
        User user = getLoggedInUser(auth);
        contactService.saveContact(contact, user);
        redirectAttributes.addFlashAttribute("successMsg", "Contact added successfully!");
        return "redirect:/contacts";
    }

    // ─── Show Edit Contact Form ───────────────────────────────────
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Authentication auth, Model model) {
        User user = getLoggedInUser(auth);
        Contact contact = contactService.getContactById(id, user.getId());
        model.addAttribute("contact", contact);
        model.addAttribute("pageTitle", "Edit Contact");
        return "contact-form";
    }

    // ─── Update Contact ───────────────────────────────────────────
    @PostMapping("/edit/{id}")
    public String updateContact(@PathVariable Long id,
                                @Valid @ModelAttribute("contact") Contact contact,
                                BindingResult result,
                                Authentication auth,
                                RedirectAttributes redirectAttributes,
                                Model model) {
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Edit Contact");
            return "contact-form";
        }
        contact.setId(id);
        User user = getLoggedInUser(auth);
        contactService.updateContact(contact, user.getId());
        redirectAttributes.addFlashAttribute("successMsg", "Contact updated successfully!");
        return "redirect:/contacts";
    }

    // ─── Delete Contact ───────────────────────────────────────────
    @GetMapping("/delete/{id}")
    public String deleteContact(@PathVariable Long id,
                                Authentication auth,
                                RedirectAttributes redirectAttributes) {
        User user = getLoggedInUser(auth);
        contactService.deleteContact(id, user.getId());
        redirectAttributes.addFlashAttribute("successMsg", "Contact deleted.");
        return "redirect:/contacts";
    }

    // ─── View Contact Detail ──────────────────────────────────────
    @GetMapping("/view/{id}")
    public String viewContact(@PathVariable Long id, Authentication auth, Model model) {
        User user = getLoggedInUser(auth);
        Contact contact = contactService.getContactById(id, user.getId());
        model.addAttribute("contact", contact);
        return "contact-detail";
    }
}
