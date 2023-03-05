package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/owners/{ownerId}")    // set part of the path for all methods in the class
public class PetController {
    private static final String VIEWS_PETS_CREATE_OR_UPDATE_FROM = "pets/createOrUpdatePetForm";    // createOrUpdatePetForm.html View Form path
    private final PetService petService;
    private final OwnerService ownerService;
    private final PetTypeService petTypeService;

    public PetController(PetService petService, OwnerService ownerService, PetTypeService petTypeService) { // initialize Services in Constructor to make them required and to make them auto-injected
        this.petService = petService;
        this.ownerService = ownerService;
        this.petTypeService = petTypeService;
    }

    @ModelAttribute("types") // automatically create Module with key "types"
    public Collection<PetType> populatePetTypes() {
        return petTypeService.findAll();    // add collection of PetTypes to Module by key "owner"
    }

    @ModelAttribute("owner") // automatically create Module with key "owner"
    public Owner findOwner(@PathVariable Long ownerId) {
        return ownerService.findById(ownerId);   // add collection of Owners to Module by key "owner"
    }

    @InitBinder("owner")
    public void initOwnerBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/pets/new")    // "/owners/{ownerId}/pets/new" - url to access this method, takes GET request method only
    public String initCreationForm(Owner owner, Model model) {  // takes Owner
        Pet pet = new Pet();    // create Pet
        owner.getPets().add(pet);   // map Pet to the Owner
        pet.setOwner(owner);    // map Owner to Pet - both mapping required to be created in DB
        model.addAttribute("pet", pet); // add mapped Pet to Module
        return VIEWS_PETS_CREATE_OR_UPDATE_FROM;    // pass Modules with Owner, PetTypes and Pet to create/update View Form
    }

    @PostMapping("/pets/new")   // "/owners/{ownerId}/pets/new" - url to access this method, takes POST request method only
    public String processCreationForm(Owner owner, @Valid Pet pet, BindingResult result, ModelMap model) { // takes Owner, BindingResult, Validate Pet
        if (StringUtils.hasLength(pet.getName()) && pet.isNew() && owner.getPet(pet.getName(), true) != null) {
            result.rejectValue("name", "duplicate", "already exists");  // if Pet or Owner is not valid, return back to View Form with the help of BindingResult
        }
        owner.getPets().add(pet);   // add Pet to Owner Set
        if (result.hasErrors()) {
            model.put("pet", pet);
            return VIEWS_PETS_CREATE_OR_UPDATE_FROM;
        } else {
            petService.save(pet);   // save Pet to DB

            return "redirect:/owners/" + owner.getId(); // redirect to View Form that displays List of Owners
        }
    }

    @GetMapping("/pets/{petId}/edit")   // "/owners/{ownerId}/pets/{petId}/edit" - url to access this method, takes GET request method only
    public String initUpdateForm(@PathVariable Long petId, Model model) {   // takes petId from url
        model.addAttribute("pet", petService.findById(petId));  // finds Pet by petId and add it to the Model
        return VIEWS_PETS_CREATE_OR_UPDATE_FROM;    // pass Modules with Owner, PetTypes and Pet to create/update View Form
    }

    @PostMapping("/pets/{petId}/edit")  // "/owners/{ownerId}/pets/{petId}/edit" - url to access this method, takes POST request method only
    public String processUpdateForm(@Valid Pet pet, BindingResult result, Owner owner, Model model) {   // takes BindingResult, Owner, validate Pet
        if (result.hasErrors()) {
            pet.setOwner(owner);
            model.addAttribute("pet", pet);
            return VIEWS_PETS_CREATE_OR_UPDATE_FROM;
        } else {
            owner.getPets().add(pet);   // map Pet to Owner
            petService.save(pet);   // map Owner to Pet
            return "redirect:/owners/" + owner.getId(); // redirect to display information about Owner by ownerId
        }
    }
}
