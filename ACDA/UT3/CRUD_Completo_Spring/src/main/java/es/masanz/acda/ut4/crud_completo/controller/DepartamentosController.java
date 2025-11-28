package es.masanz.acda.ut4.crud_completo.controller;

import es.masanz.acda.ut4.crud_completo.db.model.Departamentos;
import es.masanz.acda.ut4.crud_completo.db.model.Persona;
import es.masanz.acda.ut4.crud_completo.db.service.DepartamentosService;
import es.masanz.acda.ut4.crud_completo.db.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/departamentos")
public class DepartamentosController {
    @Autowired
    private DepartamentosService departamentosService;

    @GetMapping({"", "/"})
    public String listadoDepartamentos(/*@RequestParam(name="nombre", required = false, defaultValue = "World") String nombre, */Model model) {
//        model.addAttribute("nombre", nombre);
//        personaService.save(new Persona("Aitor",30));
        model.addAttribute("departamentos", departamentosService.findAll());
        return "departamentos/listado_departamentos";
    }

    @GetMapping("/crear")
    public String crearDepartamentos(Model model) {
        model.addAttribute("departamentos", new Departamentos());
        return "departamentos/formulario_departamentos";
    }

    @PostMapping("/crear")
    public String crearDepartamentos(@ModelAttribute Departamentos departamentos) {
        departamentosService.save(departamentos);
        return "redirect:/departamentos";
    }

    @GetMapping("/eliminar/{departamentosId}")
    public String eliminarDepartamentos(@PathVariable(name = "departamentosId") Integer departamentosId) {
        departamentosService.deleteById(departamentosId);
        return "redirect:/departamentos";
    }

    @GetMapping("/actualizar/{departamentosId}")
    public String actualizarDepartamentos(@PathVariable(name = "departamentosId") Integer departamentosId, Model model) {
        Optional<Departamentos> departamentos = departamentosService.findById(departamentosId);
        if (departamentos.isEmpty()) {
            return "redirect:/departamentos";
        }
        model.addAttribute("departamentos", departamentos.get());
        return "departamentos/formulario_departamentos";
    }
}
