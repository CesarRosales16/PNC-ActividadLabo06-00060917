package com.uca.capas.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.uca.capas.dao.EstudianteDAO;
import com.uca.capas.domain.Estudiante;
import com.uca.capas.service.EstudianteService;

@Controller
public class MainController {

	@Autowired
	private EstudianteService estudianteService;

	@RequestMapping("/inicio")
	public ModelAndView inicio() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("estudiante", new Estudiante());
		mav.setViewName("index");

		return mav;
	}

	@RequestMapping("/insertar")
	public ModelAndView formEstudiante(@Valid @ModelAttribute Estudiante estudiante, BindingResult result) {
		ModelAndView mav = new ModelAndView();

		if (!(result.hasErrors())) {

			try {
				estudianteService.insert(estudiante);

			} catch (Exception e) {
				e.printStackTrace();
			}

			mav.addObject("estudiante", new Estudiante());
		}
		mav.setViewName("index");

		return mav;
	}

	@RequestMapping("/listado")
	public ModelAndView listado() {
		ModelAndView mav = new ModelAndView();
		List<Estudiante> estudiantes = null;

		try {
			estudiantes = estudianteService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}

		mav.addObject("estudiantes", estudiantes);
		mav.setViewName("listado");

		return mav;
	}

	@RequestMapping(value = "/eliminar", method = RequestMethod.POST)
	public ModelAndView eliminarEstudiante(@RequestParam(value = "codigo") int codigo) {
		ModelAndView mav = new ModelAndView();
		List<Estudiante> estudiantes = null;
		try {

			estudianteService.delete(codigo);
			estudiantes = estudianteService.findAll();

		} catch (Exception e) {
			e.printStackTrace();
		}

		mav.addObject("estudiantes", estudiantes);
		mav.setViewName("listado");

		return mav;

	}
}