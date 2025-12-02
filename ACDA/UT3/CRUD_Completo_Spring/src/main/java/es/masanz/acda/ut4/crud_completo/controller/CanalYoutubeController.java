package es.masanz.acda.ut4.crud_completo.controller;

import es.masanz.acda.ut4.crud_completo.db.model.CanalYoutube;
import es.masanz.acda.ut4.crud_completo.db.model.Usuario;
import es.masanz.acda.ut4.crud_completo.db.service.CanalYoutubeService;
import es.masanz.acda.ut4.crud_completo.db.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/canales")
public class CanalYoutubeController {

    @Autowired
    private CanalYoutubeService canalYoutubeService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping({"", "/"})
    public String listadoCanales(Model model) {
        model.addAttribute("canales", canalYoutubeService.findAll());
        return "canales/listado_canales";
    }

    @GetMapping("/crear")
    public String crearCanal(Model model) {
        model.addAttribute("canal", new CanalYoutube());
        model.addAttribute("usuarios", usuarioService.findAll());
        return "canales/formulario_canales";
    }

    @PostMapping("/crear")
    public String crearCanal(@ModelAttribute CanalYoutube canal,
                             @RequestParam("usuarioId") Integer usuarioId,
                             Model model) {

        Optional<Usuario> usuarioOpt = usuarioService.findById(usuarioId);
        if (usuarioOpt.isEmpty()) {
            model.addAttribute("canal", canal);
            model.addAttribute("usuarios", usuarioService.findUsuariosSinCanal());
            model.addAttribute("error", "Usuario no encontrado");
            return "canales/formulario_canales";
        }

        Usuario usuario = usuarioOpt.get();

        if (usuario.getCanalYoutube() != null) {
            model.addAttribute("canal", canal);
            model.addAttribute("usuarios", usuarioService.findUsuariosSinCanal());
            model.addAttribute("error", "Ese usuario ya tiene un canal asignado");
            return "canales/formulario_canales";
        }

        canal.setUsuario(usuario);
        usuario.setCanalYoutube(canal);

        try {
            canalYoutubeService.save(canal);
        } catch (IllegalArgumentException e) {
            model.addAttribute("canal", canal);
            model.addAttribute("usuarios", usuarioService.findUsuariosSinCanal());
            model.addAttribute("error", e.getMessage());
            return "canales/formulario_canales";
        }

        return "redirect:/canales";
    }

    @GetMapping("/eliminar/{canalId}")
    public String eliminarCanal(@PathVariable(name = "canalId") Integer canalId) {
        canalYoutubeService.deleteById(canalId);
        return "redirect:/canales";
    }

    @GetMapping("/actualizar/{canalId}")
    public String actualizarCanal(@PathVariable(name = "canalId") Integer canalId, Model model) {
        Optional<CanalYoutube> canal = canalYoutubeService.findById(canalId);
        if (canal.isEmpty()) {
            return "redirect:/canales";
        }

        model.addAttribute("canal", canal.get());
        model.addAttribute("usuarios", usuarioService.findAll());
        return "canales/formulario_canales";
    }
}
