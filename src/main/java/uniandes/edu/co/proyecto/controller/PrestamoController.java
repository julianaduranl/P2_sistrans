package uniandes.edu.co.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import uniandes.edu.co.proyecto.modelo.Prestamo;
import uniandes.edu.co.proyecto.repositorio.PrestamoRepository;
import uniandes.edu.co.proyecto.repositorio.ClienteRepository;

import java.util.List;

@Controller
public class PrestamoController {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/prestamos")
    public String listarPrestamos(Model model) {
        List<Prestamo> prestamos = prestamoRepository.findAll();
        model.addAttribute("prestamos", prestamos);
        return "prestamos";
    }

    @GetMapping("/prestamos/new")
    public String formularioNuevoPrestamo(Model model) {
        model.addAttribute("prestamo", new Prestamo());
        model.addAttribute("clientes", clienteRepository.findAll());
        return "prestamoNuevo";
    }

    @PostMapping("/prestamos/new/save")
    public String guardarPrestamo(@ModelAttribute Prestamo prestamo) {
        prestamoRepository.save(prestamo);
        return "redirect:/prestamos";
    }

    @GetMapping("/prestamos/{id}/edit")
    public String formularioEditarPrestamo(@PathVariable("id") Integer id, Model model) {
        Prestamo prestamo = prestamoRepository.findById(id).orElse(null);
        if (prestamo != null) {
            model.addAttribute("prestamo", prestamo);
            model.addAttribute("clientes", clienteRepository.findAll());
            return "editar-prestamo";
        } else {
            return "redirect:/prestamos";
        }
    }

    @PostMapping("/prestamos/{id}/update")
    public String actualizarPrestamo(@PathVariable("id") Integer id, @ModelAttribute Prestamo prestamo) {
        prestamo.setId(id);
        prestamoRepository.save(prestamo);
        return "redirect:/prestamos";
    }

    @GetMapping("/prestamos/{id}/delete")
    public String eliminarPrestamo(@PathVariable("id") Integer id) {
        prestamoRepository.deleteById(id);
        return "redirect:/prestamos";
    }
}
