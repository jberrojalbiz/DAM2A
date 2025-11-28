package es.masanz.acda.ut4.crud_completo.controller;

import es.masanz.acda.ut4.crud_completo.db.model.Persona;
import es.masanz.acda.ut4.crud_completo.db.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/personas")
public class PersonaController {

    @Autowired
    private PersonaService personaService;

    @GetMapping({"", "/"})
    public String listadoPersonas(/*@RequestParam(name="nombre", required = false, defaultValue = "World") String nombre, */Model model) {
//        model.addAttribute("nombre", nombre);
//        personaService.save(new Persona("Aitor",30));
        model.addAttribute("personas", personaService.findAll());
        return "personas/listado_personas";
    }

    @GetMapping("/crear")
    public String crearPersona(Model model) {
        model.addAttribute("persona", new Persona());
        return "personas/formulario_personas";
    }

    @PostMapping("/crear")
    public String crearPersona(@ModelAttribute Persona persona) {
        personaService.save(persona);
        return "redirect:/personas";
    }

    @GetMapping("/eliminar/{personaId}")
    public String eliminarPersona(@PathVariable(name = "personaId") Integer personaId) {
        personaService.deleteById(personaId);
        return "redirect:/personas";
    }

    @GetMapping("/actualizar/{personaId}")
    public String actualizarPersona(@PathVariable(name = "personaId") Integer personaId, Model model) {
        Optional<Persona> persona = personaService.findById(personaId);
        if (persona.isEmpty()) {
            return "redirect:/personas";
        }
        model.addAttribute("persona", persona.get());
        return "personas/formulario_personas";
    }
}
