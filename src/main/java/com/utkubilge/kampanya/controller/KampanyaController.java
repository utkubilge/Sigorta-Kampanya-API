package com.utkubilge.kampanya.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import com.utkubilge.kampanya.model.Kampanya;
import com.utkubilge.kampanya.repo.KampanyaRepo;

@RestController
public class KampanyaController {

	@Autowired
	private KampanyaRepo kampanyaRepo;

	// API endpoints ---

	// Kampanya Aktivasyon
	@PutMapping("/api/kampanyaOnay/{id}")
	public ResponseEntity<Kampanya> kampanyaOnay(@PathVariable Long id) {
		Optional<Kampanya> oldKampanya = kampanyaRepo.findById(id);

		if (oldKampanya.isPresent()) {
			Kampanya updatedKampanya = oldKampanya.get();
			updatedKampanya.setStatus("Aktif");
			Kampanya response = kampanyaRepo.save(updatedKampanya);

			return ResponseEntity.ok(response);
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/api/statistics")
    public ResponseEntity<String> getStatistics() {
        Long activeCount = kampanyaRepo.countByStatus("Aktif");
		Long deactiveCount = kampanyaRepo.countByStatus("Deaktif");
		Long pendingCount = kampanyaRepo.countByStatus("Onay Bekliyor");
		
        return ResponseEntity.ok("Aktif:" + activeCount + ",Deaktif:"  + deactiveCount + "Onay Bekliyor:" + pendingCount);
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

	@DeleteMapping("/api/deleteKampanyaById/{id}")
	public ResponseEntity<HttpStatus> deleteBookById(@PathVariable Long id) {
		kampanyaRepo.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
