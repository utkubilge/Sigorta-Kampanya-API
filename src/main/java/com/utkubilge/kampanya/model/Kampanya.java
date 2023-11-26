package com.utkubilge.kampanya.model;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "Kampanyalar")
public class Kampanya {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Size(min = 10, max = 50, message = "Length between 10-50 chars")
	private String name;
	@Size(min = 20, max = 200, message = "Length between 20-200 chars")
	private String desc;
	//type: TSS:1, ÖSS:2, HSS:3, Diğer:4
	private int type;
	//status: Default:0, Aktif:1, Deaktif:2, Onay Bekliyor:3, Mükerrer:4
	private int status;
	
	private Instant createdDate;
	private Instant modifiedDate;
}
