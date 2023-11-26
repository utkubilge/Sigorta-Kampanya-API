package com.utkubilge.kampanya.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.utkubilge.kampanya.model.Kampanya;
import com.utkubilge.kampanya.repo.KampanyaRepo;
import org.springframework.ui.Model;

@RestController
public class KampanyaController {

	@Autowired
	private KampanyaRepo kampanyaRepo;

	private final Logger logger = LoggerFactory.getLogger(KampanyaController.class);

	@GetMapping("/")
	public ModelAndView index() {
		logger.debug("request to GET index");
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("kampanyalarItems", kampanyaRepo.findAll());
		return modelAndView;
	}

	// API endpoints ---
	// Kampanya Aktivasyon
	@PutMapping("/api/kampanyaOnay/{id}")
	public ResponseEntity<Kampanya> kampanyaOnay(@PathVariable Long id) {
		Optional<Kampanya> oldKampanya = kampanyaRepo.findById(id);

		if (oldKampanya.isPresent()) {
			Kampanya updatedKampanya = oldKampanya.get();
			updatedKampanya.setStatus(1);
			Kampanya response = kampanyaRepo.save(updatedKampanya);

			return ResponseEntity.ok(response);
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/api/statistics")
	public ResponseEntity<String> getStatistics() {
		Long deactiveCount = kampanyaRepo.countByStatus(2);
		Long pendingCount = kampanyaRepo.countByStatus(3);
		Long activeCount = kampanyaRepo.countByStatus(1);

		return ResponseEntity
				.ok("Aktif:" + activeCount + ", Deaktif:" + deactiveCount + ", Onay Bekliyor:" + pendingCount);
	}

	// Optionals ---
	@GetMapping("/api/getAllKampanyalar")
	public ResponseEntity<List<Kampanya>> getAllKampanyalar() {
		try {
			List<Kampanya> kampanyaList = new ArrayList<>();
			kampanyaRepo.findAll().forEach(kampanyaList::add);

			if (kampanyaList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);

			}
			return new ResponseEntity<>(kampanyaList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/api/getKampanyaById/{id}")
	public ResponseEntity<Kampanya> getKampanyaById(@PathVariable Long id) {
		Optional<Kampanya> kampanyaData = kampanyaRepo.findById(id);
		if (kampanyaData.isPresent()) {
			return new ResponseEntity<>(kampanyaData.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/api/addKampanya")
	public ResponseEntity<Kampanya> addKampanya(@RequestBody Kampanya kampanya) {
		Kampanya kampanyaObj = kampanyaRepo.save(kampanya);
		return new ResponseEntity<>(kampanyaObj, HttpStatus.OK);

	}

	// update the state only
	@PostMapping("/api/updateKampanyaById/{id}")
	public ResponseEntity<Kampanya> updateKampanyaById(@PathVariable Long id, @RequestBody Kampanya newKampanyaData) {
		Optional<Kampanya> oldKampanyaData = kampanyaRepo.findById(id);

		if (oldKampanyaData.isPresent()) {
			Kampanya updatedKampanyaData = oldKampanyaData.get();
			// updatedKampanyaData.setStatus(newKampanyaData.getStatus());

			Kampanya kampanyaObj = kampanyaRepo.save(updatedKampanyaData);
			return new ResponseEntity<>(kampanyaObj, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/api/deleteKampanyaById/{id}")
	public ResponseEntity<HttpStatus> deleteKampanyaById(@PathVariable Long id) {
		kampanyaRepo.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/api/delete/{id}")
    public ResponseEntity<HttpStatus> deleteTodoItem(@PathVariable("id") long id, Model model) {
        Kampanya kampanyaItem = kampanyaRepo
        .findById(id)
        .orElseThrow(() -> new IllegalArgumentException("TodoItem id: " + id + " not found"));
    
        kampanyaRepo.delete(kampanyaItem);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
