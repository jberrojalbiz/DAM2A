package es.masanz.acda.ut4.crud_completo.controller;

import es.masanz.acda.ut4.crud_completo.db.model.Empleados;
import es.masanz.acda.ut4.crud_completo.db.model.Persona;
import es.masanz.acda.ut4.crud_completo.db.service.EmpleadosService;
import es.masanz.acda.ut4.crud_completo.db.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/empleados")
public class EmpleadosController {
    @Autowired
    private EmpleadosService empleadosService;

    @GetMapping({"", "/"})
    public String listadoEmpleados(/*@RequestParam(name="nombre", required = false, defaultValue = "World") String nombre, */Model model) {
//        model.addAttribute("nombre", nombre);
//        personaService.save(new Persona("Aitor",30));
        model.addAttribute("empleados", empleadosService.findAll());
        return "empleados/listado_empleados";
    }

    @GetMapping("/crear")
    public String crearEmpleados(Model model) {
        model.addAttribute("empleados", new Empleados());
        return "empleados/formulario_empleados";
    }

    @PostMapping("/crear")
    public String crearEmpleados(@ModelAttribute Empleados empleados) {
        empleadosService.save(empleados);
        return "redirect:/empleados";
    }

    @GetMapping("/eliminar/{idE}")
    public String eliminarEmpleados(@PathVariable(name = "idE") Integer empleadosId) {
        empleadosService.deleteById(empleadosId);
        return "redirect:/empleados";
    }

    @GetMapping("/actualizar/{idE}")
    public String actualizarEmpleados(@PathVariable(name = "idE") Integer empleadosId, Model model) {
        Optional<Empleados> empleados = empleadosService.findById(empleadosId);
        if (empleados.isEmpty()) {
            return "redirect:/empleados";
        }
        model.addAttribute("empleados", empleados.get());
        return "empleados/formulario_empleados";
    }
}
