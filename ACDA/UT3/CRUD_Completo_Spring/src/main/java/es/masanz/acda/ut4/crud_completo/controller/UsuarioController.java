package es.masanz.acda.ut4.crud_completo.controller;

import org.springframework.ui.Model;
import es.masanz.acda.ut4.crud_completo.db.model.Usuario;
import es.masanz.acda.ut4.crud_completo.db.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping({"", "/"})
    public String listadoUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.findAll());
        return "usuarios/listado_usuarios";
    }

    @GetMapping("/crear")
    public String crearUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuarios/formulario_usuarios";
    }

    @PostMapping("/crear")
    public String crearUsuario(@ModelAttribute Usuario usuario, Model model) {
        try {
            usuarioService.save(usuario);
        } catch (IllegalArgumentException e) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("error", e.getMessage());
            return "usuarios/formulario_usuarios";
        }
        return "redirect:/usuarios";
    }

    @GetMapping("/eliminar/{usuarioId}")
    public String eliminarUsuario(@PathVariable(name = "usuarioId") Integer usuarioId) {
        usuarioService.deleteById(usuarioId);
        return "redirect:/usuarios";
    }

    @GetMapping("/actualizar/{usuarioId}")
    public String actualizarUsuario(@PathVariable(name = "usuarioId") Integer usuarioId, Model model) {
        Optional<Usuario> usuario = usuarioService.findById(usuarioId);
        if (usuario.isEmpty()) {
            return "redirect:/usuarios";
        }
        model.addAttribute("usuario", usuario.get());
        return "usuarios/formulario_usuarios";
    }
}
